package com.example.myusermgmt.address.converter;

import com.example.myusermgmt.address.domain.Address;
import com.example.myusermgmt.address.persistence.AddressEntity;
import com.example.myusermgmt.user.persistence.UserEntity;
import com.example.myusermgmt.user.persistence.UserRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AddressConverter implements Converter<Address, AddressEntity> {

    private final UserRepository userRepository;

    private final ObjectMapper objectMapper;

    public AddressConverter(UserRepository userRepository, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    }

    @Override
    public AddressEntity convert(Address source) {
        UserEntity user = this.userRepository.getById(UUID.fromString(source.user()));
        if (user == null) {
            throw new IllegalArgumentException();
        }
        return new AddressEntity(
            source.id(),
            user,
            source.street(),
            source.zipcode(),
            source.city()
        );
    }
}
