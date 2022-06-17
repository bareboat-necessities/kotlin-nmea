package net.sf.marineapi.nmea.parser

import org.junit.Assert.assertEquals

/**
 * @author Richard van Nieuwenhoven
 */
class MWDTest {
    private var mwd: MWDSentence? = null

    /**
     * @throws java.lang.Exception
     */
    @Before
    @Throws(Exception::class)
    fun setUp() {
        mwd = MWDParser(EXAMPLE)
    }

    @Test
    fun testMWDParserTalkerId() {
        val mwdp = MWDParser(TalkerId.II)
        assertEquals(TalkerId.II, mwdp.talkerId)
        assertEquals(SentenceId.MWD.toString(), mwdp.sentenceId)
    }

    @Test
    fun testGetMagneticWindDirection() {
        assertTrue(java.lang.Double.isNaN(mwd.magneticWindDirection))
    }

    @Test
    fun testGetTrueWindDirection() {
        assertEquals(295.19, mwd.trueWindDirection, 0.1)
    }

    @Test
    fun testGetWindSpeed() {
        assertEquals(2.62, mwd.windSpeed, 0.1)
    }

    @Test
    fun testGetWindSpeedKnots() {
        assertEquals(5.09, mwd.windSpeedKnots, 0.1)
    }

    @Test
    fun testSetMagneticWindDirection() {
        mwd.magneticWindDirection = 123.4
        assertEquals(123.4, mwd.magneticWindDirection, 0.1)
    }

    @Test
    fun testSetTrueWindDirection() {
        mwd.trueWindDirection = 234.5
        assertEquals(234.5, mwd.trueWindDirection, 0.1)
    }

    @Test
    fun testSetWindSpeed() {
        mwd.windSpeed = 12.3
        assertEquals(12.3, mwd.windSpeed, 0.1)
    }

    @Test
    fun testSetWindSpeedKnots() {
        mwd.windSpeedKnots = 6.2
        assertEquals(6.2, mwd.windSpeedKnots, 0.1)
    }

    companion object {
        const val EXAMPLE = "\$IIMWD,295.19,T,,M,5.09,N,2.62,M*56"
    }
}