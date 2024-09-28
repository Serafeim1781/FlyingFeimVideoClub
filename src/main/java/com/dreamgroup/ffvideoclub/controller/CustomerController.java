package com.dreamgroup.ffvideoclub.controller;

import com.dreamgroup.ffvideoclub.dto.CustomerDTO;
import com.dreamgroup.ffvideoclub.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {

        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@RequestBody CustomerDTO customerDTO) {

        return new ResponseEntity<>(customerService.register(customerDTO), HttpStatus.OK);
    }

    @PostMapping("/list-register")
    public ResponseEntity<String> registerCustomerList(@RequestBody List<CustomerDTO> customerDTOList) {

        return new ResponseEntity<>(customerService.registerList(customerDTOList), HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable("id") Long id, @RequestBody CustomerDTO customerDTO) {

        return new ResponseEntity<>(customerService.update(id, customerDTO), HttpStatus.OK);
    }

    @PostMapping("/feedback")
    public ResponseEntity<CustomerDTO> feedbackBody(@RequestBody CustomerDTO customerDTO) {

//        return new ResponseEntity<>(new CustomerDTO(null, customerDTO.userName(), customerDTO.email(), LocalDate.now(), null), HttpStatus.OK);
        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    @PostMapping("/list-feedback")
    public ResponseEntity<List<CustomerDTO>> listFeedbackBody(@RequestBody List<CustomerDTO> customerDTOList) {

//        return new ResponseEntity<>(new CustomerDTO(null, customerDTO.userName(), customerDTO.email(), LocalDate.now(), null), HttpStatus.OK);
        return new ResponseEntity<>(customerDTOList, HttpStatus.OK);
    }
}