package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RuleNameService {

    Logger LOGGER = LogManager.getLogger(RuleNameService.class);

    @Autowired
    private RuleNameRepository ruleNameRepository;

    public List<RuleName> getAllRuleNames() {
        return ruleNameRepository.findAll();
    }

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

    public void saveRuleName(RuleName ruleName) {
        ruleNameRepository.save(ruleName);
        LOGGER.info("Rule Name's successfully created");
    }

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
