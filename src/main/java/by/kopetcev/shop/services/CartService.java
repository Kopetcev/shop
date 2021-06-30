package by.kopetcev.shop.services;

import by.kopetcev.shop.exception.ServiceException;
import by.kopetcev.shop.model.Cart;
import by.kopetcev.shop.model.Item;
import by.kopetcev.shop.model.User;
import by.kopetcev.shop.repositories.CartRepository;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;

    private final MailSender mailSender;

    public CartService(CartRepository cartRepository, MailSender mailSender) {
        this.cartRepository = cartRepository;
        this.mailSender = mailSender;
    }

    public Cart save(Cart cart){
        return cartRepository.save(cart);
    }

    public Cart findByUserId(Long id) {
        return cartRepository.findByUserId(id)
                .orElseThrow(() -> new ServiceException("User with id = " + id + " does have cart", HttpStatus.NOT_FOUND));
    }

    public Cart findById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Cart with id =  " + id + " does not exist",HttpStatus.NOT_FOUND));
    }

    public Cart update(Cart updatedCart, Long id) {
            Cart cart = cartRepository.findById(id)
                    .orElseThrow(() -> new ServiceException("Cart with id =  " + id + " does not exist",HttpStatus.BAD_REQUEST));
            cart.setUser(updatedCart.getUser());
            cart.setItems(updatedCart.getItems());
            return cartRepository.save(cart);

    }

    public void deleteById(Long id) {
        cartRepository.deleteById(id);
    }

    public void sellCart(Long id) {
        var cart = cartRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cart with id =  \" + id + \" does not exist"));
        cartRepository.deleteById(id);
        String message = createBuyMessage(cart.getUser(), cart.getItems());
        try {
            mailSender.send(cart.getUser(), "Congratulations!", message);
        }catch (MailException e){
            throw new ServiceException("Can't send email message", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    private String createBuyMessage(User user, Set<Item> items){
        var sb = new StringBuilder();
        sb.append("Dear, ");
        sb.append(user.getUsername());
        sb.append("\n\n");
        sb.append("You have bought:\n");
        for(Item item: items){
            sb.append(item.getName());
            sb.append("\n");
        }
        sb.append("\nBest regards,\nYour Shop");
        return sb.toString();
    }
}