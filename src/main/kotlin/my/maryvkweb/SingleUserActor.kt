package my.maryvkweb

import com.vk.api.sdk.client.actors.UserActor
import org.springframework.stereotype.Component

@Component
data class SingleUserActor(private val vkProperties: VkProperties) : UserActor(null, null) {
    override fun getId() = vkProperties.ownerId
    override fun getAccessToken() = vkProperties.accessToken
}