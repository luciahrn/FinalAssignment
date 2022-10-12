package sk.ness.academy;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import sk.ness.academy.config.DatabaseConfig;
import sk.ness.academy.service.ArticleService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;



@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "sk.ness.academy", excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = AssignmentApplication.class) })
@Import(DatabaseConfig.class)
public class ArticleIngester {

  public static void main(final String[] args) throws Exception {
    try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ArticleIngester.class)) {
      context.registerShutdownHook();

      final ArticleService articleService = context.getBean(ArticleService.class);

      // Load file with articles and ingest

      BufferedReader reader = new BufferedReader(new FileReader("articles_to_ingest.txt"));
      String json = "";
      try {
        StringBuilder sb = new StringBuilder();
        String line = reader.readLine();

        while (line != null) {
          sb.append(line);
          sb.append("\n");
          line = reader.readLine();
        }
        json = sb.toString();
      } finally {
        reader.close();
      }

      articleService.ingestArticles(json);
    }
  }

  public static String readFileAsString(String file)throws Exception
  {
    return new String(Files.readAllBytes(Paths.get(file)));
  }
}
