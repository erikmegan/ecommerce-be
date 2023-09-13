package com.ecommerce.assignment.repository;

import com.ecommerce.assignment.entity.Product;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository  extends JpaRepository<Product, UUID> {
    public Product findByIdAndIsActiveAndIsDeleted(Long productId, boolean isActive,boolean isDeleted);

    @Transactional
    @Modifying
    @Query(value = "UPDATE products p SET stock = :stock WHERE p.id = :id", nativeQuery = true)
    void updateStockById(@Param("stock") Integer stock, @Param("id") Long id);

    public List<Product> findAllByMerchantIdAndIsActive(Long merhcnatId,Boolean isActive);
    public List<Product> findAll();

    public Optional<Product> findAllByName(String productName);
}
