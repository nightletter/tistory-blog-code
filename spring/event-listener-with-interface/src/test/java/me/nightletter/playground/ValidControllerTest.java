package me.nightletter.playground;

import me.nightletter.playground.controllers.TestRequest;
import me.nightletter.playground.controllers.ValidController;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = ValidController.class)
public class ValidControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void run() throws Exception {
        mockMvc.perform(
                        get("/")
                                .param("birthYear", "1900")
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
