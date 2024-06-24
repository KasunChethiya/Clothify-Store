package edu.icet.dao.sales;

import edu.icet.bo.user.SignInService;
import edu.icet.entity.OrderEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SalesDaoImpl implements SalesDao {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");

    public List<OrderEntity> retriveTodaysOrders() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<OrderEntity> productList = entityManager.createQuery("FROM edu.icet.entity.OrderEntity p WHERE p.date = :date AND p.userId = :userId", OrderEntity.class)
                .setParameter("date", loadDate())
                .setParameter("userId", SignInService.userId)
                .getResultList();

        entityManager.close();
        return productList;
    }

    private String loadDate() {
        Date date = new Date();
        SimpleDateFormat f =new SimpleDateFormat("yyyy-MM-dd");
        return f.format(date);
    }
}
