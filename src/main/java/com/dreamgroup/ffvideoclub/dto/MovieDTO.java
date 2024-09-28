package com.dreamgroup.ffvideoclub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {
    private Long movieId;
    private String title;
    private Year releaseYear;
    private Integer availableStock;
    private Integer totalStock;
}
