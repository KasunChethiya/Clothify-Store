package edu.icet.bo.orders;

import edu.icet.bo.SuperBo;
import edu.icet.model.Order;
import javafx.scene.control.TableView;

public interface OrderBo extends SuperBo {
    void persistOrder(Order order);
    void persistOrderItems();
    int retriveTodayOrders(TableView orderTbl);
}
