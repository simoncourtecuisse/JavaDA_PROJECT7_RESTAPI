package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class BidListService {
    @Autowired
    private BidListRepository bidListRepository;

    public List<BidList> getAllBids() {
        return bidListRepository.findAll();
    }

    public BidList getBidById(Integer bidListId) {
        return bidListRepository.findById(bidListId)
                .orElseThrow(() -> new NoSuchElementException("Invalid Bid Id: " + bidListId)); // -> avoid Optional type
    }

    public BidList saveBidList(BidList bidList) {
        return bidListRepository.save(bidList);
    }

    public BidList updateBidList(BidList updatedBidList) {
        BidList refBidList = bidListRepository.findById(updatedBidList.getBidListId())
                .orElseThrow(() -> new NoSuchElementException("Invalid Bid Id: " + updatedBidList.getBidListId()));
        refBidList.setAccount(updatedBidList.getAccount());
        refBidList.setType(updatedBidList.getType());
        refBidList.setBidQuantity(updatedBidList.getBidQuantity());
        return refBidList;
    }

    public void deleteBidList(BidList bidList) {
        bidListRepository.deleteById(bidList.getBidListId());
    }
}
