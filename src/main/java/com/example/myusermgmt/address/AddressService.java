package com.example.myusermgmt.address;

import com.example.myusermgmt.address.domain.Address;
import com.example.myusermgmt.address.persistence.AddressRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

  private final AddressRepository addressRepository;

  public AddressService(AddressRepository addressRepository) {
    this.addressRepository = addressRepository;
  }

  public List<Address> getAllAddresses() {
    return addressRepository.getAllAddresses();
  }
}
