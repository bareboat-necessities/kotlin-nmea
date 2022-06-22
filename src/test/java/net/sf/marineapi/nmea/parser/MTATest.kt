package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.MTASentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MTATest {
    private var mta: MTASentence? = null
    @Before
    @Throws(Exception::class)
    fun setUp() {
        mta = MTAParser(EXAMPLE)
    }

    @Test
    fun testMTAParserString() {
        assertEquals(TalkerId.II, mta!!.getTalkerId())
        assertEquals(SentenceId.MTA.name, mta!!.getSentenceId())
    }

    @Test
    fun testMTAParserTalkerId() {
        val empty = MTAParser(TalkerId.WI)
        assertEquals(TalkerId.WI, empty.getTalkerId())
        assertEquals(SentenceId.MTA.name, empty.getSentenceId())
        assertTrue(empty.getCharValue(1) == 'C')
    }

    @Test
    fun testGetTemperature() {
        assertEquals(21.5, mta!!.getTemperature(), 0.01)
    }

    @Test
    fun testSetTemperature() {
        mta!!.setTemperature(15.3335)
        assertEquals(15.33, mta!!.getTemperature(), 0.01)
    }

    companion object {
        const val EXAMPLE = "\$IIMTA,21.5,C"
    }
}