package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.security.services.CustomOAuth2UserService;
import com.nnk.springboot.security.services.OAuth2LoginSuccessHandler;
import com.nnk.springboot.security.services.UserDetailsServiceImpl;
import com.nnk.springboot.services.CurvePointService;
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
@WebMvcTest(CurvePointController.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CurvePointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurvePointService mockCurvePointService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private DataSource dataSource;

    @MockBean
    private CustomOAuth2UserService customOAuth2UserService;

    @MockBean
    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Test
    public void testGetAllCurvePoints() throws Exception {

        ArrayList<CurvePoint> curvePoints = new ArrayList<>();
        when(mockCurvePointService.getAllCurvePoints()).thenReturn(curvePoints);

        this.mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"));
    }

    @Test
    public void testAddCurvePointForm() throws Exception {
        this.mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    public void testValidateCurvePoint() throws Exception {
        CurvePoint curvePoint = new CurvePoint(1, 1.1, 1.1);
        when(mockCurvePointService.getCurvePointById(1)).thenReturn(curvePoint);

        when(mockCurvePointService.saveCurvePoint(any(CurvePoint.class))).thenReturn(curvePoint);

        this.mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", curvePoint.getCurveId().toString())
                        .param("term", String.valueOf(curvePoint.getTerm()))
                        .param("value", String.valueOf(curvePoint.getValue())))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/curvePoint/list"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testValidateCurvePoint_BadRequest() throws Exception {
        CurvePoint curvePoint = new CurvePoint(1, 1.1, 1.1);
        when(mockCurvePointService.getCurvePointById(1)).thenReturn(curvePoint);

        this.mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", "")
                        .param("term", "")
                        .param("value", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void testShowUpdateFormForCurvePoint() throws Exception {
        CurvePoint curvePoint = new CurvePoint(1, 1.1, 1.1);
        when(mockCurvePointService.getCurvePointById(1)).thenReturn(curvePoint);

        this.mockMvc.perform(get("/curvePoint/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attribute("curvePoint", hasProperty("term", is(1.1))));
    }

    @Test
    public void testUpdateCurvePoint() throws Exception {
        CurvePoint curvePoint = new CurvePoint(1, 1.1, 1.1);
        curvePoint.setId(1);
        when(mockCurvePointService.getCurvePointById(1)).thenReturn(curvePoint);

        when(mockCurvePointService.updateCurvePoint(1, curvePoint)).thenReturn(true);
        ArrayList<CurvePoint> curvePoints = new ArrayList<>();
        curvePoints.add(curvePoint);
        when(mockCurvePointService.getAllCurvePoints()).thenReturn(curvePoints);

        this.mockMvc.perform(post("/curvePoint/update/1")
                        .param("id", "1")
                        .param("curveId", "1")
                        .param("term", "1.1")
                        .param("value", "1.1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/curvePoint/list"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testUpdateCurvePoint_BadRequest() throws Exception {
        CurvePoint curvePoint = new CurvePoint(1, 1.1, 1.1);
        when(mockCurvePointService.getCurvePointById(1)).thenReturn(curvePoint);

        this.mockMvc.perform(post("/curvePoint/update/1")
                        .param("curveId", "")
                        .param("term", "")
                        .param("value", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void testDeleteCurvePoint() throws Exception {
        CurvePoint curvePoint = new CurvePoint(1, 1.1, 1.1);
        when(mockCurvePointService.getCurvePointById(1)).thenReturn(curvePoint);

        this.mockMvc.perform(get("/curvePoint/delete/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/curvePoint/list"));

    }
}
