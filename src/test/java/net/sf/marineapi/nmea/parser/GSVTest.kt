package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.GSVSentence
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.SatelliteInfo
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Test the GSV sentence parser.
 *
 * @author Kimmo Tuukkanen
 */
class GSVTest {
    private var empty: GSVSentence? = null
    private var gsv: GSVSentence? = null
    @Before
    fun setUp() {
        try {
            empty = GSVParser(TalkerId.GP)
            gsv = GSVParser(EXAMPLE)
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    @Test
    fun testConstructor() {
        assertEquals(19, empty!!.getFieldCount().toLong())
    }

    /**
     * Test method for
     * [GSVParser.getSatelliteCount].
     */
    @Test
    fun testGetSatelliteCount() {
        assertEquals(12, gsv!!.getSatelliteCount().toLong())
    }

    /**
     * Test method for
     * [GSVParser.getSatelliteInfo].
     */
    @Test
    fun testGetSatelliteInfo() {
        val sat = gsv!!.getSatelliteInfo()
        assertEquals(4, sat!!.size.toLong())
        testSatelliteInfo(sat[0], "15", 56, 182, 51)
        testSatelliteInfo(sat[1], "17", 38, 163, 47)
        testSatelliteInfo(sat[2], "18", 63, 58, 50)
        testSatelliteInfo(sat[3], "21", 53, 329, 47)
    }

    /**
     * Test method for
     * [GSVParser.getSatelliteInfo].
     */
    @Test
    fun testGetSatelliteInfoWithEmptyFields() {
        val g: GSVSentence = GSVParser("\$GPGSV,3,2,12,15,56,182,51,17,38,163,47,18,,,,21,53,329,47")
        val sat = g.getSatelliteInfo()
        assertEquals(3, sat!!.size.toLong())
        testSatelliteInfo(sat[0], "15", 56, 182, 51)
        testSatelliteInfo(sat[1], "17", 38, 163, 47)
        testSatelliteInfo(sat[2], "21", 53, 329, 47)
    }

    /**
     * Test method for
     * [GSVParser.getSatelliteInfo].
     */
    @Test
    fun testGetSatelliteInfoWithShortSentence() {
        val g: GSVSentence = GSVParser("\$GPGSV,3,2,12,15,56,182,51,17,38,163,47")
        val sat = g.getSatelliteInfo()
        assertEquals(2, sat!!.size.toLong())
        testSatelliteInfo(sat[0], "15", 56, 182, 51)
        testSatelliteInfo(sat[1], "17", 38, 163, 47)
    }

    /**
     * Test method for
     * [GSVParser.getSentenceCount].
     */
    @Test
    fun testGetSentenceCount() {
        assertEquals(3, gsv!!.getSentenceCount().toLong())
    }

    /**
     * Test method for
     * [GSVParser.getSentenceIndex].
     */
    @Test
    fun testGetSentenceIndex() {
        assertEquals(2, gsv!!.getSentenceIndex().toLong())
    }

    /**
     * Test method for [GSVParser.isFirst].
     */
    @Test
    fun testIsFirst() {
        assertFalse(gsv!!.isFirst())
    }

    /**
     * Test method for [GSVParser.isLast] .
     */
    @Test
    fun testIsLast() {
        assertFalse(gsv!!.isLast())
    }

    /**
     * Test method for
     * [GSVParser.setSatelliteCount].
     */
    @Test
    fun testSetSatelliteCount() {
        gsv!!.setSatelliteCount(5)
        assertEquals(5, gsv!!.getSatelliteCount().toLong())
        gsv!!.setSatelliteCount(10)
        assertEquals(10, gsv!!.getSatelliteCount().toLong())
    }

    /**
     * Test method for
     * [GSVParser.getSatelliteInfo].
     */
    @Test
    fun testSetSatelliteInfo() {
        val si: MutableList<SatelliteInfo?> = ArrayList()
        si.add(SatelliteInfo("01", 11, 12, 13))
        si.add(SatelliteInfo("02", 21, 22, 23))
        si.add(SatelliteInfo("03", 31, 32, 33))
        gsv!!.setSatelliteInfo(si)
        assertTrue(gsv.toString().contains(",03,31,032,33,,,,*"))
        val sat = gsv!!.getSatelliteInfo()
        assertEquals(3, sat!!.size.toLong())
        testSatelliteInfo(sat[0], "01", 11, 12, 13)
        testSatelliteInfo(sat[1], "02", 21, 22, 23)
        testSatelliteInfo(sat[2], "03", 31, 32, 33)
    }

    /**
     * Test method for
     * [GSVParser.setSentenceCount].
     */
    @Test
    fun testSetSentenceCount() {
        gsv!!.setSentenceCount(1)
        assertEquals(1, gsv!!.getSentenceCount().toLong())
        gsv!!.setSentenceCount(2)
        assertEquals(2, gsv!!.getSentenceCount().toLong())
    }

    @Test
    fun testParserGlonassGSV() {
        val gl = GSVParser("\$GLGSV,2,1,07,70,28,145,44,71,67,081,46,72,34,359,40,77,16,245,35,1*76")
        assertEquals(TalkerId.GL, gl.getTalkerId())
    }

    /**
     * Tests the given SatelliteInfo against specified values.
     */
    private fun testSatelliteInfo(
        si: SatelliteInfo?, id: String, elevation: Int,
        azimuth: Int, noise: Int
    ) {
        assertEquals(id, si!!.id)
        assertEquals(elevation.toDouble(), si.getElevation().toDouble(), 0.1)
        assertEquals(azimuth.toDouble(), si.getAzimuth().toDouble(), 0.1)
        assertEquals(noise.toDouble(), si.getNoise().toDouble(), 0.1)
    }

    companion object {
        /** Example sentence  */
        const val EXAMPLE = "\$GPGSV,3,2,12,15,56,182,51,17,38,163,47,18,63,058,50,21,53,329,47*73"
    }
}