package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @Mock
    private RatingRepository mockRatingRepository;

    @InjectMocks
    private RatingService ratingServiceUnderTest;

    @Test
    void testGetAllRatings() {
        // Setup
        // Configure RatingRepository.findAll(...).
        final List<Rating> ratings = List.of(new Rating("moodyRating", "sandPRating", "fitchRating", 0));
        when(mockRatingRepository.findAll()).thenReturn(ratings);

        // Run the test
        final List<Rating> result = ratingServiceUnderTest.getAllRatings();

        // Verify the results
    }

    @Test
    void testGetAllRatings_RatingRepositoryReturnsNoItems() {
        // Setup
        when(mockRatingRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<Rating> result = ratingServiceUnderTest.getAllRatings();

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void testGetRatingById() {
        // Setup
        // Configure RatingRepository.findById(...).
        final Optional<Rating> rating = Optional.of(new Rating("moodyRating", "sandPRating", "fitchRating", 0));
        when(mockRatingRepository.findById(0)).thenReturn(rating);

        // Run the test
        final Rating result = ratingServiceUnderTest.getRatingById(0);

        // Verify the results
    }

    @Test
    void testGetRatingById_RatingRepositoryReturnsAbsent() {
        // Setup
        when(mockRatingRepository.findById(0)).thenReturn(Optional.empty());

        // Run the test
        final Rating result = ratingServiceUnderTest.getRatingById(0);

        // Verify the results
    }

    @Test
    void testSaveRating() {
        // Setup
        final Rating rating = new Rating("moodyRating", "sandPRating", "fitchRating", 0);

        // Configure RatingRepository.save(...).
        final Rating rating1 = new Rating("moodyRating", "sandPRating", "fitchRating", 0);
        when(mockRatingRepository.save(any(Rating.class))).thenReturn(rating1);

        // Run the test
        ratingServiceUnderTest.saveRating(rating);

        // Verify the results
        verify(mockRatingRepository).save(any(Rating.class));
    }

    @Test
    void testUpdateRating() {
        // Setup
        final Rating rating = new Rating("moodyRating", "sandPRating", "fitchRating", 0);

        // Configure RatingRepository.findById(...).
        final Optional<Rating> rating1 = Optional.of(new Rating("moodyRating", "sandPRating", "fitchRating", 0));
        when(mockRatingRepository.findById(0)).thenReturn(rating1);

        // Configure RatingRepository.save(...).
        final Rating rating2 = new Rating("moodyRating", "sandPRating", "fitchRating", 0);
        when(mockRatingRepository.save(any(Rating.class))).thenReturn(rating2);

        // Run the test
        final boolean result = ratingServiceUnderTest.updateRating(0, rating);

        // Verify the results
        assertTrue(result);
        verify(mockRatingRepository).save(any(Rating.class));
    }

    @Test
    void testUpdateRating_RatingRepositoryFindByIdReturnsAbsent() {
        // Setup
        final Rating rating = new Rating("moodyRating", "sandPRating", "fitchRating", 0);
        when(mockRatingRepository.findById(0)).thenReturn(Optional.empty());

        // Configure RatingRepository.save(...).
        final Rating rating1 = new Rating("moodyRating", "sandPRating", "fitchRating", 0);
        when(mockRatingRepository.save(any(Rating.class))).thenReturn(rating1);

        // Run the test
        final boolean result = ratingServiceUnderTest.updateRating(0, rating);

        // Verify the results
        assertTrue(result);
        verify(mockRatingRepository).save(any(Rating.class));
    }

    @Test
    void testDeleteRatingById() {
        // Setup
        // Configure RatingRepository.findById(...).
        final Optional<Rating> rating = Optional.of(new Rating("moodyRating", "sandPRating", "fitchRating", 0));
        when(mockRatingRepository.findById(0)).thenReturn(rating);

        // Run the test
        ratingServiceUnderTest.deleteRatingById(0);

        // Verify the results
        verify(mockRatingRepository).delete(any(Rating.class));
    }

    @Test
    void testDeleteRatingById_RatingRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockRatingRepository.findById(0)).thenReturn(Optional.empty());

        // Run the test
        ratingServiceUnderTest.deleteRatingById(0);

        // Verify the results
        verify(mockRatingRepository).delete(any(Rating.class));
    }
}
