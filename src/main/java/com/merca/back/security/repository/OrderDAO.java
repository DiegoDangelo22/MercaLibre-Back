package com.merca.back.security.repository;

import com.merca.back.security.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderDAO extends CrudRepository<Order, Long> {
    Order findByPaypalOrderId(String paypalOrderId);
}