package com.example.myusermgmt.user;

import com.example.myusermgmt.user.domain.User;
import com.example.myusermgmt.user.persistence.ContactViewRepository;
import com.example.myusermgmt.user.persistence.UserRepository;
import com.example.myusermgmt.user.readmodel.ContactView;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final ContactViewRepository contactViewRepository;

  public UserService(UserRepository userRepository, ContactViewRepository contactViewRepository) {
    this.userRepository = userRepository;
    this.contactViewRepository = contactViewRepository;
  }

  public List<User> getAllUsers() {
    return userRepository.getAllUsers();
  }

  @Transactional
  public List<ContactView> getAllContactViews() {
    return contactViewRepository.getAllContactViews();
  }

  public User createUser(final User user) {
    return userRepository.createUser(user);
  }
}
