package com.coffeeshop.infra.repository.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
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
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
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

    @Type(type = "jsonb")
    @Column(name = "menu", columnDefinition = "jsonb")
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
    private LocalDateTime createDate;

    @Column(name = "update_date", nullable = false)
    @LastModifiedDate
    private LocalDateTime updateDate;
}
