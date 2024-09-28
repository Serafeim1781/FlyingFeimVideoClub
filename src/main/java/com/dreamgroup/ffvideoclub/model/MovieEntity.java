package com.dreamgroup.ffvideoclub.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

import java.time.Year;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "movies")
public class MovieEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String title;

    @NonNull
    @Column(nullable = false, updatable = false)
    private Year releaseYear;

    @NonNull
    @Column(nullable = false)
    private Integer availableStock;

    @NonNull
    @Column(nullable = false)
    private Integer totalStock;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<RentalEntity> rentals;
}

