package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.RPMSentence
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.DataStatus
import org.junit.Assert
import org.junit.Assert.assertEquals
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
        assertEquals(TalkerId.II, rpm!!.getTalkerId())
        assertEquals("RPM", rpm!!.getSentenceId())
        assertEquals(5, rpm!!.getFieldCount().toLong())
    }

    @Test
    fun testRPMParserTalkerId() {
        assertEquals(TalkerId.II, empty!!.getTalkerId())
        assertEquals("RPM", empty!!.getSentenceId())
        assertEquals(5, empty!!.getFieldCount().toLong())
    }

    @Test
    fun testGetId() {
        assertEquals(1, rpm!!.getId().toLong())
    }

    @Test
    fun testGetPitch() {
        assertEquals(10.5, rpm!!.getPitch(), 0.1)
    }

    @Test
    fun testGetRPM() {
        assertEquals(2418.2, rpm!!.getRPM(), 0.1)
    }

    @Test
    fun testGetSource() {
        assertEquals('E'.code.toLong(), rpm!!.getSource().code.toLong())
    }

    @Test
    fun testGetStatus() {
        assertEquals(DataStatus.ACTIVE, rpm!!.getStatus())
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
        assertEquals(2, empty!!.getId().toLong())
    }

    @Test
    fun testSetPitch() {
        empty!!.setPitch(3.14)
        assertEquals(3.1, empty!!.getPitch(), 0.1)
    }

    @Test
    fun testSetRPM() {
        empty!!.setRPM(1234.56)
        assertEquals(1234.56, empty!!.getRPM(), 0.01)
    }

    @Test
    fun testSetSource() {
        empty!!.setSource(RPMSentence.SHAFT)
        Assert.assertTrue(empty!!.isShaft())
        assertEquals(RPMSentence.SHAFT.code.toLong(), empty!!.getSource().code.toLong())
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
        assertEquals(DataStatus.VOID, empty!!.getStatus())
    }

    companion object {
        const val EXAMPLE = "\$IIRPM,E,1,2418.2,10.5,A"
    }
}