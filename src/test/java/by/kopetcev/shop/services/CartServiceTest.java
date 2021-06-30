package by.kopetcev.shop.services;

import by.kopetcev.shop.exception.ServiceException;
import by.kopetcev.shop.model.*;
import by.kopetcev.shop.repositories.CartRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {CartService.class})
class CartServiceTest {

    @MockBean
    private CartRepository cartRepositoryMock;

    @MockBean
    private  MailSender mailSenderMock;

    @Autowired
    private CartService service;

    @Test
    void shouldInvokeSave() {
        User user = new User(2L, "User", "$2y$12$6LEfdRapKUiv/w3/Mu2bYuyOFy5pnmx1pMDKjIrJdoO/57gP73iae", Role.ROLE_USER,
                "User@mail.com", Status.ACTIVE);
        Cart newCart = new Cart(user, Set.of(new Item(1L, "phone", "The phone with a camera and buttons", Set.of(new Tag(1L, "electronics"))),
                (new Item(2L, "TV", "TV with a large CRT screen", Set.of(new Tag(5L, "entertainments"),new Tag(1L, "electronics"))))));

        Cart expected = new Cart(1L, user, Set.of(new Item(1L, "phone", "The phone with a camera and buttons", Set.of(new Tag(1L, "electronics"))),
                (new Item(2L, "TV", "TV with a large CRT screen", Set.of(new Tag(5L, "entertainments"),new Tag(1L, "electronics"))))));
        when(cartRepositoryMock.save(newCart)).thenReturn(expected);
        assertThat(service.save(newCart), equalTo(expected));
        Mockito.verify(cartRepositoryMock, Mockito.times(1)).save(newCart);
    }

    @Test
    void shouldInvokeFindById() {
        User user = new User(2L, "User", "$2y$12$6LEfdRapKUiv/w3/Mu2bYuyOFy5pnmx1pMDKjIrJdoO/57gP73iae", Role.ROLE_USER,
                "User@mail.com", Status.ACTIVE);

        Optional<Cart> expected=  Optional.of(new Cart(1L, user, Set.of(new Item(1L, "phone", "The phone with a camera and buttons", Set.of(new Tag(1L, "electronics"))),
                (new Item(2L, "TV", "TV with a large CRT screen", Set.of(new Tag(5L, "entertainments"),new Tag(1L, "electronics")))))));
        when(cartRepositoryMock.findById(expected.get().getId())).thenReturn(expected);
        assertThat(service.findById(expected.get().getId()), equalTo(expected.get()));
        Mockito.verify(cartRepositoryMock, Mockito.times(1)).findById(expected.get().getId());
    }

    @Test
    void shouldFindByIdThrowExceptionOnEmpty() {
        Long id = 1L;
        Optional<Cart> empty=  Optional.empty();
        when(cartRepositoryMock.findById(id)).thenReturn(empty);
        assertThrows(ServiceException.class, () -> service.findById(id));
    }

    @Test
    void shouldInvokeFindByUserId() {
        User user = new User(2L, "User", "$2y$12$6LEfdRapKUiv/w3/Mu2bYuyOFy5pnmx1pMDKjIrJdoO/57gP73iae", Role.ROLE_USER,
                "User@mail.com", Status.ACTIVE);

        Optional<Cart> expected=  Optional.of(new Cart(1L, user, Set.of(new Item(1L, "phone", "The phone with a camera and buttons", Set.of(new Tag(1L, "electronics"))),
                (new Item(2L, "TV", "TV with a large CRT screen", Set.of(new Tag(5L, "entertainments"),new Tag(1L, "electronics")))))));
        when(cartRepositoryMock.findByUserId(user.getId())).thenReturn(expected);
        assertThat(service.findByUserId(user.getId()), equalTo(expected.get()));
        Mockito.verify(cartRepositoryMock, Mockito.times(1)).findByUserId(user.getId());
    }

    @Test
    void shouldFindByUserIdThrowExceptionOnEmpty() {
        Long id = 1L;
        Optional<Cart> empty=  Optional.empty();
        when(cartRepositoryMock.findByUserId(id)).thenReturn(empty);
        assertThrows(ServiceException.class, () -> service.findByUserId(id));
    }

    @Test
    void shouldInvokeDeleteById() {
        Long id = 1L;
        service.deleteById(id);
        Mockito.verify(cartRepositoryMock, Mockito.times(1)).deleteById(id);
    }

    @Test
    void shouldInvokeSellCart() {
        Long id = 1L;
        User user = new User(2L, "User", "$2y$12$6LEfdRapKUiv/w3/Mu2bYuyOFy5pnmx1pMDKjIrJdoO/57gP73iae", Role.ROLE_USER,
                "User@mail.com", Status.ACTIVE);
        Optional<Cart> cart=  Optional.of(new Cart(1L, user, Set.of(new Item(1L, "phone", "The phone with a camera and buttons", Set.of(new Tag(1L, "electronics"))),
                (new Item(2L, "TV", "TV with a large CRT screen", Set.of(new Tag(5L, "entertainments"),new Tag(1L, "electronics")))))));
        when(cartRepositoryMock.findById(id)).thenReturn(cart);
        service.sellCart(id);
        Mockito.verify(mailSenderMock, Mockito.times(1)).send(user,"Congratulations!","Dear, User\n" +
                "\n" +
                "You have bought:\n" +
                "TV\n" +
                "phone\n" +
                "\n" +
                "Best regards,\n" +
                "Your Shop");
        Mockito.verify(cartRepositoryMock, Mockito.times(1)).deleteById(id);
            }

}