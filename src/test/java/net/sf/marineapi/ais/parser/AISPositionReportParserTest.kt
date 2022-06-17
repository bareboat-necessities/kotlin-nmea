package net.sf.marineapi.ais.parser

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import net.sf.marineapi.ais.util.Sixbit
import org.junit.Test

/**
 * AISPositionReportParser test, covering parsers for types 01, 02 and 03.
 *
 * Expected values based on http://www.maritec.co.za/tools/aisvdmvdodecoding/
 */
class AISPositionReportParserTest {
    // !AIVDM,1,1,,A,13u?etPv2;0n:dDPwUM1U1Cb069D,0*23
    private val payload = "13u?etPv2;0n:dDPwUM1U1Cb069D"
    private val sixbit = Sixbit(payload, 0)
    private val msg = AISPositionReportParser(sixbit)

    @get:Throws(Exception::class)
    @get:Test
    val navigationalStatus: Unit
        get() {
            assertEquals(0, msg.navigationalStatus)
        }

    @get:Throws(Exception::class)
    @get:Test
    val rateOfTurn: Unit
        get() {
            assertEquals(-2.9, msg.rateOfTurn, 0.1)
        }

    @get:Throws(Exception::class)
    @get:Test
    val speedOverGround: Unit
        get() {
            assertEquals(13.9, msg.speedOverGround, 0.1)
        }

    // 0 == low (> 10 meters)
    @get:Throws(Exception::class)
    @get:Test
    val positionAccuracy: Unit
        get() {
            // 0 == low (> 10 meters)
            assertFalse(msg.isAccurate)
        }

    @get:Throws(Exception::class)
    @get:Test
    val longitudeInDegrees: Unit
        get() {
            assertEquals(11.8329767, msg.longitudeInDegrees, 0.0000001)
        }

    @get:Throws(Exception::class)
    @get:Test
    val latitudeInDegrees: Unit
        get() {
            assertEquals(57.6603533, msg.latitudeInDegrees, 0.0000001)
        }

    @get:Throws(Exception::class)
    @get:Test
    val courseOverGround: Unit
        get() {
            assertEquals(40.4, msg.courseOverGround, 0.1)
        }

    @get:Throws(Exception::class)
    @get:Test
    val trueHeading: Unit
        get() {
            assertEquals(41, msg.trueHeading)
        }

    @get:Throws(Exception::class)
    @get:Test
    val timeStamp: Unit
        get() {
            assertEquals(53, msg.timeStamp)
        }

    @get:Throws(Exception::class)
    @get:Test
    val manouverIndicator: Unit
        get() {
            assertEquals(0, msg.manouverIndicator)
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
    fun hasRateOfTurn() {
        assertEquals(true, msg.hasRateOfTurn())
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