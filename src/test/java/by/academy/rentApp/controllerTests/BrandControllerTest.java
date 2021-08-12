package by.academy.rentApp.controllerTests;


import by.academy.rentApp.controller.BrandController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BrandController.class)
public class BrandControllerTest {

    @Autowired
    private MockMvc mockMvc;

//    @Test
//    void shouldForbidAccessToUnauthenticatedRequests() throws Exception {
//        this.mockMvc
//                .perform(MockMvcRequestBuilders.get("/brands"))
//                .andExpect(status().is4xxClientError());
//    }

    @Test
    public void goToAllBrandsPage() throws Exception{
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/brands"))
                .andExpect(status().isOk());
    }

}
