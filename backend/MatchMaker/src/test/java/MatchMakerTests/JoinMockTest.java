package MatchMakerTests;


import MatchMakerTests.WebSocketClient.WebSocketClient;
import boot.MMController;
import boot.MmApplication;
import boot.Requests;
import okhttp3.Response;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@WebMvcTest
@ContextConfiguration(classes = MmApplication.class)
public class JoinMockTest {
    @Autowired
    MockMvc mockMvc;
    @Test
    public void joinOnePlayer() throws Exception {
        mockMvc.perform(post("/matchmaker/join")
                .content("name=Кунька_Задунайский")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string("42"));
        Assert.assertTrue(MMController.getGameId().equals(42));

    }
    @Test
    public void joinFourPlayer() throws Exception {
        mockMvc.perform(post("/matchmaker/join")
                .content("name=Поручик_Ржевский")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string("42"));

        WebSocketClient client1 = new WebSocketClient("Поручик_Ржевский","42");
        client1.startClient();

        mockMvc.perform(post("/matchmaker/join")
                .content("name=Кашалот_Евгенич")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string("42"));

        WebSocketClient client2 = new WebSocketClient("Кашалот_Евгенич","42");
        client2.startClient();

        mockMvc.perform(post("/matchmaker/join")
                .content("name=Изя_Шпайцман")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string("42"));

        WebSocketClient client3 = new WebSocketClient("Изя_Шпайцман","42");
        client3.startClient();

        mockMvc.perform(post("/matchmaker/join")
                .content("name=Адольф_Виссарионович")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string("42"));

        WebSocketClient client4 = new WebSocketClient("Адольф_Виссарионович","42");
        client4.startClient();

        Assert.assertTrue(MMController.getGameId().equals(null));
    }
}