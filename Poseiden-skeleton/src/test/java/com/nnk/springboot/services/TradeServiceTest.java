package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
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
public class TradeServiceTest {

    @Mock
    private TradeRepository mockTradeRepository;

    @InjectMocks
    private TradeService tradeServiceUnderTest;

    @Test
    void testGetAllTrades() {
        // Setup
        when(mockTradeRepository.findAll()).thenReturn(List.of(new Trade("account", "type")));

        // Run the test
        final List<Trade> result = tradeServiceUnderTest.getAllTrades();

        // Verify the results
    }

    @Test
    void testGetAllTrades_TradeRepositoryReturnsNoItems() {
        // Setup
        when(mockTradeRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<Trade> result = tradeServiceUnderTest.getAllTrades();

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void testGetTradeById() {
        // Setup
        // Configure TradeRepository.findById(...).
        final Optional<Trade> trade = Optional.of(new Trade("account", "type"));
        when(mockTradeRepository.findById(0)).thenReturn(trade);

        // Run the test
        final Trade result = tradeServiceUnderTest.getTradeById(0);

        // Verify the results
    }

    @Test
    void testGetTradeById_TradeRepositoryReturnsAbsent() {
        // Setup
        when(mockTradeRepository.findById(0)).thenReturn(Optional.empty());

        // Run the test
        final Trade result = tradeServiceUnderTest.getTradeById(0);

        // Verify the results
    }

    @Test
    void testSaveTrade() {
        // Setup
        final Trade trade = new Trade("account", "type");
        when(mockTradeRepository.save(any(Trade.class))).thenReturn(new Trade("account", "type"));

        // Run the test
        tradeServiceUnderTest.saveTrade(trade);

        // Verify the results
        verify(mockTradeRepository).save(any(Trade.class));
    }

    @Test
    void testUpdateTrade() {
        // Setup
        final Trade trade = new Trade("account", "type");

        // Configure TradeRepository.findById(...).
        final Optional<Trade> trade1 = Optional.of(new Trade("account", "type"));
        when(mockTradeRepository.findById(0)).thenReturn(trade1);

        when(mockTradeRepository.save(any(Trade.class))).thenReturn(new Trade("account", "type"));

        // Run the test
        final boolean result = tradeServiceUnderTest.updateTrade(0, trade);

        // Verify the results
        assertTrue(result);
        verify(mockTradeRepository).save(any(Trade.class));
    }

    @Test
    void testUpdateTrade_TradeRepositoryFindByIdReturnsAbsent() {
        // Setup
        final Trade trade = new Trade("account", "type");
        when(mockTradeRepository.findById(0)).thenReturn(Optional.empty());
        when(mockTradeRepository.save(any(Trade.class))).thenReturn(new Trade("account", "type"));

        // Run the test
        final boolean result = tradeServiceUnderTest.updateTrade(0, trade);

        // Verify the results
        assertTrue(result);
        verify(mockTradeRepository).save(any(Trade.class));
    }

    @Test
    void testDeleteTradeById() {
        // Setup
        // Configure TradeRepository.findById(...).
        final Optional<Trade> trade = Optional.of(new Trade("account", "type"));
        when(mockTradeRepository.findById(0)).thenReturn(trade);

        // Run the test
        tradeServiceUnderTest.deleteTradeById(0);

        // Verify the results
        verify(mockTradeRepository).delete(any(Trade.class));
    }

    @Test
    void testDeleteTradeById_TradeRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockTradeRepository.findById(0)).thenReturn(Optional.empty());

        // Run the test
        tradeServiceUnderTest.deleteTradeById(0);

        // Verify the results
        verify(mockTradeRepository).delete(any(Trade.class));
    }
}
