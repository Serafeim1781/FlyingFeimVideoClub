package com.dreamgroup.ffvideoclub.service;

import com.dreamgroup.ffvideoclub.dto.MovieDTO;
import com.dreamgroup.ffvideoclub.exception.InvalidResourceException;
import com.dreamgroup.ffvideoclub.exception.ResourceNotFoundException;
import com.dreamgroup.ffvideoclub.mapper.MovieMapper;
import com.dreamgroup.ffvideoclub.model.MovieEntity;
import com.dreamgroup.ffvideoclub.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    private final MovieMapper movieMapper;

    @Autowired
    public MovieService(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }


    //TODO: Tests with junit and mockito

    public MovieDTO showInfo(Long id) throws ResourceNotFoundException {

        if (id == null) {
            throw new InvalidResourceException("Requested to Search on null.");
        }

        MovieEntity movieEntity = movieRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("There is no movie with this id."));

        return movieMapper.movieEntityToMovieDto(movieEntity);
    }

    public List<MovieDTO> bunchMovieInfo(Integer pageNumber, Integer pageSize) {

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = 0;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 20;
        }

        Page<MovieEntity> movieEntityPage = movieRepository.findAll(PageRequest.of(pageNumber, pageSize));

        return movieMapper.movieEntityPageToDtoList(movieEntityPage);
    }

    @Transactional
    public String addMovie(MovieDTO movieDTO) throws InvalidResourceException, ResourceNotFoundException {

        if (movieDTO == null || movieDTO.getMovieId() == null && movieDTO.getTitle() == null) {
            throw new InvalidResourceException("Requested to Search on null.");
        }

        if (movieDTO.getMovieId() != null) {
            MovieEntity movieEntity= movieRepository.findById(movieDTO.getMovieId()).orElseThrow(() ->
                    new ResourceNotFoundException("There is no movie with that id."));

            // assumed that there will be a stock addition
            movieEntity.setAvailableStock(movieEntity.getAvailableStock() + movieDTO.getAvailableStock());
            movieEntity.setTotalStock(movieEntity.getTotalStock() + movieDTO.getTotalStock());
            movieRepository.save(movieEntity);

            return "Movie already on DB. Stock updated.";
        }

        movieRepository.save(movieMapper.movieDTOtoMovieEntity(movieDTO));

        return "Movie added.";
    }

    @Transactional
    public String addMovies(List<MovieDTO> movieDTOs) {

        movieRepository.saveAll(movieMapper.movieDTOListToEntityList(movieDTOs));

        return "Movies added.";
    }

//    @Transactional
//    public String updateMovie(MovieDTO movieDTO) {
//
//        if (movieDTO == null || movieDTO.getMovieId() == null && movieDTO.getTitle() == null) {
//            throw new InvalidResourceException("Requested to Search on null.");
//        }
//
//        return "Stock updated.";
//    }


    /*----------------------- Private methods ----------------------*/

}
