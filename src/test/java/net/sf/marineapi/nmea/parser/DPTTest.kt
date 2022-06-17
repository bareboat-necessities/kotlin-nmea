package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.DPTSentence
import net.sf.marineapi.nmea.sentence.TalkerId
import org.junit.Assert
import org.junit.Before
import org.junit.Test

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
        Assert.assertEquals(TalkerId.II, empty!!.getTalkerId())
        Assert.assertEquals("DPT", empty!!.getSentenceId())
        Assert.assertEquals(3, empty!!.getFieldCount().toLong())
    }

    @Test
    fun testDPTParserString() {
        Assert.assertEquals(TalkerId.II, dpt!!.getTalkerId())
        Assert.assertEquals("DPT", dpt!!.getSentenceId())
        Assert.assertEquals(3, dpt!!.getFieldCount().toLong())
    }

    @Test
    fun testGetDepth() {
        Assert.assertEquals(12.6, dpt!!.getDepth(), 0.01)
    }

    @Test
    fun testGetOffset() {
        Assert.assertEquals(-1.0, dpt!!.getOffset(), 0.01)
    }

    @Test
    fun testSetDepth() {
        val depth = 1.2333333
        empty!!.setDepth(depth)
        Assert.assertEquals(depth, empty!!.getDepth(), 0.1)
    }

    @Test
    fun testSetOffset() {
        val offset = 1.555555
        empty!!.setOffset(offset)
        Assert.assertEquals(offset, empty!!.getOffset(), 0.1)
    }

    @Test
    fun testGetMaximum() {
        Assert.assertEquals(100.0, dpt!!.getMaximum(), 1.0)
    }

    @Test
    fun testSetMaximum() {
        val max = 123
        dpt!!.setMaximum(max.toDouble())
        Assert.assertEquals(max.toDouble(), dpt!!.getMaximum(), 1.0)
    }

    companion object {
        const val EXAMPLE = "\$IIDPT,012.6,-1.0,100"
    }
}