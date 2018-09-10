object PillMap {
    fun go(myCar: Car, deadlinePos: Int, tick: Int): String {
        if (deadlinePos > 100) {
            if (myCar.squaredWheels) {
                return if (tick % 2 == 0) "left" else "stop"
            }
            return "left"
        }

        return "stop"
    }
}