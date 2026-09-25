package se.fk.github.rimfrost.erbjudande.topic.integration.config;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class ErbjudandeTopicProvider
{
   private static final Logger LOGGER = LoggerFactory.getLogger(ErbjudandeTopicProvider.class);

   @ConfigProperty(name = "erbjudande.topic.config.path", defaultValue = "")
   String erbjudandeTopicConfigPath;

   private ErbjudandeTopicConfig erbjudandeTopicConfig;

   @PostConstruct
   void init()
   {
      if (erbjudandeTopicConfigPath != null && !erbjudandeTopicConfigPath.isEmpty())
      {
         try
         {
            this.erbjudandeTopicConfig = YamlConfigLoader.loadFromFile(Path.of(erbjudandeTopicConfigPath),
                  ErbjudandeTopicConfig.class);
         }
         catch (FileNotFoundException e)
         {
            LOGGER.warn("Config file {} not found, falling back to classpath discovery", erbjudandeTopicConfigPath);

            // Set erbjudandeTopicConfigPath to null to fall back to classpath loading
            erbjudandeTopicConfigPath = null;
         }
      }

      if (erbjudandeTopicConfigPath == null || erbjudandeTopicConfigPath.isEmpty())
      {
         this.erbjudandeTopicConfig = YamlConfigLoader.loadFromClasspath("config.yaml", ErbjudandeTopicConfig.class);
      }
   }

   public Map<String, String> getErbjudandeTopics()
   {
      if (this.erbjudandeTopicConfig == null || this.erbjudandeTopicConfig.getErbjudandeTopics() == null)
      {
         LOGGER.warn("Missing erbjudande topic values map. Falling back to empty map.");

         return Map.of();
      }

      return this.erbjudandeTopicConfig.getErbjudandeTopics();
   }
}
