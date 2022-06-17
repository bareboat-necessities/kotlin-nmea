package net.sf.marineapi.nmea.parser

import org.junit.Assert.assertEquals

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
        assertEquals(19, empty.fieldCount)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.GSVParser.getSatelliteCount].
     */
    @Test
    fun testGetSatelliteCount() {
        assertEquals(12, gsv.satelliteCount)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.GSVParser.getSatelliteInfo].
     */
    @Test
    fun testGetSatelliteInfo() {
        val sat: List<SatelliteInfo> = gsv.getSatelliteInfo()
        assertEquals(4, sat.size)
        testSatelliteInfo(sat[0], "15", 56, 182, 51)
        testSatelliteInfo(sat[1], "17", 38, 163, 47)
        testSatelliteInfo(sat[2], "18", 63, 58, 50)
        testSatelliteInfo(sat[3], "21", 53, 329, 47)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.GSVParser.getSatelliteInfo].
     */
    @Test
    fun testGetSatelliteInfoWithEmptyFields() {
        val g: GSVSentence = GSVParser("\$GPGSV,3,2,12,15,56,182,51,17,38,163,47,18,,,,21,53,329,47")
        val sat: List<SatelliteInfo> = g.getSatelliteInfo()
        assertEquals(3, sat.size)
        testSatelliteInfo(sat[0], "15", 56, 182, 51)
        testSatelliteInfo(sat[1], "17", 38, 163, 47)
        testSatelliteInfo(sat[2], "21", 53, 329, 47)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.GSVParser.getSatelliteInfo].
     */
    @Test
    fun testGetSatelliteInfoWithShortSentence() {
        val g: GSVSentence = GSVParser("\$GPGSV,3,2,12,15,56,182,51,17,38,163,47")
        val sat: List<SatelliteInfo> = g.getSatelliteInfo()
        assertEquals(2, sat.size)
        testSatelliteInfo(sat[0], "15", 56, 182, 51)
        testSatelliteInfo(sat[1], "17", 38, 163, 47)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.GSVParser.getSentenceCount].
     */
    @Test
    fun testGetSentenceCount() {
        assertEquals(3, gsv.sentenceCount)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.GSVParser.getSentenceIndex].
     */
    @Test
    fun testGetSentenceIndex() {
        assertEquals(2, gsv.sentenceIndex)
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.GSVParser.isFirst].
     */
    @Test
    fun testIsFirst() {
        assertFalse(gsv.isFirst)
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.GSVParser.isLast] .
     */
    @Test
    fun testIsLast() {
        assertFalse(gsv.isLast)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.GSVParser.setSatelliteCount].
     */
    @Test
    fun testSetSatelliteCount() {
        gsv.satelliteCount = 5
        assertEquals(5, gsv.satelliteCount)
        gsv.satelliteCount = 10
        assertEquals(10, gsv.satelliteCount)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.GSVParser.getSatelliteInfo].
     */
    @Test
    fun testSetSatelliteInfo() {
        val si: MutableList<SatelliteInfo> = ArrayList<SatelliteInfo>()
        si.add(SatelliteInfo("01", 11, 12, 13))
        si.add(SatelliteInfo("02", 21, 22, 23))
        si.add(SatelliteInfo("03", 31, 32, 33))
        gsv.setSatelliteInfo(si)
        assertTrue(gsv.toString().contains(",03,31,032,33,,,,*"))
        val sat: List<SatelliteInfo> = gsv.getSatelliteInfo()
        assertEquals(3, sat.size)
        testSatelliteInfo(sat[0], "01", 11, 12, 13)
        testSatelliteInfo(sat[1], "02", 21, 22, 23)
        testSatelliteInfo(sat[2], "03", 31, 32, 33)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.GSVParser.setSentenceCount].
     */
    @Test
    fun testSetSentenceCount() {
        gsv.sentenceCount = 1
        assertEquals(1, gsv.sentenceCount)
        gsv.sentenceCount = 2
        assertEquals(2, gsv.sentenceCount)
    }

    @Test
    fun testParserGlonassGSV() {
        val gl = GSVParser("\$GLGSV,2,1,07,70,28,145,44,71,67,081,46,72,34,359,40,77,16,245,35,1*76")
        assertEquals(TalkerId.GL, gl.talkerId)
    }

    /**
     * Tests the given SatelliteInfo against specified values.
     */
    private fun testSatelliteInfo(
        si: SatelliteInfo, id: String, elevation: Int,
        azimuth: Int, noise: Int
    ) {
        assertEquals(id, si.id)
        assertEquals(elevation, si.getElevation(), 0.1)
        assertEquals(azimuth, si.getAzimuth(), 0.1)
        assertEquals(noise, si.getNoise(), 0.1)
    }

    companion object {
        /** Example sentence  */
        const val EXAMPLE = "\$GPGSV,3,2,12,15,56,182,51,17,38,163,47,18,63,058,50,21,53,329,47*73"
    }
}