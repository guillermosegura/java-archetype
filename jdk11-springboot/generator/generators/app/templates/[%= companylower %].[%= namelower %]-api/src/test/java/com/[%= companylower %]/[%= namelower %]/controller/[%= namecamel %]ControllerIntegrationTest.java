package com.[%= companylower %].[%= namelower %].controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.[%= companylower %].[%= namelower %].commons.dto.[%= namecamel %]Dto;
import com.[%= companylower %].[%= namelower %].persistence.redis.StringRedisRepository;
import com.google.gson.GsonBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * [%= namecamel %] Controller Test class
 * 
 * @author [%= username %]
 */
@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class [%= namecamel %]ControllerIntegrationTest
{
  @Autowired
  private MockMvc mockMvc;
  
  @MockBean
  private StringRedisRepository redis;
  
  /**
   * Test method for
   * {@link com.[%= companylower %].[%= namelower %].controller.[%= namecamel %]Controller#create(com.[%= companylower %].[%= namelower %].commons.dto.[%= namecamel %]Dto)}.
   */
  @Test
  void testCreate() throws Exception
  {
    var [%= namelower %] = new [%= namecamel %]Dto();
    [%= namelower %].setCity( "CDMX" );
    [%= namelower %].setTerritory( "LATAM" );
    [%= namelower %].setState( "CDMX" );
    [%= namelower %].setAddressLine1( "Address" );
    [%= namelower %].setAddressLine2( "Address" );
    [%= namelower %].setCountry( "Mexico" );
    [%= namelower %].setPhone( "5555555" );
    [%= namelower %].setPostalCode( "55555" );
       
    var gson = new GsonBuilder().create();
    
    MvcResult result = mockMvc.perform( MockMvcRequestBuilders.post( "/api/[%= namelower %]s"  )
            .content(gson.toJson([%= namelower %]))
            .accept( MediaType.APPLICATION_JSON )
            .contentType( MediaType.APPLICATION_JSON ))
        .andExpect( status().isCreated() )
        .andExpect( jsonPath( "$.header.code" ).value( "0" ) )
        .andExpect( jsonPath( "$.body.id" ).value( 100 ) ).andReturn();

    assertNotNull( result );
  }
}
