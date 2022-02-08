package com.example.myusermgmt.user;

import com.example.myusermgmt.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping(path = "/users")
  @Operation(description = "Gets all users", summary = "Gets all users")
  @Tag(name = "public api")
  public List<User> getUsers() {
    return userService.getAllUsers();
  }

  @PostMapping(path = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
  @Operation(description = "Creates a new user", summary = "Creates a new user")
  @Tag(name = "public api")
  public User createUser(@Valid @RequestBody final CreateUserDto userDto) {
    return userService.saveUser(userDto.toDomain());
  }
}

record CreateUserDto(
    @NotNull
    @Size(min = 1)
    String firstName,

    @NotNull
    @Size(min = 1)
    String lastName,

    @NotNull
    @Size(min = 3)
    String emailAddress) {
  User toDomain() {
    return new User(UUID.randomUUID(), firstName, lastName, emailAddress);
  }
}
