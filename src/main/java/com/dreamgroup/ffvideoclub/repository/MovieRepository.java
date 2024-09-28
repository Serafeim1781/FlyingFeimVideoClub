package com.dreamgroup.ffvideoclub.repository;

import com.dreamgroup.ffvideoclub.model.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

}
