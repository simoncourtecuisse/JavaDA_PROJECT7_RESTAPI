package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class CurvePointService {

    Logger LOGGER = LogManager.getLogger(CurvePointService.class);

    @Autowired
    private CurvePointRepository curvePointRepository;

    public List<CurvePoint> getAllCurvePoints() {
        return curvePointRepository.findAll();
    }

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

    public void saveCurvePoint(CurvePoint curvePoint) {
        curvePointRepository.save(curvePoint);
        LOGGER.info("Curve Point's successfully created");
    }

    public boolean updateCurvePoint(Integer id, CurvePoint curvePoint) {
        boolean updated = false;
        Optional<CurvePoint> refCurvePoint = curvePointRepository.findById(id);
        if (refCurvePoint.isPresent()) {
            CurvePoint updatedCurvePoint = refCurvePoint.get();
            updatedCurvePoint.setCurveId(curvePoint.getCurveId());
            updatedCurvePoint.setTerm(curvePoint.getTerm());
            updatedCurvePoint.setValue(curvePoint.getValue());
            curvePointRepository.save(updatedCurvePoint);
            updated = true;
            LOGGER.info("Curve Point's successfully updated");
        } else {
            LOGGER.error("Failed to update Curve Point");
        }
        return updated;
    }

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
