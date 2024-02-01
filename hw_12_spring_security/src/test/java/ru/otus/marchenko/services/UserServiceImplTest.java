package ru.otus.marchenko.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.marchenko.models.User;
import ru.otus.marchenko.repositories.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void shouldLoadUserByUsername(){
        Mockito.when(userRepository.findByUsername(any(String.class)))
                .thenReturn(Optional.of(new User(1L, "user", "password")));

        assertThat(userService.loadUserByUsername("user")).isNotNull()
                .matches(user -> user.getUsername().equals("user"))
                .matches(user -> user.getPassword().equals("password"));
    }
}