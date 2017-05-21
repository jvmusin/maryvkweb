package my.maryvkweb

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "vk")
data class VkProperties(

        //app data
        var clientId: Int = 0,
        var clientSecret: String = "",
        var apiVersion: String = "",

        //additional data
        var defaultPeriodToSeek: Long = 0,
        var apiCallDelay: Long = 0,

        //auth only
        var display: String? = "",
        var redirectUri: String = "",
        var scope: String = "",
        var responseType: String = "",
        var accessToken: String = "",
        var ownerId: Int = 0
)