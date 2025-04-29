package tech.buildrun.agregadordeinvestimentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.buildrun.agregadordeinvestimentos.entity.User;

import java.util.UUID;

@Repository // Indica que essa interface é um repositório Spring (será gerenciado pelo Spring Data JPA)
public interface UserRepository extends JpaRepository<User, UUID> {
    // Interface que herda de JpaRepository, fornecendo métodos prontos para operações no banco de dados
    // (como save, findById, findAll, deleteById, etc.)

    // O tipo <User, UUID> indica que vai gerenciar entidades User, que usam UUID como ID.
}
