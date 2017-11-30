package MatchMakerTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@WebMvcTest
public class WebSocketTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void webSocketConnection() throws Exception {
        mockMvc.perform(webSocketConnection()
                .
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(content().string("42"));
    }

}