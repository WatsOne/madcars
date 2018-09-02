import mu.KLogging
import org.json.JSONObject

class Strategy {
    companion object: KLogging()

    fun go() {
        readLine()

        while (true) {
            readLine()
            println(JSONObject(mapOf("command" to "right")))
        }
    }
}