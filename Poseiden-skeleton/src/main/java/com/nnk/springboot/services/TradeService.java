package com.nnk.springboot.services;

import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * The service provides methods that get called by the TradeController for CRUD operations.
 *
 * @author SimonC.
 * @version 1.0
 * @see TradeController
 * @see TradeRepository
 */
@Service
@Transactional
public class TradeService {

    Logger LOGGER = LogManager.getLogger(TradeService.class);

    @Autowired
    private TradeRepository tradeRepository;

    /**
     * Get all the Trades contained in the database.
     *
     * @return A list of all the trades.
     */
    public List<Trade> getAllTrades() {
        return tradeRepository.findAll();
    }

    /**
     * Get a trade from its id.
     *
     * @param id The trade id to get.
     * @return An optional which may or may not contain the trade. If a trade is present, isPresent() returns true. If no trade is present, the object is considered empty and isPresent() returns false.
     */
    public Trade getTradeById(Integer id) {
        Optional<Trade> trade = tradeRepository.findById(id);
        if (trade.isPresent()) {
            LOGGER.info("Trade's successfully found");
            return trade.get();
        } else {
            LOGGER.error("Failed to find Trade");
            return null;
        }
    }

    /**
     * Save a trade.
     * The creationName and the creationDate are saved automatically.
     *
     * @param trade The trade to save.
     * @return The trade saved.
     */
    public Trade saveTrade(Trade trade) {
        trade.setCreationName("Trade_name_" + trade.getAccount());
        trade.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        LOGGER.info("Trade's successfully created");
        return tradeRepository.save(trade);
    }

    /**
     * Update a trade.
     * The revisionName and the revisionDate are updated and saved automatically.
     *
     * @param id    The trade id to update.
     * @param trade The trade to update.
     * @return The updated trade saved.
     */
    public boolean updateTrade(Integer id, Trade trade) {
        boolean updated = false;
        Optional<Trade> refTrade = tradeRepository.findById(id);
        if (refTrade.isPresent()) {
            Trade updatedTrade = refTrade.get();
            updatedTrade.setAccount(trade.getAccount());
            updatedTrade.setType(trade.getType());
            updatedTrade.setBuyQuantity(trade.getBuyQuantity());
            updatedTrade.setRevisionName("Updated_Trade_name_" + trade.getAccount());
            updatedTrade.setRevisionDate(Timestamp.valueOf(LocalDateTime.now()));
            tradeRepository.save(updatedTrade);
            updated = true;
            LOGGER.info("Trade's successfully updated");
        } else {
            LOGGER.error("Failed to update Trade");
        }
        return updated;
    }

    /**
     * Delete a trade.
     *
     * @param id The trade id to delete.
     */
    public void deleteTradeById(Integer id) {
        Optional<Trade> removeTrade = tradeRepository.findById(id);
        if (removeTrade.isPresent()) {
            tradeRepository.delete(removeTrade.get());
            LOGGER.info("Trade's successfully deleted");
        } else {
            LOGGER.error("Failed to delete Trade");
        }
    }
}
