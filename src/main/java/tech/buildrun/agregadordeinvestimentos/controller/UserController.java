package tech.buildrun.agregadordeinvestimentos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.buildrun.agregadordeinvestimentos.controller.DTO.*;
import tech.buildrun.agregadordeinvestimentos.entity.User;
import tech.buildrun.agregadordeinvestimentos.service.UserService;

import java.net.URI;
import java.util.List;

@RestController // Indica que esta classe é um controller REST
@RequestMapping("/v1/users") // Define o caminho base da API para /v1/users
public class UserController {

    private UserService userService; // Serviço responsável pela lógica de usuários

    // Injeção de dependência do UserService via construtor
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping//Endpoint para criar um novo usuário
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto createUserDto) {
        var userId = userService.createUser(createUserDto); // Chama o serviço para criar o usuário
        return ResponseEntity.created(URI.create("/v1/users/" + userId.toString())).build(); // Retorna status 201 Created com location do novo recurso
    }

    @GetMapping("/{userId}") // Endpoint para buscar um usuário pelo ID
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") String userId) {
        var user = userService.getUserById(userId); // Chama o serviço para buscar o usuário
        if (user.isPresent()) {
            return ResponseEntity.ok(new UserDto(user.get())); // Se encontrado, retorna 200 OK com o usuário
        } else {
            return ResponseEntity.notFound().build(); // Se não encontrado, retorna 404 Not Found
        }
    }

    @GetMapping// Endpoint para listar todos os usuários
    public ResponseEntity<List<UserDto>> listUsers() {
        var users = userService.listUsers();
        var userDtos = users.stream()
                .map(UserDto::new)
                .toList();
        return ResponseEntity.ok(userDtos);
    }


    @PutMapping("/{userId}") // Endpoint para atualizar um usuário pelo ID
    public ResponseEntity<Void> updateUserById(@PathVariable("userId") String userId,
                                               @RequestBody UpdateUserDto updateUserDto){
            userService.updateUserById(userId, updateUserDto); // Chama o serviço para atualizar o usuário
            return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}") // Endpoint para deletar um usuário pelo ID
    public ResponseEntity<Void> deleteUserById(@PathVariable("userId") String userId) {
        userService.deleteById(userId); // Chama o serviço para deletar o usuário
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/accounts")
    public ResponseEntity<Void> createAccount(@PathVariable("userId") String userId,
                                               @RequestBody CreateAccountDto createAccountDto) {
        userService.createAccount(userId, createAccountDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<AccountResponseDto>> listAccounts(@PathVariable("userId") String userId) {

        var accounts = userService.listAccounts(userId);

        return ResponseEntity.ok(accounts);
    }
}
