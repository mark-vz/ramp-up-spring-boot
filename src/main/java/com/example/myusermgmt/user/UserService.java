package com.example.myusermgmt.user;

import com.example.myusermgmt.common.exception.UserNotFoundForEmailAddressException;
import com.example.myusermgmt.user.domain.User;
import com.example.myusermgmt.user.persistence.ContactViewRepository;
import com.example.myusermgmt.user.persistence.UserRepository;
import com.example.myusermgmt.user.readmodel.ContactView;
import java.util.List;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  private final UserRepository userRepository;
  private final ContactViewRepository contactViewRepository;

  public UserService(UserRepository userRepository, ContactViewRepository contactViewRepository) {
    this.userRepository = userRepository;
    this.contactViewRepository = contactViewRepository;
  }

  public List<User> getAllUsers() {
    return userRepository.getAllUsers();
  }

  public User getUserByEmailAddress(final String emailAddress) {
    final User user = userRepository.getUserByEmailAddress(emailAddress);
    if (user == null) {
      logger.error("user not found for given email address");
      throw new UserNotFoundForEmailAddressException();
    }
    return user;
  }

  @Transactional
  public List<ContactView> getAllContactViews() {
    return contactViewRepository.getAllContactViews();
  }

  public User createUser(final User user) {
    return userRepository.createUser(user);
  }
}
