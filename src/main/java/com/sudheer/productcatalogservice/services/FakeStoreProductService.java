package com.sudheer.productcatalogservice.services;

import com.sudheer.productcatalogservice.clients.FakeStoreApiClient;
import com.sudheer.productcatalogservice.dtos.FakeStoreProductDto;
import com.sudheer.productcatalogservice.models.Category;
import com.sudheer.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
//@Primary
public class FakeStoreProductService implements IProductService{

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Autowired
    FakeStoreApiClient fakeStoreApiClient;

    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDto[] fakeStoreProductDtos = fakeStoreApiClient.requestForEntity("https://fakestoreapi.com/products", HttpMethod.GET, null, FakeStoreProductDto[].class).getBody();
        List<Product> products = new ArrayList<>();
        for (FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtos) {
            products.add(getProduct(fakeStoreProductDto));
        }
        return products;
    }

    @Override
    public Product getProductById(Long id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = restTemplate.getForEntity("https://fakestoreapi.com/products/{id}", FakeStoreProductDto.class, id);
        if(fakeStoreProductDtoResponseEntity.getStatusCode().is2xxSuccessful() && fakeStoreProductDtoResponseEntity.getBody() != null) {
            return getProduct(fakeStoreProductDtoResponseEntity.getBody());
        }
        return null;
    }

    @Override
    public Product createProduct(Product product) {
        FakeStoreProductDto input = getFakeStoreProductDto(product);
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity =  fakeStoreApiClient.requestForEntity("https://fakestoreapi.com/products/", HttpMethod.POST, input, FakeStoreProductDto.class);
        FakeStoreProductDto fakeStoreProductDto = fakeStoreProductDtoResponseEntity.getBody();
        return getProduct(fakeStoreProductDto);
    }

    @Override
    public Product replaceProduct(Product product, Long id) {
        FakeStoreProductDto input = getFakeStoreProductDto(product);
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity =  fakeStoreApiClient.requestForEntity("https://fakestoreapi.com/products/{id}", HttpMethod.PUT, input, FakeStoreProductDto.class, id);
        FakeStoreProductDto fakeStoreProductDto = fakeStoreProductDtoResponseEntity.getBody();
        return getProduct(fakeStoreProductDto);
    }

    private FakeStoreProductDto getFakeStoreProductDto(Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setTitle(product.getName());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setImage(product.getImageUrl());
        if(product.getCategory() != null) {
            fakeStoreProductDto.setCategory(product.getCategory().getName());
        }

        return fakeStoreProductDto;
    }

    private Product getProduct(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setImageUrl(fakeStoreProductDto.getImage());
        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        return product;
    }
}
