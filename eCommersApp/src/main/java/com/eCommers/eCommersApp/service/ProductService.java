package com.eCommers.eCommersApp.service;

import com.eCommers.eCommersApp.dto.ProductDTO;
import com.eCommers.eCommersApp.exception.ProductException;
import com.eCommers.eCommersApp.model.Product;
import com.eCommers.eCommersApp.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    public Product addProduct(Product product) throws ProductException {
        if (product == null){
            throw new ProductException("Product cannot be null");
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Integer productId, ProductDTO updatedProduct) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            throw new ProductException("Product with ID " + productId + " not found.");
        }
        Product existingProduct = product.get();

        // Update the existing product's properties with the new data
        System.out.println("before");
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setImageUrl(updatedProduct.getImageUrl());
        existingProduct.setDescription(updatedProduct.getDescription());
        System.out.println("after");
        productRepository.save(existingProduct);
        return existingProduct;
    }

    public List<Product> getProductByName(String name) throws ProductException {

        List<Product> existProductByName = productRepository.findByName(name);
        if (existProductByName.isEmpty()) {
            throw new ProductException("Product Not found with name " + name);
        }
        return existProductByName;
    }


    public List<Product> getProducts(String keyword, String sortDirection, String sortBy) throws ProductException {

        Sort sort = Sort.by(sortDirection.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC,sortBy);

        List<Product> products;

        if (keyword != null) {

            products = productRepository.findAllByNameContainingIgnoreCase(keyword, sort);
        } else {
            products = productRepository.findAll(sort);
        }
        if (products.isEmpty()) {
            throw new ProductException("Product List Empty");
        }

        return products;

    }




    public List<Product> getProductByCategory(String category) throws ProductException {
        // Retrieve products by category from the database
        List<Product> allProductCategoryName = productRepository.getProductCategoryName(category);
        if (allProductCategoryName.isEmpty())
            throw new ProductException("Product with category Name " + category + " not found.");

        return allProductCategoryName;
    }



    public void removeProduct(Integer productId) throws ProductException {

        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product with ID " + productId + " not found."));

        productRepository.delete(existingProduct);
    }


    public Product getSingleProduct(Integer productId) {

        return productRepository.findById(productId).orElseThrow(() -> new ProductException("Product not found"));
    }


}
