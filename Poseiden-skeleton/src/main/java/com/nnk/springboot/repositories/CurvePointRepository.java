package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Gives access to the JPA CRUD methods on the CurvePoint entity.
 *
 * @author SimonC.
 * @version 1.0
 * @see CurvePoint
 */
@Repository
public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

}
