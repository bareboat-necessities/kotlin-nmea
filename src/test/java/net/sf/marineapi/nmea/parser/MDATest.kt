package net.sf.marineapi.nmea.parser

import org.junit.Assert.assertEquals

/**
 * @author Richard van Nieuwenhoven
 */
class MDATest {
    private var mda: MDASentence? = null
    private var mda2: MDASentence? = null

    /**
     * @throws java.lang.Exception
     */
    @Before
    @Throws(Exception::class)
    fun setUp() {
        mda = MDAParser(EXAMPLE)
        mda2 = MDAParser(EXAMPLE2)
    }

    @Test
    fun testMDAParserTalkerId() {
        val mwdp = MDAParser(TalkerId.II)
        assertEquals(TalkerId.II, mwdp.talkerId)
        assertEquals(SentenceId.MDA.toString(), mwdp.sentenceId)
    }

    @Test
    fun testGetMagneticWindDirection() {
        assertTrue(java.lang.Double.isNaN(mda.magneticWindDirection))
        assertEquals(38.7, mda2.magneticWindDirection, 0.1)
    }

    @Test
    fun testGetTrueWindDirection() {
        assertEquals(295.19, mda.trueWindDirection, 0.1)
        assertTrue(java.lang.Double.isNaN(mda2.trueWindDirection))
    }

    @Test
    fun testGetWindSpeed() {
        assertEquals(2.93, mda.windSpeed, 0.1)
        assertEquals(5.6, mda2.windSpeed, 0.1)
    }

    @Test
    fun testGetWindSpeedKnots() {
        assertEquals(5.7, mda.windSpeedKnots, 0.1)
        assertEquals(10.88, mda2.windSpeedKnots, 0.1)
    }

    @Test
    fun testGetAbsoluteHumidity() {
        assertTrue(java.lang.Double.isNaN(mda.absoluteHumidity))
        assertEquals(16.4, mda2.absoluteHumidity, 0.1)
    }

    @Test
    fun testGetAirTemperature() {
        assertEquals(3.2, mda.airTemperature, 0.1)
        assertEquals(26.8, mda2.airTemperature, 0.1)
    }

    @Test
    fun testGetSecondaryBarometricPressure() {
        assertEquals(1.0, mda.secondaryBarometricPressure, 0.1)
        assertEquals('B', mda.secondaryBarometricPressureUnit)
        assertEquals(1.0, mda2.secondaryBarometricPressure, 0.1)
        assertEquals('B', mda2.secondaryBarometricPressureUnit)
    }

    @Test
    fun testGetPrimaryBarometricPressure() {
        assertEquals(99700.0, mda.primaryBarometricPressure, 0.1)
        assertEquals('P', mda.primaryBarometricPressureUnit)
        assertEquals(30.0, mda2.primaryBarometricPressure, 0.1)
        assertEquals('I', mda2.primaryBarometricPressureUnit)
    }

    @Test
    fun testGetRelativeHumidity() {
        assertTrue(java.lang.Double.isNaN(mda.relativeHumidity))
        assertEquals(64.2, mda2.relativeHumidity, 0.1)
    }

    @Test
    fun testGetDewPoint() {
        assertTrue(java.lang.Double.isNaN(mda.dewPoint))
        assertEquals(19.5, mda2.dewPoint, 0.1)
    }

    @Test
    fun testGetWaterTemperature() {
        assertTrue(java.lang.Double.isNaN(mda.waterTemperature))
        assertTrue(java.lang.Double.isNaN(mda2.waterTemperature))
    }

    @Test
    fun testSetMagneticWindDirection() {
        mda.magneticWindDirection = 123.4
        assertEquals(123.4, mda.magneticWindDirection, 0.1)
    }

    @Test
    fun testSetTrueWindDirection() {
        mda.trueWindDirection = 234.5
        assertEquals(234.5, mda.trueWindDirection, 0.1)
    }

    @Test
    fun testSetWindSpeed() {
        mda.windSpeed = 12.3
        assertEquals(12.3, mda.windSpeed, 0.1)
    }

    @Test
    fun testSetWindSpeedKnots() {
        mda.windSpeedKnots = 6.78
        assertEquals(6.78, mda.windSpeedKnots, 0.01)
    }

    @Test
    fun testSetAbsoluteHumidity() {
        mda.absoluteHumidity = 34.5
        assertEquals(34.5, mda.absoluteHumidity, 0.1)
    }

    @Test
    fun testSetAirTemperature() {
        mda.airTemperature = 18.9
        assertEquals(18.9, mda.airTemperature, 0.1)
    }

    @Test
    fun testSetSecondaryBarometricPressure() {
        mda.secondaryBarometricPressure = 0.99
        mda.secondaryBarometricPressureUnit = 'B'
        assertEquals(0.99, mda.secondaryBarometricPressure, 0.01)
        assertEquals('B', mda.secondaryBarometricPressureUnit)
    }

    @Test
    fun testSetPrimaryBarometricPressure() {
        mda.primaryBarometricPressure = 29.53
        mda.primaryBarometricPressureUnit = 'I'
        assertEquals(29.53, mda.primaryBarometricPressure, 0.01)
        assertEquals('I', mda.primaryBarometricPressureUnit)
    }

    @Test
    fun testSetRelativeHumidity() {
        mda.relativeHumidity = 55.5
        assertEquals(55.5, mda.relativeHumidity, 0.1)
    }

    @Test
    fun testSetDewPoint() {
        mda.dewPoint = 6.7
        assertEquals(6.7, mda.dewPoint, 0.1)
    }

    @Test
    fun testSetWaterTemperature() {
        mda.waterTemperature = 8.9
        assertEquals(8.9, mda.waterTemperature, 0.1)
    }

    companion object {
        const val EXAMPLE = "\$IIMDA,99700.0,P,1.00,B,3.2,C,,C,,,,C,295.19,T,,M,5.70,N,2.93,M*08"
        const val EXAMPLE2 = "\$IIMDA,30.0,I,1.0149,B,26.8,C,,C,64.2,16.4,19.5,C,,T,38.7,M,10.88,N,5.60,M*36"
    }
}