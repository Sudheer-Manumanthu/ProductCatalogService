package com.sudheer.productcatalogservice.services;

import com.sudheer.productcatalogservice.dtos.FakeStoreProductDto;
import com.sudheer.productcatalogservice.models.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IproductService {
    public List<Product> getAllProducts();
    public Product getProductById(Long id);
    public Product createProduct(Product product);
}
