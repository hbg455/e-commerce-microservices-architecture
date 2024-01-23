package com.myshop.order.entities;

import com.myshop.commonDtos.events.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "t_Order")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Order extends AbstractMappedEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", unique = true, nullable = false, updatable = false)
    private Integer orderId;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderLine> orderLineItemsList;
}
