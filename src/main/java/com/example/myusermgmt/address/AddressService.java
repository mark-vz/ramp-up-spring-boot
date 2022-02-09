package com.example.myusermgmt.address;

import com.example.myusermgmt.address.converter.AddressConverter;
import com.example.myusermgmt.address.domain.Address;
import com.example.myusermgmt.address.persistence.AddressEntity;
import com.example.myusermgmt.address.persistence.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
