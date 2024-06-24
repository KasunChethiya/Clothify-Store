package edu.icet.bo;

import edu.icet.bo.orders.OrderBoImpl;
import edu.icet.bo.product.ProductBoImpl;
import edu.icet.bo.user.UserBoImpl;
import edu.icet.util.BoType;

public class BoFactory{
    private static BoFactory instance;

    private BoFactory(){}

    public static BoFactory getInstance(){
        return instance != null ? instance:(instance = new BoFactory());
    }

    public <T extends SuperBo>T getBo(BoType type){
        switch (type){
            case USER:return (T)new UserBoImpl();
            case ORDER:return (T)new OrderBoImpl();
            case PRODUCT:return (T)new ProductBoImpl();
        }
        return null;
    }
}