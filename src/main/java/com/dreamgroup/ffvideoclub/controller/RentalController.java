package com.dreamgroup.ffvideoclub.controller;

import com.dreamgroup.ffvideoclub.dto.RentalDTO;
import com.dreamgroup.ffvideoclub.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rental")
public class RentalController {

    private final RentalService rentalService;

    @Autowired
    RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("/new")
    public ResponseEntity<RentalDTO> createRental(@RequestBody RentalDTO rentalDTO) {
        /* json example
        {
            "id": null,
            "movieId": "1",
            "customerId": "1",
            "rentalDate": null
        }
         */
        return new ResponseEntity<>(rentalService.createRental(rentalDTO), HttpStatus.OK);
    }

    @PatchMapping("/return")
    public ResponseEntity<RentalDTO> returnRental(@RequestBody RentalDTO rentalDTO) {

        return new ResponseEntity<>(rentalService.returnRental(rentalDTO), HttpStatus.OK);
    }
}
    /*------------- Exception Handlers ----------------*/

