package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.sentence.VDRSentence
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class VDRTest {
    private var vdr: VDRSentence? = null
    private var empty: VDRSentence? = null
    @Before
    @Throws(Exception::class)
    fun setUp() {
        vdr = VDRParser(EXAMPLE)
        empty = VDRParser(TalkerId.IN)
    }

    @Test
    fun testVDRParserString() {
        Assert.assertEquals(TalkerId.II, vdr!!.getTalkerId())
        Assert.assertEquals(SentenceId.VDR.name, vdr!!.getSentenceId())
        Assert.assertEquals(6, vdr!!.getFieldCount().toLong())
    }

    @Test
    fun testVDRParserTalkerId() {
        Assert.assertEquals(TalkerId.IN, empty!!.getTalkerId())
        Assert.assertEquals(SentenceId.VDR.name, empty!!.getSentenceId())
        Assert.assertEquals(6, empty!!.getFieldCount().toLong())
        Assert.assertTrue(empty.toString().startsWith("\$INVDR,,T,,M,,N*"))
    }

    @Test
    fun testGetMagneticDirection() {
        Assert.assertEquals(124.5, vdr!!.getMagneticDirection(), 0.1)
    }

    @Test
    fun testGetSpeed() {
        Assert.assertEquals(1.2, vdr!!.getSpeed(), 0.1)
    }

    @Test
    fun testGetTrueDirection() {
        Assert.assertEquals(123.4, vdr!!.getTrueDirection(), 0.1)
    }

    @Test
    fun testSetMagneticDirection() {
        val dir = 45.123
        empty!!.setMagneticDirection(dir)
        Assert.assertEquals(dir, empty!!.getMagneticDirection(), 0.1)
    }

    @Test
    fun testSetSpeed() {
        val speed = 2.124
        empty!!.setSpeed(speed)
        Assert.assertEquals(speed, empty!!.getSpeed(), 0.1)
    }

    @Test
    fun testSetTrueDirection() {
        val dir = 55.234
        empty!!.setTrueDirection(dir)
        Assert.assertEquals(dir, empty!!.getTrueDirection(), 0.1)
    }

    companion object {
        const val EXAMPLE = "\$IIVDR,123.4,T,124.5,M,1.2,N"
    }
}