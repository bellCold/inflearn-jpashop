package jpabook.jpashop.repository.order.query;

import jpabook.jpashop.domain.Address;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderQueryDtos {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private Address address;
    private List<OrderItemQueryDto> orderItems;

    public OrderQueryDtos(Long orderId, String name, LocalDateTime orderDate, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.address = address;
    }
}
