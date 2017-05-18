package my.maryvk.maryvkweb;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class MaryVkWebApplication {

	@Bean
    public VkApiClient vkApiClient() {
	    return new VkApiClient(HttpTransportClient.getInstance());
    }

    @Bean
    public UserActor owner(@Value("${vk.owner-id}") int ownerId, @Value("${vk.access-token}") String accessToken) {
        return new UserActor(ownerId, accessToken);
    }

    @Bean
    public TemplateEngine templateEngine(ITemplateResolver templateResolver) {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.addDialect(new Java8TimeDialect());
        engine.setTemplateResolver(templateResolver);
        return engine;
    }

    public static void main(String[] args) {
        SpringApplication.run(MaryVkWebApplication.class, args);
    }

}