package by.kopetcev.shop.services;

import by.kopetcev.shop.model.Role;
import by.kopetcev.shop.model.Status;
import by.kopetcev.shop.model.User;
import by.kopetcev.shop.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {UserService.class})
class UserServiceTest {

    @MockBean
    private UserRepository userRepositoryMock;

    @Autowired
    private UserService service;


    @Test
    void shouldInvokeFindAllByItemInCart() {
            Set<User> expected = Set.of(new User(1L, "User", "PasswordCode", Role.ROLE_USER, "user@mail.com", Status.ACTIVE), new User(2L, "User2", "PasswordCode2", Role.ROLE_USER, "user2@mail.com", Status.ACTIVE));
            when(userRepositoryMock.findAllByItemInCart(1L)).thenReturn(expected);
            assertThat(service.findAllByItemInCart(1L), equalTo(expected));
            Mockito.verify(userRepositoryMock, Mockito.times(1)).findAllByItemInCart(1L);
        }
}
