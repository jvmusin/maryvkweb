package my.maryvkweb

import com.vk.api.sdk.client.ClientResponse
import com.vk.api.sdk.client.TransportClient
import com.vk.api.sdk.exceptions.ApiException
import com.vk.api.sdk.exceptions.ClientException
import com.vk.api.sdk.httpclient.HttpTransportClient
import org.springframework.stereotype.Component
import java.io.File
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.locks.ReentrantLock

@Component
class RestTransportClient(
        vkProperties: VkProperties
) : TransportClient {

    private val client = HttpTransportClient.getInstance()
    private val apiCallDelay = vkProperties.apiCallDelay

    private val lastTimeApiUsed = AtomicLong(0)
    private val lock = ReentrantLock(true)

    @Throws(ApiException::class, ClientException::class)
    private fun execute(request: () -> ClientResponse): ClientResponse {
        lock.lock()
        val period = System.currentTimeMillis() - lastTimeApiUsed.get()
        if (period < apiCallDelay) Thread.sleep(apiCallDelay - period)

        try {
            return request()
        } finally {
            lastTimeApiUsed.set(System.currentTimeMillis())
            lock.unlock()
        }
    }

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