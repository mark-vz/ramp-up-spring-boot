package com.example.myusermgmt.user;

import com.example.myusermgmt.address.domain.Address;
import com.example.myusermgmt.address.readModel.AddressReadModel;
import com.example.myusermgmt.user.domain.User;
import com.example.myusermgmt.user.readModel.Contact;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
        return userService.createUser(userDto.toDomain());
    }

    @GetMapping(path = "/contacts")
    @Operation(description = "Fetch contacts", summary = "Get existing contacts")
    @Tag(name = "public api")
    public List<Contact> getAllContacts() {
        return userService.getAllContacts();
    }

    @GetMapping(path = "/users/{userId}")
    @Operation(description = "Get an existing user", summary = "Get existing user")
    @Tag(name = "public api")
    public User getUser(@PathVariable final String userId) {
        return userService.getUser(userId);
    }
}

record CreateUserDto(
        @NotNull
        @Size(min = 1, message = "firstname must not be blank")
        String firstName,

        @NotNull
        @Size(min = 1, message = "lastname must not be blank")
        String lastName,

        @NotNull
        @Size(min = 3, message = "email address must be at least 3 characters long")
        String emailAddress) {
    User toDomain() {
        return new User(UUID.randomUUID(), firstName, lastName, emailAddress);
    }
}

record CreateUserReadDto(
        @NotNull
        @Size(min = 1, message = "firstname must not be blank")
        String firstName,

        @NotNull
        @Size(min = 1, message = "lastname must not be blank")
        String lastName,

        @NotNull
        @Size(min = 3, message = "email address must be at least 3 characters long")
        String emailAddress,

        List<AddressReadModel> addresses) {
        Contact toDomain() {
        return new Contact(firstName, lastName, emailAddress, addresses);
    }
}
