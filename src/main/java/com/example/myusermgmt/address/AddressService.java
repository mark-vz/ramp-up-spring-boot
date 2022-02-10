package com.example.myusermgmt.address;

import com.example.myusermgmt.address.converter.AddressConverter;
import com.example.myusermgmt.address.domain.Address;
import com.example.myusermgmt.address.persistence.AddressEntity;
import com.example.myusermgmt.address.persistence.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AddressService {

    private final AddressConverter addressConverter;

    private final AddressRepository addressRepository;

    public AddressService(AddressConverter addressConverter, AddressRepository addressRepository) {
        this.addressConverter = addressConverter;
        this.addressRepository = addressRepository;
    }

    public List<Address> getAllAddresses() {
        return addressRepository.getAllAddresses();
    }

    public Address createAddress(final Address address) {
        AddressEntity addressEntity = addressRepository.save(addressConverter.convert(address));
        return addressEntity.toDomain();
    }

    public Address getAddress(final String addressId) {
        AddressEntity addressEntity = addressRepository.findById(UUID.fromString(addressId)).get();
        return addressEntity.toDomain();
    }
}
