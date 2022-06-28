package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RuleNameServiceTest {

    @Mock
    private RuleNameRepository mockRuleNameRepository;

    @InjectMocks
    private RuleNameService ruleNameServiceUnderTest;

    @Test
    void testGetAllRuleNames() {
        // Setup
        // Configure RuleNameRepository.findAll(...).
        final List<RuleName> ruleNames = List.of(
                new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart"));
        when(mockRuleNameRepository.findAll()).thenReturn(ruleNames);

        // Run the test
        final List<RuleName> result = ruleNameServiceUnderTest.getAllRuleNames();

        // Verify the results
    }

    @Test
    void testGetAllRuleNames_RuleNameRepositoryReturnsNoItems() {
        // Setup
        when(mockRuleNameRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<RuleName> result = ruleNameServiceUnderTest.getAllRuleNames();

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void testGetRuleNameById() {
        // Setup
        // Configure RuleNameRepository.findById(...).
        final Optional<RuleName> ruleName = Optional.of(
                new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart"));
        when(mockRuleNameRepository.findById(0)).thenReturn(ruleName);

        // Run the test
        final RuleName result = ruleNameServiceUnderTest.getRuleNameById(0);

        // Verify the results
    }

    @Test
    void testGetRuleNameById_RuleNameRepositoryReturnsAbsent() {
        // Setup
        when(mockRuleNameRepository.findById(0)).thenReturn(Optional.empty());

        // Run the test
        final RuleName result = ruleNameServiceUnderTest.getRuleNameById(0);

        // Verify the results
    }

    @Test
    void testSaveRuleName() {
        // Setup
        final RuleName ruleName = new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart");

        // Configure RuleNameRepository.save(...).
        final RuleName ruleName1 = new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart");
        when(mockRuleNameRepository.save(any(RuleName.class))).thenReturn(ruleName1);

        // Run the test
        ruleNameServiceUnderTest.saveRuleName(ruleName);

        // Verify the results
        verify(mockRuleNameRepository).save(any(RuleName.class));
    }

    @Test
    void testUpdateRuleName() {
        // Setup
        final RuleName ruleName = new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart");

        // Configure RuleNameRepository.findById(...).
        final Optional<RuleName> ruleName1 = Optional.of(
                new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart"));
        when(mockRuleNameRepository.findById(0)).thenReturn(ruleName1);

        // Configure RuleNameRepository.save(...).
        final RuleName ruleName2 = new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart");
        when(mockRuleNameRepository.save(any(RuleName.class))).thenReturn(ruleName2);

        // Run the test
        final boolean result = ruleNameServiceUnderTest.updateRuleName(0, ruleName);

        // Verify the results
        assertTrue(result);
        verify(mockRuleNameRepository).save(any(RuleName.class));
    }

    @Test
    void testUpdateRuleName_RuleNameRepositoryFindByIdReturnsAbsent() {
        // Setup
        final RuleName ruleName = new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart");
        when(mockRuleNameRepository.findById(0)).thenReturn(Optional.empty());

        // Configure RuleNameRepository.save(...).
        final RuleName ruleName1 = new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart");
        when(mockRuleNameRepository.save(any(RuleName.class))).thenReturn(ruleName1);

        // Run the test
        final boolean result = ruleNameServiceUnderTest.updateRuleName(0, ruleName);

        // Verify the results
        assertTrue(result);
        verify(mockRuleNameRepository).save(any(RuleName.class));
    }

    @Test
    void testDeleteRuleNameById() {
        // Setup
        // Configure RuleNameRepository.findById(...).
        final Optional<RuleName> ruleName = Optional.of(
                new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart"));
        when(mockRuleNameRepository.findById(0)).thenReturn(ruleName);

        // Run the test
        ruleNameServiceUnderTest.deleteRuleNameById(0);

        // Verify the results
        verify(mockRuleNameRepository).delete(any(RuleName.class));
    }

    @Test
    void testDeleteRuleNameById_RuleNameRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockRuleNameRepository.findById(0)).thenReturn(Optional.empty());

        // Run the test
        ruleNameServiceUnderTest.deleteRuleNameById(0);

        // Verify the results
        verify(mockRuleNameRepository).delete(any(RuleName.class));
    }
}
