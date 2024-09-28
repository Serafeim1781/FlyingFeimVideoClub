package com.dreamgroup.ffvideoclub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private Long customerId;
    private String username;
    private String email;
    private LocalDate dateOfBirth;
    private Boolean isDeactivated;
}
