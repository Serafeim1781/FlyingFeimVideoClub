package com.dreamgroup.ffvideoclub.service;

import com.dreamgroup.ffvideoclub.dto.CustomerDTO;
import com.dreamgroup.ffvideoclub.exception.InUseException;
import com.dreamgroup.ffvideoclub.exception.ResourceNotFoundException;
import com.dreamgroup.ffvideoclub.mapper.CustomerMapper;
import com.dreamgroup.ffvideoclub.model.CustomerEntity;
import com.dreamgroup.ffvideoclub.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }



    @Transactional
    public String register(CustomerDTO customerDTO) throws InUseException {

        String message = errorMessageIfUserNameOrEmailInUse(customerDTO, null);
        /* Although a check is happening, it doesn't guarantee that
           there won't be another insertion or update after the check
           but before the update...
           Probably fixed with Transactional
         */
        if (message != null) {
            throw new InUseException(message);
        }

        CustomerEntity customer = customerRepository.save(customerMapper.customerDTOtoCustomerEntity(customerDTO));


        return String.format("Welcome %s!", customer.getUsername());
    }

    //Maybe TODO: Tests with junit and mockito
    @Transactional
    public String registerList(List<CustomerDTO> customerDTOList){

        customerRepository.saveAll(customerMapper.customerDTOListToEntityList(customerDTOList));

        return "Welcome All";
    }

    @Transactional
    public String update(Long id, CustomerDTO customerDTO) throws ResourceNotFoundException, InUseException {

        CustomerEntity customerEntity = customerRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No customer with that id."));

        String message = errorMessageIfUserNameOrEmailInUse(customerDTO, id);
        /* Although a check is happening, it doesn't guarantee that
           there won't be another insertion or update after the check
           but before the update...
           Probably fixed with Transactional
           TODO find more info about Transactional
         */
        if (message != null) {
            throw new InUseException(message);
        }

        customerMapper.updateEntityFromDTONonNull(customerDTO, customerEntity);
        customerEntity = customerRepository.save(customerEntity);

        return "Account updated.";
    }


    /*----------------------- Private methods ----------------------*/

    private String errorMessageIfUserNameOrEmailInUse(CustomerDTO customerDTO, Long id) {
        boolean isUserNameInUse;
        boolean isEmailInUse;

        if (id == null) {
            isUserNameInUse = customerRepository.existsByUsername(customerDTO.getUsername());
            isEmailInUse = customerRepository.existsByEmail(customerDTO.getEmail());
        }
        else { /* id is used to exclude a specific user from search */
            isUserNameInUse = customerRepository.existsByUsernameAndIdIsNot(customerDTO.getUsername(), id);
            isEmailInUse = customerRepository.existsByEmailAndIdIsNot(customerDTO.getEmail(), id);
        }

        if (isUserNameInUse && isEmailInUse) {
            return "Username and email already in use.";
        }

        if (isUserNameInUse) {
            return "Username already in use.";
        }

        if (isEmailInUse) {
            return "Email already in use.";
        }

        return null;
    }
}
