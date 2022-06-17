package net.sf.marineapi.nmea.parser

import org.junit.Assert.assertEquals

class MTATest {
    private var mta: MTASentence? = null
    @Before
    @Throws(Exception::class)
    fun setUp() {
        mta = MTAParser(EXAMPLE)
    }

    @Test
    fun testMTAParserString() {
        assertEquals(TalkerId.II, mta.talkerId)
        assertEquals(SentenceId.MTA.name, mta.sentenceId)
    }

    @Test
    fun testMTAParserTalkerId() {
        val empty = MTAParser(TalkerId.WI)
        assertEquals(TalkerId.WI, empty.talkerId)
        assertEquals(SentenceId.MTA.name, empty.sentenceId)
        assertTrue(empty.getCharValue(1) == 'C')
    }

    @Test
    fun testGetTemperature() {
        assertEquals(21.5, mta.temperature, 0.01)
    }

    @Test
    fun testSetTemperature() {
        mta.temperature = 15.3335
        assertEquals(15.33, mta.temperature, 0.01)
    }

    companion object {
        const val EXAMPLE = "\$IIMTA,21.5,C"
    }
}