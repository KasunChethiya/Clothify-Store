package edu.icet.bo.orders;

import edu.icet.dao.sales.SalesDaoImpl;
import edu.icet.entity.OrderEntity;
import edu.icet.model.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class OrderBoImpl implements OrderBo{
    ModelMapper mapper = new ModelMapper();
    public static double totalRevenue = 0.0;
    @Override
    public void persistOrder(Order order) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(
                mapper.map(order, OrderEntity.class)
        );

        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }



    @Override
    public void persistOrderItems() {
        
    }

    @Override
    public int retriveTodayOrders(TableView saleHistoryTbl) {
        SalesDaoImpl salesDaoImpl = new SalesDaoImpl();
        List<OrderEntity> orders = salesDaoImpl.retriveTodaysOrders();
        int orderCount = 0;
        ObservableList<OrderEntity> orderList = FXCollections.observableArrayList();

        for (OrderEntity order : orders) {
            orderCount++;
            totalRevenue += order.getTotal();
            orderList.add(order);
        }
        saleHistoryTbl.setItems(orderList);
        return orderCount;
    }
}
