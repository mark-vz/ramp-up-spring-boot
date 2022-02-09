package com.example.myusermgmt.address;

import com.example.myusermgmt.address.domain.Address;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
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
}
