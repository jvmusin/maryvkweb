package my.maryvkweb

import com.vk.api.sdk.client.ClientResponse
import com.vk.api.sdk.client.TransportClient
import com.vk.api.sdk.exceptions.ApiException
import com.vk.api.sdk.exceptions.ClientException
import com.vk.api.sdk.httpclient.HttpTransportClient
import org.springframework.stereotype.Component
import java.io.File
import java.lang.System.currentTimeMillis
import java.util.*
import java.util.concurrent.locks.ReentrantLock

@Component
class RestTransportClient(
        vkProperties: VkProperties
) : TransportClient {

    private val ONE_SECOND_MILLIS = 1000L

    private val client = HttpTransportClient.getInstance()
    private val maxQueriesPerSecond = vkProperties.maxQueriesPerSecond

    private val lock = ReentrantLock(true)
    private val usedTimes = ArrayDeque<Long>()

    @Throws(ApiException::class, ClientException::class)
    private inline fun execute(request: () -> ClientResponse): ClientResponse {
        try {
            lock.lock()

            while (timeSinceFirstRequest() > ONE_SECOND_MILLIS)
                usedTimes.pollFirst()

            if (usedTimes.size == maxQueriesPerSecond) {
                val toWait = ONE_SECOND_MILLIS - timeSinceFirstRequest()
                if (toWait > 0)     //if garbage collection started, it may be < 0
                    Thread.sleep(toWait)
                usedTimes.pollFirst()
            }
            
            return request()
        } finally {
            //set used time after request() to do requests more safely
            usedTimes.addLast(currentTimeMillis())
            lock.unlock()
        }
    }

    private fun timeSinceFirstRequest() = if (usedTimes.isEmpty()) ONE_SECOND_MILLIS else currentTimeMillis() - usedTimes.peekFirst()

    override fun post(url: String?, body: String?): ClientResponse {
        return execute { client.post(url, body) }
    }

    override fun post(url: String?, fileName: String?, file: File?): ClientResponse {
        return execute { client.post(url, fileName, file) }
    }

    override fun post(url: String?): ClientResponse {
        return execute { client.post(url) }
    }
}