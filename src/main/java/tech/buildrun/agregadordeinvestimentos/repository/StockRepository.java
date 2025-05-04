package tech.buildrun.agregadordeinvestimentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.buildrun.agregadordeinvestimentos.entity.Stock;


public interface StockRepository extends JpaRepository<Stock, String> {
}
