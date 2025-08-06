package com.Car.Carservice.Security;

import com.Car.Carservice.Entity.User;
import com.Car.Carservice.Repository.UserRepository;
import com.Car.Carservice.Repository.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRolesRepository userRolesRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, UserRolesRepository userRolesRepository) {
        this.userRepository = userRepository;
        this.userRolesRepository = userRolesRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow();

        return new CustomUserDetails(user,userRolesRepository);

    }

}
