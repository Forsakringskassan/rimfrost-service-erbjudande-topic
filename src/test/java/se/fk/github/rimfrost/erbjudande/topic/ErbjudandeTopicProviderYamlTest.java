package se.fk.github.rimfrost.erbjudande.topic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.fk.github.rimfrost.erbjudande.topic.integration.config.ErbjudandeTopicProvider;

public class ErbjudandeTopicProviderYamlTest
{
   @Test
   @DisplayName("Config laddas från sökväg angiven via konfigurationsproperty")
   void init_loadsConfigFromApplicationConfigPath() throws Exception
   {
      var configPath = Path.of(getClass().getClassLoader().getResource("config-path-test.yaml").toURI());
      ErbjudandeTopicProvider provider = new ErbjudandeTopicProvider();
      setField(provider, "erbjudandeTopicConfigPath", configPath.toString());

      invokeInit(provider);

      assertNotNull(provider.getErbjudandeTopics());
      assertEquals("test-topic", provider.getErbjudandeTopics().get("7d4a6c38-348b-4f46-9278-b1bfeabc0353"));
   }

   @Test
   @DisplayName("Config som inte uppfyller JSON Schema ska avvisas")
   void init_shouldFailWhenConfigViolatesSchema() throws Exception
   {
      var configPath = Path.of(getClass().getClassLoader().getResource("config-schema-invalid-test.yaml").toURI());
      ErbjudandeTopicProvider provider = new ErbjudandeTopicProvider();
      setField(provider, "erbjudandeTopicConfigPath", configPath.toString());

      var ex = assertThrows(IllegalStateException.class, () -> invokeInit(provider));
      assertTrue(ex.getMessage().contains("Config violates JSON Schema"));
   }

   @Test
   @DisplayName("Config där obligatoriska sektioner saknas ska avvisas — tillämpas via JSON Schema required-attribut")
   void init_shouldFailWhenRequiredSectionIsMissing() throws Exception
   {
      var configPath = Path.of(getClass().getClassLoader().getResource("config-missing-section-test.yaml").toURI());
      ErbjudandeTopicProvider provider = new ErbjudandeTopicProvider();
      setField(provider, "erbjudandeTopicConfigPath", configPath.toString());

      var ex = assertThrows(IllegalStateException.class, () -> invokeInit(provider));
      assertTrue(ex.getMessage().contains("Config violates JSON Schema"));
   }

   private static void setField(Object target, String fieldName, String value) throws Exception
   {
      Field field = target.getClass().getDeclaredField(fieldName);
      field.setAccessible(true);
      field.set(target, value);
   }

   private static void invokeInit(ErbjudandeTopicProvider provider) throws Exception
   {
      var method = ErbjudandeTopicProvider.class.getDeclaredMethod("init");
      method.setAccessible(true);
      try
      {
         method.invoke(provider);
      }
      catch (InvocationTargetException e)
      {
         if (e.getCause() instanceof Exception cause)
         {
            throw cause;
         }
         throw e;
      }
   }
}
