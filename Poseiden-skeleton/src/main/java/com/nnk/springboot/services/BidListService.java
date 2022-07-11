package com.nnk.springboot.services;

import com.nnk.springboot.controllers.BidListController;
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

/**
 * The service provides methods that get called by the BidListController for CRUD operations.
 *
 * @author SimonC.
 * @version 1.0
 * @see BidListController
 * @see BidListRepository
 */
@Service
@Transactional
public class BidListService {

    Logger LOGGER = LogManager.getLogger(BidListService.class);

    @Autowired
    private BidListRepository bidListRepository;

    /**
     * Get all the BidLists contained in the database.
     *
     * @return A list of all the bid lists.
     */
    public List<BidList> getAllBids() {
        return bidListRepository.findAll();
    }

    /**
     * Get a bid list from its id.
     *
     * @param id The bid list id to get.
     * @return An optional which may or may not contain the bid list. If a bid list is present, isPresent() returns true. If no bid list is present, the object is considered empty and isPresent() returns false.
     */
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

    /**
     * Save a bid list.
     * The setBidListDate, the creationName and the creationDate are saved automatically.
     *
     * @param bidList The bid list to save.
     * @return The bid list saved.
     */
    public BidList saveBidList(BidList bidList) {
        bidList.setBidListDate(Timestamp.valueOf(LocalDateTime.now()));
        bidList.setCreationName("BidList_name_" + bidList.getAccount());
        bidList.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        LOGGER.info("Bid List's successfully created");
        return bidListRepository.save(bidList);
    }

    /**
     * Update a bid list.
     * The revisionDate and the revisionName are updated and saved automatically.
     *
     * @param id      The bid list id to update.
     * @param bidList The bid list to update.
     * @return The updated bid list saved.
     */
    public boolean updateBidList(Integer id, BidList bidList) {
        boolean updated = false;
        Optional<BidList> refList = bidListRepository.findById(id);
        if (refList.isPresent()) {
            BidList updatedBidList = refList.get();
            updatedBidList.setAccount(bidList.getAccount());
            updatedBidList.setType(bidList.getType());
            updatedBidList.setBidQuantity(bidList.getBidQuantity());
            updatedBidList.setRevisionName("Updated_BidList_name_" + bidList.getAccount());
            updatedBidList.setRevisionDate(Timestamp.valueOf(LocalDateTime.now()));
            bidListRepository.save(updatedBidList);
            updated = true;
            LOGGER.info("Bid List's successfully updated");
        } else {
            LOGGER.error("Failed to update Bid List");
        }
        return updated;
    }

    /**
     * Delete a bid list.
     *
     * @param id The bid list id to delete.
     */
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
