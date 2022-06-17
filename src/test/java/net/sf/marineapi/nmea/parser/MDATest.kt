package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.MDASentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * @author Richard van Nieuwenhoven
 */
class MDATest {
    private var mda: MDASentence? = null
    private var mda2: MDASentence? = null

    /**
     * @throws Exception
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
        Assert.assertEquals(TalkerId.II, mwdp.getTalkerId())
        Assert.assertEquals(SentenceId.MDA.toString(), mwdp.getSentenceId())
    }

    @Test
    fun testGetMagneticWindDirection() {
        Assert.assertTrue(java.lang.Double.isNaN(mda!!.getMagneticWindDirection()))
        Assert.assertEquals(38.7, mda2!!.getMagneticWindDirection(), 0.1)
    }

    @Test
    fun testGetTrueWindDirection() {
        Assert.assertEquals(295.19, mda!!.getTrueWindDirection(), 0.1)
        Assert.assertTrue(java.lang.Double.isNaN(mda2!!.getTrueWindDirection()))
    }

    @Test
    fun testGetWindSpeed() {
        Assert.assertEquals(2.93, mda!!.getWindSpeed(), 0.1)
        Assert.assertEquals(5.6, mda2!!.getWindSpeed(), 0.1)
    }

    @Test
    fun testGetWindSpeedKnots() {
        Assert.assertEquals(5.7, mda!!.getWindSpeedKnots(), 0.1)
        Assert.assertEquals(10.88, mda2!!.getWindSpeedKnots(), 0.1)
    }

    @Test
    fun testGetAbsoluteHumidity() {
        Assert.assertTrue(java.lang.Double.isNaN(mda!!.getAbsoluteHumidity()))
        Assert.assertEquals(16.4, mda2!!.getAbsoluteHumidity(), 0.1)
    }

    @Test
    fun testGetAirTemperature() {
        Assert.assertEquals(3.2, mda!!.getAirTemperature(), 0.1)
        Assert.assertEquals(26.8, mda2!!.getAirTemperature(), 0.1)
    }

    @Test
    fun testGetSecondaryBarometricPressure() {
        Assert.assertEquals(1.0, mda!!.getSecondaryBarometricPressure(), 0.1)
        Assert.assertEquals('B'.code.toLong(), mda!!.getSecondaryBarometricPressureUnit().code.toLong())
        Assert.assertEquals(1.0, mda2!!.getSecondaryBarometricPressure(), 0.1)
        Assert.assertEquals('B'.code.toLong(), mda2!!.getSecondaryBarometricPressureUnit().code.toLong())
    }

    @Test
    fun testGetPrimaryBarometricPressure() {
        Assert.assertEquals(99700.0, mda!!.getPrimaryBarometricPressure(), 0.1)
        Assert.assertEquals('P'.code.toLong(), mda!!.getPrimaryBarometricPressureUnit().code.toLong())
        Assert.assertEquals(30.0, mda2!!.getPrimaryBarometricPressure(), 0.1)
        Assert.assertEquals('I'.code.toLong(), mda2!!.getPrimaryBarometricPressureUnit().code.toLong())
    }

    @Test
    fun testGetRelativeHumidity() {
        Assert.assertTrue(java.lang.Double.isNaN(mda!!.getRelativeHumidity()))
        Assert.assertEquals(64.2, mda2!!.getRelativeHumidity(), 0.1)
    }

    @Test
    fun testGetDewPoint() {
        Assert.assertTrue(java.lang.Double.isNaN(mda!!.getDewPoint()))
        Assert.assertEquals(19.5, mda2!!.getDewPoint(), 0.1)
    }

    @Test
    fun testGetWaterTemperature() {
        Assert.assertTrue(java.lang.Double.isNaN(mda!!.getWaterTemperature()))
        Assert.assertTrue(java.lang.Double.isNaN(mda2!!.getWaterTemperature()))
    }

    @Test
    fun testSetMagneticWindDirection() {
        mda!!.setMagneticWindDirection(123.4)
        Assert.assertEquals(123.4, mda!!.getMagneticWindDirection(), 0.1)
    }

    @Test
    fun testSetTrueWindDirection() {
        mda!!.setTrueWindDirection(234.5)
        Assert.assertEquals(234.5, mda!!.getTrueWindDirection(), 0.1)
    }

    @Test
    fun testSetWindSpeed() {
        mda!!.setWindSpeed(12.3)
        Assert.assertEquals(12.3, mda!!.getWindSpeed(), 0.1)
    }

    @Test
    fun testSetWindSpeedKnots() {
        mda!!.setWindSpeedKnots(6.78)
        Assert.assertEquals(6.78, mda!!.getWindSpeedKnots(), 0.01)
    }

    @Test
    fun testSetAbsoluteHumidity() {
        mda!!.setAbsoluteHumidity(34.5)
        Assert.assertEquals(34.5, mda!!.getAbsoluteHumidity(), 0.1)
    }

    @Test
    fun testSetAirTemperature() {
        mda!!.setAirTemperature(18.9)
        Assert.assertEquals(18.9, mda!!.getAirTemperature(), 0.1)
    }

    @Test
    fun testSetSecondaryBarometricPressure() {
        mda!!.setSecondaryBarometricPressure(0.99)
        mda!!.setSecondaryBarometricPressureUnit('B')
        Assert.assertEquals(0.99, mda!!.getSecondaryBarometricPressure(), 0.01)
        Assert.assertEquals('B'.code.toLong(), mda!!.getSecondaryBarometricPressureUnit().code.toLong())
    }

    @Test
    fun testSetPrimaryBarometricPressure() {
        mda!!.setPrimaryBarometricPressure(29.53)
        mda!!.setPrimaryBarometricPressureUnit('I')
        Assert.assertEquals(29.53, mda!!.getPrimaryBarometricPressure(), 0.01)
        Assert.assertEquals('I'.code.toLong(), mda!!.getPrimaryBarometricPressureUnit().code.toLong())
    }

    @Test
    fun testSetRelativeHumidity() {
        mda!!.setRelativeHumidity(55.5)
        Assert.assertEquals(55.5, mda!!.getRelativeHumidity(), 0.1)
    }

    @Test
    fun testSetDewPoint() {
        mda!!.setDewPoint(6.7)
        Assert.assertEquals(6.7, mda!!.getDewPoint(), 0.1)
    }

    @Test
    fun testSetWaterTemperature() {
        mda!!.setWaterTemperature(8.9)
        Assert.assertEquals(8.9, mda!!.getWaterTemperature(), 0.1)
    }

    companion object {
        const val EXAMPLE = "\$IIMDA,99700.0,P,1.00,B,3.2,C,,C,,,,C,295.19,T,,M,5.70,N,2.93,M*08"
        const val EXAMPLE2 = "\$IIMDA,30.0,I,1.0149,B,26.8,C,,C,64.2,16.4,19.5,C,,T,38.7,M,10.88,N,5.60,M*36"
    }
}