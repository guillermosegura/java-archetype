package com.[%= companylower %].[%= namelower %].controller;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.[%= companylower %].[%= namelower %].dto.QueryWrapperDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OfficeGraphQLControllerIntegrationTest
{
  @Autowired
  private MockMvc mockMvc;

  @Test
  void testOffices() throws Exception
  {
    String query = "query {\r\n"
        + "    offices (query : {\r\n"
        + "        territory : \"NA\"\r\n"
        + "    })\r\n"
        + "    {\r\n"
        + "        officeCode\r\n"
        + "        city\r\n"
        + "        city\r\n"
        + "        phone\r\n"
        + "    }\r\n"
        + "}";
    
    
    Gson gson = new GsonBuilder().create();
    
    var wrapper = new QueryWrapperDto();
    wrapper.setQuery( query );

    MvcResult result = mockMvc
        .perform( MockMvcRequestBuilders.post( "/graphql" )
          .content( gson.toJson( wrapper ) )
          .accept( MediaType.APPLICATION_JSON )
          .contentType( MediaType.APPLICATION_JSON ) )
        .andExpect( status().isOk() )
        .andExpect(request().asyncStarted())
        .andReturn();
    
    this.mockMvc.perform( asyncDispatch( result ) )
    .andDo( print() ).andExpect(status().isOk())
    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    .andExpect( jsonPath( "$.data" ).isNotEmpty() )
    .andExpect( jsonPath( "$.data.offices" ).isArray() )
    .andReturn();

    assertNotNull( result );
    
    var content = result.getResponse().getContentAsString();
    log.info( "--->{}", content );
  }
}
