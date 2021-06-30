package by.kopetcev.shop.services;

import by.kopetcev.shop.exception.ServiceException;
import by.kopetcev.shop.model.Cart;
import by.kopetcev.shop.model.Item;
import by.kopetcev.shop.model.Tag;
import by.kopetcev.shop.model.User;
import by.kopetcev.shop.repositories.CartRepository;
import by.kopetcev.shop.repositories.ItemRepository;
import by.kopetcev.shop.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    private final CartRepository cartRepository;

    private final UserRepository userRepository;

    private final MailSender mailSender;

    public ItemService(ItemRepository itemRepository, CartRepository cartRepository, UserRepository userRepository, MailSender mailSender) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    public List<Item> findAll() {
        return StreamSupport
                .stream(itemRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public Item findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Item with id =  " + id + " does not exist", HttpStatus.NOT_FOUND));
    }

    private Item update(Item updatedItem, Long id) {
        return itemRepository.findById(id)
                .map(item -> {
                    item.setName(updatedItem.getName());
                    item.setDescription(updatedItem.getDescription());
                    item.setTags(updatedItem.getTags());

                    return itemRepository.save(item);
                })
                .orElseThrow(() -> new ServiceException("Item with id =  " + id + " does not exist", HttpStatus.NOT_FOUND));
    }

    public Item replace(Item updatedItem, Long id, boolean isForce) {
        if (!isForce) {
            List<Cart> carts = cartRepository.findAllByItemId(id);
            if (carts.isEmpty()) {
                return update(updatedItem, id);
            } else {
                throw new ServiceException("Can not update item with id = " + id + " in user's carts", HttpStatus.BAD_REQUEST);
            }
        } else {
            return updateForce(updatedItem, id);
        }
    }

    private Item updateForce(Item updatedItem, Long id) {
        Set<User> users = userRepository.findAllByItemInCart(id);
        Item oldItem = itemRepository.findById(id).orElseThrow(() -> new ServiceException("Item of a user with id = " + id + " does not exist", HttpStatus.NOT_FOUND));

        for (User user : users) {
            String message = createChangeMessage(user, oldItem, updatedItem);
            try {
                mailSender.send(user, "Information", message);
            } catch (MailException e) {
                throw new ServiceException("Can't send email message", HttpStatus.INTERNAL_SERVER_ERROR, e);
            }
        }
        return update(updatedItem, id);
    }

    public void deleteById(Long id) {
        itemRepository.deleteById(id);    }

    public List<Item> findBy(List<String> tagNames, String contains) {
        if (tagNames != null && !tagNames.isEmpty() && contains != null) {
            return itemRepository.findAllByTagsNameInAndDescriptionLikeIgnoreCaseOrderByName(tagNames, String.format("%%%s%%", contains));
        } else if (tagNames!=null&&!tagNames.isEmpty()) {
            return itemRepository.findDistinctByTagsNameInOrderByName(tagNames);
        } else if (contains != null) {
            return itemRepository.findByDescriptionLikeIgnoreCaseOrderByName(String.format("%%%s%%", contains));
        }
        return findAll();
    }

    private String createChangeMessage(User user, Item oldItem, Item newItem) {
        var sb = new StringBuilder();
        sb.append("Dear, ");
        sb.append(user.getUsername());
        sb.append("\n\nThe product in your cart has changed:\n\nBefore:\n");
        sb.append(oldItem.getName());
        sb.append("\n");
        sb.append(oldItem.getDescription());
        sb.append("\n");
        sb.append("tags: ");
        for (Tag tag : oldItem.getTags()) {
            sb.append(tag.getName());
            sb.append(", ");
        }
        sb.append("\n\nAfter:\n");
        sb.append(newItem.getName());
        sb.append("\n");
        sb.append(newItem.getDescription());
        sb.append("\n");
        sb.append("tags: ");
        for (Tag tag : newItem.getTags()) {
            sb.append(tag.getName());
            sb.append(", ");
        }
        sb.append("\n\nBest regards,\nYour Shop");
        return sb.toString();
    }
}
