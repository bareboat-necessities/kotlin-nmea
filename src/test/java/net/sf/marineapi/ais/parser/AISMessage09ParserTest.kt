package net.sf.marineapi.ais.parser

import net.sf.marineapi.ais.message.AISMessage09
import net.sf.marineapi.ais.util.Sixbit
import org.junit.Assert
import org.junit.Assert.assertEquals
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

    @Test
    @Throws(Exception::class)
    fun getAltitude() {
        assertEquals(16, msg.altitude.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getSpeedOverGround() {
        assertEquals(100.0, msg.speedOverGround.toDouble(), 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun getPositionAccuracy() {
        assertEquals(true, msg.isAccurate)
    }

    @Test
    @Throws(Exception::class)
    fun getLongitudeInDegrees() {
        assertEquals(-82.91646, msg.longitudeInDegrees, 0.00001)
    }

    @Test
    @Throws(Exception::class)
    fun getLatitudeInDegrees() {
        assertEquals(29.20575, msg.latitudeInDegrees, 0.00001)
    }

    @Test
    @Throws(Exception::class)
    fun getCourseOverGround() {
        assertEquals(30.0, msg.courseOverGround, 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun getTimeStamp() {
        assertEquals(11, msg.timeStamp.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getDTEFlag() {
        // 1 == false, "not available" (default)
        assertEquals(false, msg.dTEFlag)
    }

    @Test
    @Throws(Exception::class)
    fun getAssignedModeFlag() {
        // 0 == Autonomous and continuous mode (default)
        assertEquals(false, msg.assignedModeFlag)
    }

    @Test
    @Throws(Exception::class)
    fun getRAIMFlag() {
        // 0 = RAIM not in use (default)
        assertEquals(false, msg.rAIMFlag)
    }

    @Test
    @Throws(Exception::class)
    fun getRadioStatus() {
        assertEquals(0, msg.radioStatus.toLong())
    }

    @Test
    fun hasLatitude() {
        assertEquals(true, msg.hasLatitude())
    }

    @Test
    fun hasLongitude() {
        assertEquals(true, msg.hasLongitude())
    }
}