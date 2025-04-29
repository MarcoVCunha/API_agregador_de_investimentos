package tech.buildrun.agregadordeinvestimentos.service;

import org.springframework.stereotype.Service;
import tech.buildrun.agregadordeinvestimentos.controller.CreateUserDto;
import tech.buildrun.agregadordeinvestimentos.controller.UpdateUserDto;
import tech.buildrun.agregadordeinvestimentos.entity.User;
import tech.buildrun.agregadordeinvestimentos.repository.UserRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service // Indica que essa classe é um serviço do Spring (pode ser injetado em controllers, por exemplo)
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository; // Injeta o repositório de usuários
    }

    public UUID createUser(CreateUserDto createUserDto) {
        // Cria um novo objeto User a partir dos dados do DTO recebido

        // DTO ->  ENTITY
        var entity = new User();
        entity.setUsername(createUserDto.userName());
        entity.setEmail(createUserDto.email());
        entity.setPassword(createUserDto.password());
        entity.setCreationTimestamp(Instant.now()); // Define a data/hora atual como data de criação
        entity.setUpdateTimestamp(null); // Ainda não atualizado, então null

        var userSaved = userRepository.save(entity); // Salva no banco de dados

        return userSaved.getUserId(); // Retorna o ID do usuário salvo
    }

    public Optional<User> getUserById(String userId) {
        // Busca um usuário pelo ID no banco de dados
        return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> listUsers() {
        // Retorna todos os usuários do banco
        return userRepository.findAll();
    }

    public void updateUserById(String userId,
                               UpdateUserDto updateUserDto) {
        // Atualiza um usuário

        var id = UUID.fromString(userId); // Converte o ID de String para UUID

        var userEntity = userRepository.findById(id); // Busca o usuário no banco

        if (userEntity.isPresent()) { // Se o usuário existir
            var user = userEntity.get();

            if (updateUserDto.userName() != null){
                user.setUsername(updateUserDto.userName()); // Atualiza o username se foi informado
            }

            if (updateUserDto.password() != null){
                user.setPassword(updateUserDto.password()); // Atualiza a senha se foi informada
            }

            userRepository.save(user); // Salva as alterações
        }

    }

    public void deleteById(String userId) {
        // Deleta um usuário
        var id = UUID.fromString(userId); // Converte o ID de String para UUID

        var userExists = userRepository.existsById(id); // Verifica se o usuário existe

        if (userExists) { //Se existir
            userRepository.deleteById(id); // Deleta o usuário
        }
    }
}
