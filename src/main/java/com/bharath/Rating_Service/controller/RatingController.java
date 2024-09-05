package com.bharath.Rating_Service.controller;

import lombok.extern.slf4j.Slf4j;
import com.bharath.Rating_Service.model.Rating;
import com.bharath.Rating_Service.model.RatingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bharath.Rating_Service.service.RatingService;


@Slf4j
@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    RatingService ratingService;

    @GetMapping("/{name}")
    public ResponseEntity<Rating> getRating(@PathVariable String name){
        Rating rating = ratingService.fetchRating(name);
        log.info("Returning rating for movie: {}", name);
        return ResponseEntity.ok(rating);
    }

    @PostMapping
    public ResponseEntity<Rating> UpdateRating(@RequestBody RatingRequest request){
       Rating rating = ratingService.updateRating(request.getName(), request.getStars());
        log.info("Returned new average for movie {}", request.getName());
        return ResponseEntity.ok(rating);
    }
}
