package com.ecommerce.assignment.repository;

import com.ecommerce.assignment.entity.OrderCart;
import com.ecommerce.assignment.entity.constant.enums.CartStatus;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderCartRepository extends JpaRepository<OrderCart, UUID> {
    public OrderCart findById(Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE order_cart SET cart_status = :cartStatus WHERE id = :id", nativeQuery = true)
    int updateCartStatusById(@Param("cartStatus") String cartStatus, @Param("id") Long id);

    public List<OrderCart> findAllByUserIdAndCartStatus(Long userId, CartStatus cartStatus);

}
