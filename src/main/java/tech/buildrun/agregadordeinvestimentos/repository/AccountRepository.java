package tech.buildrun.agregadordeinvestimentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.buildrun.agregadordeinvestimentos.entity.Account;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
}
