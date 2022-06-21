package net.sf.marineapi.ais.parser

import net.sf.marineapi.ais.message.AISMessage21
import net.sf.marineapi.ais.util.Sixbit
import org.junit.Assert
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
        Assert.assertEquals(1, msg.aidType.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getName() {
        Assert.assertEquals("THIS IS A TEST NAME1", msg.name)
    }

    @Test
    @Throws(Exception::class)
    fun getPositionAccuracy() {
        Assert.assertFalse(msg.isAccurate)
    }

    @Test
    @Throws(Exception::class)
    fun getLongitudeInDegrees() {
        Assert.assertEquals(145.181, msg.longitudeInDegrees, 0.001)
    }

    @Test
    @Throws(Exception::class)
    fun getLatitudeInDegrees() {
        Assert.assertEquals(-38.220167, msg.latitudeInDegrees, 0.000001)
    }

    @Test
    @Throws(Exception::class)
    fun getBow() {
        Assert.assertEquals(5, msg.bow.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getStern() {
        Assert.assertEquals(3, msg.stern.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getPort() {
        Assert.assertEquals(3, msg.port.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getStarboard() {
        Assert.assertEquals(5, msg.starboard.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getTypeOfEPFD() {
        Assert.assertEquals(1, msg.typeOfEPFD.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getUtcSecond() {
        // UTC time stamp?
        Assert.assertEquals(9, msg.utcSecond.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getOffPositionIndicator() {
        Assert.assertEquals(true, msg.offPositionIndicator)
    }

    @Test
    @Throws(Exception::class)
    fun getRegional() {
        // "00001010" ?
        Assert.assertEquals(10, msg.regional.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getRAIMFlag() {
        Assert.assertFalse(msg.rAIMFlag)
    }

    @Test
    @Throws(Exception::class)
    fun getVirtualAidFlag() {
        Assert.assertFalse(msg.virtualAidFlag)
    }

    @Test
    @Throws(Exception::class)
    fun getAssignedModeFlag() {
        Assert.assertTrue(msg.assignedModeFlag)
    }

    @Test
    @Throws(Exception::class)
    fun getNameExtension() {
        Assert.assertEquals("EXTENDED NAME", msg.nameExtension)
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