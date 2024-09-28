package com.dreamgroup.ffvideoclub.service;

import com.dreamgroup.ffvideoclub.dto.RentalDTO;
import com.dreamgroup.ffvideoclub.exception.InsufficientPaymentException;
import com.dreamgroup.ffvideoclub.exception.InvalidResourceException;
import com.dreamgroup.ffvideoclub.exception.ResourceNotFoundException;
import com.dreamgroup.ffvideoclub.mapper.RentalMapper;
import com.dreamgroup.ffvideoclub.model.CustomerEntity;
import com.dreamgroup.ffvideoclub.model.MovieEntity;
import com.dreamgroup.ffvideoclub.model.RentalEntity;
import com.dreamgroup.ffvideoclub.repository.CustomerRepository;
import com.dreamgroup.ffvideoclub.repository.MovieRepository;
import com.dreamgroup.ffvideoclub.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final MovieRepository movieRepository;
    private final CustomerRepository customerRepository;
    private final RentalMapper rentalMapper;

    @Autowired
    public RentalService(RentalRepository rentalRepository,
                         MovieRepository movieRepository,
                         CustomerRepository customerRepository,
                         RentalMapper rentalMapper
                         ) {
        this.rentalRepository = rentalRepository;
        this.movieRepository = movieRepository;
        this.customerRepository = customerRepository;
        this.rentalMapper = rentalMapper;
    }

    
    //TODO: Tests with junit and mockito
    /**
     * Creates a new rental record on the database.
     *
     * @param rentalDTO the rental data transfer object
     *                  values for customerID and movieID are needed
     * @return the created rental data transfer object
     *                  values on ID, customerID, movieID, and  rentedDate are filled the rest are null
     * @throws ResourceNotFoundException if the customer or movie is not found, or if no available copies of the movie
     */
    @Transactional
    public RentalDTO createRental(RentalDTO rentalDTO) throws ResourceNotFoundException { //Can rentalDTO be null??

        CustomerEntity customer = customerRepository.findById(rentalDTO.getCustomerId()).orElseThrow(() -> new
                ResourceNotFoundException("Customer not found in the database."));

        MovieEntity movie = movieRepository.findById(rentalDTO.getMovieId()).orElseThrow(() -> new
                ResourceNotFoundException("Movie not found in the database."));


        if (movie.getAvailableStock() == 0) {
            // Maybe needs a custom message... with OK response.
            // or a not found response??
            throw new ResourceNotFoundException("No available copies at the moment.");
        }

        // update stock of movie
        movie.setAvailableStock(movie.getAvailableStock() - 1);
        movieRepository.save(movie);

        RentalEntity rental = new RentalEntity(
                (rentalDTO.getRentalDate() != null) ? (rentalDTO.getRentalDate()) : (LocalDate.now()),
                customer,
                movie);

        //Save the rental on DB and update the rental var with the newly acquired id
        rental = rentalRepository.save(rental);

        return rentalMapper.rentalEntityToRentalDto(rental);
    }

    /**
     * Completes a rental, after customer return of the movie, and sufficient payment.
     *
     * @param rentalDTO the rental data transfer object
     *                  values for ID and paid are needed
     * @return the updated rental data transfer object
     *                  all the available values are filled
     * @throws ResourceNotFoundException if the rental record is not found
     * @throws InsufficientPaymentException if the payment is insufficient
     * @throws InvalidResourceException if the rental is already complete
     */
    @Transactional
    public RentalDTO returnRental(RentalDTO rentalDTO) throws ResourceNotFoundException, InsufficientPaymentException, InvalidResourceException {

        RentalEntity rental = rentalRepository.findById(rentalDTO.getRentalId()).orElseThrow(() -> new
                ResourceNotFoundException("No record for that rental."));

        if (rental.isComplete()) {
//            return rentalMapper.rentalEntityToRentalDto(rentalEntity);
            throw new InvalidResourceException("This movie has already been return.");
        }

        rental.setReturnDate(LocalDate.now());
//        if (rental.getCost() > rentalDTO.getAmountPaid()) {
        if (rentalMapper.calculateCost(rental) > rentalDTO.getAmountPaid()) {
            throw new InsufficientPaymentException("Insufficient payment.");
        }

        MovieEntity movie = rental.getMovie();
//        Maybe I should make a check that the movie actually exist in the DB and not been removed
        movie.setAvailableStock(movie.getAvailableStock() + 1);
        movieRepository.save(movie);

        rentalRepository.save(rental);

        return createRentalPaymentReplyDTO(rental, rentalDTO);
    }


    /*----------------------- Private methods ----------------------*/

    /**
     * Calculates the change to be returned to the customer.
     *
     * @param cost the cost of the rental
     * @param payment the payment made by the customer
     * @return the change to be returned, or null if payment is insufficient
     */
    private Double calculateChange(Double cost, Double payment) {

        return (payment <= cost) ? null : (payment - cost);
    }

    //TODO: evaluate if it needs to be transferred to RentalMapper
    /**
     * Creates a rental payment reply DTO.
     *
     * @param rentalEntity the rental entity
     * @param requestDTO the rental request data transfer object
     * @return the rental payment reply data transfer object
     */
    private RentalDTO createRentalPaymentReplyDTO(RentalEntity rentalEntity, RentalDTO requestDTO) {
        double rentalCost = rentalMapper.calculateCost(rentalEntity);

        RentalDTO replyDTO = rentalMapper.rentalEntityToRentalDto(rentalEntity);
        replyDTO.setAmountPaid(requestDTO.getAmountPaid());
        replyDTO.setChangeDue(calculateChange(rentalCost, requestDTO.getAmountPaid()));
        return replyDTO;
    }
}
