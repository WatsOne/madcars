package ru.alexkulikov.madcars

import org.physics.jipmunk.Group

class CarGroup(val id: Int): Group {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CarGroup

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}