package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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
 * The controller provides CRUD operations for the RuleName entity.
 *
 * @author SimonC.
 * @version 1.0
 * @see RuleName
 * @see RuleNameService
 */
@Controller
public class RuleNameController {
    // DONE: Inject RuleName service

    Logger LOGGER = LogManager.getLogger(RuleNameController.class);

    @Autowired
    private RuleNameService ruleNameService;

    /**
     * Rule Name home.
     *
     * @param model Data for the view.
     * @return Path of the requested view.
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        // DONE: find all RuleName, add to model
        LOGGER.info("All RuleNames retrieved");
        model.addAttribute("allRuleNames", ruleNameService.getAllRuleNames());
        return "ruleName/list";
    }

    /**
     * The form for adding a rule name.
     *
     * @param ruleName The rule name.
     * @return Path of the requested view.
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName) {
        LOGGER.info("Getting the addRuleName Form");
        return "ruleName/add";
    }

    /**
     * Add a rule name.
     *
     * @param ruleName The rule name must be valid.
     * @param result   The result of the validation.
     * @param model    Data for the view.
     * @return Redirect to the rule name list view if the result is valid. If the result has an error, it returns to the add form view.
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        // DONE: check data valid and save to db, after saving return RuleName list
        if (!result.hasErrors()) {
            ruleNameService.saveRuleName(ruleName);
            LOGGER.info("RuleName's successfully created !");
            model.addAttribute("allRuleNames", ruleNameService.getAllRuleNames());
            LOGGER.info("Rule Name's successfully created !");
            return "redirect:/ruleName/list";
        }
        LOGGER.error("Failed to create a new Rule Name");
        return "ruleName/add";
    }

    /**
     * The form for updating a rule name.
     *
     * @param id    The rule name id to update.
     * @param model Data for the view.
     * @return Path of the requested view.
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // DONE: get RuleName by Id and to model then show to the form
        LOGGER.info("Getting the updateRuleName Form");
        model.addAttribute("ruleName", ruleNameService.getRuleNameById(id));
        return "ruleName/update";
    }

    /**
     * Update a rule name.
     *
     * @param id       The rule name id to update.
     * @param ruleName The rule name must be valid.
     * @param result   The result of the validation.
     * @param model    Data for the view.
     * @return Redirect to the rule name list view if the result is valid. If the result has an error, it returns to the update form view.
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                                 BindingResult result, Model model) {
        // DONE: check required fields, if valid call service to update RuleName and return RuleName list
        if (result.hasErrors()) {
            LOGGER.error("Failed to update RuleName");
            return "ruleName/update";
        } else {
            boolean updated = ruleNameService.updateRuleName(id, ruleName);
            if (updated) {
                LOGGER.info("Rule Name's successfully updated !");
                model.addAttribute("allRuleNames", ruleNameService.getAllRuleNames());
            }
            return "redirect:/ruleName/list";
        }
    }

    /**
     * Delete a rule name.
     *
     * @param id    The rule name id to delete.
     * @param model Data for the view.
     * @return Redirect to the rule name list view.
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        // DONE: Find RuleName by Id and delete the RuleName, return to Rule list
        ruleNameService.deleteRuleNameById(id);
        LOGGER.info("RuleName's successfully deleted !");
        model.addAttribute("allRuleNames", ruleNameService.getAllRuleNames());
        return "redirect:/ruleName/list";
    }
}
