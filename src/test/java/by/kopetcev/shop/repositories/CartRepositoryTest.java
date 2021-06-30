package by.kopetcev.shop.repositories;

import by.kopetcev.shop.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@Sql(scripts = {"/sql/cleanup.sql", "/sql/insert_UserAndCartRepository.sql"}, executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = {"/sql/cleanup.sql"}, executionPhase = AFTER_TEST_METHOD)
@Transactional
class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Test
    void shouldFindByUserId() {
        User user = new User(2L, "User", "$2y$12$6LEfdRapKUiv/w3/Mu2bYuyOFy5pnmx1pMDKjIrJdoO/57gP73iae", Role.ROLE_USER,
                "User@mail.com", Status.ACTIVE);
        Optional<Cart> expected = Optional.of( new Cart(1L, user, Set.of(new Item(1L, "phone", "The phone with a camera and buttons", Set.of(new Tag(1L, "electronics"))),
               (new Item(2L, "TV", "TV with a large CRT screen", Set.of(new Tag(5L, "entertainments"),new Tag(1L, "electronics")))))));
        Optional<Cart> cart = cartRepository.findByUserId(user.getId());
        assertThat(cart.get(), equalTo(expected.get()));
    }

    @Test
    void shouldFindAllByItemId() {
        Item item = new Item(2L, "TV", "TV with a large CRT screen", Set.of(new Tag(5L, "entertainments"), new Tag(1L, "electronics")));
        Cart cart1 = new Cart(1L,new User(2L, "User", "$2y$12$6LEfdRapKUiv/w3/Mu2bYuyOFy5pnmx1pMDKjIrJdoO/57gP73iae", Role.ROLE_USER,
                "User@mail.com", Status.ACTIVE), Set.of(new Item(1L, "phone", "The phone with a camera and buttons", Set.of(new Tag(1L, "electronics"))),
               item));
        Cart cart2 =new Cart(3L,new User(4L, "Derrick", "$2y$12$CYvL2Kx4.Z21UJDJQIutV.jS8aoySh8GUlQqJfjdy858Px3RrOdSG", Role.ROLE_USER,
                "Derrick@mail.com", Status.ACTIVE), Set.of(item));
        List<Cart> result = cartRepository.findAllByItemId(item.getId());
        assertThat(result, hasSize(2));
        assertThat(result, contains(cart1, cart2));

    }

}