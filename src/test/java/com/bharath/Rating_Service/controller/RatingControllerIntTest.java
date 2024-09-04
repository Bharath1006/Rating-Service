package com.bharath.Rating_Service.controller;

import com.bharath.Rating_Service.model.Rating;
import com.bharath.Rating_Service.model.RatingRequest;
import com.bharath.Rating_Service.repository.RatingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RatingControllerIntTest {

@Autowired
    MockMvc mockMvc;

@Autowired
    RatingRepository ratingRepository;

@Autowired
ObjectMapper objectMapper;
    //create

    @BeforeEach
    void cleanUp(){
        ratingRepository.deleteAllInBatch();
    }

@Test
    void givenGetRating_whenRatingfetched_returnRatedMovie() throws Exception {

        //givenGetMovie
        Rating rating = new Rating();
        rating.setName("rrr");
        rating.setAvgRating(2.5);
        rating.setCount(1);
        Rating ratedMovie = ratingRepository.save(rating);

        //whenfetched
        var resultActions = mockMvc.perform(get("/ratings/" + ratedMovie.getName()));

        //returnFetchedMovie
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(ratedMovie.getId())))
                .andExpect(jsonPath("$.name", is(ratedMovie.getName())))
                .andExpect((jsonPath("$.avgRating", is(ratedMovie.getAvgRating()))))
                .andExpect(jsonPath("$.count", is((ratedMovie.getCount()))));

    }

    @Test
    void givenRatingUpdateMovie_whenRatingUpdated_returnRatingUpdatedMovie() throws Exception {

        //givenGetMovie
        Rating rating = new Rating();
        rating.setName("rrr");
        rating.setAvgRating(3.0);
        rating.setCount(0);
        Rating rated = ratingRepository.save(rating);

        RatingRequest request = new RatingRequest();
        request.setName("rrr");
        request.setStars(4.0);

        var movieRatingUpdated = mockMvc.perform(post("/ratings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        movieRatingUpdated.andDo(print())
                .andExpect(status().isOk());

        var fetchedRatedMovies = mockMvc.perform(get("/ratings/" + rating.getName() ));
        fetchedRatedMovies.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(rated.getId())))
                .andExpect(jsonPath("$.name", is(rated.getName())));

    }
}