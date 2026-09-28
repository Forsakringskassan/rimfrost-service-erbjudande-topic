package se.fk.github.rimfrost.erbjudande.topic.integration.config;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class ErbjudandeTopicProvider
{
   private static final Logger LOGGER = LoggerFactory.getLogger(ErbjudandeTopicProvider.class);

   @ConfigProperty(name = "erbjudande.topic.config.path")
   Optional<String> erbjudandeTopicConfigPath;

   private ErbjudandeTopicConfig erbjudandeTopicConfig;

   @PostConstruct
   void init()
   {
      if (erbjudandeTopicConfigPath != null && erbjudandeTopicConfigPath.isPresent()
            && !erbjudandeTopicConfigPath.get().isEmpty())
      {
         try
         {
            this.erbjudandeTopicConfig = YamlConfigLoader.loadFromFile(Path.of(erbjudandeTopicConfigPath.get()),
                  ErbjudandeTopicConfig.class);
         }
         catch (FileNotFoundException e)
         {
            LOGGER.warn("Config file {} not found, falling back to classpath discovery", erbjudandeTopicConfigPath.get());

            // Set erbjudandeTopicConfigPath to null to fall back to classpath loading
            erbjudandeTopicConfigPath = null;
         }
      }

      if (erbjudandeTopicConfigPath == null || erbjudandeTopicConfigPath.isEmpty() || erbjudandeTopicConfigPath.get().isEmpty())
      {
         LOGGER.info("No config file path configured. Falling back to classpath discovery.");
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
