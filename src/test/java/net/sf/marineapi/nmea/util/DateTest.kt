/*
 * DateTest.java
 * Copyright (C) 2010 Kimmo Tuukkanen
 *
 * This file is part of Java Marine API.
 * <http://ktuukkan.github.io/marine-api/>
 *
 * Java Marine API is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * Java Marine API is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Java Marine API. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.marineapi.nmea.util

import org.junit.Assert.assertEquals

/**
 * @author Kimmo Tuukkanen
 */
class DateTest {
    private var instance: Date? = null
    private var cal: GregorianCalendar? = null

    /**
     * @throws java.lang.Exception
     */
    @Before
    @Throws(Exception::class)
    fun setUp() {
        instance = Date()
        cal = GregorianCalendar()
    }

    /**
     * Test method for [net.sf.marineapi.nmea.util.Date.Date].
     */
    @Test
    fun testConstructor() {
        assertEquals(cal.get(Calendar.YEAR), instance!!.getYear())
        assertEquals(cal.get(Calendar.MONTH) + 1, instance!!.getMonth())
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), instance!!.getDay())
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.util.Date.Date].
     */
    @Test
    fun testConstructorWithValues() {
        val d = Date(2010, 6, 15)
        assertEquals(2010, d.getYear())
        assertEquals(6, d.getMonth())
        assertEquals(15, d.getDay())
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.util.Date.Date].
     */
    @Test
    fun testConstructorWithString() {
        val d = Date("150610")
        assertEquals(2010, d.getYear())
        assertEquals(6, d.getMonth())
        assertEquals(15, d.getDay())
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.util.Date.equals].
     */
    @Test
    fun testEqualsAfterInit() {
        val d = Date()
        assertTrue(d.equals(instance))
        val one = Date(2010, 6, 15)
        val two = Date(2010, 6, 15)
        assertTrue(one.equals(two))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.util.Date.equals].
     */
    @Test
    fun testEqualsItself() {
        assertTrue(instance!!.equals(instance))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.util.Date.equals].
     */
    @Test
    fun testEqualsWhenChanged() {
        val y = 2011
        val m = 6
        val d = 15
        val a = Date(y, m, d)
        val b = Date(y, m, d)
        a.setDay(b.getDay() - 1)
        assertFalse(a.equals(b))
        b.setDay(a.getDay())
        assertTrue(a.equals(b))
        a.setMonth(b.getMonth() - 1)
        assertFalse(a.equals(b))
        b.setMonth(a.getMonth())
        assertTrue(a.equals(b))
        a.setYear(b.getYear() - 1)
        assertFalse(a.equals(b))
        b.setYear(a.getYear())
        assertTrue(a.equals(b))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.util.Date.equals].
     */
    @Test
    fun testEqualsWrongType() {
        val str: Any = String("foobar")
        val dbl: Any = java.lang.Double.valueOf(123.0)
        assertFalse(instance!!.equals(str))
        assertFalse(instance!!.equals(dbl))
    }

    /**
     * Test method for [net.sf.marineapi.nmea.util.Date.getDay].
     */
    @Test
    fun testGetDay() {
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), instance!!.getDay())
    }

    /**
     * Test method for [net.sf.marineapi.nmea.util.Date.getMonth].
     */
    @Test
    fun testGetMonth() {
        assertEquals(cal.get(Calendar.MONTH) + 1, instance!!.getMonth())
    }

    /**
     * Test method for [net.sf.marineapi.nmea.util.Date.getYear].
     */
    @Test
    fun testGetYear() {
        assertEquals(cal.get(Calendar.YEAR), instance!!.getYear())
    }

    /**
     * Test method for [net.sf.marineapi.nmea.util.Date.setDay].
     */
    @Test
    fun testSetDay() {
        val day = 10
        instance!!.setDay(day)
        assertEquals(day, instance!!.getDay())
    }

    /**
     * Test method for [net.sf.marineapi.nmea.util.Date.setDay].
     */
    @Test
    fun testSetDayOutOfBounds() {
        var day = 0
        try {
            instance!!.setDay(day)
            fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            // pass
        }
        day = 32
        try {
            instance!!.setDay(day)
            fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            // pass
        }
    }

    /**
     * Test method for [net.sf.marineapi.nmea.util.Date.setMonth].
     */
    @Test
    fun testSetMonth() {
        val month = 10
        instance!!.setMonth(month)
        assertEquals(month, instance!!.getMonth())
    }

    /**
     * Test method for [net.sf.marineapi.nmea.util.Date.setMonth].
     */
    @Test
    fun testSetMonthOutOfBounds() {
        var month = 0
        try {
            instance!!.setMonth(month)
            fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            // pass
        }
        month = 32
        try {
            instance!!.setMonth(month)
            fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            // pass
        }
    }

    /**
     * Test method for [net.sf.marineapi.nmea.util.Date.setYear].
     */
    @Test
    fun testSetYearFiveDigits() {
        try {
            instance!!.setYear(10000)
            fail("Did not throw IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
            // pass
        }
    }

    /**
     * Test method for [net.sf.marineapi.nmea.util.Date.setYear].
     */
    @Test
    fun testSetYearFourDigit() {
        for (year in 1000..9999) {
            instance!!.setYear(year)
            assertEquals(year, instance!!.getYear())
        }
    }

    /**
     * Test method for [net.sf.marineapi.nmea.util.Date.setYear].
     */
    @Test
    fun testSetYearNegative() {
        try {
            instance!!.setYear(-1)
            fail("Did not throw IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
            // pass
        }
    }

    /**
     * Test method for [net.sf.marineapi.nmea.util.Date.setYear].
     */
    @Test
    fun testSetYearThreeDigits() {
        try {
            instance!!.setYear(100)
            fail("Did not throw IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
            // pass
        }
        try {
            instance!!.setYear(999)
            fail("Did not throw IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
            // pass
        }
    }

    /**
     * Test method for [net.sf.marineapi.nmea.util.Date.setYear].
     */
    @Test
    fun testSetYearTwoDigit() {
        var century = 2000
        for (year in 0..99) {
            instance!!.setYear(year)
            assertEquals(century + year, instance!!.getYear())
            if (year == Date.PIVOT_YEAR) {
                century = 1900
            }
        }
    }

    @Test
    fun testToStringTwoDigitYear() {
        val d = Date(13, 9, 2)
        assertEquals("020913", d.toString())
    }

    @Test
    fun testToStringFourDigitYear() {
        val d = Date(2013, 9, 2)
        assertEquals("020913", d.toString())
    }

    @Test
    fun testToISO8601TwoDigitYear() {
        val d = Date(13, 9, 2)
        assertEquals("2013-09-02", d.toISO8601())
    }

    @Test
    fun testToISO8601FourDigitYear() {
        val d = Date(2013, 9, 2)
        assertEquals("2013-09-02", d.toISO8601())
    }

    @Test
    fun testToISO8601WithTime() {
        val d = Date(2013, 9, 2)
        val t = Time(2, 7, 9.0)
        assertEquals("2013-09-02T02:07:09+00:00", d.toISO8601(t))
    }

    @Test
    fun testToISO8601WithTimeAndZeroZone() {
        val d = Date(2013, 9, 2)
        val t = Time(2, 7, 9.0, 0, 0)
        assertEquals("2013-09-02T02:07:09+00:00", d.toISO8601(t))
    }

    @Test
    fun testToISO8601WithTimeAndPositiveOffset() {
        val d = Date(2013, 9, 2)
        val t = Time(2, 7, 9.0, 2, 0)
        assertEquals("2013-09-02T02:07:09+02:00", d.toISO8601(t))
    }

    @Test
    fun testToISO8601WithTimeAndNegativeOffset() {
        val d = Date(2013, 9, 2)
        val t = Time(2, 7, 9.0, -2, 5)
        assertEquals("2013-09-02T02:07:09-02:05", d.toISO8601(t))
    }
}