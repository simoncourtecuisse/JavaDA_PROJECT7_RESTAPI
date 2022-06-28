//package com.nnk.springboot.controllers;
//
//import com.nnk.springboot.Application;
//import com.nnk.springboot.domain.BidList;
//import com.nnk.springboot.security.services.UserDetailsServiceImpl;
//import com.nnk.springboot.services.BidListService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import javax.sql.DataSource;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@ContextConfiguration(classes = Application.class)
//public class BidListControllerTest {
//
//    //@Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private WebApplicationContext context;
//
//    @MockBean
//    private UserDetailsServiceImpl mockUserDetailsServiceImpl;
//
//    @MockBean
//    private DataSource mockDataSource;
//
//    @MockBean
//    private BidListService mockBidListService;
//
//    @BeforeEach
//    public void setup() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//    }
//
//    @Test
//    void testHome() throws Exception {
//        // Setup
//        // Configure BidListService.getAllBids(...).
//        ArrayList<BidList> bidLists = new ArrayList<>();
//        BidList bid1 = new BidList("account1", "type1", 1.1);
//        BidList bid2 = new BidList("account2", "type2", 2.2);
//        bidLists.add(bid1);
//        bidLists.add(bid2);
//        when(mockBidListService.getAllBids()).thenReturn(bidLists);
//
//        // Run the test
//        this.mockMvc.perform(get("/bidList/list"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("bidList/list"))
//                .andExpect(model().attribute("allBids", contains(bidLists.toString())));
//
//
//
////        // Verify the results
////        assertEquals(HttpStatus.OK.value(), response.getStatus());
////        assertEquals("expectedResponse", response.getContentAsString());
//    }
//
//    @Test
//    void testHome_BidListServiceReturnsNoItems() throws Exception {
//        // Setup
//        when(mockBidListService.getAllBids()).thenReturn(Collections.emptyList());
//
//        // Run the test
//        final MockHttpServletResponse response = mockMvc.perform(get("/bidList/list")
//                        .accept(MediaType.TEXT_HTML))
//                .andReturn().getResponse();
//
//        // Verify the results
//        assertEquals(HttpStatus.OK.value(), response.getStatus());
//        assertEquals("expectedResponse", response.getContentAsString());
//    }
//
//    @Test
//    void testAddBidForm() throws Exception {
//        // Setup
//        // Run the test
//        final MockHttpServletResponse response = mockMvc.perform(get("/bidList/add")
//                        .accept(MediaType.TEXT_HTML))
//                .andReturn().getResponse();
//
//        // Verify the results
//        assertEquals(HttpStatus.OK.value(), response.getStatus());
//        assertEquals("expectedResponse", response.getContentAsString());
//    }
//
//    @Test
//    void testValidate() throws Exception {
//        // Setup
//        // Configure BidListService.getAllBids(...).
//        final List<BidList> bidLists = List.of(new BidList("account", "type", 0.0));
//        when(mockBidListService.getAllBids()).thenReturn(bidLists);
//
//        // Run the test
//        final MockHttpServletResponse response = mockMvc.perform(post("/bidList/validate")
//                        .param("bidListId", "0")
//                        .param("account", "account")
//                        .param("type", "type")
//                        .param("bidQuantity", "0")
//                        .param("askQuantity", "0")
//                        .param("bid", "0")
//                        .param("ask", "0")
//                        .param("benchmark", "benchmark")
//                        .param("bidListDate", "bidListDate")
//                        .param("commentary", "commentary")
//                        .param("security", "security")
//                        .param("status", "status")
//                        .param("trader", "trader")
//                        .param("book", "book")
//                        .param("creationName", "creationName")
//                        .param("creationDate", "creationDate")
//                        .param("revisionName", "revisionName")
//                        .param("revisionDate", "revisionDate")
//                        .param("dealName", "dealName")
//                        .param("dealType", "dealType")
//                        .param("sourceListId", "sourceListId")
//                        .param("side", "side")
//                        .with(csrf())
//                        .accept(MediaType.TEXT_HTML))
//                .andReturn().getResponse();
//
//        // Verify the results
//        assertEquals(HttpStatus.OK.value(), response.getStatus());
//        assertEquals("expectedResponse", response.getContentAsString());
//        verify(mockBidListService).saveBidList(any(BidList.class));
//    }
//
//    @Test
//    void testValidate_BidListServiceGetAllBidsReturnsNoItems() throws Exception {
//        // Setup
//        when(mockBidListService.getAllBids()).thenReturn(Collections.emptyList());
//
//        // Run the test
//        final MockHttpServletResponse response = mockMvc.perform(post("/bidList/validate")
//                        .param("bidListId", "0")
//                        .param("account", "account")
//                        .param("type", "type")
//                        .param("bidQuantity", "0")
//                        .param("askQuantity", "0")
//                        .param("bid", "0")
//                        .param("ask", "0")
//                        .param("benchmark", "benchmark")
//                        .param("bidListDate", "bidListDate")
//                        .param("commentary", "commentary")
//                        .param("security", "security")
//                        .param("status", "status")
//                        .param("trader", "trader")
//                        .param("book", "book")
//                        .param("creationName", "creationName")
//                        .param("creationDate", "creationDate")
//                        .param("revisionName", "revisionName")
//                        .param("revisionDate", "revisionDate")
//                        .param("dealName", "dealName")
//                        .param("dealType", "dealType")
//                        .param("sourceListId", "sourceListId")
//                        .param("side", "side")
//                        .with(csrf())
//                        .accept(MediaType.TEXT_HTML))
//                .andReturn().getResponse();
//
//        // Verify the results
//        assertEquals(HttpStatus.OK.value(), response.getStatus());
//        assertEquals("expectedResponse", response.getContentAsString());
//        verify(mockBidListService).saveBidList(any(BidList.class));
//    }
//
//    @Test
//    void testShowUpdateForm() throws Exception {
//        // Setup
//        when(mockBidListService.getBidById(0)).thenReturn(new BidList("account", "type", 0.0));
//
//        // Run the test
//        final MockHttpServletResponse response = mockMvc.perform(get("/bidList/update/{id}", 0)
//                        .accept(MediaType.TEXT_HTML))
//                .andReturn().getResponse();
//
//        // Verify the results
//        assertEquals(HttpStatus.OK.value(), response.getStatus());
//        assertEquals("expectedResponse", response.getContentAsString());
//    }
//
//    @Test
//    void testUpdateBid() throws Exception {
//        // Setup
//        when(mockBidListService.updateBidList(eq(0), any(BidList.class))).thenReturn(false);
//
//        // Configure BidListService.getAllBids(...).
//        final List<BidList> bidLists = List.of(new BidList("account", "type", 0.0));
//        when(mockBidListService.getAllBids()).thenReturn(bidLists);
//
//        // Run the test
//        final MockHttpServletResponse response = mockMvc.perform(post("/bidList/update/{id}", 0)
//                        .param("bidListId", "0")
//                        .param("account", "account")
//                        .param("type", "type")
//                        .param("bidQuantity", "0")
//                        .param("askQuantity", "0")
//                        .param("bid", "0")
//                        .param("ask", "0")
//                        .param("benchmark", "benchmark")
//                        .param("bidListDate", "bidListDate")
//                        .param("commentary", "commentary")
//                        .param("security", "security")
//                        .param("status", "status")
//                        .param("trader", "trader")
//                        .param("book", "book")
//                        .param("creationName", "creationName")
//                        .param("creationDate", "creationDate")
//                        .param("revisionName", "revisionName")
//                        .param("revisionDate", "revisionDate")
//                        .param("dealName", "dealName")
//                        .param("dealType", "dealType")
//                        .param("sourceListId", "sourceListId")
//                        .param("side", "side")
//                        .with(csrf())
//                        .accept(MediaType.TEXT_HTML))
//                .andReturn().getResponse();
//
//        // Verify the results
//        assertEquals(HttpStatus.OK.value(), response.getStatus());
//        assertEquals("expectedResponse", response.getContentAsString());
//    }
//
//    @Test
//    void testUpdateBid_BidListServiceGetAllBidsReturnsNoItems() throws Exception {
//        // Setup
//        when(mockBidListService.updateBidList(eq(0), any(BidList.class))).thenReturn(false);
//        when(mockBidListService.getAllBids()).thenReturn(Collections.emptyList());
//
//        // Run the test
//        final MockHttpServletResponse response = mockMvc.perform(post("/bidList/update/{id}", 0)
//                        .param("bidListId", "0")
//                        .param("account", "account")
//                        .param("type", "type")
//                        .param("bidQuantity", "0")
//                        .param("askQuantity", "0")
//                        .param("bid", "0")
//                        .param("ask", "0")
//                        .param("benchmark", "benchmark")
//                        .param("bidListDate", "bidListDate")
//                        .param("commentary", "commentary")
//                        .param("security", "security")
//                        .param("status", "status")
//                        .param("trader", "trader")
//                        .param("book", "book")
//                        .param("creationName", "creationName")
//                        .param("creationDate", "creationDate")
//                        .param("revisionName", "revisionName")
//                        .param("revisionDate", "revisionDate")
//                        .param("dealName", "dealName")
//                        .param("dealType", "dealType")
//                        .param("sourceListId", "sourceListId")
//                        .param("side", "side")
//                        .with(csrf())
//                        .accept(MediaType.TEXT_HTML))
//                .andReturn().getResponse();
//
//        // Verify the results
//        assertEquals(HttpStatus.OK.value(), response.getStatus());
//        assertEquals("expectedResponse", response.getContentAsString());
//    }
//
//    @Test
//    void testDeleteBid() throws Exception {
//        // Setup
//        // Configure BidListService.getAllBids(...).
//        final List<BidList> bidLists = List.of(new BidList("account", "type", 0.0));
//        when(mockBidListService.getAllBids()).thenReturn(bidLists);
//
//        // Run the test
//        final MockHttpServletResponse response = mockMvc.perform(get("/bidList/delete/{id}", 0)
//                        .accept(MediaType.TEXT_HTML))
//                .andReturn().getResponse();
//
//        // Verify the results
//        assertEquals(HttpStatus.OK.value(), response.getStatus());
//        assertEquals("expectedResponse", response.getContentAsString());
//        verify(mockBidListService).deleteBidListById(0);
//    }
//
//    @Test
//    void testDeleteBid_BidListServiceGetAllBidsReturnsNoItems() throws Exception {
//        // Setup
//        when(mockBidListService.getAllBids()).thenReturn(Collections.emptyList());
//
//        // Run the test
//        final MockHttpServletResponse response = mockMvc.perform(get("/bidList/delete/{id}", 0)
//                        .accept(MediaType.TEXT_HTML))
//                .andReturn().getResponse();
//
//        // Verify the results
//        assertEquals(HttpStatus.OK.value(), response.getStatus());
//        assertEquals("expectedResponse", response.getContentAsString());
//        verify(mockBidListService).deleteBidListById(0);
//    }
//}
