package tech.buildrun.agregadordeinvestimentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.buildrun.agregadordeinvestimentos.entity.AccountStock;
import tech.buildrun.agregadordeinvestimentos.entity.AccountStockId;

public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
}
