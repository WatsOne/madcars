package ru.alexkulikov.madcars

import org.physics.jipmunk.Space

class Buggy(carGroup: Int,
            direction: Direction,
            space: Space):

        Car(carBodyPoly = listOf(Pair(0f, 6f), Pair(0f, 25f), Pair(33f, 42f), Pair(85f, 42f), Pair(150f, 20f), Pair(150f, 0f), Pair(20f, 0f)),
            carBodyMass = 200f,
            buttonPosition = Pair(40f, 42f),
            buttonHw = Pair(1f, 38f),
            maxSpeed = 70f,
            torque = 14000000f,
            drive = Drive.FR,
            rearWheelMass = 50f,
            rearWheelPosition = Pair(29f, -5f),
            rearWheelJoint = Pair(78.06632f, -18.534052f),
            rearWheelDampPosition = Pair(-42.933678f, 1.4659481f),
            rearWheelDampPositionOrig = Pair(29f, 20f),
            rearWheelDampStiffness = 5e4f,
            rearWheelDampDamping = 3e3f,
            rearWheelDampLength = 25f,
            rearWheelRadius = 12f,
            frontWheelMass = 50f,
            frontWheelPosition = Pair(122f, -5f),
            frontWheelJoint = Pair(-71.93368f, -12.534052f),
            frontWheelDampPosition = Pair(50.066322f, 1.4659481f),
            frontWheelDampPositionOrig = Pair(122f, 20f),
            frontWheelDampLength = 25f,
            frontWheelRadius = 12f,

            carGroup = carGroup,
            direction = direction,
            space = space)