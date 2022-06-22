package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.sentence.XTESentence
import net.sf.marineapi.nmea.util.DataStatus
import net.sf.marineapi.nmea.util.Direction
import net.sf.marineapi.nmea.util.FaaMode

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class XTETest {
    private var empty: XTESentence? = null
    private var instance: XTESentence? = null
    @Before
    @Throws(Exception::class)
    fun setUp() {
        instance = XTEParser(EXAMPLE)
        empty = XTEParser(TalkerId.GP)
    }

    @Test
    fun testXTEParserString() {
        assertEquals(TalkerId.II, instance!!.getTalkerId())
        assertEquals(SentenceId.XTE.name, instance!!.getSentenceId())
        assertEquals(6, instance!!.getFieldCount().toLong())
        assertTrue(instance!!.isValid())
    }

    @Test
    fun testXTEParserTalkerId() {
        assertEquals(TalkerId.GP, empty!!.getTalkerId())
        assertEquals(SentenceId.XTE.name, empty!!.getSentenceId())
        assertEquals(6, empty!!.getFieldCount().toLong())
        assertTrue(empty!!.isValid())
    }

    @Test
    fun testGetCycleLockStatus() {
        assertEquals(DataStatus.ACTIVE, instance!!.getCycleLockStatus())
    }

    @Test
    fun testGetMagnitude() {
        assertEquals(5.36, instance!!.getMagnitude(), 0.001)
    }

    @Test
    fun testGetMode() {
        assertEquals(FaaMode.NONE, empty!!.getMode())
    }

    @Test
    fun testGetStatus() {
        assertEquals(DataStatus.VOID, empty!!.getStatus())
    }

    @Test
    fun testGetSteerTo() {
        assertEquals(Direction.RIGHT, instance!!.getSteerTo())
    }

    @Test
    fun testSetCycleLockStatus() {
        instance!!.setCycleLockStatus(DataStatus.VOID)
        assertEquals(DataStatus.VOID, instance!!.getCycleLockStatus())
    }

    @Test
    fun testSetMagnitude() {
        val distance = 1.234
        empty!!.setMagnitude(1.234)
        assertEquals(distance, empty!!.getMagnitude(), 0.01)
    }

    @Test
    fun testSetMode() {
        instance!!.setMode(FaaMode.DGPS)
        assertEquals(FaaMode.DGPS, instance!!.getMode())
    }

    @Test
    fun testSetStatus() {
        instance!!.setStatus(DataStatus.VOID)
        assertEquals(DataStatus.VOID, instance!!.getStatus())
    }

    @Test
    fun testSetSteerTo() {
        instance!!.setSteerTo(Direction.LEFT)
        assertEquals(Direction.LEFT, instance!!.getSteerTo())
    }

    companion object {
        const val EXAMPLE = "\$IIXTE,A,A,5.36,R,N*67"
    }
}