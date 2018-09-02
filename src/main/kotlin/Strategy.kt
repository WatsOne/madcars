import mu.KLogging
import org.json.JSONObject

class Strategy {
    companion object: KLogging()

    fun go() {
        logger.trace { readLine() }

        while (true) {
            logger.trace { readLine() }
            println(JSONObject(mapOf("command" to "right")))
        }
    }
}