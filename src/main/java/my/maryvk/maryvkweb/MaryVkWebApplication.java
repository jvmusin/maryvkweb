package my.maryvk.maryvkweb;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import my.maryvk.maryvkweb.service.VkService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

@SpringBootApplication
@EnableScheduling
public class MaryVkWebApplication {

	@Bean
    public VkApiClient vk() {
	    return new VkApiClient(HttpTransportClient.getInstance());
    }

    @Bean
    public UserActor owner(@Value("${vk.owner-id}") int ownerId, @Value("${vk.access-token}") String accessToken) {
        return new UserActor(ownerId, accessToken);
    }

    public static void main(String[] args) {
        SpringApplication.run(MaryVkWebApplication.class, args);
    }

}