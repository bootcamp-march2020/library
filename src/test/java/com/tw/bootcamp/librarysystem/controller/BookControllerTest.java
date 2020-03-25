package com.tw.bootcamp.librarysystem.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class BookControllerTest {

    @Autowired
    private MockMvc bookControllerMock;

    @Test
    public void sampleEndpointTest() throws Exception {
        ResultActions result = bookControllerMock.perform(MockMvcRequestBuilders.get("/books/sample"));
        result.andExpect(status().isOk());
    }
}
