package com.example.accessingdatapostgresql.repositories;

import com.example.accessingdatapostgresql.entities.Sale;
import com.example.accessingdatapostgresql.entities.SaleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findAllByCustomer_Id(Long id);

    Sale findSaleByCustomer_IdAndStatus(Long id, SaleStatus status);
}
