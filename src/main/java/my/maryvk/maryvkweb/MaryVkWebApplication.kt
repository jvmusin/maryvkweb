package my.maryvk.maryvkweb

import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.UserActor
import com.vk.api.sdk.httpclient.HttpTransportClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling
import org.thymeleaf.TemplateEngine
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect
import org.thymeleaf.spring4.SpringTemplateEngine
import org.thymeleaf.templateresolver.ITemplateResolver

@SpringBootApplication
@EnableScheduling
@EnableCaching
open class MaryVkWebApplication {

    @Bean
    open fun vkApiClient(): VkApiClient {
        return VkApiClient(HttpTransportClient.getInstance())
    }

    @Bean
    open fun owner(@Value("\${vk.owner-id}") ownerId: Int, @Value("\${vk.access-token}") accessToken: String): UserActor {
        return UserActor(ownerId, accessToken)
    }

    @Bean
    open fun templateEngine(templateResolver: ITemplateResolver): TemplateEngine {
        val engine = SpringTemplateEngine()
        engine.addDialect(Java8TimeDialect())
        engine.setTemplateResolver(templateResolver)
        return engine
    }

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(MaryVkWebApplication::class.java, *args)
        }
    }
}