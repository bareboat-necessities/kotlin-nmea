package net.sf.marineapi.ais.parser

import net.sf.marineapi.ais.message.AISMessage09
import net.sf.marineapi.ais.util.Sixbit
import org.junit.Assert
import org.junit.Test

/**
 * Tests for AIS message 9 parser.
 *
 * Expected values based on http://www.maritec.co.za/tools/aisvdmvdodecoding/
 */
class AISMessage09ParserTest {
    // !AIVDO,1,1,,A,95M2oQ@41Tr4L4H@eRvQ;2h20000,0*0D
    private val payload = "95M2oQ@41Tr4L4H@eRvQ;2h20000"
    private val sixbit = Sixbit(payload, 0)
    private val msg: AISMessage09 = AISMessage09Parser(sixbit)

    @get:Throws(Exception::class)
    @get:Test
    val altitude: Unit
        get() {
            Assert.assertEquals(16, msg.altitude.toLong())
        }

    @get:Throws(Exception::class)
    @get:Test
    val speedOverGround: Unit
        get() {
            Assert.assertEquals(100.0, msg.speedOverGround.toDouble(), 0.1)
        }

    @get:Throws(Exception::class)
    @get:Test
    val positionAccuracy: Unit
        get() {
            Assert.assertEquals(true, msg.isAccurate)
        }

    @get:Throws(Exception::class)
    @get:Test
    val longitudeInDegrees: Unit
        get() {
            Assert.assertEquals(-82.91646, msg.longitudeInDegrees, 0.00001)
        }

    @get:Throws(Exception::class)
    @get:Test
    val latitudeInDegrees: Unit
        get() {
            Assert.assertEquals(29.20575, msg.latitudeInDegrees, 0.00001)
        }

    @get:Throws(Exception::class)
    @get:Test
    val courseOverGround: Unit
        get() {
            Assert.assertEquals(30.0, msg.courseOverGround, 0.1)
        }

    @get:Throws(Exception::class)
    @get:Test
    val timeStamp: Unit
        get() {
            Assert.assertEquals(11, msg.timeStamp.toLong())
        }

    // 1 == false, "not available" (default)
    @get:Throws(Exception::class)
    @get:Test
    val dTEFlag: Unit
        get() {
            // 1 == false, "not available" (default)
            Assert.assertEquals(false, msg.dTEFlag)
        }

    // 0 == Autonomous and continuous mode (default)
    @get:Throws(Exception::class)
    @get:Test
    val assignedModeFlag: Unit
        get() {
            // 0 == Autonomous and continuous mode (default)
            Assert.assertEquals(false, msg.assignedModeFlag)
        }

    // 0 = RAIM not in use (default)
    @get:Throws(Exception::class)
    @get:Test
    val rAIMFlag: Unit
        get() {
            // 0 = RAIM not in use (default)
            Assert.assertEquals(false, msg.rAIMFlag)
        }

    @get:Throws(Exception::class)
    @get:Test
    val radioStatus: Unit
        get() {
            Assert.assertEquals(0, msg.radioStatus.toLong())
        }

    @Test
    fun hasLatitude() {
        Assert.assertEquals(true, msg.hasLatitude())
    }

    @Test
    fun hasLongitude() {
        Assert.assertEquals(true, msg.hasLongitude())
    }
}