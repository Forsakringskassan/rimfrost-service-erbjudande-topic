package se.fk.github.rimfrost.erbjudande.topic.presentation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import se.fk.rimfrost.erbjudande.kafka.topic.jaxrsspec.controllers.generatedsource.ErbjudandeTopicApi;
import se.fk.rimfrost.erbjudande.kafka.topic.jaxrsspec.controllers.generatedsource.model.TopicResponse;

import java.util.HashMap;
import java.util.Map;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ErbjudandeTopicController implements ErbjudandeTopicApi
{
   private final static Map<String, String> TOPICS = new HashMap<>(Map.of(
         "7d4a6c38-348b-4f46-9278-b1bfeabc0353", "vah-handlaggning-requests",
         "256470a0-671f-433d-80bc-2bf6ba097868", "vab-handlaggning-requests"));

   @Override
   public TopicResponse getTopic(@Size(min = 1) String erbjudandeId)
   {
      var topic = TOPICS.get(erbjudandeId);

      if (topic == null)
      {
         throw new WebApplicationException(Response.Status.NOT_FOUND);
      }

      return new TopicResponse(topic);
   }
}
