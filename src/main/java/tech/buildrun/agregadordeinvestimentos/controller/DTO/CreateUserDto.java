package tech.buildrun.agregadordeinvestimentos.controller.DTO;

// Record que representa o DTO (Data Transfer Object) para criação de um novo usuário
public record CreateUserDto(String userName,String email, String password) {
    // Campos necessários para criar um usuário: userName, email e password
    // O Java gera automaticamente construtor, getters, equals, hashCode e toString para o record
}
