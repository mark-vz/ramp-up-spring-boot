package com.example.myusermgmt.user;

import com.example.myusermgmt.user.domain.User;
import com.example.myusermgmt.user.persistence.ContactViewRepository;
import com.example.myusermgmt.user.persistence.UserRepository;
import com.example.myusermgmt.user.readmodel.Contact;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final ContactViewRepository contactRepository;

  public UserService(UserRepository userRepository, ContactViewRepository contactRepository) {
    this.userRepository = userRepository;
    this.contactRepository = contactRepository;
  }

  public List<User> getAllUsers() {
    return userRepository.getAllUsers();
  }

  @Transactional
  public List<Contact> getAllContacts() {
    return contactRepository.getAllContacts();
  }

  public User createUser(final User user) {
    return userRepository.createUser(user);
  }
}
