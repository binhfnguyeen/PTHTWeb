/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heulwen.hibernatedemo;


import com.heulwen.pojo.Category;
import com.heulwen.pojo.Comment;
import com.heulwen.pojo.OrderDetail;
import com.heulwen.pojo.ProdTag;
import com.heulwen.pojo.Product;
import com.heulwen.pojo.SaleOrder;
import com.heulwen.pojo.Tag;
import com.heulwen.pojo.User;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author admin
 */

/*
    - Singleton Config class:
        + Cấu hình Hibernate
        + Tạo 1 SessionFactory duy nhất trên toàn app
        + Cho phép mở Hibernate  Session từ factory này
*/
public class HibernateConfigs {
    private static final SessionFactory FACTORY;
    
    static {
        Configuration conf = new Configuration();
        Properties props = new Properties(); // Đặt property (Url, user, driver, dialect), đăng ký entity 
        props.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        props.put(Environment.JAKARTA_JDBC_DRIVER, "com.mysql.cj.jdbc.Driver");
        props.put(Environment.JAKARTA_JDBC_URL, "jdbc:mysql://localhost:3306/saledb");
        props.put(Environment.JAKARTA_JDBC_USER, "root");
        props.put(Environment.JAKARTA_JDBC_PASSWORD, "nguyennguyen123");
        props.put(Environment.SHOW_SQL, "true");
        conf.setProperties(props);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
        
        // Đăng ký các entity class
        conf.addAnnotatedClass(Category.class);
        conf.addAnnotatedClass(Product.class);
        conf.addAnnotatedClass(Comment.class);
        conf.addAnnotatedClass(OrderDetail.class);
        conf.addAnnotatedClass(ProdTag.class);
        conf.addAnnotatedClass(SaleOrder.class);
        conf.addAnnotatedClass(Tag.class);
        conf.addAnnotatedClass(User.class);
        FACTORY = conf.buildSessionFactory(serviceRegistry);
    }

    /**
     * @return the FACTORY
     */
    public static SessionFactory getFACTORY() {
        return FACTORY;
    }
}
