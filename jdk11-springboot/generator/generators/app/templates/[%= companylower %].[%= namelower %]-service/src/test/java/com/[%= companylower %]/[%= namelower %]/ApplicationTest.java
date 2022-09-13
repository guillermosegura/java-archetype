package com.[%= companylower %].[%= namelower %];

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot Application Test class
 * 
 * @author [%= username %]
 */
@SpringBootApplication
class ApplicationTest
{

  public static void main( String[] args )
  {
    SpringApplication.run( ApplicationTest.class, args );
  }

  @Test
  void test() {
    assertTrue( true );
  }
}
