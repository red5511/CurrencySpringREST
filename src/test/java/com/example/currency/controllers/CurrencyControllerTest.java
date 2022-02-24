package com.example.currency.controllers;

import com.example.currency.services.CurrencyDataService;
import com.example.currency.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(CurrencyController.class)
//@SpringBootTest
class CurrencyControllerTest {
    @MockBean
    CurrencyDataService currencyDataService;

    @MockBean
    UserService userService;
    @MockBean
    PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvck;

    @Test
    @WithMockUser(roles = {"Admin"})
    void shouldReturnHelloWorld() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/");
        MvcResult result = mockMvck.perform(request).andReturn();
        assertEquals("Hello Word", result.getResponse().getContentAsString());
    }
    @Test
    @WithMockUser(roles = {"Admin"})
    void shouldReturnTrueAnd200FWithAdminRole() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api");
        MvcResult result = mockMvck.perform(request).andReturn();
        verify(currencyDataService).getCurrentData();
        assertEquals(200, result.getResponse().getStatus());
    }
    @Test
    @WithMockUser(roles = {"User"})
    void shouldReturnTrueAnd400WithoutAdminRole() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api");
        MvcResult result = mockMvck.perform(request).andReturn();
        verify(currencyDataService, Mockito.times(0)).getCurrentData();
        assertEquals(403, result.getResponse().getStatus());
    }

    @Test
    @WithMockUser(roles = {"Admin"})
    void shouldReturnTrueAnd200WhenReturningFullInfo() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/full_info");
        MvcResult result = mockMvck.perform(request).andReturn();
        verify(currencyDataService).getFullData();
        assertEquals(200, result.getResponse().getStatus());

    }

    @Test
    @WithMockUser(roles = {"Admin"})
    void shouldReturnExchangedStringCurrency() throws Exception {
        Double returned = 30.;
        Double passed = 33.;
        Mockito.when(currencyDataService.getPrice("curr1", "curr2")).thenReturn(returned);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/curr1/curr2/" + passed);
        MvcResult result = mockMvck.perform(request).andReturn();
        System.out.println(result.getResponse().getContentAsString());
        assertEquals(200, result.getResponse().getStatus());

        verify(currencyDataService).getPrice("curr1", "curr2");
        assertEquals("{\"amount\":" + passed + ",\"price\":" + returned + ",\"exchanged\":" + returned * passed + ",\"curr1\":\"curr1\",\"curr2\":\"curr2\"}", result.getResponse().getContentAsString());



    }
}