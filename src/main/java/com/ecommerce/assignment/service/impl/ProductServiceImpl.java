package com.ecommerce.assignment.service.impl;

import com.ecommerce.assignment.entity.Product;
import com.ecommerce.assignment.entity.constant.ErrorCategory;
import com.ecommerce.assignment.exception.BusinessException;
import com.ecommerce.assignment.repository.ProductRepository;
import com.ecommerce.assignment.service.api.ProductService;
import com.ecommerce.assignment.service.model.ProductServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceResponse findById(Long productId){
        Product product = productRepository.findByIdAndIsActiveAndIsDeleted(productId,true,false);
        if (Objects.isNull(product)) {
            throw BusinessException.builder().errorCode(ErrorCategory.PRODUCT_NOT_FOUND).build();
        }
        return buildProductResponse(product);
    }

    private ProductServiceResponse buildProductResponse(Product product){
        return ProductServiceResponse.builder()
                .id(product.getId())
                .merchantId(product.getMerchantId())
                .name(product.getName())
                .productTypeId(product.getProductTypeId())
                .desc(product.getDescription())
                .stock(product.getStock())
                .isActive(product.getIsActive())
                .isDeleted(product.isDeleted())
                .build();


    }

    public void updateStockById(Long id, Integer stock){
        productRepository.updateStockById(stock,id);
    }

    public List<ProductServiceResponse> findByMerchantId(Long merchatId){
        List<Product> product = productRepository.findAllByMerchantIdAndIsActive(merchatId,true);
        if (Objects.isNull(product)) {
            throw BusinessException.builder().errorCode(ErrorCategory.PRODUCT_NOT_FOUND).build();
        }
        return buildProductResponse(product);
    }

    private List<ProductServiceResponse> buildProductResponse(List<Product> product){
        List<ProductServiceResponse> resp = new ArrayList<>();
        product.forEach(p -> resp.add(
                ProductServiceResponse.builder()
                        .id(p.getId())
                        .merchantId(p.getMerchantId())
                        .name(p.getName())
                        .productTypeId(p.getProductTypeId())
                        .desc(p.getDescription())
                        .stock(p.getStock())
                        .isActive(p.getIsActive())
                        .isDeleted(p.isDeleted())
                        .build()
        ));
        return resp;
    }

    public List<ProductServiceResponse> findAll(){
        List<Product> product = productRepository.findAll();
        return buildProductResponse(product);
    }
}
