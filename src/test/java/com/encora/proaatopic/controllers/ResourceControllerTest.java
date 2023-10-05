package com.encora.proaatopic.controllers;

import com.encora.proaatopic.domain.Resource;
import com.encora.proaatopic.services.AuthService;
import com.encora.proaatopic.services.ResourceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ResourceController.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ResourceControllerTest {
    @MockBean
    private AuthService authService;

    @MockBean
    private ResourceService resourceService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    private List<Resource> resources;

    private ObjectMapper objectMapper;

    @BeforeAll
    void beforeAll() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        resources = new ArrayList<>();
        objectMapper = new ObjectMapper();

        resources.add(new Resource(1, "Unit testing 1", "", null));
        resources.add(new Resource(2, "Unit testing 2", "", null));
        resources.add(new Resource(3, "Unit testing 3", "", null));

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn("1");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Nested
    class ResourcesByOwner {

        @Test
        void when_called_with_valid_userId_should_return_list() throws Exception {
            String userId = "1";

            when(resourceService.resourcesByOwner(userId)).thenReturn(resources);

            mockMvc.perform(get("/resources"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(3)))
                    .andExpect(jsonPath("$[0].descriptionName", is("Unit testing 1")))
                    .andExpect(jsonPath("$[1].descriptionName", is("Unit testing 2")))
                    .andExpect(jsonPath("$[2].descriptionName", is("Unit testing 3")));
        }

        @Test
        void when_called_with_invalid_userId_should_return_empty() throws Exception {
            String userId = "0";

            when(resourceService.resourcesByOwner(userId)).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/resources"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(0)));
        }
    }
}
