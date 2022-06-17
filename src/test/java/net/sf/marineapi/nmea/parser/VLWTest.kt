package net.sf.marineapi.nmea.parser

import org.junit.Assert.assertEquals

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
        assertEquals(TalkerId.VW, vlw.talkerId)
        assertEquals("VLW", vlw.sentenceId)
        assertEquals(4, vlw.fieldCount)
    }

    @Test
    fun testVLWParserTalkerId() {
        assertEquals(TalkerId.VD, empty.talkerId)
        assertEquals("VLW", empty.sentenceId)
        assertEquals(4, empty.fieldCount)
    }

    @Test
    fun testGetTotal() {
        assertEquals(2.8, vlw.getTotal(), 0.1)
    }

    @Test
    fun testGetTotalUnits() {
        assertEquals('N', vlw.getTotalUnits())
    }

    @Test
    fun testGetTrip() {
        assertEquals(0.8, vlw.getTrip(), 0.1)
    }

    @Test
    fun testGetTripUnits() {
        assertEquals('N', vlw.getTripUnits())
    }

    @Test
    fun testSetTotal() {
        empty.setTotal(3.14)
        assertEquals(3.1, empty.getTotal(), 0.1)
    }

    @Test
    fun testSetTotalUnits() {
        empty.setTotalUnits(VLWSentence.KM)
        assertEquals(VLWSentence.KM, empty.getTotalUnits())
    }

    @Test
    fun testSetTrip() {
        empty.setTrip(0.0)
        assertEquals(0.0, empty.getTrip(), 0.1)
    }

    @Test
    fun testSetTripUnits() {
        empty.setTripUnits(VLWSentence.NM)
        assertEquals(VLWSentence.NM, empty.getTripUnits())
    }

    companion object {
        const val EXAMPLE = "\$VWVLW,2.8,N,0.8,N"
    }
}