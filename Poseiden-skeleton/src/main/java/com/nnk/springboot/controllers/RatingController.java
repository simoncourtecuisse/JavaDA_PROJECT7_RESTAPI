package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
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

@Controller
public class RatingController {
    // TODO: Inject Rating service

    Logger LOGGER = LogManager.getLogger(RatingController.class);

    @Autowired
    private RatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model) {
        // TODO: find all Rating, add to model
        LOGGER.info("All Ratings retrieved");
        model.addAttribute("allRatings", ratingService.getAllRatings());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        LOGGER.info("Getting the addRating Form");
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Rating list
        if (!result.hasErrors()) {
            ratingService.saveRating(rating);
            LOGGER.info("Rating's successfully created !");
            model.addAttribute("allRatings", ratingService.getAllRatings());
            return "redirect:/rating/list";
        }
        LOGGER.error("Failed to create a new Rating");
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Rating by Id and to model then show to the form
        LOGGER.info("Getting the updateRating Form");
        model.addAttribute("rating", ratingService.getRatingById(id));
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Rating and return Rating list
        if (result.hasErrors()) {
            LOGGER.error("Failed to update Rating");
            return "rating/update";
        } else {
            boolean updated = ratingService.updateRating(id, rating);
            if (updated) {
                LOGGER.info("Rating's successfully updated !");
                model.addAttribute("allRatings", ratingService.getAllRatings());
            }
            return "redirect:/rating/list";
        }
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Rating by Id and delete the Rating, return to Rating list
        ratingService.deleteRatingById(id);
        LOGGER.info("Rating's successfully deleted !");
        model.addAttribute("allRatings", ratingService.getAllRatings());
        return "redirect:/rating/list";
    }
}
