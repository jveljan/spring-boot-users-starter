package com.example.demo.service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.demo.dto.CreateUserReq;
import com.example.demo.model.Authority;
import com.example.demo.model.User;
import com.example.demo.model.exception.DuplicateEntryException;
import com.example.demo.model.exception.ItemNotFoundException;
import com.example.demo.repository.AuthorityRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.Crypto;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Service
@Transactional
public class UserService {

  private AuthorityRepository authorityRepository;
  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(AuthorityRepository authorityRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.authorityRepository = authorityRepository;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public Optional<User> findUser(String username) {
    return userRepository.findByUsername(username);
  }

  public Optional<User> findUserAllowedToLogin(String username) {
    return userRepository.findByUsernameAndAllowedToLogin(username, true);
  }

  private User getUser(String username) {
    return findUser(username).orElseThrow(() -> new ItemNotFoundException("User not found"));
  }

  public void deleteUser(String username) {
    userRepository.delete(getUser(username));
  }

  public void updateAuthorities(String username, List<String> authorities) {
    User user = getUser(username);
    user.getAuthorities().clear();
    user.getAuthorities().addAll(authorities.stream().map(s -> Authority.of(s)).collect(Collectors.toSet()));
    userRepository.save(user);
  }

  public void setUserPassword(String username, String password) {
    User user = getUser(username);
    user.setPassword(passwordEncoder.encode(password));
    userRepository.save(user);
  }

  public Authority createOrGetAuthority(String name) {
    final Authority authority = authorityRepository.findByName(name);
    if (authority != null) {
      return authority;
    }
    Authority a = new Authority();
    a.setName(name);
    return authorityRepository.save(a);
  }

  public Page<User> listUsers(Pageable pageable) {
    return userRepository.findAll(pageable);
  }

  public User createUser(CreateUserReq req) {
    assertNotNull(req.getEmail());
    if (req.getUsername() == null) {
      req.setUsername(req.getEmail());
    }
    if (userRepository.findByUsername(req.getUsername()).isPresent()) {
      throw new DuplicateEntryException("Username already exists.");
    }
    final User u = new User();
    u.setUsername(req.getUsername());
    u.setEmail(req.getEmail());
    String password = req.getPassword() != null ? req.getPassword() : Crypto.randomKey(16);
    u.setPassword(passwordEncoder.encode(password));
    Set<String> authorities = CollectionUtils.isEmpty(req.getRoles()) ? new HashSet<>() : req.getRoles();
    u.getAuthorities().addAll(authorities.stream().map(s -> Authority.of(s)).collect(Collectors.toSet()));
    return userRepository.save(u);
  }
}
