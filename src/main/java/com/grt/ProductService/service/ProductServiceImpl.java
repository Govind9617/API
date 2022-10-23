package com.grt.ProductService.service;

import com.grt.ProductService.entity.Product;
import com.grt.ProductService.exception.ProductServiceCustomException;
import com.grt.ProductService.model.ProductRequest;
import com.grt.ProductService.model.ProductResponse;
import com.grt.ProductService.repo.ProductRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.beans.BeanUtils.*;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepo productRepo;
    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("adding product");

        Product product=Product.builder().productName(productRequest.getName()).
                quantity(productRequest.getQuantity()).
                price(productRequest.getPrice()).build();

        productRepo.save(product);
        log.info(" product created");
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        log.info("get product id:{}",productId);

        Product product=productRepo.findById(productId).orElseThrow(()->new ProductServiceCustomException("product id not found","PRODUCT_NOT_FOUND"));
       ProductResponse productResponse =new ProductResponse();
        copyProperties(product,productResponse);
        return productResponse;
    }

    @Override
    public void reduceQuantity(long productId, long quantity) {
        log.info("reduced quantity id:{}", quantity,productId);
        Product product=productRepo.findById(productId).orElseThrow(()->new ProductServiceCustomException("product id givrn not found",
                "PRODUCT_NOT_FOUND"));
        if(product.getQuantity()<quantity){
            throw  new ProductServiceCustomException("product does not have sufficient quantity","INSUFFICIENT_QUANTITY");
        }

        product.setQuantity(product.getQuantity()-quantity);
        productRepo.save(product);
        log.info("product quantity added successfully");
    }
}
