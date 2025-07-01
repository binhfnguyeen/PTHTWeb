/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heulwen.repositories.impl;

import com.heulwen.hibernatedemo.HibernateConfigs;
import java.util.List;
import java.util.Map;
import com.heulwen.pojo.Product;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import org.hibernate.Session;

/**
 *
 * @author admin
 */
public class ProductRepositoryImpl {

    private static final int PAGE_SIZE = 6;

    public List<Product> getProducts(Map<String, String> params) {
        try ( Session s = HibernateConfigs.getFACTORY().openSession()) {
            CriteriaBuilder b = s.getCriteriaBuilder(); // Xây dựng câu query
            CriteriaQuery<Product> q = b.createQuery(Product.class);
            Root root = q.from(Product.class); // Bảng gốc
            q.select(root);

            // Loc du lieu
            if (params != null) {
                List<Predicate> predicates = new ArrayList<>();
                String kw = params.get("kw");
                if (kw != null && !kw.isEmpty()) {
                    predicates.add(b.like(root.get("name"), String.format("%%%s%%", kw)));
                }

                String fromPrice = params.get("fromPrice");
                if (fromPrice != null && !fromPrice.isEmpty()) {
                    predicates.add(b.greaterThanOrEqualTo(root.get("price"), fromPrice));
                }

                q.where(predicates.toArray(Predicate[]::new));

                // Sap xep du lieu
                q.orderBy(b.desc(root.get(params.getOrDefault("sortBy", "id"))));
            }

            Query query = s.createQuery(q);

            // Phan trang du lieu
            if (params != null) {
                String page = params.get("page");
                if (page != null) {
                    int p = Integer.parseInt(page);
                    int start = (p - 1) * PAGE_SIZE;

                    query.setFirstResult(start);
                    query.setMaxResults(PAGE_SIZE);
                }
            }
            return query.getResultList();
        }
    }
    
    public Product getProductById(int id){
        try (Session s = HibernateConfigs.getFACTORY().openSession()){
            return s.get(Product.class, id);
        }
    }
    
    public void addOrUpdateProduct(Product p){
        try (Session s = HibernateConfigs.getFACTORY().openSession()){
            if (p.getId() == null){
                s.persist(p);
            } else {
                s.merge(p);
            }
        }
    }
    
    public void deleteProduct(int id){
        try (Session s = HibernateConfigs.getFACTORY().openSession()){
            Product p = this.getProductById(id);
            s.remove(p);
        }
    }
}
