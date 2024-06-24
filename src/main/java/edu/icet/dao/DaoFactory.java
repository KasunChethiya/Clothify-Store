package edu.icet.dao;


import edu.icet.dao.orders.OrderDaoImpl;
import edu.icet.dao.product.ProductDaoImpl;
import edu.icet.dao.sales.SalesDaoImpl;
import edu.icet.dao.supplier.SupplierDaoImpl;
import edu.icet.dao.user.UserDaoImpl;
import edu.icet.util.DaoType;

public class DaoFactory {
    private static DaoFactory instance;
    private DaoFactory(){}

    public static DaoFactory getInstance(){
        return instance != null ? instance:(instance = new DaoFactory());
    }

    public <T extends SuperDAO> T getDao(DaoType type){
        switch (type){
            case USER:return (T)new UserDaoImpl();
            case PRODUCT:return (T)new ProductDaoImpl();
            case SALES:return (T)new SalesDaoImpl();
            case ORDER:return (T)new OrderDaoImpl();
            case SUPPLIER:return (T)new SupplierDaoImpl();
        }
        return null;
    }

}
