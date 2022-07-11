package com.nnk.springboot.services;

import com.nnk.springboot.controllers.CurvePointController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * The service provides methods that get called by the CurvePointController for CRUD operations.
 *
 * @author SimonC.
 * @version 1.0
 * @see CurvePointController
 * @see CurvePointRepository
 */
@Service
@Transactional
public class CurvePointService {

    Logger LOGGER = LogManager.getLogger(CurvePointService.class);
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formatDateTime = now.format(formatter);
    @Autowired
    private CurvePointRepository curvePointRepository;

    /**
     * Get all the curve points contained in the database.
     *
     * @return A list of all the curve points.
     */
    public List<CurvePoint> getAllCurvePoints() {
        return curvePointRepository.findAll();
    }

    /**
     * Get a curve point from its id.
     *
     * @param id The curve point id to get.
     * @return An optional which may or may not contain the curve point. If a curve point is present, isPresent() returns true. If no curve point is present, the object is considered empty and isPresent() returns false.
     */
    public CurvePoint getCurvePointById(Integer id) {
        Optional<CurvePoint> curvePoint = curvePointRepository.findById(id);
        if (curvePoint.isPresent()) {
            LOGGER.info("Curve Point's successfully found");
            return curvePoint.get();
        } else {
            LOGGER.error("Failed to find Curve Point");
            return null;
        }
    }

    /**
     * Save a curve point.
     * The creationDate is saved automatically.
     *
     * @param curvePoint The curve point to save.
     * @return The curve point saved.
     */
    public CurvePoint saveCurvePoint(CurvePoint curvePoint) {
        curvePoint.setCreationDate(Timestamp.valueOf(formatDateTime));
        LOGGER.info("Curve Point's successfully created");
        return curvePointRepository.save(curvePoint);
    }

    /**
     * Update a curve point.
     * The asOfDate is saved automatically.
     *
     * @param id         The curve point id to update.
     * @param curvePoint The curve point to update.
     * @return The updated curve point saved.
     */
    public boolean updateCurvePoint(Integer id, CurvePoint curvePoint) {
        boolean updated = false;
        Optional<CurvePoint> refCurvePoint = curvePointRepository.findById(id);
        if (refCurvePoint.isPresent()) {
            CurvePoint updatedCurvePoint = refCurvePoint.get();
            updatedCurvePoint.setCurveId(curvePoint.getCurveId());
            updatedCurvePoint.setTerm(curvePoint.getTerm());
            updatedCurvePoint.setValue(curvePoint.getValue());
            updatedCurvePoint.setAsOfDate(Timestamp.valueOf(formatDateTime));
            curvePointRepository.save(updatedCurvePoint);
            updated = true;
            LOGGER.info("Curve Point's successfully updated");
        } else {
            LOGGER.error("Failed to update Curve Point");
        }
        return updated;
    }

    /**
     * Delete a curve point.
     *
     * @param id The curve point id to delete.
     */
    public void deleteCurvePointById(Integer id) {
        Optional<CurvePoint> removeCurvePoint = curvePointRepository.findById(id);
        if (removeCurvePoint.isPresent()) {
            curvePointRepository.delete(removeCurvePoint.get());
            LOGGER.info("Curve Point's successfully deleted");
        } else {
            LOGGER.error("Failed to delete Curve Point");
        }
    }
}
