package by.kopetcev.shop.controllers;

import by.kopetcev.shop.model.Cart;
import by.kopetcev.shop.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name="Cart", description="Cart API")
public class CartController {

    private final CartService cartService;

    CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/carts/{id}")
    @PreAuthorize("hasAuthority('read')")
    @Operation(summary = "Get a cart", description = "Allows to get a cart by id")
    public Cart one(@PathVariable @Parameter(description = "Cart id") Long id) {
        return cartService.findById(id);
    }

    @GetMapping("/carts/user/{id}")
    @PreAuthorize("hasAuthority('read')")
    @Operation(summary = "Get a cart", description = "Allows to get a cart by user id" )
    public Cart findOneByUserId(@PathVariable @Parameter(description = "User id") Long id) {
        return cartService.findByUserId(id);
    }

    @PostMapping("/carts")
    @PreAuthorize("hasAuthority('read')")
    @Operation(summary = "create new cart")
    public Cart newCart(@RequestBody Cart newCart) {
        return cartService.save(newCart);
    }

    @PutMapping("/carts/{id}")
    @PreAuthorize("hasAuthority('read')")
    @Operation(summary = "update card by id", description = "Allows to update cart and add/remove items from cart" )
    public Cart updateCart(@RequestBody Cart updatedCart,  @PathVariable @Parameter(description = "Cart id") Long id) {
        return cartService.update(updatedCart, id);
    }

    @DeleteMapping("/carts/{id}")
    @PreAuthorize("hasAuthority('read')")
    @Operation(summary = "delete carts")
    public void deleteCart(@PathVariable @Parameter(description = "Cart id") Long id) {
        cartService.deleteById(id);
    }

    @DeleteMapping("/carts/{id}/buy")
    @PreAuthorize("hasAuthority('read')")
    @Operation(summary = "sell items from cart", description = "Delete cart and send email with items")
    public void realizeCart(@PathVariable @Parameter(description = "Cart id") Long id) {
        cartService.sellCart(id);
    }
}

