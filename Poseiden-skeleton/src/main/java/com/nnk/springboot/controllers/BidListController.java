package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * The controller provides CRUD operations for the BidList entity.
 *
 * @author SimonC.
 * @version 1.0
 * @see BidList
 * @see BidListService
 */
@Controller
public class BidListController {
    // DONE: Inject Bid service

    Logger LOGGER = LogManager.getLogger(BidListController.class);

    @Autowired
    private BidListService bidListService;

    /**
     * Bid List home.
     *
     * @param model Data for the view.
     * @return Path of the requested view.
     */
    @RequestMapping("/bidList/list")
    public String home(Model model) {
        // DONE: call service find all bids to show to the view
        LOGGER.info("All BidList retrieved");
        model.addAttribute("allBidLists", bidListService.getAllBids());
        return "bidList/list";
    }

    /**
     * The form for adding a bid list.
     *
     * @param bidList The bid.
     * @return Path of the requested view.
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bidList) {
        LOGGER.info("Getting the addBid Form");
        return "bidList/add";
    }

    /**
     * Add a bid list.
     *
     * @param bidList The bid list must be valid.
     * @param result  The result of the validation.
     * @param model   Data for the view.
     * @return Redirect to the bid list view if the result is valid. If the result has an error, it returns to the add form view.
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result, Model model) {
        // DONE: check data valid and save to db, after saving return bid list
        if (!result.hasErrors()) {
            bidListService.saveBidList(bidList);
            LOGGER.info("BidList's successfully created !");
            model.addAttribute("allBidLists", bidListService.getAllBids());
            return "redirect:/bidList/list";
        }
        LOGGER.error("Failed to create a new BidList");
        return "bidList/add";
    }

    /**
     * The form for updating a bid list.
     *
     * @param id    The bid list id to update.
     * @param model Data for the view.
     * @return Path of the requested view.
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // DONE: get Bid by Id and to model then show to the form
        LOGGER.info("Getting the updateBid Form");
        model.addAttribute("bidList", bidListService.getBidById(id));
        return "bidList/update";
    }

    /**
     * Update a bid list.
     *
     * @param id      The bid list id to update.
     * @param bidList The bid list must be valid.
     * @param result  The result of the validation.
     * @param model   Data for the view.
     * @return Redirect to the bid list view if the result is valid. If the result has an error, it returns to the update form view.
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        // DONE: check required fields, if valid call service to update Bid and return list Bid
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

    /**
     * Delete a bid list.
     *
     * @param id    The bid list id to delete.
     * @param model Data for the view.
     * @return Redirect to the bid list view.
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // DONE: Find Bid by Id and delete the bid, return to Bid list
        bidListService.deleteBidListById(id);
        LOGGER.info("BidList's successfully deleted !");
        model.addAttribute("allBidLists", bidListService.getAllBids());
        return "redirect:/bidList/list";
    }
}
