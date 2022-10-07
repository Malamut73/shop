package com.online.shop.domain;

import com.online.shop.domain.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

    private static final String SEQ_NAME = "orders_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;

    @CreationTimestamp
    private LocalDateTime created;

//    @UpdateTimestamp
//    private Timestamp update;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private BigDecimal sum;
    private String address;
//    @OneToMany(cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)

    private List<OrderDetails> details;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
