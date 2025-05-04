package tech.buildrun.agregadordeinvestimentos.entity;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Indica que essa classe é uma entidade JPA, ou seja, será mapeada para uma tabela no banco de dados
@Entity

// Especifica o nome da tabela no banco de dados como "tb_users"
@Table(name = "tb_users")
public class User {

    @Id // Define o identificador primário da entidade
    @GeneratedValue(strategy = GenerationType.UUID) // Gera o UUID automaticamente como estratégia de geração de chave primária
    private UUID userId;

    // Mapeia a propriedade 'username' para a coluna 'username' da tabela
    @Column(name = "username")
    private String username;

    // Mapeia a propriedade 'email' para a coluna 'email' da tabela
    @Column(name = "email")
    private String email;

    // Mapeia a propriedade 'password' para a coluna 'password' da tabela
    @Column(name = "password")
    private String password;

    // Preenche automaticamente com a data/hora de criação do registro
    @CreationTimestamp
    private Instant creationTimestamp;

    // Preenche automaticamente com a data/hora da última atualização do registro
    @UpdateTimestamp
    private Instant updateTimestamp;

    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts = new ArrayList<>();

    // Construtor vazio obrigatório para o JPA
    public User() {

    }

    public User(UUID userId, String username, String email, String password, Instant creationTimestamp, Instant updateTimestamp) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.creationTimestamp = creationTimestamp;
        this.updateTimestamp = updateTimestamp;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Instant getCreationTimestamp() {
        return creationTimestamp;
    }

    public Instant getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreationTimestamp(Instant creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUpdateTimestamp(Instant updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
