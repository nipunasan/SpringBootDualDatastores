package com.ns.dualds.repository.DB_Two;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ns.dualds.model.DB_Two.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
