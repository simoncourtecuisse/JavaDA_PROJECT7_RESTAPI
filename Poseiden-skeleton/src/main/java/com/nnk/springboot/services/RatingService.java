package com.nnk.springboot.services;


import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RatingService {

    Logger LOGGER = LogManager.getLogger(RatingService.class);

    @Autowired
    private RatingRepository ratingRepository;

    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    public Rating getRatingById(Integer id) {
        Optional<Rating> rating = ratingRepository.findById(id);
        if (rating.isPresent()) {
            LOGGER.info("Rating's successfully found");
            return rating.get();
        } else {
            LOGGER.error("Failed to find Rating");
            return null;
        }
    }

    public Rating saveRating(Rating rating) {
        LOGGER.info("Rating successfully created");
        return ratingRepository.save(rating);
    }

    public boolean updateRating(Integer id, Rating rating) {
        boolean updated = false;
        Optional<Rating> refRating = ratingRepository.findById(id);
        if (refRating.isPresent()) {
            Rating updatedRating = refRating.get();
            updatedRating.setMoodyRating(rating.getMoodyRating());
            updatedRating.setSandPRating(rating.getSandPRating());
            updatedRating.setFitchRating(rating.getFitchRating());
            updatedRating.setOrderNumber(rating.getOrderNumber());
            ratingRepository.save(updatedRating);
            updated = true;
            LOGGER.info("Rating's successfully updated");
        } else {
            LOGGER.error("Failed to update Rating");
        }
        return updated;
    }

    public void deleteRatingById(Integer id) {
        Optional<Rating> removeRating = ratingRepository.findById(id);
        if (removeRating.isPresent()) {
            ratingRepository.delete(removeRating.get());
            LOGGER.info("Bid List's successfully deleted");
        } else {
            LOGGER.error("Failed to delete Bid List");
        }
    }
}
