package com.yahiaprince.store.orders;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.yahiaprince.store.auth.AuthService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final AuthService authService;

    public List<OrderDto> getAllOrders() {
        var user = authService.getCurrentUser();
        var orders = orderRepository.getOrdersByCustomer(user);
        return orders.stream().map(orderMapper::toDto).toList();
    }

    public OrderDto getOrder(Long id) {
        var order = orderRepository
                .getOrderWithItems(id)
                .orElseThrow(OrderNotFoundException::new);
        var user = authService.getCurrentUser();
        if (!order.isPlacedBy(user)) {
            throw new AccessDeniedException("You don't have access to this order.");
        }
        return orderMapper.toDto(order);
    }
}