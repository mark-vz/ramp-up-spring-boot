package com.example.myusermgmt.address;

import com.example.myusermgmt.address.domain.Address;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

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

  @PostMapping(path = "/addresses", consumes = MediaType.APPLICATION_JSON_VALUE)
  @Operation(description = "Creates a new address", summary = "Creates a new address")
  @Tag(name = "public api")
  public Address createAddress(@Valid @RequestBody final CreateAddressDto addressDto) {
    return addressService.createAddress(addressDto.toDomain());
  }
}

record CreateAddressDto(
    @NotNull
    @Size(min = 1, message = "street must not be blank")
    String street,

    @NotNull
    @Size(min = 5, max = 5, message = "zipcode must have exactly 5 characters long")
    String zipcode,

    @NotNull
    @Size(min = 3, message = "city must be at least 3 characters long")
    String city,

    @NotNull
    String user) {

      Address toDomain() {
        return new Address(UUID.randomUUID(), street, zipcode, city, user);
      }
}
