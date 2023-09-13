package com.ecommerce.assignment.web.controller;

import com.ecommerce.assignment.service.api.ProductService;
import com.ecommerce.assignment.service.model.ProductServiceResponse;
import com.ecommerce.assignment.web.constant.ApiPath;
import com.ecommerce.assignment.web.model.ProductApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(ApiPath.PRODUCT)
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping
    public ResponseEntity<ProductApiResponse> getProductById(@RequestParam Long productId){
        ProductServiceResponse resp = productService.findById(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(buildProductResponse(resp));
    }

    private ProductApiResponse buildProductResponse(ProductServiceResponse productServiceResponse){
        return ProductApiResponse.builder()
                .id(productServiceResponse.getId())
                .merchantId(productServiceResponse.getMerchantId())
                .name(productServiceResponse.getName())
                .productTypeId(productServiceResponse.getProductTypeId())
                .desc(productServiceResponse.getDesc())
                .stock(productServiceResponse.getStock())
                .build();
    }

    @GetMapping(value = ApiPath.MERCHANT)
    public ResponseEntity<List<ProductApiResponse>> getProductByMerchantId(@RequestParam Long merchantId){
        List<ProductServiceResponse> resp = productService.findByMerchantId(merchantId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(buildProductResponse(resp));
    }

    private List<ProductApiResponse> buildProductResponse(List<ProductServiceResponse> productServiceResponse){

        List<ProductApiResponse> responses = new ArrayList<>();
        productServiceResponse.forEach(p ->responses.add(ProductApiResponse.builder()
                .id(p.getId())
                .merchantId(p.getMerchantId())
                .name(p.getName())
                .productTypeId(p.getProductTypeId())
                .desc(p.getDesc())
                .stock(p.getStock())
                .build()));
        return responses;
    }

    @GetMapping(value = ApiPath.GET_ALL)
    public ResponseEntity<List<ProductApiResponse>> getProductByMerchantId(){
        List<ProductServiceResponse> resp = productService.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(buildProductResponse(resp));
    }


}
