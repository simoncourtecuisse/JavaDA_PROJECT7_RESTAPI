package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
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
public class BidListServiceTest {

    @Mock
    private BidListRepository mockBidListRepository;

    @InjectMocks
    private BidListService bidListServiceUnderTest;

    @Test
    void testGetAllBids() {
        // Setup
        // Configure BidListRepository.findAll(...).
        final List<BidList> bidLists = List.of(new BidList("account", "type", 0.0));
        when(mockBidListRepository.findAll()).thenReturn(bidLists);

        // Run the test
        final List<BidList> result = bidListServiceUnderTest.getAllBids();

        // Verify the results
    }

    @Test
    void testGetAllBids_BidListRepositoryReturnsNoItems() {
        // Setup
        when(mockBidListRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<BidList> result = bidListServiceUnderTest.getAllBids();

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void testGetBidById() {
        // Setup
        // Configure BidListRepository.findById(...).
        final Optional<BidList> bidList = Optional.of(new BidList("account", "type", 0.0));
        when(mockBidListRepository.findById(0)).thenReturn(bidList);

        // Run the test
        final BidList result = bidListServiceUnderTest.getBidById(0);

        // Verify the results
    }

    @Test
    void testGetBidById_BidListRepositoryReturnsAbsent() {
        // Setup
        when(mockBidListRepository.findById(0)).thenReturn(Optional.empty());

        // Run the test
        final BidList result = bidListServiceUnderTest.getBidById(0);

        // Verify the results
    }

    @Test
    void testSaveBidList() {
        // Setup
        final BidList bidList = new BidList("account", "type", 0.0);
        when(mockBidListRepository.save(any(BidList.class))).thenReturn(new BidList("account", "type", 0.0));

        // Run the test
        bidListServiceUnderTest.saveBidList(bidList);

        // Verify the results
        verify(mockBidListRepository).save(any(BidList.class));
    }

    @Test
    void testUpdateBidList() {
        // Setup
        final BidList bidList = new BidList("account", "type", 0.0);

        // Configure BidListRepository.findById(...).
        final Optional<BidList> bidList1 = Optional.of(new BidList("account", "type", 0.0));
        when(mockBidListRepository.findById(0)).thenReturn(bidList1);

        when(mockBidListRepository.save(any(BidList.class))).thenReturn(new BidList("account", "type", 0.0));

        // Run the test
        final boolean result = bidListServiceUnderTest.updateBidList(0, bidList);

        // Verify the results
        assertTrue(result);
        verify(mockBidListRepository).save(any(BidList.class));
    }

    @Test
    void testUpdateBidList_BidListRepositoryFindByIdReturnsAbsent() {
        // Setup
        final BidList bidList = new BidList("account", "type", 0.0);
        when(mockBidListRepository.findById(0)).thenReturn(Optional.empty());
        when(mockBidListRepository.save(any(BidList.class))).thenReturn(new BidList("account", "type", 0.0));

        // Run the test
        final boolean result = bidListServiceUnderTest.updateBidList(0, bidList);

        // Verify the results
        assertTrue(result);
        verify(mockBidListRepository).save(any(BidList.class));
    }

    @Test
    void testDeleteBidListById() {
        // Setup
        // Configure BidListRepository.findById(...).
        final Optional<BidList> bidList = Optional.of(new BidList("account", "type", 0.0));
        when(mockBidListRepository.findById(0)).thenReturn(bidList);

        // Run the test
        bidListServiceUnderTest.deleteBidListById(0);

        // Verify the results
        verify(mockBidListRepository).delete(any(BidList.class));
    }

    @Test
    void testDeleteBidListById_BidListRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockBidListRepository.findById(0)).thenReturn(Optional.empty());

        // Run the test
        bidListServiceUnderTest.deleteBidListById(0);

        // Verify the results
        verify(mockBidListRepository).delete(any(BidList.class));
    }
}
