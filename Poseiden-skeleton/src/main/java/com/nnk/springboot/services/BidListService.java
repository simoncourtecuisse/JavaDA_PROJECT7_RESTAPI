package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BidListService {

    Logger LOGGER = LogManager.getLogger(BidListService.class);

    @Autowired
    private BidListRepository bidListRepository;

    public List<BidList> getAllBids() {
        return bidListRepository.findAll();
    }

//    public BidList getBidById(Integer bidListId) {
//        return bidListRepository.findById(bidListId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid Bid Id: " + bidListId)); // -> avoid Optional type
//    }

    public BidList getBidById(Integer id) {
        Optional<BidList> bidList = bidListRepository.findById(id);
        if (bidList.isPresent()) {
            LOGGER.info("Bid List's successfully found");
            return bidList.get();
        } else {
            LOGGER.error("Failed to find Bid List");
            return null;
        }
    }

    public BidList saveBidList(BidList bidList) {
        bidList.setBidListDate(Timestamp.valueOf(LocalDateTime.now()));
        LOGGER.info("Bid List's successfully created");
        return bidListRepository.save(bidList);
    }

    public boolean updateBidList(Integer id, BidList bidList) {
        boolean updated = false;
        Optional<BidList> refList = bidListRepository.findById(id);
        if (refList.isPresent()) {
            BidList updatedBidList = refList.get();
            updatedBidList.setAccount(bidList.getAccount());
            updatedBidList.setType(bidList.getType());
            updatedBidList.setBidQuantity(bidList.getBidQuantity());
            updatedBidList.setRevisionDate(Timestamp.valueOf(LocalDateTime.now()));
            bidListRepository.save(updatedBidList);
            updated = true;
            LOGGER.info("Bid List's successfully updated");
        } else {
            LOGGER.error("Failed to update Bid List");
        }
        return updated;
    }

    public void deleteBidListById(Integer id) {
        Optional<BidList> removeBidList = bidListRepository.findById(id);
        if (removeBidList.isPresent()) {
            bidListRepository.delete(removeBidList.get());
            LOGGER.info("Bid List's successfully deleted");
        } else {
            LOGGER.error("Failed to delete Bid List");
        }
    }
}
