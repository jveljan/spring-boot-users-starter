package com.example.demo.service.impl;

import com.example.demo.model.Authority;
import com.example.demo.model.User;
import com.example.demo.model.exception.DuplicateEntryException;
import com.example.demo.repository.AuthorityRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private AuthorityRepository authorityRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(AuthorityRepository authorityRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authorityRepository = authorityRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(String username, String password, List<String> authorities) {
        if(userRepository.findByUsername(username) != null) {
            throw new DuplicateEntryException("Username already exists.");
        }
        final List<Authority> authoritiesObjList = createOrGetAuthorities(authorities);
        final User u = new User();
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(password));
        u.setAuthorities(authoritiesObjList);
        return userRepository.save(u);
    }

    @Override
    public User findUser(String username) {
        final User u = userRepository.findByUsername(username);
        if(u == null) {
            return null;
        }
        if(u.getAuthorities() == null) {
            u.setAuthorities(new ArrayList<>());
        }
        // load lazy list
        if(u.getAuthorities().size() > 0) {
            u.getAuthorities().get(0);
        }
        return u;
    }

    @Override
    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username);
        userRepository.delete(user);
    }

    @Override
    public void updateAuthorities(String username, List<String> authorities) {
        User user = userRepository.findByUsername(username);
        user.setAuthorities(createOrGetAuthorities(authorities));
        userRepository.save(user);
    }

    @Override
    public void setUserPassword(String username, String password) {
        User user = userRepository.findByUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    private List<Authority> createOrGetAuthorities(List<String> authorities) {
        final List<Authority> rv = new ArrayList<>();
        for(String a : authorities) {
            rv.add(createOrGetAuthority(a));
        }
        return rv;
    }

    private Authority createOrGetAuthority(String a) {
        final Authority authority = authorityRepository.findByName(a);
        if(authority != null) {
            return authority;
        }
        return authorityRepository.save(new Authority(a));
    }
}
