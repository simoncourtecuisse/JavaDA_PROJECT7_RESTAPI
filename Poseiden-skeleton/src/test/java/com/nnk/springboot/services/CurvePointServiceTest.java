package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
public class CurvePointServiceTest {

    @Mock
    private CurvePointRepository mockCurvePointRepository;

    @InjectMocks
    private CurvePointService curvePointServiceUnderTest;

    @Test
    void testGetAllCurvePoints() {
        // Setup
        when(mockCurvePointRepository.findAll()).thenReturn(List.of(new CurvePoint(0, 0.0, 0.0)));

        // Run the test
        final List<CurvePoint> result = curvePointServiceUnderTest.getAllCurvePoints();

        // Verify the results
    }

    @Test
    void testGetAllCurvePoints_CurvePointRepositoryReturnsNoItems() {
        // Setup
        when(mockCurvePointRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<CurvePoint> result = curvePointServiceUnderTest.getAllCurvePoints();

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void testGetCurvePointById() {
        // Setup
        when(mockCurvePointRepository.findById(0)).thenReturn(Optional.of(new CurvePoint(0, 0.0, 0.0)));

        // Run the test
        final CurvePoint result = curvePointServiceUnderTest.getCurvePointById(0);

        // Verify the results
    }

    @Test
    void testGetCurvePointById_CurvePointRepositoryReturnsAbsent() {
        // Setup
        when(mockCurvePointRepository.findById(0)).thenReturn(Optional.empty());

        // Run the test
        final CurvePoint result = curvePointServiceUnderTest.getCurvePointById(0);

        // Verify the results
    }

    @Test
    void testSaveCurvePoint() {
        // Setup
        final CurvePoint curvePoint = new CurvePoint(0, 0.0, 0.0);
        when(mockCurvePointRepository.save(any(CurvePoint.class))).thenReturn(new CurvePoint(0, 0.0, 0.0));

        // Run the test
        curvePointServiceUnderTest.saveCurvePoint(curvePoint);

        // Verify the results
        verify(mockCurvePointRepository).save(any(CurvePoint.class));
    }

    @Test
    void testUpdateCurvePoint() {
        // Setup
        final CurvePoint curvePoint = new CurvePoint(0, 0.0, 0.0);
        when(mockCurvePointRepository.findById(0)).thenReturn(Optional.of(new CurvePoint(0, 0.0, 0.0)));
        when(mockCurvePointRepository.save(any(CurvePoint.class))).thenReturn(new CurvePoint(0, 0.0, 0.0));

        // Run the test
        final boolean result = curvePointServiceUnderTest.updateCurvePoint(0, curvePoint);

        // Verify the results
        assertTrue(result);
        verify(mockCurvePointRepository).save(any(CurvePoint.class));
    }

    @Test
    void testUpdateCurvePoint_CurvePointRepositoryFindByIdReturnsAbsent() {
        // Setup
        final CurvePoint curvePoint = new CurvePoint(0, 0.0, 0.0);
        when(mockCurvePointRepository.findById(0)).thenReturn(Optional.empty());
        when(mockCurvePointRepository.save(any(CurvePoint.class))).thenReturn(new CurvePoint(0, 0.0, 0.0));

        // Run the test
        final boolean result = curvePointServiceUnderTest.updateCurvePoint(0, curvePoint);

        // Verify the results
        assertTrue(result);
        verify(mockCurvePointRepository).save(any(CurvePoint.class));
    }

    @Test
    void testDeleteCurvePointById() {
        // Setup
        when(mockCurvePointRepository.findById(0)).thenReturn(Optional.of(new CurvePoint(0, 0.0, 0.0)));

        // Run the test
        curvePointServiceUnderTest.deleteCurvePointById(0);

        // Verify the results
        verify(mockCurvePointRepository).delete(any(CurvePoint.class));
    }

    @Test
    void testDeleteCurvePointById_CurvePointRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockCurvePointRepository.findById(0)).thenReturn(Optional.empty());

        // Run the test
        curvePointServiceUnderTest.deleteCurvePointById(0);

        // Verify the results
        verify(mockCurvePointRepository).delete(any(CurvePoint.class));
    }
}
