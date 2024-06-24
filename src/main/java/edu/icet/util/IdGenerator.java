package edu.icet.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class IdGenerator {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");

    public static synchronized String genarateUserId() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        String lastUser = (String) entityManager.createQuery("SELECT id FROM users u ORDER BY u.id DESC")
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);

        int nextId = 1;
        if(lastUser != null){
            String lastId = String.valueOf(lastUser);
            nextId = Integer.parseInt(lastId.substring(1)) +1;
        }
        entityManager.close();
        return String.format("U%03d",nextId);
    }

    public static synchronized String genarateOrderId() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        String lastUser = (String) entityManager.createQuery("SELECT orderId FROM orders u ORDER BY u.orderId DESC")
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);

        int nextId = 1;
        if(lastUser != null){
            String lastId = String.valueOf(lastUser);
            nextId = Integer.parseInt(lastId.substring(3)) +1;
        }
        entityManager.close();
        return String.format("ODR%03d",nextId);
    }
}
