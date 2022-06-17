package net.sf.marineapi.ais.parser

import net.sf.marineapi.ais.message.AISPositionReport
import net.sf.marineapi.ais.util.Sixbit
import org.junit.Assert
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
    private val msg: AISPositionReport = AISPositionReportParser(sixbit)

    @get:Throws(Exception::class)
    @get:Test
    val navigationalStatus: Unit
        get() {
            Assert.assertEquals(0, msg.navigationalStatus.toLong())
        }

    @get:Throws(Exception::class)
    @get:Test
    val rateOfTurn: Unit
        get() {
            Assert.assertEquals(-2.9, msg.rateOfTurn, 0.1)
        }

    @get:Throws(Exception::class)
    @get:Test
    val speedOverGround: Unit
        get() {
            Assert.assertEquals(13.9, msg.speedOverGround, 0.1)
        }

    // 0 == low (> 10 meters)
    @get:Throws(Exception::class)
    @get:Test
    val positionAccuracy: Unit
        get() {
            // 0 == low (> 10 meters)
            Assert.assertFalse(msg.isAccurate)
        }

    @get:Throws(Exception::class)
    @get:Test
    val longitudeInDegrees: Unit
        get() {
            Assert.assertEquals(11.8329767, msg.longitudeInDegrees, 0.0000001)
        }

    @get:Throws(Exception::class)
    @get:Test
    val latitudeInDegrees: Unit
        get() {
            Assert.assertEquals(57.6603533, msg.latitudeInDegrees, 0.0000001)
        }

    @get:Throws(Exception::class)
    @get:Test
    val courseOverGround: Unit
        get() {
            Assert.assertEquals(40.4, msg.courseOverGround, 0.1)
        }

    @get:Throws(Exception::class)
    @get:Test
    val trueHeading: Unit
        get() {
            Assert.assertEquals(41, msg.trueHeading.toLong())
        }

    @get:Throws(Exception::class)
    @get:Test
    val timeStamp: Unit
        get() {
            Assert.assertEquals(53, msg.timeStamp.toLong())
        }

    @get:Throws(Exception::class)
    @get:Test
    val manouverIndicator: Unit
        get() {
            Assert.assertEquals(0, msg.manouverIndicator.toLong())
        }

    @Test
    fun hasLatitude() {
        Assert.assertEquals(true, msg.hasLatitude())
    }

    @Test
    fun hasLongitude() {
        Assert.assertEquals(true, msg.hasLongitude())
    }

    @Test
    fun hasRateOfTurn() {
        Assert.assertEquals(true, msg.hasRateOfTurn())
    }

    @Test
    fun hasCourseOverGround() {
        Assert.assertEquals(true, msg.hasCourseOverGround())
    }

    @Test
    fun hasSpeedOverGround() {
        Assert.assertEquals(true, msg.hasSpeedOverGround())
    }

    @Test
    fun hasTimeStamp() {
        Assert.assertEquals(true, msg.hasTimeStamp())
    }
}