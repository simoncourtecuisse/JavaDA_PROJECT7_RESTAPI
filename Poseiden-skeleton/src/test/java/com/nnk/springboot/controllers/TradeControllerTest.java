package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.security.services.UserDetailsServiceImpl;
import com.nnk.springboot.services.RuleNameService;
import com.nnk.springboot.services.TradeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TradeController.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeService mockTradeService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private DataSource dataSource;

    @Test
    public void testGetAllTrades() throws Exception {
        ArrayList<Trade> trades = new ArrayList<>();
        when(mockTradeService.getAllTrades()).thenReturn(trades);

        this.mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"));
    }

    @Test
    public void testAddTradeForm() throws Exception {
        this.mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    public void testValidateTrade() throws Exception {
        Trade trade = new Trade("account1", "type1");
        when(mockTradeService.getTradeById(1)).thenReturn(trade);

        when(mockTradeService.saveTrade(any(Trade.class))).thenReturn(trade);

        this.mockMvc.perform(post("/trade/validate")
                        .param("account", trade.getAccount())
                        .param("type", trade.getType())
                        .param("buyQuantity", String.valueOf(1.1)))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/trade/list"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testValidateTrade_BadRequest() throws Exception {
        Trade trade = new Trade("account1", "type1");
        when(mockTradeService.getTradeById(1)).thenReturn(trade);

        this.mockMvc.perform(post("/trade/validate")
                        .param("account", "")
                        .param("type", "")
                        .param("buyQuantity", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void testShowUpdateFormForTrade() throws Exception {
        Trade trade = new Trade("account1", "type1");
        when(mockTradeService.getTradeById(1)).thenReturn(trade);

        this.mockMvc.perform(get("/trade/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attribute("trade", hasProperty("account", is("account1"))));
    }

    @Test
    public void testUpdateTrade() throws Exception {
        Trade trade = new Trade("account1", "type1");
        trade.setTradeId(1);
        trade.setBuyQuantity(1.1);
        when(mockTradeService.getTradeById(1)).thenReturn(trade);

        when(mockTradeService.updateTrade(1, trade)).thenReturn(true);
        ArrayList<Trade> trades = new ArrayList<>();
        trades.add(trade);
        when(mockTradeService.getAllTrades()).thenReturn(trades);

        this.mockMvc.perform(post("/trade/update/1")
                        .param("tradeId", "1")
                        .param("account", "account1")
                        .param("type", "type1")
                        .param("buyQuantity", "1.1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/trade/list"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testUpdateTrade_BadRequest() throws Exception {
        Trade trade = new Trade("account1", "type1");
        when(mockTradeService.getTradeById(1)).thenReturn(trade);

        this.mockMvc.perform(post("/trade/update/1")
                        .param("account", "")
                        .param("type", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void testDeleteTrade() throws Exception {
        Trade trade = new Trade("account1", "type1");
        when(mockTradeService.getTradeById(1)).thenReturn(trade);

        this.mockMvc.perform(get("/trade/delete/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/trade/list"));

    }
}
