package com.example.demo.service.impl;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Authority;
import com.example.demo.model.User;
import com.example.demo.model.exception.DuplicateEntryException;
import com.example.demo.model.exception.ItemNotFoundException;
import com.example.demo.repository.AuthorityRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

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
    if (userRepository.findByUsername(username).isPresent()) {
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
  public Optional<User> findUser(String username) {
    return userRepository.findByUsername(username);
  }

  private User getUser(String username) {
    return findUser(username).orElseThrow(() -> new ItemNotFoundException("User not found"));
  }

  @Override
  public void deleteUser(String username) {
    userRepository.delete(getUser(username));
  }

  @Override
  public void updateAuthorities(String username, List<String> authorities) {
    User user = getUser(username);
    user.setAuthorities(createOrGetAuthorities(authorities));
    userRepository.save(user);
  }

  @Override
  public void setUserPassword(String username, String password) {
    User user = getUser(username);
    user.setPassword(passwordEncoder.encode(password));
    userRepository.save(user);
  }

  private List<Authority> createOrGetAuthorities(List<String> authorities) {
    final List<Authority> rv = new ArrayList<>();
    for (String a : authorities) {
      rv.add(createOrGetAuthority(a));
    }
    return rv;
  }

  private Authority createOrGetAuthority(String a) {
    final Authority authority = authorityRepository.findByName(a);
    if (authority != null) {
      return authority;
    }
    return authorityRepository.save(new Authority(a));
  }
}
