package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.sentence.VLWSentence
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class VLWTest {
    var vlw: VLWSentence? = null
    var empty: VLWSentence? = null
    @Before
    @Throws(Exception::class)
    fun setUp() {
        vlw = VLWParser(EXAMPLE)
        empty = VLWParser(TalkerId.VD)
    }

    @Test
    fun testVLWParserString() {
        Assert.assertEquals(TalkerId.VW, vlw!!.getTalkerId())
        Assert.assertEquals("VLW", vlw!!.getSentenceId())
        Assert.assertEquals(4, vlw!!.getFieldCount().toLong())
    }

    @Test
    fun testVLWParserTalkerId() {
        Assert.assertEquals(TalkerId.VD, empty!!.getTalkerId())
        Assert.assertEquals("VLW", empty!!.getSentenceId())
        Assert.assertEquals(4, empty!!.getFieldCount().toLong())
    }

    @Test
    fun testGetTotal() {
        Assert.assertEquals(2.8, vlw!!.getTotal(), 0.1)
    }

    @Test
    fun testGetTotalUnits() {
        Assert.assertEquals('N'.code.toLong(), vlw!!.getTotalUnits().code.toLong())
    }

    @Test
    fun testGetTrip() {
        Assert.assertEquals(0.8, vlw!!.getTrip(), 0.1)
    }

    @Test
    fun testGetTripUnits() {
        Assert.assertEquals('N'.code.toLong(), vlw!!.getTripUnits().code.toLong())
    }

    @Test
    fun testSetTotal() {
        empty!!.setTotal(3.14)
        Assert.assertEquals(3.1, empty!!.getTotal(), 0.1)
    }

    @Test
    fun testSetTotalUnits() {
        empty!!.setTotalUnits(VLWSentence.KM)
        Assert.assertEquals(VLWSentence.KM.code.toLong(), empty!!.getTotalUnits().code.toLong())
    }

    @Test
    fun testSetTrip() {
        empty!!.setTrip(0.0)
        Assert.assertEquals(0.0, empty!!.getTrip(), 0.1)
    }

    @Test
    fun testSetTripUnits() {
        empty!!.setTripUnits(VLWSentence.NM)
        Assert.assertEquals(VLWSentence.NM.code.toLong(), empty!!.getTripUnits().code.toLong())
    }

    companion object {
        const val EXAMPLE = "\$VWVLW,2.8,N,0.8,N"
    }
}