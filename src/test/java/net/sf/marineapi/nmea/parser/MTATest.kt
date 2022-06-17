package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.MTASentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import org.junit.Assert
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
        Assert.assertEquals(TalkerId.II, mta!!.getTalkerId())
        Assert.assertEquals(SentenceId.MTA.name, mta!!.getSentenceId())
    }

    @Test
    fun testMTAParserTalkerId() {
        val empty = MTAParser(TalkerId.WI)
        Assert.assertEquals(TalkerId.WI, empty.getTalkerId())
        Assert.assertEquals(SentenceId.MTA.name, empty.getSentenceId())
        Assert.assertTrue(empty.getCharValue(1) == 'C')
    }

    @Test
    fun testGetTemperature() {
        Assert.assertEquals(21.5, mta!!.getTemperature(), 0.01)
    }

    @Test
    fun testSetTemperature() {
        mta!!.setTemperature(15.3335)
        Assert.assertEquals(15.33, mta!!.getTemperature(), 0.01)
    }

    companion object {
        const val EXAMPLE = "\$IIMTA,21.5,C"
    }
}