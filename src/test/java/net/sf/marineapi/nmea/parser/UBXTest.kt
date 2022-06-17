package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.sentence.UBXSentence
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * @author Gunnar Hillert
 */
class UBXTest {
    val message00 =
        "\$PUBX,00,202920.00,1932.33821,N,15555.72641,W,451.876,G3,3.3,4.0,0.177,0.00,-0.035,,1.11,1.39,1.15,17,0,0*62"
    private var ubxSentence: UBXSentence? = null

    /**
     * @throws Exception
     */
    @Before
    @Throws(Exception::class)
    fun setUp() {
        ubxSentence = UBXParser(message00)
    }

    /**
     * Test method for
     * [VDMParser.VDMParser]
     * .
     */
    @Test
    fun testUBXParserTalkerId() {
        val empty: UBXSentence = UBXParser(TalkerId.P)
        Assert.assertEquals(TalkerId.P, empty.getTalkerId())
        Assert.assertEquals("UBX", empty.getSentenceId())
        Assert.assertEquals(6, empty.getFieldCount().toLong())
    }

    /**
     * Test method for
     * [UBXParser.getMessageId].
     */
    @Test
    fun testGetMessageId() {
        assertEquals(Integer.valueOf(0), ubxSentence!!.getMessageId())
    }

    /**
     * Test method for
     * [UBXParser.getUBXFieldCount].
     */
    @Test
    fun testGetUBXFieldCount() {
        Assert.assertEquals(20, ubxSentence!!.getUBXFieldCount().toLong())
    }
}