package com.encora.proaatopic.controllers;

import com.encora.proaatopic.dto.TopicDto;
import com.encora.proaatopic.services.TopicService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TopicController.class)
class TopicControllerTest {
    @MockBean
    private TopicService topicService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void topTen_should_return_topic_list() throws Exception {
        when(topicService.topTen()).thenReturn(this.buildTestingTopTen());

        mockMvc.perform(get("/topics/top-ten"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Unit testing 1")))
                .andExpect(jsonPath("$[0].resources", is(3)));
    }

    @Test
    void topTen_should_handle_empty() throws Exception {
        when(topicService.topTen()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/topics/top-ten"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void topTen_should_return_sorted_list() throws Exception {
        when(topicService.topTen()).thenReturn(this.buildTestingTopTen());

        mockMvc.perform(get("/topics/top-ten"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].resources", is(3)))
                .andExpect(jsonPath("$[1].resources", is(1)))
                .andExpect(jsonPath("$[2].resources", is(0)));
    }

    private List<TopicDto> buildTestingTopTen() {
        List<TopicDto> topics = new ArrayList<>();
        topics.add(new TopicDto(1, "Unit testing 1", 3));
        topics.add(new TopicDto(2, "Unit testing 2", 1));
        topics.add(new TopicDto(3, "Unit testing 3", 0));
        return topics;
    }

}
