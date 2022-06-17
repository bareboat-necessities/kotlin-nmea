package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.DTMSentence
import net.sf.marineapi.nmea.sentence.TalkerId
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DTMTest {
    private var dtm: DTMSentence? = null
    private var empty: DTMSentence? = null
    @Before
    @Throws(Exception::class)
    fun setUp() {
        dtm = DTMParser(EXAMPLE)
        empty = DTMParser(TalkerId.GP)
    }

    @Test
    fun testDTMParserString() {
        Assert.assertEquals("DTM", dtm!!.getSentenceId())
        Assert.assertEquals(TalkerId.GP, dtm!!.getTalkerId())
        Assert.assertEquals(8, dtm!!.getFieldCount().toLong())
    }

    @Test
    fun testDTMParserTalkerId() {
        Assert.assertEquals("DTM", empty!!.getSentenceId())
        Assert.assertEquals(TalkerId.GP, empty!!.getTalkerId())
        Assert.assertEquals(8, empty!!.getFieldCount().toLong())
    }

    @Test
    fun testGetAltitudeOffset() {
        Assert.assertEquals(0.0, dtm!!.getAltitudeOffset(), 0.1)
    }

    @Test
    fun testGetDatumCode() {
        Assert.assertEquals("W84", dtm!!.getDatumCode())
    }

    @Test
    fun testGetDatumSubCode() {
        try {
            dtm!!.getDatumSubCode()
            Assert.fail("didn't throw exception")
        } catch (e: Exception) {
            // pass
        }
    }

    @Test
    fun testGetLatitudeOffset() {
        Assert.assertEquals(0.0, dtm!!.getLatitudeOffset(), 0.1)
    }

    @Test
    fun testGetLongitudeOffset() {
        Assert.assertEquals(0.0, dtm!!.getLongitudeOffset(), 0.1)
    }

    @Test
    fun testGetName() {
        Assert.assertEquals("W84", dtm!!.getName())
    }

    @Test
    fun testSetDatumCode() {
        dtm!!.setDatumCode("W72")
        Assert.assertEquals("W72", dtm!!.getDatumCode())
    }

    @Test
    fun testSetDatumSubCode() {
        dtm!!.setDatumSubCode("123")
        Assert.assertEquals("123", dtm!!.getDatumSubCode())
    }

    @Test
    fun testSetLatitudeOffset() {
        dtm!!.setLatitudeOffset(0.12345678)
        Assert.assertEquals(0.1235, dtm!!.getLatitudeOffset(), 0.0001)
    }

    @Test
    fun testSetLongitudeOffset() {
        dtm!!.setLongitudeOffset(1.23456789)
        Assert.assertEquals(1.2346, dtm!!.getLongitudeOffset(), 0.0001)
    }

    @Test
    fun testSetName() {
        dtm!!.setName("S83")
        Assert.assertEquals("S83", dtm!!.getName())
    }

    companion object {
        const val EXAMPLE = "\$GPDTM,W84,,0.000000,N,0.000000,E,0.0,W84*6F"
    }
}