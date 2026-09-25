package se.fk.github.rimfrost.erbjudande.topic.integration.config;

import java.util.Map;

public class ErbjudandeTopicConfig
{
   public ErbjudandeTopicConfig()
   {
      // required by SnakeYAML
   }

   private Map<String, String> erbjudandeTopics;

   public Map<String, String> getErbjudandeTopics()
   {
      return erbjudandeTopics;
   }

   public void setErbjudandeTopics(Map<String, String> erbjudandeTopics)
   {
      this.erbjudandeTopics = Map.copyOf(erbjudandeTopics);
   }
}
