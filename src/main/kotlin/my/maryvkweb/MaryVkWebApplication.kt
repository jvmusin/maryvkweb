package my.maryvkweb

import com.vk.api.sdk.client.TransportClient
import com.vk.api.sdk.client.VkApiClient
import my.maryvkweb.service.impl.HashMapUserServiceImpl
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
class MaryVkWebApplication {

    @Bean
    fun templateEngine(templateResolver: ITemplateResolver): TemplateEngine {
        val engine = SpringTemplateEngine()
        engine.addDialect(Java8TimeDialect())
        engine.setTemplateResolver(templateResolver)
        return engine
    }

    @Bean
    fun vkApiClient(transportClient: TransportClient) = VkApiClient(transportClient)

    @Bean
    fun userService() = HashMapUserServiceImpl()
}

fun main(args: Array<String>) {
    SpringApplication.run(MaryVkWebApplication::class.java, *args)
}