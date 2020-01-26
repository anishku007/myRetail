package com.myRetail.productservice.repository;

import com.myRetail.productservice.modal.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("ProductRepository")
public interface ProductRepository extends JpaRepository<Product,Long> {

    public Product findByPid(String pid);

    @SuppressWarnings("unchecked")
    public Product save(Product product);
}