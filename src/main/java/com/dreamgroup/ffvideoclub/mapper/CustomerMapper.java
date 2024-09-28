package com.dreamgroup.ffvideoclub.mapper;

import com.dreamgroup.ffvideoclub.dto.CustomerDTO;
import com.dreamgroup.ffvideoclub.model.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring"/*, nullValueMappingStrategy = nullValueMappingStrategy.IGNORE*/)
public interface CustomerMapper {

    @Mapping(target = "customerId", source = "id")
    @Mapping(target = "isDeactivated", source = "deactivated")
    CustomerDTO customerEntityToCustomerDto(CustomerEntity customerEntity);

    @Mapping(ignore = true, target = "rentalHistory")
    @Mapping(ignore = true, target = "id")
    @Mapping(target = "deactivated", source = "isDeactivated", defaultExpression = "java(false)")
    @Mapping(target = "createDate", expression = "java(java.time.LocalDate.now())")
    CustomerEntity customerDTOtoCustomerEntity(CustomerDTO customerDTO);

    List<CustomerEntity> customerDTOListToEntityList(List<CustomerDTO> customerDTOList);

    @Mapping(ignore = true, target = "rentalHistory")
    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "createDate")
    @Mapping(source = "isDeactivated", target = "deactivated")
    void updateEntityFromDTO(CustomerDTO customerDTO, @MappingTarget CustomerEntity customerEntity);
    

    default void updateEntityFromDTONonNull(CustomerDTO customerDTO, @MappingTarget CustomerEntity customerEntity){
        if (customerDTO.getUsername() != null) {
            customerEntity.setUsername(customerDTO.getUsername());
        }
        if (customerDTO.getEmail() != null) {
            customerEntity.setEmail(customerDTO.getEmail());
        }
        if (customerDTO.getDateOfBirth() != null) {
            customerEntity.setDateOfBirth(customerDTO.getDateOfBirth());
        }
        if (customerDTO.getIsDeactivated() != null) {
            customerEntity.setDeactivated(customerDTO.getIsDeactivated());
        }
    }
}
