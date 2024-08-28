package com.sudheer.productcatalogservice.services;

import com.sudheer.productcatalogservice.models.Product;
import com.sudheer.productcatalogservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class ProductStorageService implements IProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product replaceProduct(Product product, Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()) {
            Product productInDB = optionalProduct.get();
            productInDB.setName(product.getName());
            productInDB.setPrice(product.getPrice());
            productInDB.setDescription(product.getDescription());
            if(product.getCategory() != null){
                productInDB.setCategory(product.getCategory());
            }
            productInDB.setDescription(product.getDescription());
            productInDB.setImageUrl(product.getImageUrl());
            productInDB.setStatus(product.getStatus());
            return productRepository.save(productInDB);
        }
        return null;
    }
}
