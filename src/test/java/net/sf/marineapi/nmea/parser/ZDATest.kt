package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.util.Date
import net.sf.marineapi.nmea.util.Time
import org.junit.Assert.assertEquals
import java.util.*

/**
 * Tests the ZDA sentence parser.
 *
 * @author Kimmo Tuukkanen
 */
class ZDATest {
    private var empty: ZDAParser? = null
    private var zda: ZDAParser? = null
    @Before
    fun setUp() {
        try {
            empty = ZDAParser(TalkerId.GP)
            zda = ZDAParser(EXAMPLE)
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    @Test
    fun testConstructor() {
        assertEquals(6, empty!!.fieldCount)
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.ZDAParser.getDate].
     */
    @Test
    fun testGetDate() {
        val expected = Date(2004, 8, 7)
        val parsed = zda!!.getDate()
        assertEquals(expected, parsed)
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.ZDAParser.getDay].
     */
    @Test
    fun testGetDay() {
        assertEquals(7, zda!!.getDate().getDay())
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.ZDAParser.getLocalZoneHours].
     */
    @Test
    fun testGetLocalZoneHours() {
        assertEquals(0, zda!!.getLocalZoneHours())
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.ZDAParser.setLocalZoneHours].
     */
    @Test
    fun testSetLocalZoneHours() {
        val hours = 7
        zda!!.setLocalZoneHours(hours)
        assertTrue(zda.toString().contains(",2004,07,00*"))
        assertEquals(hours, zda!!.getLocalZoneHours())
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.ZDAParser.getLocalZoneMinutes].
     */
    @Test
    fun testGetLocalZoneMinutes() {
        assertEquals(0, zda!!.getLocalZoneMinutes())
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.ZDAParser.setLocalZoneMinutes].
     */
    @Test
    fun testSetLocalZoneMinutes() {
        val min = 9
        zda!!.setLocalZoneMinutes(min)
        assertTrue(zda.toString().contains(",2004,00,09*"))
        assertEquals(min, zda!!.getLocalZoneMinutes())
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.ZDAParser.getMonth]
     * .
     */
    @Test
    fun testGetMonth() {
        assertEquals(8, zda!!.getDate().getMonth())
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.ZDAParser.getTime].
     */
    @Test
    fun testGetTime() {
        val t = zda!!.getTime()
        assertNotNull(t)
        assertEquals(3, t.getHour())
        assertEquals(29, t.getMinutes())
        assertEquals(15.0, t.getSeconds(), 0.1)
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.ZDAParser.getYear].
     */
    @Test
    fun testGetYear() {
        assertEquals(2004, zda!!.getDate().getYear())
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.ZDAParser.getTime].
     */
    @Test
    fun testSetDate() {
        zda!!.setDate(Date(10, 6, 9))
        assertTrue(zda.toString().contains(",032915,09,06,2010,00,"))
        zda!!.setDate(Date(85, 12, 11))
        assertTrue(zda.toString().contains(",032915,11,12,1985,00,"))
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.ZDAParser.setTime].
     */
    @Test
    fun testSetTime() {
        // 09:08:07.6
        val t = Time(9, 8, 7.6)
        zda!!.setTime(t)
        assertTrue(zda.toString().startsWith("\$GPZDA,090807.600,07,"))
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.ZDAParser.setTimeAndLocalZone].
     */
    @Test
    fun testSetTimeAndLocalZone() {
        // 09:08:07.6+01:02
        val t = Time(9, 8, 7.6, 1, 2)
        zda!!.setTimeAndLocalZone(t)
        assertEquals(1, zda!!.getLocalZoneHours())
        assertEquals(2, zda!!.getLocalZoneMinutes())
        assertTrue(zda.toString().startsWith("\$GPZDA,090807.600,07,"))
        assertTrue(zda.toString().contains("2004,01,02*"))
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.ZDAParser.toDate].
     */
    @Test
    fun testToDate() {
        val d = Date(2010, 6, 15)
        val t = Time(12, 15, 30.246, 2, 0)
        zda!!.setDate(d)
        zda!!.setTime(t)
        val cal = GregorianCalendar()
        cal.set(Calendar.YEAR, 2010)
        cal.set(Calendar.MONTH, 5)
        cal.set(Calendar.DAY_OF_MONTH, 15)
        cal.set(Calendar.HOUR_OF_DAY, 12)
        cal.set(Calendar.MINUTE, 15)
        cal.set(Calendar.SECOND, 30)
        cal.set(Calendar.MILLISECOND, 246)
        val result = zda!!.toDate()
        val expected: java.util.Date = cal.getTime()
        assertEquals(expected, result)
        assertEquals(expected.time, result!!.time)
    }

    companion object {
        /** Example sentence  */
        const val EXAMPLE = "\$GPZDA,032915,07,08,2004,00,00*4D"
    }
}