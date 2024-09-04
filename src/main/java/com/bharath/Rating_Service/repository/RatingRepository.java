package com.bharath.Rating_Service.repository;

import com.bharath.Rating_Service.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository <Rating, Long>{

    Rating findByName(String name);

}
