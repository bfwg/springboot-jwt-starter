package com.bfwg;

/**
 * Created by fan.jin on 2017-01-14.
 */
import com.bfwg.security.auth.TokenAuthenticationFilter;
import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;

@Configuration
public class MockMvcConfig {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private TokenAuthenticationFilter filter;

    @Autowired
    private Environment env;

    private int port = 8080;

    public RequestBuilder mockRequestBuilder() {
        return null;
    }

    @Bean
    public MockMvc mockMvc() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(wac);
        return builder.addFilters(filter)
                .build();
    }

    @PostConstruct
    protected void restAssured() {
        RestAssuredMockMvc.mockMvc(mockMvc());
        RestAssured.port = this.port;
    }
}
