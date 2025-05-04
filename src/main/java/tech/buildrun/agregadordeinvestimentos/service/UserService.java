package tech.buildrun.agregadordeinvestimentos.service;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.buildrun.agregadordeinvestimentos.controller.DTO.AccountResponseDto;
import tech.buildrun.agregadordeinvestimentos.controller.DTO.CreateAccountDto;
import tech.buildrun.agregadordeinvestimentos.controller.DTO.CreateUserDto;
import tech.buildrun.agregadordeinvestimentos.controller.DTO.UpdateUserDto;
import tech.buildrun.agregadordeinvestimentos.entity.Account;
import tech.buildrun.agregadordeinvestimentos.entity.BillingAddress;
import tech.buildrun.agregadordeinvestimentos.entity.User;
import tech.buildrun.agregadordeinvestimentos.repository.AccountRepository;
import tech.buildrun.agregadordeinvestimentos.repository.BillingAddressRepository;
import tech.buildrun.agregadordeinvestimentos.repository.UserRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.isNull;


@Service // Indica que essa classe é um serviço do Spring (pode ser injetado em controllers, por exemplo)
public class UserService {

    private UserRepository userRepository;

    private AccountRepository accountRepository;

    private BillingAddressRepository billingAddressRepository;

    public UserService(UserRepository userRepository,
                       AccountRepository accountRepository,
                       BillingAddressRepository billingAddressRepository) {
        this.userRepository = userRepository; // Injeta o repositório de usuários
        this.accountRepository = accountRepository;
        this.billingAddressRepository = billingAddressRepository;
    }

    public UUID createUser(CreateUserDto createUserDto) {
        // Cria um novo objeto User a partir dos dados do DTO recebido

        // DTO ->  ENTITY
        var entity = new User(
                null,
                createUserDto.userName(),
                createUserDto.email(),
                createUserDto.password(),
                Instant.now(),
                null);

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

    @Transactional
    public void createAccount(String userId, CreateAccountDto createAccountDto) {


        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não existe"));

        if (isNull(user.getAccounts())) {
            user.setAccounts(new ArrayList<>());
        }

        //DTO -> Entity
        var account = new Account(
                null,
                user,
                null,
                createAccountDto.description(),
                new ArrayList<>()
        );

        var accountSaved = accountRepository.save(account);

        var billingAddress = new BillingAddress();
        billingAddress.setAccount(accountSaved);
        billingAddress.setStreet(createAccountDto.street());
        billingAddress.setNumber(createAccountDto.number());

        billingAddressRepository.save(billingAddress);

    }

    @Transactional
    public List<AccountResponseDto> listAccounts(String userId) {

        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não existe"));

        return user.getAccounts()
                .stream()
                .map(ac ->
                        new AccountResponseDto(ac. getAccountId().toString(), ac.getDescription()))
                .toList();
    }
}
