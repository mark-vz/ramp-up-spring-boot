package com.example.myusermgmt.user.persistence;

import com.example.myusermgmt.user.domain.User;
import java.util.List;
import java.util.UUID;

import com.example.myusermgmt.user.readModel.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

  default List<User> getAllUsers() {
    return findAll().stream().map(UserEntity::toDomain).toList();
  }

  default User createUser(User user) {
    return save(UserEntity.fromDomain(user)).toDomain();
  }

  default User getUser(String userId) {
    return findById(UUID.fromString(userId)).get().toDomain();
  }

  @Transactional
  @Query("Select u from UserEntity u join u.addresses")
  default List<Contact> getAllContacts() {
    return findAll().stream().map(UserEntity::toContactDomain).toList();
  }
}
