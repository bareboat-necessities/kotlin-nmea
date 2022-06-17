package net.sf.marineapi.nmea.parser

import org.junit.Assert.assertEquals

/**
 * @author Gunnar Hillert
 */
class UBXTest {
    val message00 =
        "\$PUBX,00,202920.00,1932.33821,N,15555.72641,W,451.876,G3,3.3,4.0,0.177,0.00,-0.035,,1.11,1.39,1.15,17,0,0*62"
    private var ubxSentence: UBXSentence? = null

    /**
     * @throws java.lang.Exception
     */
    @Before
    @Throws(Exception::class)
    fun setUp() {
        ubxSentence = UBXParser(message00)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.VDMParser.VDMParser]
     * .
     */
    @Test
    fun testUBXParserTalkerId() {
        val empty: UBXSentence = UBXParser(TalkerId.P)
        assertEquals(TalkerId.P, empty.talkerId)
        assertEquals("UBX", empty.sentenceId)
        assertEquals(6, empty.fieldCount)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.UBXParser.getMessageId].
     */
    @Test
    fun testGetMessageId() {
        assertEquals(Integer.valueOf(0), ubxSentence.getMessageId())
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.UBXParser.getUBXFieldCount].
     */
    @Test
    fun testGetUBXFieldCount() {
        assertEquals(20, ubxSentence.getUBXFieldCount())
    }
}