package by.kopetcev.shop.repositories;

import by.kopetcev.shop.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query(value = "SELECT * FROM users WHERE users.id IN(SELECT user_ref FROM carts WHERE carts.id IN (SELECT cart_ref FROM cart_items WHERE item_ref =?1))", nativeQuery = true)
    Set<User> findAllByItemInCart(Long id);
}
