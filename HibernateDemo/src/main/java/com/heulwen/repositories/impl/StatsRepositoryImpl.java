/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heulwen.repositories.impl;

import com.heulwen.hibernatedemo.HibernateConfigs;
import com.heulwen.pojo.OrderDetail;
import com.heulwen.pojo.Product;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author admin
 */
public class StatsRepositoryImpl {
    public List<Object[]> statsRevenueByProduct(){
        try ( Session s = HibernateConfigs.getFACTORY().openSession()) {
            CriteriaBuilder b = s.getCriteriaBuilder();
            CriteriaQuery<Product> q = b.createQuery(Product.class);
            Root r = q.from(OrderDetail.class);
            Join<OrderDetail, Product> join = r.join("productId");
            
            q.multiselect(join.get("id"), join.get("name"), b.sum(r.get("quantity"), r.get("unitPrice")));
            q.groupBy(join.get("id"));
            
            Query query = s.createQuery(q);
            return query.getResultList();
        }
    }
}
