package ru.alexkulikov.madcars

import org.physics.jipmunk.Space

class Bus(carGroup: Int,
          direction: Direction,
          space: Space) :

        Car(carBodyPoly = listOf(Pair(0f, 6f), Pair(8f, 62f), Pair(136f, 62f), Pair(153f, 32f), Pair(153f, 5f), Pair(110f, 0f), Pair(23f, 0f)),
            carBodyMass = 700f,
            buttonPosition = Pair(137f, 59f),
            buttonHw = Pair(1f, 28f),
            maxSpeed = 45f,
            torque = 35000000f,
            drive = Drive.AWD,
            rearWheelRadius = 13f,
            rearWheelPosition = Pair(38f, -5f),
            rearWheelFriction = 0.9f,
            rearWheelJoint = Pair(76.89152f, -25.66442f),
            rearWheelDampPosition = Pair(-38.108482f, -0.6644192f),
            rearWheelDampPositionOrig = Pair(38f, 30f),
            rearWheelDampStiffness = 10e4f,
            rearWheelDampDamping = 6e3f,
            rearWheelDampLength = 35f,
            frontWheelPosition = Pair(125f, -5f),
            frontWheelJoint = Pair(-76.10848f, -24.66442f),
            frontWheelDampPosition = Pair(48.891518f, -0.6644192f),
            frontWheelDampPositionOrig = Pair(125f, 30f),
            frontWheelDampLength = 35f,
            frontWheelDampStiffness = 10e4f,
            frontWheelDampDamping = 6e3f,
            frontWheelRadius = 13f,

            carGroup = carGroup,
            direction = direction,
            space = space)