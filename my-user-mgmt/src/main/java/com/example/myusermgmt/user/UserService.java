package com.example.myusermgmt.user;

import com.example.myusermgmt.user.domain.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  public List<User> getAllUsers() {
    final List<User> users = new ArrayList<>();
    users.add(new User("Mark", "Foo", "mark@example.com"));
    users.add(new User("Jan", "Bar", "jan@example.com"));
    return users;
  }
}
