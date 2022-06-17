package net.sf.marineapi.nmea.parser

import org.junit.Assert.assertEquals

/**
 * MTW parser tests.
 *
 * @author Kimmo Tuukkanen
 */
class MTWTest {
    private var mtw: MTWSentence? = null

    /**
     * @throws java.lang.Exception
     */
    @Before
    @Throws(Exception::class)
    fun setUp() {
        mtw = MTWParser(EXAMPLE)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.MTWParser.MTWParser]
     * .
     */
    @Test
    fun testMTWParserString() {
        assertEquals("MTW", mtw.sentenceId)
        assertEquals(TalkerId.II, mtw.talkerId)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.MTWParser.MTWParser]
     * .
     */
    @Test
    fun testMTWParserTalkerId() {
        val empty = MTWParser(TalkerId.II)
        assertEquals("MTW", empty.sentenceId)
        assertEquals(TalkerId.II, empty.talkerId)
        assertEquals(2, empty.fieldCount)
        assertEquals('C', empty.getCharValue(1))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.MTWParser.getTemperature].
     */
    @Test
    fun testGetTemperature() {
        assertEquals(17.75, mtw.temperature, 0.01)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.MTWParser.setTemperature].
     */
    @Test
    fun testSetTemperature() {
        mtw.temperature = 12.345
        assertEquals(12.345, mtw.temperature, 0.01)
    }

    companion object {
        const val EXAMPLE = "\$IIMTW,17.75,C"
    }
}