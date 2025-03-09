package com.coffeeshop.infra.repository.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "shop_id", nullable = false)
    private Long shopId;

    @Type(type = "jsonb")
    @Column(name = "order_details", columnDefinition = "jsonb")
    private String orderDetails;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "create_date", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createDate;

    @Column(name = "update_date", nullable = false)
    @LastModifiedDate
    private LocalDateTime updateDate;
}
