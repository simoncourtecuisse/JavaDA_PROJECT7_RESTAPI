package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TradeService {

    Logger LOGGER = LogManager.getLogger(TradeService.class);

    @Autowired
    private TradeRepository tradeRepository;

    public List<Trade> getAllTrades() {
        return tradeRepository.findAll();
    }

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

    public void saveTrade(Trade trade) {
        tradeRepository.save(trade);
        LOGGER.info("Trade's successfully created");
    }

    public boolean updateTrade(Integer id, Trade trade) {
        boolean updated = false;
        Optional<Trade> refTrade = tradeRepository.findById(id);
        if (refTrade.isPresent()) {
            Trade updatedTrade = refTrade.get();
            updatedTrade.setAccount(trade.getAccount());
            updatedTrade.setType(trade.getType());
            tradeRepository.save(updatedTrade);
            updated = true;
            LOGGER.info("Trade's successfully updated");
        } else {
            LOGGER.error("Failed to update Trade");
        }
        return updated;
    }

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
