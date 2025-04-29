package tech.buildrun.agregadordeinvestimentos.controller;

// Record que representa o DTO (Data Transfer Object) para atualizar informações de um usuário
public record UpdateUserDto(String userName, String password) {
    // Contém apenas dois campos: userName e password
    // O Java gera automaticamente construtor, getters, equals, hashCode e toString para o record
}
