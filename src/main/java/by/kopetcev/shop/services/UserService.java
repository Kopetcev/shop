package by.kopetcev.shop.services;

import by.kopetcev.shop.model.Role;
import by.kopetcev.shop.model.Status;
import by.kopetcev.shop.model.User;
import by.kopetcev.shop.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private static final Role DEFAULT_ROLE = Role.ROLE_USER;

    private static final Status DEFAULT_STATUS = Status.ACTIVE;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(String username, String password, String email) {
        var bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
        var user = new User();
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setEmail(email);
        user.setRole(DEFAULT_ROLE);
        user.setStatus(DEFAULT_STATUS);
        return userRepository.save(user);
    }

    public Set<User> findAllByItemInCart(Long id) {
        return userRepository.findAllByItemInCart(id);
    }
}
