package com.example.myusermgmt.address;

import com.example.myusermgmt.address.domain.Address;
import com.example.myusermgmt.address.persistence.AddressRepository;
import com.example.myusermgmt.address.writemodel.AddressWithUserEmail;
import com.example.myusermgmt.user.UserService;
import com.example.myusermgmt.user.domain.User;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

  private final AddressRepository addressRepository;
  private final UserService userService;

  public AddressService(AddressRepository addressRepository, UserService userService) {
    this.addressRepository = addressRepository;
    this.userService = userService;
  }

  public List<Address> getAllAddresses() {
    return addressRepository.getAllAddresses();
  }

  public Address createAddress(final AddressWithUserEmail addressWithUserEmail) {
    final User user = userService.getUserByEmailAddress(addressWithUserEmail.userEmailAddress());
    final Address address = new Address(UUID.randomUUID(), addressWithUserEmail.street(), addressWithUserEmail.zipcode(), addressWithUserEmail.city(), user);
    return addressRepository.createAddress(address);
  }
}
