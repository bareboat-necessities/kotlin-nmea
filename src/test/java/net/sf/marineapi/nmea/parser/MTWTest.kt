package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.parser.MTWParser
import net.sf.marineapi.nmea.sentence.MTWSentence
import net.sf.marineapi.nmea.sentence.TalkerId
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * MTW parser tests.
 *
 * @author Kimmo Tuukkanen
 */
class MTWTest {
    private var mtw: MTWSentence? = null

    /**
     * @throws Exception
     */
    @Before
    @Throws(Exception::class)
    fun setUp() {
        mtw = MTWParser(EXAMPLE)
    }

    /**
     * Test method for
     * [MTWParser.MTWParser]
     * .
     */
    @Test
    fun testMTWParserString() {
        Assert.assertEquals("MTW", mtw!!.getSentenceId())
        Assert.assertEquals(TalkerId.II, mtw!!.getTalkerId())
    }

    /**
     * Test method for
     * [MTWParser.MTWParser]
     * .
     */
    @Test
    fun testMTWParserTalkerId() {
        val empty = MTWParser(TalkerId.II)
        Assert.assertEquals("MTW", empty.getSentenceId())
        Assert.assertEquals(TalkerId.II, empty.getTalkerId())
        Assert.assertEquals(2, empty.getFieldCount().toLong())
        Assert.assertEquals('C'.code.toLong(), empty.getCharValue(1).code.toLong())
    }

    /**
     * Test method for
     * [MTWParser.getTemperature].
     */
    @Test
    fun testGetTemperature() {
        Assert.assertEquals(17.75, mtw!!.getTemperature(), 0.01)
    }

    /**
     * Test method for
     * [MTWParser.setTemperature].
     */
    @Test
    fun testSetTemperature() {
        mtw!!.setTemperature(12.345)
        Assert.assertEquals(12.345, mtw!!.getTemperature(), 0.01)
    }

    companion object {
        const val EXAMPLE = "\$IIMTW,17.75,C"
    }
}