package edu.icet.dao.product;

import edu.icet.entity.ProductEntity;
import edu.icet.model.Product;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    ModelMapper mapper = new ModelMapper();


    @Override
    public List<ProductEntity> getAllProducts() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<ProductEntity> productList = entityManager.createQuery("FROM edu.icet.entity.ProductEntity", ProductEntity.class).getResultList();
        entityManager.close();
        return productList;
    }

    @Override
    public List<ProductEntity> getMenWearProducts() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<ProductEntity> menWearProductList = entityManager.createQuery("FROM edu.icet.entity.ProductEntity p WHERE p.category = :category", ProductEntity.class)
                .setParameter("category", "Mens wear")
                .getResultList();

        entityManager.close();
        return menWearProductList;
    }

    @Override
    public List<ProductEntity> getLadiesWearProducts() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<ProductEntity> ladiesWearProductList = entityManager.createQuery("FROM edu.icet.entity.ProductEntity p WHERE p.category = :category", ProductEntity.class)
                .setParameter("category", "Ladies wear")
                .getResultList();

        entityManager.close();
        return ladiesWearProductList;
    }

    @Override
    public List<ProductEntity> getKidsWearProducts() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<ProductEntity> kidsWearProductList = entityManager.createQuery("FROM edu.icet.entity.ProductEntity p WHERE p.category = :category", ProductEntity.class)
                .setParameter("category", "Kids wear")
                .getResultList();

        entityManager.close();
        return kidsWearProductList;
    }

    @Override
    public void deleteById(Object productId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        ProductEntity existingProduct = entityManager.find(ProductEntity.class, productId);
        if (existingProduct != null) {
            entityManager.remove(existingProduct);
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void updateById(Object productId, double price, int qty) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        ProductEntity product = entityManager.find(ProductEntity.class, productId);
        if (product != null) {
            product.setQty(qty);
            product.setPrice(price);

            entityManager.merge(product);
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void persist(Product product, String imagePath) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        File file = new File(imagePath);
        byte[] imageByte = null;
        try {
            imageByte = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        product.setImage(imageByte);

        entityManager.persist(
                mapper.map(product, ProductEntity.class)
        );

        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Override
    public List<ProductEntity> getByid(Object id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<ProductEntity> product = entityManager.createQuery("FROM edu.icet.entity.ProductEntity p WHERE p.id = :id", ProductEntity.class)
                .setParameter("id", id)
                .getResultList();

        entityManager.close();
        return product;
    }
}
