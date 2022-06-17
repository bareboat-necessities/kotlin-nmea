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

    // Nav Type?
    @get:Throws(Exception::class)
    @get:Test
    val aidType: Unit
        get() {
            // Nav Type?
            Assert.assertEquals(1, msg.aidType.toLong())
        }

    @get:Throws(Exception::class)
    @get:Test
    val name: Unit
        get() {
            Assert.assertEquals("THIS IS A TEST NAME1", msg.name)
        }

    @get:Throws(Exception::class)
    @get:Test
    val positionAccuracy: Unit
        get() {
            Assert.assertFalse(msg.isAccurate)
        }

    @get:Throws(Exception::class)
    @get:Test
    val longitudeInDegrees: Unit
        get() {
            Assert.assertEquals(145.181, msg.longitudeInDegrees, 0.001)
        }

    @get:Throws(Exception::class)
    @get:Test
    val latitudeInDegrees: Unit
        get() {
            Assert.assertEquals(-38.220167, msg.latitudeInDegrees, 0.000001)
        }

    @get:Throws(Exception::class)
    @get:Test
    val bow: Unit
        get() {
            Assert.assertEquals(5, msg.bow.toLong())
        }

    @get:Throws(Exception::class)
    @get:Test
    val stern: Unit
        get() {
            Assert.assertEquals(3, msg.stern.toLong())
        }

    @get:Throws(Exception::class)
    @get:Test
    val port: Unit
        get() {
            Assert.assertEquals(3, msg.port.toLong())
        }

    @get:Throws(Exception::class)
    @get:Test
    val starboard: Unit
        get() {
            Assert.assertEquals(5, msg.starboard.toLong())
        }

    @get:Throws(Exception::class)
    @get:Test
    val typeOfEPFD: Unit
        get() {
            Assert.assertEquals(1, msg.typeOfEPFD.toLong())
        }

    // UTC time stamp?
    @get:Throws(Exception::class)
    @get:Test
    val utcSecond: Unit
        get() {
            // UTC time stamp?
            Assert.assertEquals(9, msg.utcSecond.toLong())
        }

    @get:Throws(Exception::class)
    @get:Test
    val offPositionIndicator: Unit
        get() {
            Assert.assertEquals(true, msg.offPositionIndicator)
        }

    // "00001010" ?
    @get:Throws(Exception::class)
    @get:Test
    val regional: Unit
        get() {
            // "00001010" ?
            Assert.assertEquals(10, msg.regional.toLong())
        }

    @get:Throws(Exception::class)
    @get:Test
    val rAIMFlag: Unit
        get() {
            Assert.assertFalse(msg.rAIMFlag)
        }

    @get:Throws(Exception::class)
    @get:Test
    val virtualAidFlag: Unit
        get() {
            Assert.assertFalse(msg.virtualAidFlag)
        }

    @get:Throws(Exception::class)
    @get:Test
    val assignedModeFlag: Unit
        get() {
            Assert.assertTrue(msg.assignedModeFlag)
        }

    @get:Throws(Exception::class)
    @get:Test
    val nameExtension: Unit
        get() {
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