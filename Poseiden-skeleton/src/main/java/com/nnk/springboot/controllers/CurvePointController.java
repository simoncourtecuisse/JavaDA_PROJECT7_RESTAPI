package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
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
 * The controller provides CRUD operations for the CurvePoint entity.
 *
 * @author SimonC.
 * @version 1.0
 * @see CurvePoint
 * @see CurvePointService
 */
@Controller
public class CurvePointController {
    // DONE: Inject Curve Point service

    Logger LOGGER = LogManager.getLogger(CurvePointController.class);

    @Autowired
    private CurvePointService curvePointService;

    /**
     * Curve Point home.
     *
     * @param model Data for the view.
     * @return Path of the requested view.
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        // DONE: find all Curve Point, add to model
        LOGGER.info("All BidList retrieved");
        model.addAttribute("allCurvePoints", curvePointService.getAllCurvePoints());
        return "curvePoint/list";
    }

    /**
     * The form for adding a curve point.
     *
     * @param curvePoint The curve point.
     * @return Path of the requested view.
     */
    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint curvePoint) {
        LOGGER.info("Getting the addCurvePoint Form");
        return "curvePoint/add";
    }

    /**
     * Add a curve point.
     *
     * @param curvePoint The curve point must be valid.
     * @param result     The result of the validation.
     * @param model      Data for the view.
     * @return Redirect to the curve point list view if the result is valid. If the result has an error, it returns to the add form view.
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        // DONE: check data valid and save to db, after saving return Curve list
        if (!result.hasErrors()) {
            curvePointService.saveCurvePoint(curvePoint);
            LOGGER.info("Curve Point's successfully created !");
            model.addAttribute("allCurvePoints", curvePointService.getAllCurvePoints());
            return "redirect:/curvePoint/list";
        }
        LOGGER.error("Failed to create a new Curve Point");
        return "curvePoint/add";
    }

    /**
     * The form for updating a curve point.
     *
     * @param id    The curve point id to update.
     * @param model Data for the view.
     * @return Path of the requested view.
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // DONE: get CurvePoint by Id and to model then show to the form
        LOGGER.info("Getting the updateCurvePoint Form");
        model.addAttribute("curvePoint", curvePointService.getCurvePointById(id));
        return "curvePoint/update";
    }

    /**
     * Update a curve point.
     *
     * @param id         The curve point id to update.
     * @param curvePoint The curve point must be valid.
     * @param result     The result of the validation.
     * @param model      Data for the view.
     * @return Redirect to the curve point list view if the result is valid. If the result has an error, it returns to the update form view.
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        // DONE: check required fields, if valid call service to update Curve and return Curve list
        if (result.hasErrors()) {
            LOGGER.error("Failed to update Curve Point");
            return "curvePoint/update";
        } else {
            boolean updated = curvePointService.updateCurvePoint(id, curvePoint);
            if (updated) {
                LOGGER.info("Curve Point's successfully updated !");
                model.addAttribute("allCurvePoints", curvePointService.getAllCurvePoints());
            }
            return "redirect:/curvePoint/list";
        }
    }

    /**
     * Delete a curve point.
     *
     * @param id    The curve point id to delete.
     * @param model Data for the view.
     * @return Redirect to the curve point list view.
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // DONE: Find Curve by Id and delete the Curve, return to Curve list
        curvePointService.deleteCurvePointById(id);
        LOGGER.info("Curve Point's successfully deleted !");
        model.addAttribute("allCurvePoints", curvePointService.getAllCurvePoints());
        return "redirect:/curvePoint/list";
    }
}
