package com.bharath.Rating_Service.service;

import com.bharath.Rating_Service.exception.NotFoundException;
import com.bharath.Rating_Service.repository.RatingRepository;
import com.bharath.Rating_Service.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    @Autowired
    RatingRepository ratingRepository;

    public Rating fetchRating(String name){
        Rating rating = ratingRepository.findByName(name);

        if(rating == null){
            throw new NotFoundException("Movie not found with name: " + name);
        }

        return rating;
    }

    public Rating updateRating(String name, double stars) {
        Rating rating = ratingRepository.findByName(name);
        if (rating == null) {
            rating = new Rating();
            rating.setName(name);
            rating.setAvgRating(stars);
            rating.setCount(1);
        } else {
           int count = rating.getCount() ;

            double updatedRating = (rating.getAvgRating() * count + stars) / (count + 1);

            rating.setAvgRating(updatedRating);
            rating.setCount(++count);

        }
        return ratingRepository.save(rating);

    }
}
