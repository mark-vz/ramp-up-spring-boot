package com.example.myusermgmt.user;

import com.example.myusermgmt.user.domain.User;
import com.example.myusermgmt.user.persistence.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> getAllUsers() {
    return userRepository.getAllUsers();
  }

  public User createUser(final User user) {
    return userRepository.createUser(user);
  }

  public User getUser(final String userId) {
    return userRepository.getUser(userId);
  }
}
