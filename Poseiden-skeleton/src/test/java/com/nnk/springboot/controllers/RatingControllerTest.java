package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.security.services.CustomOAuth2UserService;
import com.nnk.springboot.security.services.OAuth2LoginSuccessHandler;
import com.nnk.springboot.security.services.UserDetailsServiceImpl;
import com.nnk.springboot.services.RatingService;
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

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RatingController.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService mockRatingService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private DataSource dataSource;

    @MockBean
    private CustomOAuth2UserService customOAuth2UserService;

    @MockBean
    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Test
    public void testGetAllRatings() throws Exception {

        ArrayList<Rating> ratings = new ArrayList<>();
        when(mockRatingService.getAllRatings()).thenReturn(ratings);

        this.mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"));
    }

    @Test
    public void testAddRatingForm() throws Exception {
        this.mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    public void testValidateRating() throws Exception {
        Rating rating = new Rating("moodyRating1", "sandPRating1", "fitchRating1", 1);
        when(mockRatingService.getRatingById(1)).thenReturn(rating);

        when(mockRatingService.saveRating(any(Rating.class))).thenReturn(rating);

        this.mockMvc.perform(post("/rating/validate")
                        .param("moodyRating", rating.getMoodyRating())
                        .param("sandPRating", rating.getSandPRating())
                        .param("fitchRating", rating.getFitchRating())
                        .param("orderNumber", rating.getOrderNumber().toString()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/rating/list"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testValidateRating_BadRequest() throws Exception {
        Rating rating = new Rating("moodyRating1", "sandPRating1", "fitchRating1", 1);
        when(mockRatingService.getRatingById(1)).thenReturn(rating);

        this.mockMvc.perform(post("/rating/validate")
                        .param("moodyRating", "")
                        .param("sandPRating", "")
                        .param("fitchRating", "")
                        .param("orderNumber", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void testShowUpdateFormForRating() throws Exception {
        Rating rating = new Rating("moodyRating1", "sandPRating1", "fitchRating1", 1);
        when(mockRatingService.getRatingById(1)).thenReturn(rating);

        this.mockMvc.perform(get("/rating/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attribute("rating", hasProperty("moodyRating", is("moodyRating1"))));
    }

    @Test
    public void testUpdateRating() throws Exception {
        Rating rating = new Rating("moodyRating1", "sandPRating1", "fitchRating1", 1);
        rating.setId(1);
        when(mockRatingService.getRatingById(1)).thenReturn(rating);

        when(mockRatingService.updateRating(1, rating)).thenReturn(true);
        ArrayList<Rating> ratings = new ArrayList<>();
        ratings.add(rating);
        when(mockRatingService.getAllRatings()).thenReturn(ratings);

        this.mockMvc.perform(post("/rating/update/1")
                        .param("moodyRating", "moodyRating1")
                        .param("sandPRating", "sandPRating1")
                        .param("fitchRating", "fitchRating1")
                        .param("orderNumber", "1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/rating/list"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testUpdateRating_BadRequest() throws Exception {
        Rating rating = new Rating("moodyRating1", "sandPRating1", "fitchRating1", 1);
        when(mockRatingService.getRatingById(1)).thenReturn(rating);

        this.mockMvc.perform(post("/rating/update/1")
                        .param("moodyRating", "")
                        .param("sandPRating", "")
                        .param("fitchRating", "")
                        .param("orderNumber", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void testDeleteRating() throws Exception {
        Rating rating = new Rating("moodyRating1", "sandPRating1", "fitchRating1", 1);
        when(mockRatingService.getRatingById(1)).thenReturn(rating);

        this.mockMvc.perform(get("/rating/delete/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/rating/list"));

    }
}
