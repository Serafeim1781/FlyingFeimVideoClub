package com.dreamgroup.ffvideoclub.mapper;

import com.dreamgroup.ffvideoclub.dto.RentalDTO;
import com.dreamgroup.ffvideoclub.model.RentalEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.temporal.ChronoUnit;

@Mapper(componentModel = "spring")
public interface RentalMapper {

    @Mapping(ignore = true, target = "amountPaid")
    @Mapping(ignore = true, target = "changeDue")
    @Mapping(target = "rentalCost", source = "rentalEntity", qualifiedByName = "calculateCost")
    @Mapping(target = "rentalId", source = "id")
    @Mapping(target = "movieId", source = "movie.id")
    @Mapping(target = "customerId", source = "customer.id")
    RentalDTO rentalEntityToRentalDto(RentalEntity rentalEntity);

    @Mapping(target = "rentalDate", source = "rentalDate", defaultExpression = "java(java.time.LocalDate.now())")
    RentalEntity rentalDTOtoRentalEntity(RentalDTO rentalDTO);


//    default void updateEntityFromDTONonNull(RentalDTO rentalDTO, @MappingTarget RentalEntity rentalEntity){
//        if (rentalDTO.() != null) {
//            rentalEntity.set(rentalDTO.());
//        }
//        if (rentalDTO.() != null) {
//            rentalEntity.set(rentalDTO.());
//        }
//        LocalDate.now();
//    }

    @Named("calculateCost")
    default public Double calculateCost(RentalEntity rentalEntity) {

        if (rentalEntity.getReturnDate() == null) {
            return null;
        }

        long days = ChronoUnit.DAYS.between(rentalEntity.getRentalDate(), rentalEntity.getReturnDate());

        return (3D + days * 2D);
    }
}
