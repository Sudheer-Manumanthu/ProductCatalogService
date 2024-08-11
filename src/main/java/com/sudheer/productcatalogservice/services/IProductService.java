package com.sudheer.productcatalogservice.services;

import com.sudheer.productcatalogservice.models.Product;

import java.util.List;

public interface IProductService {
    public List<Product> getAllProducts();
    public Product getProductById(Long id);
    public Product createProduct(Product product);
    public Product replaceProduct(Product product, Long id);
}
