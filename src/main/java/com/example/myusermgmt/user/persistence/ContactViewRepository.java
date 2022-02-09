package com.example.myusermgmt.user.persistence;

import com.example.myusermgmt.user.readmodel.ContactView;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactViewRepository extends JpaRepository<UserEntity, UUID> {

  default List<ContactView> getAllContactViews() {
    return findAll().stream().map(ContactView::toView).toList();
  }
}
