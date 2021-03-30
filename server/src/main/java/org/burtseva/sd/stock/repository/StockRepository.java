package org.burtseva.sd.stock.repository;

import org.burtseva.sd.stock.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
    Optional<Stock> findByCompany(String company);
}
