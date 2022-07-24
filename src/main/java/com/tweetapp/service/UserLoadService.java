package com.tweetapp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.tweetapp.domain.UserModel;
import com.tweetapp.repository.UserRepository;

/**
 * @author Anshuman Panda
 * @project TweetApp-JAVA-API
 */
@Component("user-service")
public class UserLoadService implements UserDetailsService {


    private final UserRepository userRepository;

    public UserLoadService(@Qualifier("user-repository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        UserModel foundedUser = userRepository.findByUsername(username);
        if (foundedUser == null) return null;
        final String name = foundedUser.getUsername();
        final String password = foundedUser.getPassword();
        return new User(name, password, new ArrayList<>());
    }

}
