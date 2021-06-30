package by.kopetcev.shop.services;

import by.kopetcev.shop.exception.ServiceException;
import by.kopetcev.shop.model.User;
import by.kopetcev.shop.repositories.UserRepository;
import by.kopetcev.shop.security.SecurityUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new ServiceException("User does not exists", HttpStatus.NOT_FOUND));
        return SecurityUser.fromUser(user);
    }
}
