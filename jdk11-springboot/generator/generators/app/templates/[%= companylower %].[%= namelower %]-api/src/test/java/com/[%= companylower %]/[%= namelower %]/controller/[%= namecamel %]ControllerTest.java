package com.[%= companylower %].[%= namelower %].controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.[%= companylower %].[%= namelower %].commons.dto.[%= namecamel %]Dto;
import com.[%= companylower %].[%= namelower %].commons.request.PaginatedRequestDto;
import com.[%= companylower %].[%= namelower %].commons.response.GenericResponseDto;
import com.[%= companylower %].[%= namelower %].commons.response.PaginatedResponseDto;
import com.[%= companylower %].[%= namelower %].facade.[%= namecamel %]Facade;
import com.[%= companylower %].[%= namelower %].persistence.redis.StringRedisRepository;
import com.google.gson.Gson;
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
class [%= namecamel %]ControllerTest
{

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private [%= namecamel %]Facade [%= namelower %]Facade;

  @MockBean
  private StringRedisRepository redis;

  @BeforeEach
  void setUp()
  {
    when( this.redis.hasKey( anyString() ) ).thenReturn( false );
  }

  /**
   * Test method for {@link com.[%= companylower %].[%= namelower %].controller.[%= namecamel %]Controller#find[%= namecamel %]s(int, int)}.
   * 
   * @throws Exception
   */
  @Test
  void testFind[%= namecamel %]s() throws Exception
  {
    int pageSize = 20;

    var data = new ArrayList<[%= namecamel %]Dto>();
    for( int i = 0; i < pageSize; i++ )
    {
      data.add( this.create[%= namecamel %]( i + 1 ) );
    }
    var paginated = new PaginatedResponseDto<[%= namecamel %]Dto>( 0, pageSize, 50, data );
    when( this.[%= namelower %]Facade.find[%= namecamel %]s( any( PaginatedRequestDto.class ) ) ).thenReturn( paginated );

    MvcResult result = mockMvc.perform( MockMvcRequestBuilders.get( "/api/[%= namelower %]s?limit=20&offset=0" ) )
        .andExpect( status().isOk() )
        .andExpect( jsonPath( "$.header.code" ).value( "0" ) )
        .andExpect( jsonPath( "$.page" ).value( "0" ) )
        .andExpect( jsonPath( "$.size" ).value( "20" ) )
        .andExpect( jsonPath( "$.data" ).isArray() )
        .andExpect( jsonPath( "$.data[0].id" ).value( 1 ) )
        .andReturn();

    assertNotNull( result );
  }

  /**
   * Test method for {@link com.[%= companylower %].[%= namelower %].controller.[%= namecamel %]Controller#find[%= namecamel %](java.lang.String)}.
   */
  @ParameterizedTest()
  @ValueSource(booleans = { true, false })
  void testFind[%= namecamel %]( boolean exists ) throws Exception
  {
    var [%= namelower %] = this.create[%= namecamel %]( 1 );
    var generic = new GenericResponseDto<>( [%= namelower %] );

    if( exists )
    {
      when( this.redis.hasKey( anyString() ) ).thenReturn( true );
      var gson = new GsonBuilder().create();
      when( this.redis.get( anyString() ) ).thenReturn( gson.toJson( generic ) );
    }
    else
    {
      when( this.[%= namelower %]Facade.find( anyInt() ) ).thenReturn( generic );
    }

    MvcResult result = mockMvc.perform( MockMvcRequestBuilders.get( "/api/[%= namelower %]s/1" ) ).andExpect( status().isOk() )
        .andExpect( jsonPath( "$.header.code" ).value( "0" ) ).andExpect( jsonPath( "$.body.id" ).value( 1 ) )
        .andReturn();

    assertNotNull( result );
  }

  /**
   * Test method for {@link com.[%= companylower %].[%= namelower %].controller.[%= namecamel %]Controller#find[%= namecamel %](java.lang.String)}.
   */
  @Test
  void testFind[%= namecamel %]_notExists() throws Exception
  {
    when( this.[%= namelower %]Facade.find( anyInt() ) ).thenReturn( null );

    MvcResult result = mockMvc.perform( MockMvcRequestBuilders.get( "/api/[%= namelower %]s/99999" ) )
        .andExpect( status().isNoContent() ).andReturn();

    assertNotNull( result );
  }

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

    var generic = new GenericResponseDto<>( [%= namelower %] );
    generic.getBody().setId( 1 );
    when( this.[%= namelower %]Facade.create( any( [%= namecamel %]Dto.class ) ) ).thenReturn( generic );

    Gson gson = new GsonBuilder().create();

    MvcResult result = mockMvc
        .perform( MockMvcRequestBuilders.post( "/api/[%= namelower %]s" ).content( gson.toJson( [%= namelower %] ) )
            .accept( MediaType.APPLICATION_JSON ).contentType( MediaType.APPLICATION_JSON ) )
        .andExpect( status().isCreated() ).andExpect( jsonPath( "$.header.code" ).value( "0" ) )
        .andExpect( jsonPath( "$.body.id" ).value( 1 ) ).andReturn();

    assertNotNull( result );
  }

  /**
   * Test method for
   * {@link com.[%= companylower %].[%= namelower %].controller.[%= namecamel %]Controller#update(java.lang.String, com.[%= companylower %].[%= namelower %].commons.dto.[%= namecamel %]Dto)}.
   */
  @ParameterizedTest()
  @ValueSource(booleans = { true, false })
  void testUpdate( boolean exists ) throws Exception
  {
    var [%= namelower %] = this.create[%= namecamel %]( 1 );
    var generic = new GenericResponseDto<>( exists );

    when( this.[%= namelower %]Facade.update( any( [%= namecamel %]Dto.class ) ) ).thenReturn( generic );

    Gson gson = new GsonBuilder().create();

    MvcResult result = mockMvc
        .perform( MockMvcRequestBuilders.put( "/api/[%= namelower %]s/1" ).content( gson.toJson( [%= namelower %] ) )
            .accept( MediaType.APPLICATION_JSON ).contentType( MediaType.APPLICATION_JSON ) )
        .andExpect( status().isOk() ).andExpect( jsonPath( "$.header.code" ).value( "0" ) )
        .andExpect( jsonPath( "$.body" ).value( exists ) ).andReturn();

    assertNotNull( result );
  }

  /**
   * Test method for {@link com.[%= companylower %].[%= namelower %].controller.[%= namecamel %]Controller#delete(java.lang.String)}.
   */
  @ParameterizedTest()
  @ValueSource(booleans = { true, false })
  void testDelete( boolean exists ) throws Exception
  {
    var generic = new GenericResponseDto<>( exists );
    when( this.[%= namelower %]Facade.delete( anyInt() ) ).thenReturn( generic );

    MvcResult result = mockMvc
        .perform( MockMvcRequestBuilders.delete( "/api/[%= namelower %]s/1" ).accept( MediaType.APPLICATION_JSON ) )
        .andExpect( status().isOk() ).andExpect( jsonPath( "$.header.code" ).value( "0" ) )
        .andExpect( jsonPath( "$.body" ).value( exists ) ).andReturn();

    assertNotNull( result );
  }

  @Test
  void testPing() throws Exception
  {
    MvcResult result = mockMvc.perform( MockMvcRequestBuilders.get( "/api/[%= namelower %]s/ping" ) ).andExpect( status().isOk() )
        .andReturn();

    assertNotNull( result );
    log.info( result.getResponse().getContentAsString() );
  }

  private [%= namecamel %]Dto create[%= namecamel %]( int i )
  {
    var [%= namelower %] = new [%= namecamel %]Dto();
    [%= namelower %].setId( 1 );
    return [%= namelower %];
  }
}
