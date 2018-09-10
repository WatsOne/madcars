import mu.KLogging
import org.json.JSONObject

class Strategy {
    companion object : KLogging()

    fun go() {
        var tick = 1

        var mapId = 0
        var carId = 0
        var squaredWheels = false
        val myCar = Car(false)

        while (true) {

            val input = JSONObject(readLine())
            if (input.getString("type") == "new_match") {
                logger.trace { input }

                val params = input.getJSONObject("params")
                mapId = params.getJSONObject("proto_map").getInt("external_id")

                val protoCar = params.getJSONObject("proto_car")
                carId = protoCar.getInt("external_id")
                squaredWheels = protoCar.has("squared_wheels")

                myCar.squaredWheels = squaredWheels
                continue
            }

            val params = input.getJSONObject("params")
            val deadLinePos = params.getInt("deadline_position")

            val command = when (mapId) {
                1 -> PillMap.go(myCar, deadLinePos, tick)
                else -> "stop"
            }

            println(JSONObject(mapOf("command" to command)))

            tick++
        }
    }
}