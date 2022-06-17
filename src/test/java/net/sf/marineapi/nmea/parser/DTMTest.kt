package net.sf.marineapi.nmea.parser

import org.junit.Assert.assertEquals

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
        assertEquals("DTM", dtm.sentenceId)
        assertEquals(TalkerId.GP, dtm.talkerId)
        assertEquals(8, dtm.fieldCount)
    }

    @Test
    fun testDTMParserTalkerId() {
        assertEquals("DTM", empty.sentenceId)
        assertEquals(TalkerId.GP, empty.talkerId)
        assertEquals(8, empty.fieldCount)
    }

    @Test
    fun testGetAltitudeOffset() {
        assertEquals(0.0, dtm.altitudeOffset, 0.1)
    }

    @Test
    fun testGetDatumCode() {
        assertEquals("W84", dtm.datumCode)
    }

    @Test
    fun testGetDatumSubCode() {
        try {
            dtm.datumSubCode
            fail("didn't throw exception")
        } catch (e: Exception) {
            // pass
        }
    }

    @Test
    fun testGetLatitudeOffset() {
        assertEquals(0.0, dtm.latitudeOffset, 0.1)
    }

    @Test
    fun testGetLongitudeOffset() {
        assertEquals(0.0, dtm.longitudeOffset, 0.1)
    }

    @Test
    fun testGetName() {
        assertEquals("W84", dtm.name)
    }

    @Test
    fun testSetDatumCode() {
        dtm.datumCode = "W72"
        assertEquals("W72", dtm.datumCode)
    }

    @Test
    fun testSetDatumSubCode() {
        dtm.datumSubCode = "123"
        assertEquals("123", dtm.datumSubCode)
    }

    @Test
    fun testSetLatitudeOffset() {
        dtm.latitudeOffset = 0.12345678
        assertEquals(0.1235, dtm.latitudeOffset, 0.0001)
    }

    @Test
    fun testSetLongitudeOffset() {
        dtm.longitudeOffset = 1.23456789
        assertEquals(1.2346, dtm.longitudeOffset, 0.0001)
    }

    @Test
    fun testSetName() {
        dtm.name = "S83"
        assertEquals("S83", dtm.name)
    }

    companion object {
        const val EXAMPLE = "\$GPDTM,W84,,0.000000,N,0.000000,E,0.0,W84*6F"
    }
}