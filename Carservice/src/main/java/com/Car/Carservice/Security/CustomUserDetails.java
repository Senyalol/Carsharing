package com.Car.Carservice.Security;

import com.Car.Carservice.Entity.User;
import com.Car.Carservice.Entity.UserRole;
import com.Car.Carservice.Repository.UserRolesRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final User user;
    private final UserRolesRepository userRolesRepository;

    public CustomUserDetails(User user, UserRolesRepository userRolesRepository) {
        this.user = user;
        this.userRolesRepository = userRolesRepository;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        UserRole userRole = userRolesRepository.findByUserId(user.getId());

        if(userRole != null) {
            return List.of(new SimpleGrantedAuthority(userRole.getRole()));
        }

        return List.of();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
}
