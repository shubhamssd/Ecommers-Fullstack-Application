package com.eCommers.eCommersApp.repo;

import com.eCommers.eCommersApp.model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {


    @Query("SELECT p FROM Product p  WHERE p.name like %:product%")
    List<Product> findByName(@Param("product") String name);

    List<Product> findAllByNameContainingIgnoreCase(String keyword, Sort sort);

    @Query("SELECT p FROM Product p  WHERE p.category like %:cat%")
    List<Product> getProductCategoryName(@Param("cat") String category);


}
