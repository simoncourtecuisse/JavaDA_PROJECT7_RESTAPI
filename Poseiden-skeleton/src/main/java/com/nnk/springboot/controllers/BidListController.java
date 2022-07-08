package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class BidListController {
    // TODO: Inject Bid service

    Logger LOGGER = LogManager.getLogger(BidListController.class);

    @Autowired
    private BidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model) {
        // TODO: call service find all bids to show to the view
        LOGGER.info("All BidList retrieved");
        model.addAttribute("allBidLists", bidListService.getAllBids());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        LOGGER.info("Getting the addBid Form");
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return bid list
        if (!result.hasErrors()) {
            bidListService.saveBidList(bid);
            LOGGER.info("BidList's successfully created !");
            model.addAttribute("allBidLists", bidListService.getAllBids());
            return "redirect:/bidList/list";
        }
        LOGGER.error("Failed to create a new BidList");
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form
        LOGGER.info("Getting the updateBid Form");
        model.addAttribute("bidList", bidListService.getBidById(id));
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        if (result.hasErrors()) {
            LOGGER.error("Failed to update BidList");
            return "bidList/update";
        } else {
            System.out.println(bidList);
            boolean updated = bidListService.updateBidList(id, bidList);
            System.out.println(updated);
            if (updated) {
                LOGGER.info("BidList's successfully updated !");
                model.addAttribute("allBidLists", bidListService.getAllBids());
            }
            return "redirect:/bidList/list";
        }
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        bidListService.deleteBidListById(id);
        LOGGER.info("BidList's successfully deleted !");
        model.addAttribute("allBidLists", bidListService.getAllBids());
        return "redirect:/bidList/list";
    }
}
