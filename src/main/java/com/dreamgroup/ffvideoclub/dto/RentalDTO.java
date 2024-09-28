package com.dreamgroup.ffvideoclub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalDTO {
    private Long rentalId;
    private Long movieId;
    private Long customerId;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private Double rentalCost;
    private Double amountPaid;
    private Double changeDue;
}
