package net.sf.marineapi.ais.parser

import net.sf.marineapi.ais.message.AISMessage05
import net.sf.marineapi.ais.util.Sixbit
import org.junit.Assert
import org.junit.Test

/**
 * AIS Message 05 parser test.
 *
 * Expected values according to http://www.maritec.co.za/aisvdmvdodecoding1.php
 */
class AISMessage05Test {
    // !AIVDM,2,1,0,A,58wt8Ui`g??r21`7S=:22058<v05Htp000000015>8OA;0sk,0*7B
    // !AIVDM,2,2,0,A,eQ8823mDm3kP00000000000,2*5D
    private val payload = "58wt8Ui`g??r21`7S=:22058<v05Htp000000015>8OA;0skeQ8823mDm3kP00000000000"
    private val sixbit = Sixbit(payload, 2)
    private val msg: AISMessage05 = AISMessage05Parser(sixbit)

    @get:Throws(Exception::class)
    @get:Test
    val aISVersionIndicator: Unit
        get() {
            Assert.assertEquals(0, msg.aISVersionIndicator.toLong())
        }

    @get:Throws(Exception::class)
    @get:Test
    val iMONumber: Unit
        get() {
            Assert.assertEquals(439303422, msg.iMONumber.toLong())
        }

    @get:Throws(Exception::class)
    @get:Test
    val callSign: Unit
        get() {
            Assert.assertEquals("ZA83R", msg.callSign)
        }

    @get:Throws(Exception::class)
    @get:Test
    val name: Unit
        get() {
            Assert.assertEquals("ARCO AVON", msg.name)
        }

    @get:Throws(Exception::class)
    @get:Test
    val typeOfShipAndCargoType: Unit
        get() {
            Assert.assertEquals(69, msg.typeOfShipAndCargoType.toLong())
        }

    @get:Throws(Exception::class)
    @get:Test
    val bow: Unit
        get() {
            Assert.assertEquals(113, msg.bow.toLong())
        }

    @get:Throws(Exception::class)
    @get:Test
    val stern: Unit
        get() {
            Assert.assertEquals(31, msg.stern.toLong())
        }

    @get:Throws(Exception::class)
    @get:Test
    val port: Unit
        get() {
            Assert.assertEquals(17, msg.port.toLong())
        }

    @get:Throws(Exception::class)
    @get:Test
    val starboard: Unit
        get() {
            Assert.assertEquals(11, msg.starboard.toLong())
        }

    @get:Throws(Exception::class)
    @get:Test
    val typeOfEPFD: Unit
        get() {
            Assert.assertEquals(0, msg.typeOfEPFD.toLong())
        }

    @get:Throws(Exception::class)
    @get:Test
    val eTAMonth: Unit
        get() {
            Assert.assertEquals(3, msg.eTAMonth.toLong())
        }

    @get:Throws(Exception::class)
    @get:Test
    val eTADay: Unit
        get() {
            Assert.assertEquals(23, msg.eTADay.toLong())
        }

    @get:Throws(Exception::class)
    @get:Test
    val eTAHour: Unit
        get() {
            Assert.assertEquals(19, msg.eTAHour.toLong())
        }

    @get:Throws(Exception::class)
    @get:Test
    val eTAMinute: Unit
        get() {
            Assert.assertEquals(45, msg.eTAMinute.toLong())
        }

    @get:Throws(Exception::class)
    @get:Test
    val maximumDraught: Unit
        get() {
            Assert.assertEquals(13.2, msg.maximumDraught, 0.1)
        }

    @get:Throws(Exception::class)
    @get:Test
    val destination: Unit
        get() {
            Assert.assertEquals("HOUSTON", msg.destination)
        }

    @Test
    fun testIsDteReady() {
        Assert.assertFalse(msg.isDteReady)
    }
}