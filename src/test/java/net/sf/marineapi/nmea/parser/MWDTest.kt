package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.MWDSentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * @author Richard van Nieuwenhoven
 */
class MWDTest {
    private var mwd: MWDSentence? = null

    /**
     * @throws Exception
     */
    @Before
    @Throws(Exception::class)
    fun setUp() {
        mwd = MWDParser(EXAMPLE)
    }

    @Test
    fun testMWDParserTalkerId() {
        val mwdp = MWDParser(TalkerId.II)
        Assert.assertEquals(TalkerId.II, mwdp.getTalkerId())
        Assert.assertEquals(SentenceId.MWD.toString(), mwdp.getSentenceId())
    }

    @Test
    fun testGetMagneticWindDirection() {
        Assert.assertTrue(java.lang.Double.isNaN(mwd!!.getMagneticWindDirection()))
    }

    @Test
    fun testGetTrueWindDirection() {
        Assert.assertEquals(295.19, mwd!!.getTrueWindDirection(), 0.1)
    }

    @Test
    fun testGetWindSpeed() {
        Assert.assertEquals(2.62, mwd!!.getWindSpeed(), 0.1)
    }

    @Test
    fun testGetWindSpeedKnots() {
        Assert.assertEquals(5.09, mwd!!.getWindSpeedKnots(), 0.1)
    }

    @Test
    fun testSetMagneticWindDirection() {
        mwd!!.setMagneticWindDirection(123.4)
        Assert.assertEquals(123.4, mwd!!.getMagneticWindDirection(), 0.1)
    }

    @Test
    fun testSetTrueWindDirection() {
        mwd!!.setTrueWindDirection(234.5)
        Assert.assertEquals(234.5, mwd!!.getTrueWindDirection(), 0.1)
    }

    @Test
    fun testSetWindSpeed() {
        mwd!!.setWindSpeed(12.3)
        Assert.assertEquals(12.3, mwd!!.getWindSpeed(), 0.1)
    }

    @Test
    fun testSetWindSpeedKnots() {
        mwd!!.setWindSpeedKnots(6.2)
        Assert.assertEquals(6.2, mwd!!.getWindSpeedKnots(), 0.1)
    }

    companion object {
        const val EXAMPLE = "\$IIMWD,295.19,T,,M,5.09,N,2.62,M*56"
    }
}