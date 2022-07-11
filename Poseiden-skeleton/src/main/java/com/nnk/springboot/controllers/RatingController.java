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

/**
 * The controller provides CRUD operations for the Rating entity.
 *
 * @author SimonC.
 * @version 1.0
 * @see Rating
 * @see RatingService
 */
@Controller
public class RatingController {
    // DONE: Inject Rating service

    Logger LOGGER = LogManager.getLogger(RatingController.class);

    @Autowired
    private RatingService ratingService;

    /**
     * Rating home.
     *
     * @param model Data for the view.
     * @return Path of the requested view.
     */
    @RequestMapping("/rating/list")
    public String home(Model model) {
        // DONE: find all Rating, add to model
        LOGGER.info("All Ratings retrieved");
        model.addAttribute("allRatings", ratingService.getAllRatings());
        return "rating/list";
    }

    /**
     * The form for adding a rating.
     *
     * @param rating The rating.
     * @return Path of the requested view.
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        LOGGER.info("Getting the addRating Form");
        return "rating/add";
    }

    /**
     * Add a rating.
     *
     * @param rating The rating must be valid.
     * @param result The result of the validation.
     * @param model  Data for the view.
     * @return Redirect to the rating list view if the result is valid. If the result has an error, it returns to the add form view.
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        // DONE: check data valid and save to db, after saving return Rating list
        if (!result.hasErrors()) {
            ratingService.saveRating(rating);
            LOGGER.info("Rating's successfully created !");
            model.addAttribute("allRatings", ratingService.getAllRatings());
            return "redirect:/rating/list";
        }
        LOGGER.error("Failed to create a new Rating");
        return "rating/add";
    }

    /**
     * The form for updating a rating.
     *
     * @param id    The rating id to update.
     * @param model Data for the view.
     * @return Path of the requested view.
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // DONE: get Rating by Id and to model then show to the form
        LOGGER.info("Getting the updateRating Form");
        model.addAttribute("rating", ratingService.getRatingById(id));
        return "rating/update";
    }

    /**
     * Update a rating.
     *
     * @param id     The rating id to update.
     * @param rating The rating must be valid.
     * @param result The result of the validation.
     * @param model  Data for the view.
     * @return Redirect to the rating list view if the result is valid. If the result has an error, it returns to the update form view.
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                               BindingResult result, Model model) {
        // DONE: check required fields, if valid call service to update Rating and return Rating list
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

    /**
     * Delete a rating.
     *
     * @param id    The rating id to delete.
     * @param model Data for the view.
     * @return Redirect to the rating list view.
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        // DONE: Find Rating by Id and delete the Rating, return to Rating list
        ratingService.deleteRatingById(id);
        LOGGER.info("Rating's successfully deleted !");
        model.addAttribute("allRatings", ratingService.getAllRatings());
        return "redirect:/rating/list";
    }
}
