package com.dreamgroup.ffvideoclub.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "rent_records")
public class RentalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false, updatable = false)
    private LocalDate rentalDate;

    @Column(updatable = false)
    private LocalDate returnDate;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private MovieEntity movie;

    public boolean isComplete() {
        return !Objects.isNull(returnDate);
    }

    public void setReturnDate(LocalDate returnDate) {
        if (isComplete()) {
            return;
        }

        this.returnDate = returnDate.isBefore(this.rentalDate) ? this.rentalDate : returnDate;
    }

}
