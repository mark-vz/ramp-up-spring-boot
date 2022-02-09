package com.example.myusermgmt.user.persistence;

import com.example.myusermgmt.user.domain.User;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

  User findByEmailAddress(final String emailAddress);

  default List<User> getAllUsers() {
    return findAll().stream().map(UserEntity::toDomain).toList();
  }

  default User getUserByEmailAddress(final String emailAddress) {
    return findByEmailAddress(emailAddress);
  }

  default User createUser(User user) {
    return save(UserEntity.fromDomain(user)).toDomain();
  }
}
