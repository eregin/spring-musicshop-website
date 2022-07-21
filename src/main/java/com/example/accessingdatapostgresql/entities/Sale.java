package com.example.accessingdatapostgresql.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sales_id")
    private int id;

    @Column(name = "sales_date", nullable=false)
    private LocalDateTime salesDate;

    @Column(name = "ship_address")
    private String shipAddress;

    @Column(name = "ship_city")
    private String shipCity;

    @Column(name = "ship_country")
    private String shipCountry;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SaleStatus status;

    @ManyToMany
    @JoinTable(name = "sales_items",
            joinColumns = @JoinColumn(name = "sale_id"),
            inverseJoinColumns = @JoinColumn(name = "track_id"))
    private List<Track> buyTracks;

    @ManyToOne
    @JoinColumn(name="customer_id", nullable=false)
    private Customer customer;

    public Sale(LocalDateTime salesDate, SaleStatus status, Customer customer) {
        this.salesDate = salesDate;
        this.status = status;
        this.customer = customer;
    }
}
