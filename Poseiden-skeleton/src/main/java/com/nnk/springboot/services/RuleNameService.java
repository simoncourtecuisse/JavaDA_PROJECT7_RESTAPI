package com.nnk.springboot.services;

import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * The service provides methods that get called by the RuleNameController for CRUD operations.
 *
 * @author SimonC.
 * @version 1.0
 * @see RuleNameController
 * @see RuleNameRepository
 */
@Service
@Transactional
public class RuleNameService {

    Logger LOGGER = LogManager.getLogger(RuleNameService.class);

    @Autowired
    private RuleNameRepository ruleNameRepository;

    /**
     * Get all the RuleNames contained in the database.
     *
     * @return A list of all the rule names.
     */
    public List<RuleName> getAllRuleNames() {
        return ruleNameRepository.findAll();
    }

    /**
     * Get a rule name from its id.
     *
     * @param id The rule name id to get.
     * @return An optional which may or may not contain the rule name. If a rule name is present, isPresent() returns true. If no rule name is present, the object is considered empty and isPresent() returns false.
     */
    public RuleName getRuleNameById(Integer id) {
        Optional<RuleName> ruleName = ruleNameRepository.findById(id);
        if (ruleName.isPresent()) {
            LOGGER.info("Rule Name's successfully found");
            return ruleName.get();
        } else {
            LOGGER.error("Failed to find Rule Name");
            return null;
        }
    }

    /**
     * Save a rule name.
     *
     * @param ruleName The rule name to save.
     * @return The rule name saved.
     */
    public RuleName saveRuleName(RuleName ruleName) {
        LOGGER.info("Rule Name's successfully created");
        return ruleNameRepository.save(ruleName);
    }

    /**
     * Update a rule name.
     *
     * @param id       The rule name id to update.
     * @param ruleName The rule name to update.
     * @return The updated rule name saved.
     */
    public boolean updateRuleName(Integer id, RuleName ruleName) {
        boolean updated = false;
        Optional<RuleName> refRuleName = ruleNameRepository.findById(id);
        if (refRuleName.isPresent()) {
            RuleName updatedRuleName = refRuleName.get();
            updatedRuleName.setName(ruleName.getName());
            updatedRuleName.setDescription(ruleName.getDescription());
            updatedRuleName.setJson(ruleName.getJson());
            updatedRuleName.setTemplate(ruleName.getTemplate());
            updatedRuleName.setSqlStr(ruleName.getSqlStr());
            updatedRuleName.setSqlPart(ruleName.getSqlPart());
            ruleNameRepository.save(updatedRuleName);
            updated = true;
            LOGGER.info("Rule Name's successfully updated");
        } else {
            LOGGER.error("Failed to update Rule Name");
        }
        return updated;
    }

    /**
     * Delete a rule name.
     *
     * @param id The rule name id to delete.
     */
    public void deleteRuleNameById(Integer id) {
        Optional<RuleName> removeRuleName = ruleNameRepository.findById(id);
        if (removeRuleName.isPresent()) {
            ruleNameRepository.delete(removeRuleName.get());
            LOGGER.info("Rule Name's successfully deleted");
        } else {
            LOGGER.error("Failed to delete Rule Name");
        }
    }
}
