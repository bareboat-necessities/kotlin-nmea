package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.DBTSentence
import net.sf.marineapi.nmea.sentence.TalkerId
import org.junit.Assert
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
        Assert.assertEquals("DBT", empty!!.getSentenceId())
        Assert.assertEquals(TalkerId.II, empty!!.getTalkerId())
        Assert.assertEquals(6, empty!!.getFieldCount().toLong())
    }

    @Test
    fun testGetFathoms() {
        Assert.assertEquals(2.2, dbt!!.getFathoms(), 0.01)
    }

    @Test
    fun testGetFeet() {
        Assert.assertEquals(13.4, dbt!!.getFeet(), 0.01)
    }

    @Test
    fun testGetMeters() {
        Assert.assertEquals(4.1, dbt!!.getDepth(), 0.01)
    }

    @Test
    fun testSetFathoms() {
        empty!!.setFathoms(7.33333)
        Assert.assertEquals(7.3, empty!!.getFathoms(), 0.1)
    }

    @Test
    fun testSetFeet() {
        empty!!.setFeet(12.33333)
        Assert.assertEquals(12.3, empty!!.getFeet(), 0.1)
    }

    @Test
    fun testSetMeters() {
        empty!!.setDepth(23.654321)
        Assert.assertEquals(23.7, empty!!.getDepth(), 0.1)
    }

    companion object {
        const val EXAMPLE = "\$IIDBT,013.4,f,04.1,M,02.2,F*12"
    }
}