package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*
import java.util.Date

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
            Assert.fail(e.message)
        }
    }

    @Test
    fun testConstructor() {
        Assert.assertEquals(6, empty!!.getFieldCount().toLong())
    }

    /**
     * Test method for [ZDAParser.getDate].
     */
    @Test
    fun testGetDate() {
        val expected = net.sf.marineapi.nmea.util.Date(2004, 8, 7)
        val parsed = zda!!.getDate()
        Assert.assertEquals(expected, parsed)
    }

    /**
     * Test method for [ZDAParser.getDay].
     */
    @Test
    fun testGetDay() {
        Assert.assertEquals(7, zda!!.getDate().getDay().toLong())
    }

    /**
     * Test method for
     * [ZDAParser.getLocalZoneHours].
     */
    @Test
    fun testGetLocalZoneHours() {
        Assert.assertEquals(0, zda!!.getLocalZoneHours().toLong())
    }

    /**
     * Test method for
     * [ZDAParser.setLocalZoneHours].
     */
    @Test
    fun testSetLocalZoneHours() {
        val hours = 7
        zda!!.setLocalZoneHours(hours)
        Assert.assertTrue(zda.toString().contains(",2004,07,00*"))
        Assert.assertEquals(hours.toLong(), zda!!.getLocalZoneHours().toLong())
    }

    /**
     * Test method for
     * [ZDAParser.getLocalZoneMinutes].
     */
    @Test
    fun testGetLocalZoneMinutes() {
        Assert.assertEquals(0, zda!!.getLocalZoneMinutes().toLong())
    }

    /**
     * Test method for
     * [ZDAParser.setLocalZoneMinutes].
     */
    @Test
    fun testSetLocalZoneMinutes() {
        val min = 9
        zda!!.setLocalZoneMinutes(min)
        Assert.assertTrue(zda.toString().contains(",2004,00,09*"))
        Assert.assertEquals(min.toLong(), zda!!.getLocalZoneMinutes().toLong())
    }

    /**
     * Test method for [ZDAParser.getMonth]
     * .
     */
    @Test
    fun testGetMonth() {
        Assert.assertEquals(8, zda!!.getDate().getMonth().toLong())
    }

    /**
     * Test method for [ZDAParser.getTime].
     */
    @Test
    fun testGetTime() {
        val t = zda!!.getTime()
        Assert.assertNotNull(t)
        Assert.assertEquals(3, t.getHour().toLong())
        Assert.assertEquals(29, t.getMinutes().toLong())
        Assert.assertEquals(15.0, t.getSeconds(), 0.1)
    }

    /**
     * Test method for [ZDAParser.getYear].
     */
    @Test
    fun testGetYear() {
        Assert.assertEquals(2004, zda!!.getDate().getYear().toLong())
    }

    /**
     * Test method for [ZDAParser.getTime].
     */
    @Test
    fun testSetDate() {
        zda!!.setDate(net.sf.marineapi.nmea.util.Date(10, 6, 9))
        Assert.assertTrue(zda.toString().contains(",032915,09,06,2010,00,"))
        zda!!.setDate(net.sf.marineapi.nmea.util.Date(85, 12, 11))
        Assert.assertTrue(zda.toString().contains(",032915,11,12,1985,00,"))
    }

    /**
     * Test method for [ZDAParser.setTime].
     */
    @Test
    fun testSetTime() {
        // 09:08:07.6
        val t = Time(9, 8, 7.6)
        zda!!.setTime(t)
        Assert.assertTrue(zda.toString().startsWith("\$GPZDA,090807.600,07,"))
    }

    /**
     * Test method for [ZDAParser.setTimeAndLocalZone].
     */
    @Test
    fun testSetTimeAndLocalZone() {
        // 09:08:07.6+01:02
        val t = Time(9, 8, 7.6, 1, 2)
        zda!!.setTimeAndLocalZone(t)
        Assert.assertEquals(1, zda!!.getLocalZoneHours().toLong())
        Assert.assertEquals(2, zda!!.getLocalZoneMinutes().toLong())
        Assert.assertTrue(zda.toString().startsWith("\$GPZDA,090807.600,07,"))
        Assert.assertTrue(zda.toString().contains("2004,01,02*"))
    }

    /**
     * Test method for [ZDAParser.toDate].
     */
    @Test
    fun testToDate() {
        val d = net.sf.marineapi.nmea.util.Date(2010, 6, 15)
        val t = Time(12, 15, 30.246, 2, 0)
        zda!!.setDate(d)
        zda!!.setTime(t)
        val cal = GregorianCalendar()
        cal[Calendar.YEAR] = 2010
        cal[Calendar.MONTH] = 5
        cal[Calendar.DAY_OF_MONTH] = 15
        cal[Calendar.HOUR_OF_DAY] = 12
        cal[Calendar.MINUTE] = 15
        cal[Calendar.SECOND] = 30
        cal[Calendar.MILLISECOND] = 246
        val result = zda!!.toDate()
        val expected = cal.time
        Assert.assertEquals(expected, result)
        Assert.assertEquals(expected.time, result.time)
    }

    companion object {
        /** Example sentence  */
        const val EXAMPLE = "\$GPZDA,032915,07,08,2004,00,00*4D"
    }
}