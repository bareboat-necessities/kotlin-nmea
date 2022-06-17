package net.sf.marineapi.nmea.parser

import org.junit.Before

/**
 * MMBTest
 *
 * @author Kimmo Tuukkanen
 */
class MMBTest {
    var mmb: MMBSentence? = null
    var empty: MMBSentence? = null
    @Before
    @Throws(Exception::class)
    fun setUp() {
        mmb = MMBParser(EXAMPLE)
        empty = MMBParser(TalkerId.WI)
    }

    @Test
    fun testConstructors() {
        assertEquals(4, mmb.fieldCount)
        assertEquals(4, empty.fieldCount)
        assertEquals(TalkerId.II, mmb.talkerId)
        assertEquals(TalkerId.WI, empty.talkerId)
        assertEquals(SentenceId.MMB.name, empty.sentenceId)
    }

    @Test
    @Throws(Exception::class)
    fun testGetInchesOfMercury() {
        assertEquals(29.9870, mmb.inchesOfMercury, 0.0001)
    }

    @Test
    @Throws(Exception::class)
    fun testGetBars() {
        assertEquals(1.0154, mmb.bars, 0.0001)
    }

    @Test
    @Throws(Exception::class)
    fun testSetInchesOfMercury() {
        mmb.inchesOfMercury = 29.9999
        assertEquals(29.9999, mmb.inchesOfMercury, 0.0001)
    }

    @Test
    @Throws(Exception::class)
    fun testSetBars() {
        mmb.bars = 1.1234
        assertEquals(1.1234, mmb.bars, 0.0001)
    }

    companion object {
        const val EXAMPLE = "\$IIMMB,29.9870,I,1.0154,B*75"
    }
}