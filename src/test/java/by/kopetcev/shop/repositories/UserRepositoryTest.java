package by.kopetcev.shop.repositories;

import by.kopetcev.shop.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@Sql(scripts = {"/sql/cleanup.sql", "/sql/insert_UserAndCartRepository.sql"}, executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = {"/sql/cleanup.sql"}, executionPhase = AFTER_TEST_METHOD)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindByItemIdInCart() {
        Long ItemId = 1L;
        Set<User> all = userRepository.findAllByItemInCart(ItemId);
        assertThat(all, hasSize(2));
        assertThat(all, containsInAnyOrder(new User(2L, "User", "$2y$12$6LEfdRapKUiv/w3/Mu2bYuyOFy5pnmx1pMDKjIrJdoO/57gP73iae", Role.ROLE_USER,
                "User@mail.com", Status.ACTIVE),new User(2L, "User", "$2y$12$6LEfdRapKUiv/w3/Mu2bYuyOFy5pnmx1pMDKjIrJdoO/57gP73iae", Role.ROLE_USER, "User@mail.com", Status.ACTIVE)));
    }

    @Test
    void shouldFindByItemIdInCartNotOne() {
        Long ItemId = 5L;
        Set<User> all = userRepository.findAllByItemInCart(ItemId);
        assertThat(all, hasSize(0));
    }
}