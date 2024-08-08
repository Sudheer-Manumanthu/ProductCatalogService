package com.sudheer.productcatalogservice.controllers;


import com.sudheer.productcatalogservice.dtos.CategoryDto;
import com.sudheer.productcatalogservice.dtos.FakeStoreProductDto;
import com.sudheer.productcatalogservice.dtos.ProductDto;
import com.sudheer.productcatalogservice.models.Category;
import com.sudheer.productcatalogservice.models.Product;
import com.sudheer.productcatalogservice.services.FakeStoreProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    FakeStoreProductService fakeStoreProductService;

    @GetMapping("/product")
    public List<ProductDto> getProducts() {
        return null;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId) {
        try{
            if (productId == null) {
                throw new IllegalArgumentException();
            }
            ProductDto productDto = getProductDto(fakeStoreProductService.getProductById(productId));
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("CreatedBy", "Sudheer");
            return new ResponseEntity<>(productDto, headers, HttpStatus.OK);
        }
        catch (Exception e) {
            //e.printStackTrace();
            //return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/product")
    public ProductDto createProduct(@RequestBody ProductDto product) {
        return null;
    }

    private ProductDto getProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        productDto.setDescription(product.getDescription());
        if(product.getCategory() != null) {
            Category category = new Category();
            category.setId(product.getCategory().getId());
            category.setName(product.getCategory().getName());
            productDto.setCategory(category);
        }
        return productDto;
    }

}
