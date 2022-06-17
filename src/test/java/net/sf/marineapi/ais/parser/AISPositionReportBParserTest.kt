package net.sf.marineapi.ais.parser

import org.junit.Test

/**
 * AIS position report "type B" tests, covering parsers 18 and 19.
 *
 * TODO: missing getters for "Class B" flags 13 - 20.
 *
 * Expected values based on http://www.maritec.co.za/tools/aisvdmvdodecoding/
 */
class AISPositionReportBParserTest {
    // !AIVDM,1,1,,A,B6CdCm0t3`tba35f@V9faHi7kP06,0*58
    private val payload = "B6CdCm0t3`tba35f@V9faHi7kP06"
    private val sixbit: Sixbit = Sixbit(payload, 0)
    private val msg: AISPositionReportB = AISPositionReportBParser(sixbit)

    @get:Throws(Exception::class)
    @get:Test
    val speedOverGround: Unit
        get() {
            assertEquals(1.4, msg.speedOverGround, 0.1)
        }

    @get:Throws(Exception::class)
    @get:Test
    val positionAccuracy: Unit
        get() {
            assertEquals(false, msg.isAccurate)
        }

    @get:Throws(Exception::class)
    @get:Test
    val longitudeInDegrees: Unit
        get() {
            assertEquals(53.010996667, msg.longitudeInDegrees, 0.000000001)
        }

    @get:Throws(Exception::class)
    @get:Test
    val latitudeInDegrees: Unit
        get() {
            assertEquals(40.005283333, msg.latitudeInDegrees, 0.000000001)
        }

    @get:Throws(Exception::class)
    @get:Test
    val courseOverGround: Unit
        get() {
            assertEquals(177.0, msg.courseOverGround, 0.1)
        }

    @get:Throws(Exception::class)
    @get:Test
    val trueHeading: Unit
        get() {
            assertEquals(177.0, msg.trueHeading, 0.1)
        }

    @get:Throws(Exception::class)
    @get:Test
    val timeStamp: Unit
        get() {
            assertEquals(34, msg.timeStamp)
        }

    @Test
    fun hasLatitude() {
        assertEquals(true, msg.hasLatitude())
    }

    @Test
    fun hasLongitude() {
        assertEquals(true, msg.hasLongitude())
    }

    @Test
    fun hasCourseOverGround() {
        assertEquals(true, msg.hasCourseOverGround())
    }

    @Test
    fun hasSpeedOverGround() {
        assertEquals(true, msg.hasSpeedOverGround())
    }

    @Test
    fun hasTimeStamp() {
        assertEquals(true, msg.hasTimeStamp())
    }
}