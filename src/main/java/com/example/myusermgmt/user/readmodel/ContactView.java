package com.example.myusermgmt.user.readmodel;

import com.example.myusermgmt.address.readmodel.AddressView;
import java.util.List;
import java.util.UUID;

public record ContactView(
    UUID id,
    String firstName,
    String lastName,
    String emailAddress,
    List<AddressView> addresses
) {
}
