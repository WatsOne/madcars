package org.physics.jipmunk

import org.physics.jipmunk.Util.cpv
import org.physics.jipmunk.Util.cpvadd
import kotlin.Array
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class MadcarsMap : ExampleBase() {
    lateinit var space: Space

    override fun init(): Space {
        space = create()
        return space
    }

    override fun update(delta: Long) {
        val steps = 2
        val dt = 1.0f / 60.0f / steps.toFloat()

        for (j in 0 until steps) {
            space.step(dt)
        }
    }

    fun create(): Space {
        val space = Space()
        space.gravity = cpv(0f, -700f)
        space.damping = 0.85f

        val left = SegmentShape(Body.createStatic(), cpv(-600f, -400f), cpv(-600f, 400f), 1f)
        left.sensor = true

        val top = SegmentShape(Body.createStatic(), cpv(-600f, 400f), cpv(600f, 400f), 1f)
        top.sensor = true

        val right = SegmentShape(Body.createStatic(), cpv(600f, 400f), cpv(600f, -400f), 1f)
        right.sensor = true

        val bottom = SegmentShape(Body.createStatic(), cpv(600f, -400f), cpv(-600f, -400f), 1f)
        bottom.sensor = true

        space.addShape(left)
        space.addShape(top)
        space.addShape(right)
        space.addShape(bottom)

        PillMap.getObjects().forEach {
            val segment = SegmentShape(Body.createStatic(), it.first, it.second, 10f)
            segment.friction = 1f
            segment.elasticity = 0f
            space.addShape(segment)
        }

        return space
    }
}

fun getArcs(arcs: List<Arc>): List<Pair<Vector2f, Vector2f>> {
    val pointList = mutableListOf<Pair<Vector2f, Vector2f>>()
    arcs.forEach { arc ->
        val radPreSeg = (arc.b - arc.a) / arc.sc
        (0 until arc.sc).forEach {
            val fPointRad = arc.a + radPreSeg * it
            val sPointRad = arc.a + radPreSeg * (it + 1)
            val fPoint = cpvadd(arc.c, cpv(arc.r * cos(fPointRad), arc.r * sin(fPointRad)))
            val sPoint = cpvadd(arc.c, cpv(arc.r * cos(sPointRad), arc.r * sin(sPointRad)))
            pointList.add(Pair(fPoint, sPoint))
        }
    }

    return pointList
}

data class Arc(val c: Vector2f, val r: Float, val a: Float, val b: Float, val sc: Int)

object PillMap {
    private val segments =  listOf(Pair(cpv(-300f, -300f), cpv(300f, -300f)),
                Pair(cpv(-300f, 300f), cpv(300f, 300f)))

    private val arcs = listOf(Arc(cpv(-300f, 0f), 300f, PI.toFloat()/2, PI.toFloat() * 3f/2, 30),
                Arc(cpv(300f, 0f), 300f, PI.toFloat()/2, -PI.toFloat()/2, 30),
                Arc(cpv(0f, -550f), 300f, PI.toFloat()/3.2f, PI.toFloat()/1.45f, 30))

    fun getObjects(): List<Pair<Vector2f, Vector2f>> {
        return getArcs(arcs).plus(segments)
    }
}

fun main(args: Array<String>) {
    MadcarsMap().start(1200, 800)
}