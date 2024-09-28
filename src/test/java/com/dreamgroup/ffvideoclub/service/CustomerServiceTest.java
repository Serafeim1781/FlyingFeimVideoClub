package com.dreamgroup.ffvideoclub.service;

import com.dreamgroup.ffvideoclub.dto.CustomerDTO;
import com.dreamgroup.ffvideoclub.exception.InUseException;
import com.dreamgroup.ffvideoclub.exception.ResourceNotFoundException;
import com.dreamgroup.ffvideoclub.mapper.CustomerMapper;
import com.dreamgroup.ffvideoclub.model.CustomerEntity;
import com.dreamgroup.ffvideoclub.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.ap.internal.model.Mapper;
import org.mapstruct.factory.Mappers;
import org.mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;


//@ExtendWith(MockitoExtension.class)
//@SpringBootTest
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    // The mapper won't be mocked but needs to use the original mapper
    @Spy
    private CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class);

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister_Success() throws InUseException {
        CustomerDTO customerDTO = new CustomerDTO(
                null,
                "username",
                "email@example.com",
                LocalDate.of(1990, 1, 1),
                false);

        when(customerRepository.existsByUsername(anyString())).thenReturn(false);
        when(customerRepository.existsByEmail(anyString())).thenReturn(false);
        when(customerRepository.save(any(CustomerEntity.class))).then(returnsFirstArg());

        String result = customerService.register(customerDTO);

        assertEquals(String.format("Welcome %s!",customerDTO.getUsername()), result);
        verify(customerRepository, times(1)).existsByUsername(anyString());
        verify(customerRepository, times(1)).existsByEmail(anyString());

        verify(customerRepository, times(1)).save(any(CustomerEntity.class));

//
//        ArgumentCaptor<CustomerEntity> captor = ArgumentCaptor.forClass(CustomerEntity.class);
//        verify(customerRepository, times(1)).save(captor.capture());
//        CustomerEntity capturedCustomer = captor.getValue();
//        assertNotNull(capturedCustomer);
    }

    @Test
    public void testRegister_Username_InUseException() {
        CustomerDTO customerDTO = new CustomerDTO(
                null,
                "username",
                "email@example.com",
                LocalDate.of(1990, 1, 1),
                null);

        when(customerRepository.existsByUsername(anyString())).thenReturn(true);
        when(customerRepository.existsByEmail(anyString())).thenReturn(false);

        InUseException thrown = assertThrows(InUseException.class, () ->
                        customerService.register(customerDTO),
                "Expected register(customerDTO) to throw InUseException, but did not. "
        );

        assertEquals("Username already in use.", thrown.getMessage());
        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }

    @Test
    public void testRegister_Email_InUseException() {
        CustomerDTO customerDTO = new CustomerDTO(
                null,
                "username",
                "email@example.com",
                LocalDate.of(1990, 1, 1),
                null);

        when(customerRepository.existsByUsername(anyString())).thenReturn(false);
        when(customerRepository.existsByEmail(anyString())).thenReturn(true);

        InUseException thrown = assertThrows(InUseException.class, () ->
                        customerService.register(customerDTO),
                "Expected register(customerDTO) to throw InUseException, but did not. "
        );

        assertEquals("Email already in use.", thrown.getMessage());
        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }


    @Test
    public void testRegister_Uname_And_Email_InUseException() {
        CustomerDTO customerDTO = new CustomerDTO(
                null,
                "username",
                "email@example.com",
                LocalDate.of(1990, 1, 1),
                null);

        when(customerRepository.existsByUsername(anyString())).thenReturn(true);
        when(customerRepository.existsByEmail(anyString())).thenReturn(true);

        InUseException thrown = assertThrows(InUseException.class, () ->
                        customerService.register(customerDTO),
                "Expected register(customerDTO) to throw InUseException, but did not."
        );

        assertEquals("Username and email already in use.", thrown.getMessage());
        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }

    @Test
    public void testUpdate_Success() throws InUseException {
        CustomerDTO customerDTO = new CustomerDTO(
                1L,
                "username",
                "email@example.com",
                LocalDate.of(1990, 1, 1),
                false);

        CustomerEntity customer = new CustomerEntity(
                1L,
                LocalDate.of(2024, 1, 1),
                "oldname",
                "oldemail@example.com",
                null,
                false,
                null
        );
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(customerRepository.existsByUsernameAndIdIsNot(anyString(),anyLong())).thenReturn(false);
        when(customerRepository.existsByEmailAndIdIsNot(anyString(),anyLong())).thenReturn(false);
        when(customerRepository.save(any(CustomerEntity.class))).then(returnsFirstArg());

        String result = customerService.update(1L, customerDTO);

        assertEquals("Account updated.", result);
        verify(customerRepository, times(1)).existsByUsernameAndIdIsNot(anyString(),anyLong());
        verify(customerRepository, times(1)).existsByEmailAndIdIsNot(anyString(),anyLong());

        verify(customerRepository, times(1)).save(any(CustomerEntity.class));

//
//        ArgumentCaptor<CustomerEntity> captor = ArgumentCaptor.forClass(CustomerEntity.class);
//        verify(customerRepository, times(1)).save(captor.capture());
//        CustomerEntity capturedCustomer = captor.getValue();
//        assertNotNull(capturedCustomer);
    }

    @Test
    public void testUpdate_ResourceNotFoundException() {
        CustomerDTO customerDTO = new CustomerDTO(
                1L,
                "username",
                "email@example.com",
                LocalDate.of(1990, 1, 1),
                null);

        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () ->
                        customerService.update(1L, customerDTO),
                "Expected register(customerDTO) to throw ResourceNotFoundException, but did not. "
        );

        assertEquals("No customer with that id.", thrown.getMessage());

        verify(customerRepository, never()).existsByUsernameAndIdIsNot(anyString(),anyLong());
        verify(customerRepository, never()).existsByEmailAndIdIsNot(anyString(),anyLong());
        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }

    @Test
    public void testUpdate_Username_InUseException() {
        CustomerDTO customerDTO = new CustomerDTO(
                1L,
                "username",
                "email@example.com",
                LocalDate.of(1990, 1, 1),
                null);

        CustomerEntity customer = new CustomerEntity(
                1L,
                LocalDate.of(2024, 1, 1),
                "oldname",
                "oldemail@example.com",
                null,
                false,
                null
        );
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(customerRepository.existsByUsernameAndIdIsNot(anyString(),anyLong())).thenReturn(true);
        when(customerRepository.existsByEmailAndIdIsNot(anyString(),anyLong())).thenReturn(false);

        InUseException thrown = assertThrows(InUseException.class, () ->
                        customerService.update(1L, customerDTO),
                "Expected register(customerDTO) to throw InUseException, but did not. "
        );

        assertEquals("Username already in use.", thrown.getMessage());

        verify(customerRepository, times(1)).existsByUsernameAndIdIsNot(anyString(),anyLong());
        verify(customerRepository, times(1)).existsByEmailAndIdIsNot(anyString(),anyLong());
        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }

    @Test
    public void testUpdate_Email_InUseException() {
        CustomerDTO customerDTO = new CustomerDTO(
                1L,
                "username",
                "email@example.com",
                LocalDate.of(1990, 1, 1),
                null);

        CustomerEntity customer = new CustomerEntity(
                1L,
                LocalDate.of(2024, 1, 1),
                "oldname",
                "oldemail@example.com",
                null,
                false,
                null
        );
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));        when(customerRepository.existsByUsernameAndIdIsNot(anyString(),anyLong())).thenReturn(false);
        when(customerRepository.existsByEmailAndIdIsNot(anyString(),anyLong())).thenReturn(true);

        InUseException thrown = assertThrows(InUseException.class, () ->
                        customerService.update(1L, customerDTO),
                "Expected register(customerDTO) to throw InUseException, but did not. "
        );

        assertEquals("Email already in use.", thrown.getMessage());

        verify(customerRepository, times(1)).existsByUsernameAndIdIsNot(anyString(),anyLong());
        verify(customerRepository, times(1)).existsByEmailAndIdIsNot(anyString(),anyLong());
        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }


    @Test
    public void testUpdate_Uname_And_Email_InUseException() {
        CustomerDTO customerDTO = new CustomerDTO(
                1L,
                "username",
                "email@example.com",
                LocalDate.of(1990, 1, 1),
                null);

        CustomerEntity customer = new CustomerEntity(
                1L,
                LocalDate.of(2024, 1, 1),
                "oldname",
                "oldemail@example.com",
                null,
                false,
                null
        );
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(customerRepository.existsByUsernameAndIdIsNot(anyString(), anyLong())).thenReturn(true);
        when(customerRepository.existsByEmailAndIdIsNot(anyString(), anyLong())).thenReturn(true);

        InUseException thrown = assertThrows(InUseException.class, () ->
                        customerService.update(1L, customerDTO),
                "Expected register(customerDTO) to throw InUseException, but did not."
        );

        assertEquals("Username and email already in use.", thrown.getMessage());

        verify(customerRepository, times(1)).existsByUsernameAndIdIsNot(anyString(),anyLong());
        verify(customerRepository, times(1)).existsByEmailAndIdIsNot(anyString(),anyLong());
        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }
}