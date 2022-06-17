package net.sf.marineapi.nmea.parser

import org.junit.Assert.assertEquals

class DPTTest {
    var empty: DPTSentence? = null
    var dpt: DPTSentence? = null
    @Before
    @Throws(Exception::class)
    fun setUp() {
        empty = DPTParser(TalkerId.II)
        dpt = DPTParser(EXAMPLE)
    }

    @Test
    fun testDPTParser() {
        assertEquals(TalkerId.II, empty.talkerId)
        assertEquals("DPT", empty.sentenceId)
        assertEquals(3, empty.fieldCount)
    }

    @Test
    fun testDPTParserString() {
        assertEquals(TalkerId.II, dpt.talkerId)
        assertEquals("DPT", dpt.sentenceId)
        assertEquals(3, dpt.fieldCount)
    }

    @Test
    fun testGetDepth() {
        assertEquals(12.6, dpt.depth, 0.01)
    }

    @Test
    fun testGetOffset() {
        assertEquals(-1.0, dpt.offset, 0.01)
    }

    @Test
    fun testSetDepth() {
        val depth = 1.2333333
        empty.depth = depth
        assertEquals(depth, empty.depth, 0.1)
    }

    @Test
    fun testSetOffset() {
        val offset = 1.555555
        empty.offset = offset
        assertEquals(offset, empty.offset, 0.1)
    }

    @Test
    fun testGetMaximum() {
        assertEquals(100, dpt.maximum, 1)
    }

    @Test
    fun testSetMaximum() {
        val max = 123
        dpt.maximum = max
        assertEquals(max, dpt.maximum, 1)
    }

    companion object {
        const val EXAMPLE = "\$IIDPT,012.6,-1.0,100"
    }
}