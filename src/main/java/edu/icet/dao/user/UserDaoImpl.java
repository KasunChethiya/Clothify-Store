package edu.icet.dao.user;

import edu.icet.entity.UserEntity;
import edu.icet.model.User;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class UserDaoImpl implements UserDao {
    ModelMapper mapper = new ModelMapper();
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    public void persist(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(
                mapper.map(user, UserEntity.class)
        );

        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    public List<UserEntity> retriveAcount(String email) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<UserEntity> userAccount = entityManager.createQuery("FROM edu.icet.entity.UserEntity p WHERE p.email = :email", UserEntity.class)
                .setParameter("email", email)
                .getResultList();

        entityManager.close();
        return userAccount;
    }

    public void updateUserAccount(String email, String newPassword) {
        List<UserEntity> userDetails = retriveAcount(email);
        for (UserEntity user : userDetails ){
            user.setPassword(newPassword);
        }

        persist(
                mapper.map(userDetails, User.class)
        );
    }
}
