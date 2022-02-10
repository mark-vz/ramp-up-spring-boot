package com.example.myusermgmt.user.readModel;

import com.example.myusermgmt.address.domain.Address;
import com.example.myusermgmt.address.readModel.AddressReadModel;

import java.util.List;

public record Contact(String firstName, String lastName, String emailAddress, List<AddressReadModel> addresses) {
}
