package com.nnk.springboot.services;


import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * The service provides methods that get called by the RatingController for CRUD operations.
 *
 * @author SimonC.
 * @version 1.0
 * @see RatingController
 * @see RatingRepository
 */
@Service
@Transactional
public class RatingService {

    Logger LOGGER = LogManager.getLogger(RatingService.class);

    @Autowired
    private RatingRepository ratingRepository;

    /**
     * Get all the Ratings contained in the database.
     *
     * @return A list of all the ratings.
     */
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    /**
     * Get a rating from its id.
     *
     * @param id The rating id to get.
     * @return An optional which may or may not contain the rating. If a rating is present, isPresent() returns true. If no rating is present, the object is considered empty and isPresent() returns false.
     */
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

    /**
     * Save a rating.
     *
     * @param rating The rating to save.
     * @return The rating saved.
     */
    public Rating saveRating(Rating rating) {
        LOGGER.info("Rating successfully created");
        return ratingRepository.save(rating);
    }

    /**
     * Update a rating.
     *
     * @param id     The rating id to update.
     * @param rating The rating to update.
     * @return The updated rating saved.
     */
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

    /**
     * Delete a rating.
     *
     * @param id The rating id to delete.
     */
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
