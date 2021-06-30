package by.kopetcev.shop.services;

import by.kopetcev.shop.model.Role;
import by.kopetcev.shop.model.Status;
import by.kopetcev.shop.model.User;
import by.kopetcev.shop.repositories.UserRepository;
import by.kopetcev.shop.security.SecurityUser;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {UserDetailsServiceImpl.class})
class UserDetailsServiceImplTest {

    @MockBean
    private UserRepository userRepositoryMock;

    @Autowired
    private UserDetailsServiceImpl service;

    @Test
    void shouldLoadSecurityUserByUsername() {
        User user =  new User(1L, "User", "PasswordCode", Role.ROLE_USER, "user@mail.com", Status.ACTIVE);
        UserDetails expected = SecurityUser.fromUser(new User(1L, "User", "PasswordCode", Role.ROLE_USER, "user@mail.com", Status.ACTIVE));
        when(userRepositoryMock.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        assertThat(service.loadUserByUsername(user.getUsername()), equalTo(expected));
        Mockito.verify(userRepositoryMock, Mockito.times(1)).findByUsername(user.getUsername());
    }
}
