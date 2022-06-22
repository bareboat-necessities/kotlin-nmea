package net.sf.marineapi.ais.parser

import net.sf.marineapi.ais.message.AISMessage21
import net.sf.marineapi.ais.util.Sixbit
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test

/**
 * AIS message 21 tests (Aids To Navigation Report).
 *
 * Expected values based on http://www.maritec.co.za/tools/aisvdmvdodecoding/
 */
class AISMessage21ParserTest {
    // !AIVDO,2,1,5,B,E1c2;q@b44ah4ah0h:2ab@70VRpU<Bgpm4:gP50HH`Th`QF5,0*7B
    // !AIVDO,2,2,5,B,1CQ1A83PCAH0,0*60
    private val payload = "E1c2;q@b44ah4ah0h:2ab@70VRpU<Bgpm4:gP50HH`Th`QF51CQ1A83PCAH0"
    private val sixbit = Sixbit(payload, 0)
    private val msg: AISMessage21 = AISMessage21Parser(sixbit)

    @Test
    @Throws(Exception::class)
    fun getAidType() {
        // Nav Type?
        assertEquals(1, msg.aidType.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getName() {
        assertEquals("THIS IS A TEST NAME1", msg.name)
    }

    @Test
    @Throws(Exception::class)
    fun getPositionAccuracy() {
        assertFalse(msg.isAccurate)
    }

    @Test
    @Throws(Exception::class)
    fun getLongitudeInDegrees() {
        assertEquals(145.181, msg.longitudeInDegrees, 0.001)
    }

    @Test
    @Throws(Exception::class)
    fun getLatitudeInDegrees() {
        assertEquals(-38.220167, msg.latitudeInDegrees, 0.000001)
    }

    @Test
    @Throws(Exception::class)
    fun getBow() {
        assertEquals(5, msg.bow.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getStern() {
        assertEquals(3, msg.stern.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getPort() {
        assertEquals(3, msg.port.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getStarboard() {
        assertEquals(5, msg.starboard.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getTypeOfEPFD() {
        assertEquals(1, msg.typeOfEPFD.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getUtcSecond() {
        // UTC time stamp?
        assertEquals(9, msg.utcSecond.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getOffPositionIndicator() {
        assertEquals(true, msg.offPositionIndicator)
    }

    @Test
    @Throws(Exception::class)
    fun getRegional() {
        // "00001010" ?
        assertEquals(10, msg.regional.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getRAIMFlag() {
        assertFalse(msg.rAIMFlag)
    }

    @Test
    @Throws(Exception::class)
    fun getVirtualAidFlag() {
        assertFalse(msg.virtualAidFlag)
    }

    @Test
    @Throws(Exception::class)
    fun getAssignedModeFlag() {
        assertTrue(msg.assignedModeFlag)
    }

    @Test
    @Throws(Exception::class)
    fun getNameExtension() {
        assertEquals("EXTENDED NAME", msg.nameExtension)
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