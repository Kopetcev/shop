package by.kopetcev.shop.services;

import by.kopetcev.shop.exception.ServiceException;
import by.kopetcev.shop.model.*;
import by.kopetcev.shop.repositories.CartRepository;
import by.kopetcev.shop.repositories.ItemRepository;
import by.kopetcev.shop.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {ItemService.class})
class ItemServiceTest {


    @MockBean
    private ItemRepository itemRepositoryMock;

    @MockBean
    private CartRepository cartRepositoryMock;

    @MockBean
    private UserRepository userRepositoryMock;

    @Autowired
    private ItemService service;

    @Test
    void shouldInvokeSave() {
        Item newItem = new Item("car alarm", "The best loud protection of your car", Set.of(new Tag(1L, "electronics")));
        Item expected = new Item(5L, "car alarm", "The best loud protection of your car", Set.of(new Tag(1L, "electronics")));
        when(itemRepositoryMock.save(newItem)).thenReturn(expected);
        assertThat(service.save(newItem), equalTo(expected));
        Mockito.verify(itemRepositoryMock, Mockito.times(1)).save(newItem);
    }

    @Test
    void shouldInvokeFindAll() {
        List<Item> expected = new ArrayList<>();
        expected.add(new Item(3L, "laptop", "The high-performance gaming laptop", Set.of(new Tag(1L, "electronics"), new Tag(3L, "home appliances"))));
        expected.add(new Item(5L, "car alarm", "The best loud protection of your car", Set.of(new Tag(1L, "electronics"))));
        when(itemRepositoryMock.findAll()).thenReturn(expected);
        assertThat(service.findAll(), equalTo(expected));
        Mockito.verify(itemRepositoryMock, Mockito.times(1)).findAll();
    }

    @Test
    void shouldInvokeFindById() {
        Long id = 1L;
        Item item = new Item(3L, "laptop", "The high-performance gaming laptop", Set.of(new Tag(1L, "electronics"), new Tag(3L, "home appliances")));
        when(itemRepositoryMock.findById(id)).thenReturn(Optional.of(item));
        assertThat(service.findById(id), equalTo(item));
        Mockito.verify(itemRepositoryMock, Mockito.times(1)).findById(id);
    }

    @Test
    void shouldFindByIdThrowExceptionOnEmpty() {
        Long id = 1L;
        Optional<Item> empty = Optional.empty();
        when(itemRepositoryMock.findById(id)).thenReturn(empty);
        assertThrows(ServiceException.class, () -> service.findById(id));
    }

    @Test
    void shouldUpdateWhenIsForceFalse() {
        Long id = 1L;
        Item oldItem = new Item(3L, "laptop", "The high-performance gaming laptop", Set.of(new Tag(1L, "electronics")));
        Item newItem = new Item(3L, "laptop", "The high-performance gaming laptop", Set.of(new Tag(1L, "electronics"), new Tag(3L, "home appliances")));
        when(cartRepositoryMock.findAllByItemId(id)).thenReturn(new ArrayList<>());
        when(itemRepositoryMock.findById(id)).thenReturn(Optional.of(oldItem));
        when(itemRepositoryMock.save(newItem)).thenReturn(newItem);
        assertThat(service.replace(newItem, id, false), equalTo(newItem));
        Mockito.verify(itemRepositoryMock, Mockito.times(1)).save(newItem);
    }

    @Test
    void shouldThrowExceptionWhenIsForceFalseAndItemInCart() {
        Long id = 1L;
        Item newItem = new Item(3L, "laptop", "The high-performance gaming laptop", Set.of(new Tag(1L, "electronics"), new Tag(3L, "home appliances")));
        List<Cart> carts = Arrays.asList(new Cart(1L, new User(2L, "User", "$2y$12$6LEfdRapKUiv/w3/Mu2bYuyOFy5pnmx1pMDKjIrJdoO/57gP73iae", Role.ROLE_USER,
                "User@mail.com", Status.ACTIVE), Set.of(new Item(1L, "phone", "The phone with a camera and buttons", Set.of(new Tag(1L, "electronics"))), new Item(3L, "laptop", "The high-performance gaming laptop", Set.of(new Tag(1L, "electronics"))))));
        when(cartRepositoryMock.findAllByItemId(id)).thenReturn(carts);
        assertThrows(ServiceException.class, () -> service.replace(newItem, id, false));
    }

    @Test
    void shouldUpdateWhenIsForceTrue() {
        Long id = 1L;
        User user1 = new User(1L, "User", "$2y$12$6LEfdRapKUiv/w3/Mu2bYuyOFy5pnmx1pMDKjIrJdoO/57gP73iae", Role.ROLE_USER,
                "User@mail.com", Status.ACTIVE);
        User user2 = new User(2L, "User2", "$2y$12$6LEfdRapKUiv/w3/Mu2bYuyOFy5pnmx1pMDKjIrJdoO/57gP73iae", Role.ROLE_USER,
                "User2@mail.com", Status.ACTIVE);
        Item oldItem = new Item(3L, "laptop", "The high-performance gaming laptop", Set.of(new Tag(1L, "electronics")));
        Item newItem = new Item(3L, "laptop", "The high-performance gaming laptop", Set.of(new Tag(1L, "electronics"), new Tag(3L, "home appliances")));
        Set<User> users = Set.of(user1, user2);
        when(itemRepositoryMock.findById(id)).thenReturn(Optional.of(oldItem));
        when(userRepositoryMock.findAllByItemInCart(id)).thenReturn(users);
        when(itemRepositoryMock.save(newItem)).thenReturn(newItem);
        assertThat(service.replace(newItem, id, true), equalTo(newItem));
        Mockito.verify(itemRepositoryMock, Mockito.times(1)).save(newItem);
    }

    @Test
    void shouldInvokeDeleteById() {
        Long id = 1L;
        service.deleteById(id);
        Mockito.verify(itemRepositoryMock, Mockito.times(1)).deleteById(id);
    }

    @Test
    void shouldInvokeFindByTag(){
        Item item = new Item(3L, "laptop", "The high-performance gaming laptop", Set.of(new Tag(1L, "electronics"), new Tag(3L, "home appliances")));
        when(itemRepositoryMock.findDistinctByTagsNameInOrderByName(Arrays.asList("electronics", "home appliances"))).thenReturn(Arrays.asList(item));
        assertThat(service.findBy(Arrays.asList("electronics", "home appliances"), null), equalTo(Arrays.asList(item)));
    }

    @Test
    void shouldInvokeFindByTagAndDescription(){
        Item item = new Item(3L, "laptop", "The high-performance gaming laptop", Set.of(new Tag(1L, "electronics"), new Tag(3L, "home appliances")));
        when(itemRepositoryMock.findAllByTagsNameInAndDescriptionLikeIgnoreCaseOrderByName(Arrays.asList("electronics", "home appliances"), "%hight%")).thenReturn(Arrays.asList(item));
        assertThat(service.findBy(Arrays.asList("electronics", "home appliances"), "hight"), equalTo(Arrays.asList(item)));
    }

    @Test
    void shouldInvokeFindByDescription(){
        Item item = new Item(3L, "laptop", "The high-performance gaming laptop", Set.of(new Tag(1L, "electronics"), new Tag(3L, "home appliances")));
        when(itemRepositoryMock.findByDescriptionLikeIgnoreCaseOrderByName("%hight%")).thenReturn(Arrays.asList(item));
        assertThat(service.findBy(new ArrayList<>(), "hight"), equalTo(Arrays.asList(item)));
    }
}
