package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*
     * UserDetailsService for authentication with Spring Security.
     * The UserDetailsService interface is used to retrieve user-related data.
     * It has one method named loadUserByUsername() which can be overridden
     * to customize the process of finding the user.
     *
     * It is used by the DaoAuthenticationProvider to load details about the user during authentication.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Find the user by email in the database using the UserRepository injected into the service
        User user = userRepository.findByEmail(email);

        // If the user is found in the database, create a UserDetails object with their email, password, and roles
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(), // email as the username
                    user.getPassword(), // hashed password
                    mapRolesToAuthorities(user.getRoles()) // map the user's roles to Spring Security's GrantedAuthorities
            );
        } else { // If the user is not found in the database, throw an exception
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

    // Helper method to map the user's roles to Spring Security's GrantedAuthorities
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}

/*
Explanation of each element in the code:
@Service: Marks the class as a Spring service component that can be injected into other components using dependency injection.
UserRepository userRepository: A repository interface that provides access to the User entity in the database.
CustomUserDetailsService(UserRepository userRepository): Constructor injection of the UserRepository.
UserDetailsService: An interface used by Spring Security to retrieve user details.
loadUserByUsername(String email): A method that retrieves a UserDetails object for the user with the specified email address.
userRepository.findByEmail(email): Finds the User entity in the database with the specified email address.
UserDetails: An interface representing the user details required by Spring Security to perform authentication and authorization.
mapRolesToAuthorities(Collection<Role> roles): A helper method that maps a Collection of Role entities to a Collection
 of GrantedAuthority objects that Spring Security can use for authorization.
 */
