package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.RPMSentence
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.DataStatus
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class RPMTest {
    var rpm: RPMSentence? = null
    var empty: RPMSentence? = null
    @Before
    @Throws(Exception::class)
    fun setUp() {
        rpm = RPMParser(EXAMPLE)
        empty = RPMParser(TalkerId.II)
    }

    @Test
    fun testRPMParserString() {
        Assert.assertEquals(TalkerId.II, rpm!!.getTalkerId())
        Assert.assertEquals("RPM", rpm!!.getSentenceId())
        Assert.assertEquals(5, rpm!!.getFieldCount().toLong())
    }

    @Test
    fun testRPMParserTalkerId() {
        Assert.assertEquals(TalkerId.II, empty!!.getTalkerId())
        Assert.assertEquals("RPM", empty!!.getSentenceId())
        Assert.assertEquals(5, empty!!.getFieldCount().toLong())
    }

    @Test
    fun testGetId() {
        Assert.assertEquals(1, rpm!!.getId().toLong())
    }

    @Test
    fun testGetPitch() {
        Assert.assertEquals(10.5, rpm!!.getPitch(), 0.1)
    }

    @Test
    fun testGetRPM() {
        Assert.assertEquals(2418.2, rpm!!.getRPM(), 0.1)
    }

    @Test
    fun testGetSource() {
        Assert.assertEquals('E'.code.toLong(), rpm!!.getSource().code.toLong())
    }

    @Test
    fun testGetStatus() {
        Assert.assertEquals(DataStatus.ACTIVE, rpm!!.getStatus())
    }

    @Test
    fun testIsEngine() {
        Assert.assertTrue(rpm!!.isEngine())
    }

    @Test
    fun testIsShaft() {
        Assert.assertFalse(rpm!!.isShaft())
    }

    @Test
    fun testSetId() {
        empty!!.setId(2)
        Assert.assertEquals(2, empty!!.getId().toLong())
    }

    @Test
    fun testSetPitch() {
        empty!!.setPitch(3.14)
        Assert.assertEquals(3.1, empty!!.getPitch(), 0.1)
    }

    @Test
    fun testSetRPM() {
        empty!!.setRPM(1234.56)
        Assert.assertEquals(1234.56, empty!!.getRPM(), 0.01)
    }

    @Test
    fun testSetSource() {
        empty!!.setSource(RPMSentence.SHAFT)
        Assert.assertTrue(empty!!.isShaft())
        Assert.assertEquals(RPMSentence.SHAFT.code.toLong(), empty!!.getSource().code.toLong())
    }

    @Test
    fun testSetInvalidSource() {
        try {
            empty!!.setSource('A')
            Assert.fail("Didn't throw exception")
        } catch (e: Exception) {
            // pass
        }
    }

    @Test
    fun testSetStatus() {
        empty!!.setStatus(DataStatus.VOID)
        Assert.assertEquals(DataStatus.VOID, empty!!.getStatus())
    }

    companion object {
        const val EXAMPLE = "\$IIRPM,E,1,2418.2,10.5,A"
    }
}