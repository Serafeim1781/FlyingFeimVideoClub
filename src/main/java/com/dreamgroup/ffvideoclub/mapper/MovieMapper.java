package com.dreamgroup.ffvideoclub.mapper;

import com.dreamgroup.ffvideoclub.dto.MovieDTO;
import com.dreamgroup.ffvideoclub.model.MovieEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    @Mapping(target = "movieId", source = "id")
    MovieDTO movieEntityToMovieDto(MovieEntity movieEntity);

    @Mapping(ignore = true, target = "rentals")
    @Mapping(target = "id", source = "movieId")
    MovieEntity movieDTOtoMovieEntity(MovieDTO movieDTO);

    List<MovieEntity> movieDTOListToEntityList(List<MovieDTO> movieDTOs);

    default List<MovieDTO> movieEntityPageToDtoList(Page<MovieEntity> movieEntityPage) {
        return movieEntityPage.stream()
                .map(this::movieEntityToMovieDto)
                .collect(Collectors.toList());
    }

    default void updateEntityFromDTONonNull(MovieDTO movieDTO, @MappingTarget MovieEntity movieEntity){
        if (movieDTO.getAvailableStock() != null) {
            movieEntity.setAvailableStock(movieDTO.getAvailableStock());
        }
        if (movieDTO.getTotalStock() != null) {
            movieEntity.setTotalStock(movieDTO.getTotalStock());
        }
    }
}
