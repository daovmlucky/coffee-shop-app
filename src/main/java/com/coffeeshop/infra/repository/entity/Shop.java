package com.coffeeshop.infra.repository.entity;

import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shop")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "contact_details")
    private String contactDetails;

    @Column(name = "menu")
    private String menu;

    @Column(name = "queues", nullable = false)
    private Integer queues;

    @Column(name = "opening_time", nullable = false)
    private LocalTime openingTime;

    @Column(name = "closing_time", nullable = false)
    private LocalTime closingTime;

    @ToString.Include
    @Version
    private Integer version;

    @Column(name = "create_date", nullable = false, updatable = false)
    @CreatedDate
    protected LocalDateTime createDate;

    @Column(name = "update_date", nullable = false)
    @LastModifiedDate
    protected LocalDateTime updateDate;
}
