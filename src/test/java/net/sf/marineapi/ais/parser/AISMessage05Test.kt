package net.sf.marineapi.ais.parser

import net.sf.marineapi.ais.message.AISMessage05
import net.sf.marineapi.ais.util.Sixbit

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
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

    @Test
    @Throws(Exception::class)
    fun getAISVersionIndicator() {
        assertEquals(0, msg.aISVersionIndicator.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getIMONumber() {
        assertEquals(439303422, msg.iMONumber.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getCallSign() {
        assertEquals("ZA83R", msg.callSign)
    }

    @Test
    @Throws(Exception::class)
    fun getName() {
        assertEquals("ARCO AVON", msg.name)
    }

    @Test
    @Throws(Exception::class)
    fun getTypeOfShipAndCargoType() {
        assertEquals(69, msg.typeOfShipAndCargoType.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getBow() {
        assertEquals(113, msg.bow.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getStern() {
        assertEquals(31, msg.stern.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getPort() {
        assertEquals(17, msg.port.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getStarboard() {
        assertEquals(11, msg.starboard.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getTypeOfEPFD() {
        assertEquals(0, msg.typeOfEPFD.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getETAMonth() {
        assertEquals(3, msg.eTAMonth.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getETADay() {
        assertEquals(23, msg.eTADay.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getETAHour() {
        assertEquals(19, msg.eTAHour.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getETAMinute() {
        assertEquals(45, msg.eTAMinute.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getMaximumDraught() {
        assertEquals(13.2, msg.maximumDraught, 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun getDestination() {
        assertEquals("HOUSTON", msg.destination)
    }

    @Test
    fun testIsDteReady() {
        assertFalse(msg.isDteReady)
    }
}