package com.grt.ProductService.service;

import com.grt.ProductService.model.ProductRequest;
import com.grt.ProductService.model.ProductResponse;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(Long productId);

    void reduceQuantity(long productId, long quantity);
}
