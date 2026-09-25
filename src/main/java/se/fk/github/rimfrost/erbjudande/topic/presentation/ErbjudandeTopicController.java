package se.fk.github.rimfrost.erbjudande.topic.presentation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import se.fk.github.rimfrost.erbjudande.topic.integration.config.ErbjudandeTopicProvider;
import se.fk.rimfrost.erbjudande.kafka.topic.jaxrsspec.controllers.generatedsource.ErbjudandeTopicApi;
import se.fk.rimfrost.erbjudande.kafka.topic.jaxrsspec.controllers.generatedsource.model.TopicResponse;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ErbjudandeTopicController implements ErbjudandeTopicApi
{
   @Inject
   ErbjudandeTopicProvider erbjudandeTopicProvider;

   @Override
   public TopicResponse getTopic(@Size(min = 1) String erbjudandeId)
   {
      var topic = erbjudandeTopicProvider.getErbjudandeTopics().get(erbjudandeId);

      if (topic == null)
      {
         throw new WebApplicationException(Response.Status.NOT_FOUND);
      }

      return new TopicResponse(topic);
   }
}
