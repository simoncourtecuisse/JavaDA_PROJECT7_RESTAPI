package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.security.services.UserDetailsServiceImpl;
import com.nnk.springboot.services.BidListService;
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
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
//@SpringBootTest
@WebMvcTest(BidListController.class)
@AutoConfigureMockMvc
//@AutoConfigureMockMvc
//@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@ActiveProfiles("test")
public class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidListService mockBidListService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private DataSource dataSource;

    @Test
    public void testGetAllBids() throws Exception {

        ArrayList<BidList> bidLists = new ArrayList<>();
        when(mockBidListService.getAllBids()).thenReturn(bidLists);

        this.mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"));
    }

    @Test
    public void testAddBidForm() throws Exception {
        this.mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    public void testValidateBidList() throws Exception {
        BidList bid1 = new BidList("account1", "type1", 11.1);
        when(mockBidListService.getBidById(1)).thenReturn(bid1);

        when(mockBidListService.saveBidList(any(BidList.class))).thenReturn(bid1);

        this.mockMvc.perform(post("/bidList/validate")
                        .param("account", bid1.getAccount())
                        .param("type", bid1.getType())
                        .param("bidQuantity", String.valueOf(bid1.getBidQuantity())))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testValidateBidList_BadRequest() throws Exception {
        BidList bid1 = new BidList("account1", "type1", 11.1);
        when(mockBidListService.getBidById(1)).thenReturn(bid1);

        this.mockMvc.perform(post("/bidList/validate")
                        .param("account", "")
                        .param("type", "")
                        .param("bidQuantity", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void testShowUpdateFormForBidList() throws Exception {
        BidList bid1 = new BidList("account1", "type1", 11.1);
        when(mockBidListService.getBidById(1)).thenReturn(bid1);

        this.mockMvc.perform(get("/bidList/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attribute("bidList", hasProperty("account", is("account1"))));
    }

    @Test
    public void testUpdateBidList() throws Exception {
        BidList bid1 = new BidList("account1", "type1", 11.1);
        when(mockBidListService.getBidById(1)).thenReturn(bid1);

        when(mockBidListService.updateBidList(1, bid1)).thenReturn(true);
        boolean updated = mockBidListService.updateBidList(1, bid1);
        System.out.println(updated);
        ArrayList<BidList> bidLists = new ArrayList<>();
        bidLists.add(bid1);
        when(mockBidListService.getAllBids()).thenReturn(bidLists);
        System.out.println(bidLists);

        this.mockMvc.perform(post("/bidList/update/1")
                        .param("account", "account2")
                        .param("type", "type2")
                        .param("bidQuantity", "22.2"))
                .andExpect(status().isFound())
//                .andExpect(model().attribute("allBidLists", hasItem(bid1)))                   ???

                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testUpdateBidList_BadRequest() throws Exception {
        BidList bid1 = new BidList("account1", "type1", 11.1);
        when(mockBidListService.getBidById(1)).thenReturn(bid1);

        this.mockMvc.perform(post("/bidList/update/1")
                        .param("account", "")
                        .param("type", "")
                        .param("bidQuantity", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void testDeleteBidList() throws Exception {
        BidList bid1 = new BidList("account1", "type1", 11.1);
        when(mockBidListService.getBidById(1)).thenReturn(bid1);

        this.mockMvc.perform(get("/bidList/delete/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/bidList/list"));

    }
}