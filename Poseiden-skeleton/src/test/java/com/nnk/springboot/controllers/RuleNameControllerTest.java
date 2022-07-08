package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.security.services.CustomOAuth2UserService;
import com.nnk.springboot.security.services.OAuth2LoginSuccessHandler;
import com.nnk.springboot.security.services.UserDetailsServiceImpl;
import com.nnk.springboot.services.RuleNameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;
import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RuleNameController.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuleNameService mockRuleNameService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private DataSource dataSource;

    @MockBean
    private CustomOAuth2UserService customOAuth2UserService;

    @MockBean
    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Test
    public void testGetAllRuleNames() throws Exception {
        ArrayList<RuleName> ruleNames = new ArrayList<>();
        when(mockRuleNameService.getAllRuleNames()).thenReturn(ruleNames);

        this.mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"));
    }

    @Test
    public void testAddRuleForm() throws Exception {
        this.mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    public void testValidateRuleName() throws Exception {
        RuleName ruleName = new RuleName("name1", "description1", "json1", "template1", "sqlStr1", "sqlPart1");
        when(mockRuleNameService.getRuleNameById(1)).thenReturn(ruleName);

        when(mockRuleNameService.saveRuleName(any(RuleName.class))).thenReturn(ruleName);

        this.mockMvc.perform(post("/ruleName/validate")
                        .param("name", ruleName.getName())
                        .param("description", ruleName.getDescription())
                        .param("json", ruleName.getJson())
                        .param("template", ruleName.getTemplate())
                        .param("sqlStr", ruleName.getSqlStr())
                        .param("sqlPart", ruleName.getSqlPart()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/ruleName/list"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testValidateRuleName_BadRequest() throws Exception {
        RuleName ruleName = new RuleName("name1", "description1", "json1", "template1", "sqlStr1", "sqlPart1");
        when(mockRuleNameService.getRuleNameById(1)).thenReturn(ruleName);

        this.mockMvc.perform(post("/ruleName/validate")
                        .param("name", "")
                        .param("description", "")
                        .param("json", "")
                        .param("template", "")
                        .param("sqlStr", "")
                        .param("sqlPart", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void testShowUpdateFormForRuleName() throws Exception {
        RuleName ruleName = new RuleName("name1", "description1", "json1", "template1", "sqlStr1", "sqlPart1");
        when(mockRuleNameService.getRuleNameById(1)).thenReturn(ruleName);

        this.mockMvc.perform(get("/ruleName/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attribute("ruleName", hasProperty("name", is("name1"))));
    }

    @Test
    public void testUpdateRuleName() throws Exception {
        RuleName ruleName = new RuleName("name1", "description1", "json1", "template1", "sqlStr1", "sqlPart1");
        ruleName.setId(1);
        when(mockRuleNameService.getRuleNameById(1)).thenReturn(ruleName);

        when(mockRuleNameService.updateRuleName(1, ruleName)).thenReturn(true);
        ArrayList<RuleName> ruleNames = new ArrayList<>();
        ruleNames.add(ruleName);
        when(mockRuleNameService.getAllRuleNames()).thenReturn(ruleNames);

        this.mockMvc.perform(post("/ruleName/update/1")
                        .param("name", "name1")
                        .param("description", "description1")
                        .param("json", "json1")
                        .param("template", "template1")
                        .param("sqlStr", "sqlStr1")
                        .param("sqlPart", "sqlPart1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/ruleName/list"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testUpdateRuleName_BadRequest() throws Exception {
        RuleName ruleName = new RuleName("name1", "description1", "json1", "template1", "sqlStr1", "sqlPart1");
        when(mockRuleNameService.getRuleNameById(1)).thenReturn(ruleName);

        this.mockMvc.perform(post("/ruleName/update/1")
                        .param("name", "")
                        .param("description", "")
                        .param("json", "")
                        .param("template", "")
                        .param("sqlStr", "")
                        .param("sqlPart", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void testDeleteRuleName() throws Exception {
        RuleName ruleName = new RuleName("name1", "description1", "json1", "template1", "sqlStr1", "sqlPart1");
        when(mockRuleNameService.getRuleNameById(1)).thenReturn(ruleName);

        this.mockMvc.perform(get("/ruleName/delete/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/ruleName/list"));

    }
}
