package com.example.myusermgmt.address;

import com.example.myusermgmt.address.domain.Address;
import com.example.myusermgmt.address.writemodel.AddressWithUserEmail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AddressController {

  private final AddressService addressService;

  public AddressController(AddressService addressService) {
    this.addressService = addressService;
  }

  @GetMapping(path = "/addresses")
  @Operation(description = "Gets all addresses", summary = "Gets all addresses")
  @Tag(name = "public api")
  public List<Address> getAddresses() {
    return addressService.getAllAddresses();
  }

  @PostMapping(path = "/addresses")
  @Operation(description = "Creates a new address for a user. The user is identified via email address.", summary = "Creates a new address for a user")
  @Tag(name = "public api")
  public Address createAddress(@Valid @RequestBody final CreateAddressDto addressDto) {
    return addressService.createAddress(addressDto.toWriteModel());
  }
}

record CreateAddressDto(
    @NotNull
    @Size(min = 3, message = "email address must be at least 3 characters long")
    String email,

    @NotNull
    @Size(min = 1, message = "street must not be blank")
    String street,

    @NotNull
    @Size(min = 5, max = 5, message = "zipcode must be exactly 5 characters long")
    String zipcode,

    @NotNull
    @Size(min = 1, message = "city must not be blank")
    String city
) {
  AddressWithUserEmail toWriteModel() {
    return new AddressWithUserEmail(UUID.randomUUID(), email, street, zipcode, city);
  }
}
