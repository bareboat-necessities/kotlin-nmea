package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.DBTSentence
import net.sf.marineapi.nmea.sentence.TalkerId
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DBTTest {
    private var dbt: DBTSentence? = null
    private var empty: DBTSentence? = null
    @Before
    @Throws(Exception::class)
    fun setUp() {
        empty = DBTParser(TalkerId.II)
        dbt = DBTParser(EXAMPLE)
    }

    @Test
    fun testConstructor() {
        assertEquals("DBT", empty!!.getSentenceId())
        assertEquals(TalkerId.II, empty!!.getTalkerId())
        assertEquals(6, empty!!.getFieldCount().toLong())
    }

    @Test
    fun testGetFathoms() {
        assertEquals(2.2, dbt!!.getFathoms(), 0.01)
    }

    @Test
    fun testGetFeet() {
        assertEquals(13.4, dbt!!.getFeet(), 0.01)
    }

    @Test
    fun testGetMeters() {
        assertEquals(4.1, dbt!!.getDepth(), 0.01)
    }

    @Test
    fun testSetFathoms() {
        empty!!.setFathoms(7.33333)
        assertEquals(7.3, empty!!.getFathoms(), 0.1)
    }

    @Test
    fun testSetFeet() {
        empty!!.setFeet(12.33333)
        assertEquals(12.3, empty!!.getFeet(), 0.1)
    }

    @Test
    fun testSetMeters() {
        empty!!.setDepth(23.654321)
        assertEquals(23.7, empty!!.getDepth(), 0.1)
    }

    companion object {
        const val EXAMPLE = "\$IIDBT,013.4,f,04.1,M,02.2,F*12"
    }
}