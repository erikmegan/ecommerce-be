package com.ecommerce.assignment.service.api;

import com.ecommerce.assignment.service.model.ProductServiceResponse;

import java.util.List;

public interface ProductService {
    public ProductServiceResponse findById(Long productId);

    public void updateStockById(Long id, Integer stock);

    public List<ProductServiceResponse> findByMerchantId(Long merchatId);

    public List<ProductServiceResponse> findAll();
}
