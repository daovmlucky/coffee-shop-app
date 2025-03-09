package com.coffeeshop.infra.repository.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "create_date", nullable = false, updatable = false)
    @CreatedDate
    protected LocalDateTime createDate;

    @Column(name = "update_date", nullable = false)
    @LastModifiedDate
    protected LocalDateTime updateDate;
}
