package com.encora.proaatopic.controllers;

import com.encora.proaatopic.domain.Topic;
import com.encora.proaatopic.dto.TopicDto;
import com.encora.proaatopic.dto.TopicTopDto;
import com.encora.proaatopic.services.AuthService;
import com.encora.proaatopic.services.TopicService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TopicController.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TopicControllerTest {
    @MockBean
    private AuthService authService;

    @MockBean
    private TopicService topicService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    private List<Topic> topics;

    private ObjectMapper objectMapper;

    @BeforeAll
    void beforeAll() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        topics = new ArrayList<>();
        objectMapper = new ObjectMapper();

        topics.add(new Topic("Unit testing 1", "1"));
        topics.add(new Topic("Unit testing 2", "1"));
        topics.add(new Topic("Unit testing 3", "1"));

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn("1");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class TopTen {
        public List<TopicTopDto> topTen = new ArrayList<>();

        @BeforeAll
        void beforeAll() {
            topTen.add(new TopicTopDto(1, "Unit testing 1", 3L));
            topTen.add(new TopicTopDto(2, "Unit testing 2", 1L));
            topTen.add(new TopicTopDto(3, "Unit testing 3", 0L));
        }

        @Test
        void when_called_should_return_topic_list() throws Exception {
            when(topicService.topTen()).thenReturn(topTen);

            mockMvc.perform(get("/topics/top-ten"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(3)))
                    .andExpect(jsonPath("$[0].id", is(1)))
                    .andExpect(jsonPath("$[0].name", is("Unit testing 1")))
                    .andExpect(jsonPath("$[0].resources", is(3)));
        }

        @Test
        void when_no_topics_should_return_empty() throws Exception {
            when(topicService.topTen()).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/topics/top-ten"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(0)));
        }

        @Test
        void when_called_should_return_sorted_list() throws Exception {
            when(topicService.topTen()).thenReturn(topTen);

            mockMvc.perform(get("/topics/top-ten"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(3)))
                    .andExpect(jsonPath("$[0].resources", is(3)))
                    .andExpect(jsonPath("$[1].resources", is(1)))
                    .andExpect(jsonPath("$[2].resources", is(0)));
        }
    }

    @Nested
    class TopicsByOwner {

        @Test
        void when_called_with_valid_userId_should_return_list() throws Exception {
            String userId = "1";

            when(topicService.topicsByOwner(userId)).thenReturn(topics);

            mockMvc.perform(get("/topics"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(3)))
                    .andExpect(jsonPath("$[0].name", is("Unit testing 1")))
                    .andExpect(jsonPath("$[1].name", is("Unit testing 2")))
                    .andExpect(jsonPath("$[2].name", is("Unit testing 3")));
        }

        @Test
        void when_called_with_invalid_userId_should_return_empty() throws Exception {
            String userId = "0";

            when(topicService.topicsByOwner(userId)).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/topics"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(0)));
        }
    }

    @Nested
    class CreateTopic {
        @Test
        void when_called_with_topic_should_return_topic() throws Exception {
            Topic topic = new Topic("Topic", "1");

            TopicDto topicDto = new TopicDto("Topic");
            String json = objectMapper.writeValueAsString(topicDto);

            when(topicService.addTopic(ArgumentMatchers.any(Topic.class))).thenReturn(topic);

            mockMvc.perform(post("/topics")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", is("Topic")))
                    .andExpect(jsonPath("$.userId", is("1")));
        }

        @Test
        void when_called_without_topic_should_throw_error() throws Exception {
            String json = objectMapper.writeValueAsString(null);

            mockMvc.perform(post("/topics")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().is4xxClientError());
        }
    }

}
