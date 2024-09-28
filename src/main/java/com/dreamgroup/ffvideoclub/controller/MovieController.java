package com.dreamgroup.ffvideoclub.controller;

import com.dreamgroup.ffvideoclub.dto.MovieDTO;
import com.dreamgroup.ffvideoclub.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {

        this.movieService = movieService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> movieInfo(@PathVariable("id") Long id) {

        return new ResponseEntity<>(movieService.showInfo(id), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<MovieDTO>> bunchMovieInfo(@RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer pageSize) {

        return new ResponseEntity<>(movieService.bunchMovieInfo(pageNumber, pageSize), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addMovie(@RequestBody MovieDTO movieDTO) {

        return new ResponseEntity<>(movieService.addMovie(movieDTO), HttpStatus.OK);
    }

    @PostMapping("/list-add")
    public ResponseEntity<String> addMovieList(@RequestBody List<MovieDTO> movieDTOList) {

        return new ResponseEntity<>(movieService.addMovies(movieDTOList), HttpStatus.OK);

    }
}