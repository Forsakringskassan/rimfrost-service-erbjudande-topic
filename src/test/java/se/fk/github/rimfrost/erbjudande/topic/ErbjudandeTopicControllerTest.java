package se.fk.github.rimfrost.erbjudande.topic;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import se.fk.rimfrost.erbjudande.kafka.topic.jaxrsspec.controllers.generatedsource.model.TopicResponse;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class ErbjudandeTopicControllerTest
{
   @Test
   void should_return_404_on_unknown_erbjudande_id()
   {
      given().contentType(ContentType.JSON).get("/topic/20e86771-1fde-4b86-9f86-8e20d9933f6d").then().statusCode(404);
   }

   @Test
   void should_return_topic_on_known_erbjudande_id()
   {
      var response = given().contentType(ContentType.JSON).get("/topic/7d4a6c38-348b-4f46-9278-b1bfeabc0353").then()
            .statusCode(200).extract().as(TopicResponse.class);

      assertNotNull(response);
      assertEquals("vah-handlaggning-requests", response.getTopic());
   }
}
