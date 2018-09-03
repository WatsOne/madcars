package ru.alexkulikov.madcars

import org.physics.jipmunk.Body
import org.physics.jipmunk.PolyShape
import org.physics.jipmunk.Shape
import org.physics.jipmunk.constraits.DampedSpring
import org.physics.jipmunk.constraits.PinJoint
import org.physics.jipmunk.constraits.SimpleMotor

data class WheelData(val wheelShape: Shape,
                     val wheelBody: Body,
                     val wheelJoint: PinJoint,
                     val wheelDamp: DampedSpring,
                     val wheelStop: PolyShape,
                     val wheelMotor: SimpleMotor?)