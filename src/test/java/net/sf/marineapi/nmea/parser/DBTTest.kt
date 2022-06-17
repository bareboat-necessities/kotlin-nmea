package net.sf.marineapi.nmea.parser

import org.junit.Assert.assertEquals

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
        assertEquals("DBT", empty.sentenceId)
        assertEquals(TalkerId.II, empty.talkerId)
        assertEquals(6, empty.fieldCount)
    }

    @Test
    fun testGetFathoms() {
        assertEquals(2.2, dbt.fathoms, 0.01)
    }

    @Test
    fun testGetFeet() {
        assertEquals(13.4, dbt.feet, 0.01)
    }

    @Test
    fun testGetMeters() {
        assertEquals(4.1, dbt.depth, 0.01)
    }

    @Test
    fun testSetFathoms() {
        empty.fathoms = 7.33333
        assertEquals(7.3, empty.fathoms, 0.1)
    }

    @Test
    fun testSetFeet() {
        empty.feet = 12.33333
        assertEquals(12.3, empty.feet, 0.1)
    }

    @Test
    fun testSetMeters() {
        empty.depth = 23.654321
        assertEquals(23.7, empty.depth, 0.1)
    }

    companion object {
        const val EXAMPLE = "\$IIDBT,013.4,f,04.1,M,02.2,F*12"
    }
}