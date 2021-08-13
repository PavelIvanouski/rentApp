package by.academy.rentApp.controller;


import by.academy.rentApp.dto.BrandDto;
import by.academy.rentApp.service.BrandService;
import by.academy.rentApp.service.MyUserDetailsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BrandController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BrandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BrandController brandController;

    @MockBean
    private BrandService brandService;

    @MockBean
    private MyUserDetailsService userDetailsService;

    @Test
    @WithMockUser("spring")
    public void testGetBrands() throws Exception {

        given(brandService.getAll())
                .willReturn(List.of(new BrandDto(1, "VW"), new BrandDto(2, "BMW")));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/brands"))
                .andExpect(status().isOk())
                .andExpect(view().name("brand/brands"))
                .andExpect(model().attributeExists("brands"))
                .andExpect(model().attribute("brands", hasSize(2)))
                .andExpect(model().attribute("brands", hasItem(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("name", is("VW"))
                        )
                )));

        verify(brandService, times(1)).getAll();
        verifyNoMoreInteractions(brandService);

    }


}
