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


@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID createUser(CreateUserDto createUserDto) {

        // DTO ->  ENTITY
        var entity = new User();
        entity.setUsername(createUserDto.userName());
        entity.setEmail(createUserDto.email());
        entity.setPassword(createUserDto.password());
        entity.setCreationTimestamp(Instant.now());
        entity.setUpdateTimestamp(null);

        var userSaved = userRepository.save(entity);

        return userSaved.getUserId();
    }

    public Optional<User> getUserById(String userId) {

        return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public void updateUserById(String userId,
                               UpdateUserDto updateUserDto) {
        var id = UUID.fromString(userId);

        var userEntity = userRepository.findById(id);

        if (userEntity.isPresent()) {
            var user = userEntity.get();

            if (updateUserDto.userName() != null){
                user.setUsername(updateUserDto.userName());
            }

            if (updateUserDto.password() != null){
                user.setPassword(updateUserDto.password());
            }

            userRepository.save(user);
        }

    }

    public void deleteById(String userId) {
        var id = UUID.fromString(userId);

        var userExists = userRepository.existsById(id);

        if (userExists) {
            userRepository.deleteById(id);
        }
    }
}
