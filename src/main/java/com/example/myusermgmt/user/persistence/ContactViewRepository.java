package com.example.myusermgmt.user.persistence;

import com.example.myusermgmt.user.readmodel.Contact;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactViewRepository extends JpaRepository<UserEntity, UUID> {

  default List<Contact> getAllContacts() {
    return findAll().stream().map(Contact::toView).toList();
  }
}
