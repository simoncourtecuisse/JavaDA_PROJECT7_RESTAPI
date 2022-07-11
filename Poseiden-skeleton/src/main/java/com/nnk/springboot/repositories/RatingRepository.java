package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Gives access to the JPA CRUD methods on the Rating entity.
 *
 * @author SimonC.
 * @version 1.0
 * @see Rating
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

}
