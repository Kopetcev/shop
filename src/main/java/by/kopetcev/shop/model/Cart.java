package by.kopetcev.shop.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "carts")
@Schema(description = "Entity of  cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_ref", nullable = false)
    @Schema(description = "Cart owner(User)")
    private User user;

    @ManyToMany
    @JoinTable(name = "cart_items",
            joinColumns =
            @JoinColumn(name = "cart_ref", referencedColumnName = "id"),
            inverseJoinColumns =
            @JoinColumn(name = "item_ref", referencedColumnName = "id"))
    @Schema(description = "Items in cart")
    private Set<Item> items;

    public Cart() {
    }

    public Cart(User user, Set<Item> items) {
        this.user = user;
        this.items = items;
    }

    public Cart(Long id, User user, Set<Item> items) {
        this.id = id;
        this.user = user;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id) && Objects.equals(user, cart.user) && Objects.equals(items, cart.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, items);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", user=" + user +
                ", items=" + items +
                '}';
    }
}
