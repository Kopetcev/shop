package by.kopetcev.shop.repositories;

import by.kopetcev.shop.model.Cart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends CrudRepository<Cart,Long> {

    Optional<Cart> findByUserId(Long id);

    @Query(value = "SELECT * FROM carts WHERE carts.id IN (SELECT cart_ref FROM cart_items WHERE item_ref =?1)", nativeQuery = true)
    List<Cart> findAllByItemId(Long id);
}
