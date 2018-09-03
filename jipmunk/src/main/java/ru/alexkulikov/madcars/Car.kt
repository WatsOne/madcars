package ru.alexkulikov.madcars

import org.physics.jipmunk.*
import org.physics.jipmunk.constraits.DampedSpring
import org.physics.jipmunk.constraits.PinJoint
import org.physics.jipmunk.constraits.SimpleMotor

open class Car(val carBodyPoly: List<Pair<Float, Float>> = listOf(),
          val carBodyMass: Float = 100f,
          val carBodyFriction: Float = 0.9f,
          val carBodyElasticity: Float = 0.5f,
          val buttonHw: Pair<Float, Float> = Pair(3f,30f),
          val buttonPosition: Pair<Float, Float> = Pair(0f, 0f),
          val buttonAngle: Float = 0f,
          val maxSpeed: Float = 300f,
          val maxAngularSpeed: Float = 2f,
          val torque: Float = 20000000f,
          val drive: Drive = Drive.FR,
          val rearWheelMass: Float = 60f,
          val rearWheelRadius: Float = 10f,
          val rearWheelPosition: Pair<Float, Float> = Pair(0f, 0f),
          val rearWheelFriction: Float = 1f,
          val rearWheelElasticity: Float = 0.8f,
          val rearWheelJoint: Pair<Float, Float> = Pair(0f, 0f),
          val rearWheelDampPosition: Pair<Float, Float> = Pair(0f, 0f),
          val rearWheelDampPositionOrig: Pair<Float, Float> = Pair(0f, 0f),
          val rearWheelDampLength: Float = 20f,
          val rearWheelDampStiffness: Float = 6e4f,
          val rearWheelDampDamping: Float = 1e3f,
          val frontWheelMass: Float = 60f,
          val frontWheelRadius: Float = 10f,
          val frontWheelPosition: Pair<Float, Float> = Pair(0f, 0f),
          val frontWheelFriction: Float = 1f,
          val frontWheelElasticity: Float = 0.8f,
          val frontWheelJoint: Pair<Float, Float> = Pair(0f, 0f),
          val frontWheelDampPosition: Pair<Float, Float> = Pair(0f, 0f),
          val frontWheelDampPositionOrig: Pair<Float, Float> = Pair(0f, 0f),
          val frontWheelDampLength: Float = 20f,
          val frontWheelDampStiffness: Float = 60000f,
          val frontWheelDampDamping: Float = 900f,

          val carGroup: Int,
          val direction: Direction,
          val space: Space) {

    val buttonCollisionType = carGroup * 10
    val xModification = if (direction == Direction.RIGHT_DIRECTION) 1 else -1
    val carBody = createCarBody()
    val carShape = createCarShape()

    var rearWheelData: WheelData
    var frontWheelData: WheelData

    init {
        carBody.centerOfGravity = Util.cpv(carShape.centerOfGravity)
        rearWheelData = createWheel(WheelSide.REAR)
        frontWheelData = createWheel(WheelSide.FRONT)
    }

    private fun processedCarBodyPoly(): Array<Vector2f> {
        return carBodyPoly.map { Util.cpv(it.first * xModification, it.second) }.toTypedArray()
    }

    private fun createCarBody(): Body {
        return Body(carBodyMass, Util.momentForPoly(carBodyMass, processedCarBodyPoly(), Util.cpvzero(), 0f))
    }

    private fun createCarShape(): PolyShape {
        val shape = PolyShape(carBody, 0f, *processedCarBodyPoly())
        shape.friction = carBodyFriction
        shape.elasticity = carBodyElasticity
        shape.filter = ShapeFilter(CarGroup(carGroup), Bitmask(1), Bitmask(1))
        return shape
    }

    private fun createWheel(wheelSide: WheelSide): WheelData {
        val wheelMass = if (wheelSide == WheelSide.REAR) rearWheelMass else frontWheelMass
        val wheelRadius = if (wheelSide == WheelSide.REAR) rearWheelRadius else frontWheelRadius
        val wheelPosition = if (wheelSide == WheelSide.REAR) rearWheelPosition else frontWheelPosition
        val wheelFriction = if (wheelSide == WheelSide.REAR) rearWheelFriction else frontWheelFriction
        val wheelElasticity = if (wheelSide == WheelSide.REAR) rearWheelElasticity else frontWheelElasticity
        val wheelJoint = if (wheelSide == WheelSide.REAR) rearWheelJoint else frontWheelJoint
        val wheelDampPosition = if (wheelSide == WheelSide.REAR) rearWheelDampPosition else frontWheelDampPosition
        val wheelDampPositionOrig = if (wheelSide == WheelSide.REAR) rearWheelDampPositionOrig else frontWheelDampPositionOrig
        val wheelDampLength = if (wheelSide == WheelSide.REAR) rearWheelDampLength else frontWheelDampLength
        val wheelDampStiffness = if (wheelSide == WheelSide.REAR) rearWheelDampStiffness else frontWheelDampStiffness
        val wheelDampDamping = if (wheelSide == WheelSide.REAR) rearWheelDampDamping else frontWheelDampDamping

        val wheelBody = Body(wheelMass, Util.momentForCircle(wheelMass, 0f, wheelRadius, Util.cpvzero()))
        wheelBody.position = Util.cpv(wheelPosition.first * xModification, wheelPosition.second)

        val wheelShape = CircleShape(wheelBody, wheelRadius, Util.cpvzero())
        wheelShape.filter = ShapeFilter(CarGroup(carGroup), Bitmask(1), Bitmask(1))
        wheelShape.friction = wheelFriction
        wheelShape.elasticity = wheelElasticity

        val wheelJointObj = PinJoint(wheelBody, carBody, Util.cpvzero(), Util.cpv(wheelJoint.first * xModification, wheelJoint.second))

//        println(wheelDampLength.toString() + " " + wheelDampStiffness + " " + wheelDampDamping)

        val wheelDamp = DampedSpring(wheelBody, carBody, Util.cpvzero(),
                Util.cpv(wheelDampPosition.first * xModification, wheelDampPosition.second),
                wheelDampLength,
                wheelDampStiffness,
                wheelDampDamping)

        val wheelStop = PolyShape(carBody, 0f,
                Transform.transpose(0f, 0f, wheelDampPositionOrig.first * xModification - wheelRadius * xModification, 0f, 0f, wheelDampPositionOrig.second),
                Util.cpvzero(), Util.cpv(0f, 1f), Util.cpv(wheelRadius * 2 * xModification, 1f), Util.cpv(wheelRadius * 2 * xModification, 0f))

        var wheelMotor: SimpleMotor? = null
        if ((wheelSide == WheelSide.REAR && (drive == Drive.AWD || drive == Drive.FR)) || (wheelSide == WheelSide.FRONT && (drive == Drive.AWD || drive == Drive.FF))) {
            wheelMotor = SimpleMotor(wheelBody, carBody, 0f)
        }

        return WheelData(wheelShape, wheelBody, wheelJointObj, wheelDamp, wheelStop, wheelMotor)
    }

    fun setPosition(position: Vector2f) {
        carBody.position = position
        frontWheelData.wheelBody.position = Util.cpvadd(position, Util.cpv(frontWheelPosition.first * xModification, frontWheelPosition.second))
        rearWheelData.wheelBody.position = Util.cpvadd(position, Util.cpv(rearWheelPosition.first * xModification, rearWheelPosition.second))
    }

    fun addToSpace() {
        space.addBody(carBody)
        space.addShape(carShape)
        space.addBody(rearWheelData.wheelBody)
        space.addBody(frontWheelData.wheelBody)
        space.addShape(rearWheelData.wheelShape)
        space.addShape(frontWheelData.wheelShape)
        space.addConstraint(rearWheelData.wheelJoint)
//        space.addConstraint(frontWheelData.wheelJoint)
        space.addConstraint(rearWheelData.wheelDamp)
        space.addConstraint(frontWheelData.wheelDamp)
//        space.addShape(rearWheelData.wheelStop)
//        space.addShape(frontWheelData.wheelStop)
//        if (rearWheelData.wheelMotor != null) space.addConstraint(rearWheelData.wheelMotor)
//        if (frontWheelData.wheelMotor != null) space.addConstraint(frontWheelData.wheelMotor)
    }
}