package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.GBSSentence
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.Time

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GBSTest {
    private var gbs: GBSSentence? = null
    private var empty: GBSSentence? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        gbs = GBSParser(EXAMPLE)
        empty = GBSParser(TalkerId.GP)
    }

    @Test
    @Throws(Exception::class)
    fun getLatitudeError() {
        assertEquals(-0.031, gbs!!.getLatitudeError(), 0.001)
    }

    @Test
    @Throws(Exception::class)
    fun setLatitudeError() {
        empty!!.setLatitudeError(-0.123)
        assertEquals(-0.123, empty!!.getLatitudeError(), 0.001)
    }

    @Test
    @Throws(Exception::class)
    fun getLongitudeError() {
        assertEquals(-0.186, gbs!!.getLongitudeError(), 0.001)
    }

    @Test
    @Throws(Exception::class)
    fun setLongitudeError() {
        empty!!.setLongitudeError(-0.456)
        assertEquals(-0.456, empty!!.getLongitudeError(), 0.001)
    }

    @Test
    @Throws(Exception::class)
    fun getAltitudeError() {
        assertEquals(0.219, gbs!!.getAltitudeError(), 0.001)
    }

    @Test
    @Throws(Exception::class)
    fun setAltitudeError() {
        empty!!.setAltitudeError(-0.456)
        assertEquals(-0.456, empty!!.getAltitudeError(), 0.001)
    }

    @Test
    @Throws(Exception::class)
    fun getSatelliteId() {
        assertEquals("19", gbs!!.getSatelliteId())
    }

    @Test
    @Throws(Exception::class)
    fun setSatelliteId() {
        empty!!.setSatelliteId("07")
        assertEquals("07", empty!!.getSatelliteId())
    }

    @Test
    @Throws(Exception::class)
    fun getProbability() {
        assertEquals(0.000, gbs!!.getProbability(), 0.001)
    }

    @Test
    @Throws(Exception::class)
    fun setProbability() {
        empty!!.setProbability(0.123)
        assertEquals(0.123, empty!!.getProbability(), 0.001)
    }

    @Test
    @Throws(Exception::class)
    fun getEstimate() {
        assertEquals(-0.354, gbs!!.getEstimate(), 0.001)
    }

    @Test
    @Throws(Exception::class)
    fun setEstimate() {
        empty!!.setEstimate(-0.234)
        assertEquals(-0.234, empty!!.getEstimate(), 0.001)
    }

    @Test
    @Throws(Exception::class)
    fun getDeviation() {
        assertEquals(6.972, gbs!!.getDeviation(), 0.001)
    }

    @Test
    @Throws(Exception::class)
    fun setDeviation() {
        empty!!.setDeviation(1.234)
        assertEquals(1.234, empty!!.getDeviation(), 0.001)
    }

    @Test
    @Throws(Exception::class)
    fun getTime() {
        assertEquals("015509.000", gbs!!.getTime().toString())
    }

    @Test
    @Throws(Exception::class)
    fun setTime() {
        empty!!.setTime(Time(10, 31, 12.345))
        assertEquals("103112.345", empty!!.getTime().toString())
    }

    companion object {
        const val EXAMPLE = "\$GPGBS,015509.00,-0.031,-0.186,0.219,19,0.000,-0.354,6.972*4D"
    }
}