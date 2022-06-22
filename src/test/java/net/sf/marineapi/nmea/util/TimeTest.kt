/*
 * TimeTest.java
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

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.*
import java.util.Date

/**
 * @author Kimmo Tuukkanen
 */
class TimeTest {
    private var time: Time? = null

    /**
     * @throws Exception
     */
    @Before
    @Throws(Exception::class)
    fun setUp() {
        time = Time(1, 2, 3.4, 0, 0)
    }

    /**
     * Test method for setTime() and toDate() round-trip.
     */
    @Test
    fun testDateRoundTrip() {
        val now = Date()
        time!!.setTime(now)
        val result = time!!.toDate(now)
        assertEquals(now, result)
        assertEquals(now.time, result.time)
    }

    /**
     * Test method for
     * [Time.toString]
     * .
     */
    @Test
    fun testFormatTimeNoDecimals() {
        val t = Time(1, 2, 3.0)
        assertEquals("010203.000", t.toString())
    }

    /**
     * Test method for
     * [Time.toString]
     * .
     */
    @Test
    fun testFormatTimeWithDecimals() {
        val t = Time(1, 2, 3.456)
        assertEquals("010203.456", t.toString())
    }

    /**
     * Test method for
     * [Time.toString]
     * .
     */
    @Test
    fun testFormatTimeWithOneDecimal() {
        val t = Time(1, 2, 3.4)
        assertEquals("010203.400", t.toString())
    }

    /**
     * Test method for [Time.getHour].
     */
    @Test
    fun testGetHour() {
        assertEquals(1, time!!.getHour().toLong())
    }

    /**
     * Test method for [Time.milliseconds]
     */
    @Test
    fun testGetMilliseconds() {
        time!!.setHour(12)
        time!!.setMinutes(1)
        time!!.setSeconds(1.0)
        assertEquals(43261000, time!!.milliseconds)
        time!!.setHour(18)
        time!!.setMinutes(1)
        time!!.setSeconds(1.123)
        assertEquals(64861123, time!!.milliseconds)
    }

    /**
     * Test method for [Time.getMinutes].
     */
    @Test
    fun testGetMinutes() {
        assertEquals(2, time!!.getMinutes().toLong())
    }

    /**
     * Test method for [Time.getSeconds].
     */
    @Test
    fun testGetSeconds() {
        assertEquals(3.4, time!!.getSeconds(), 0.001)
    }

    /**
     * Test method for []
     * .
     */
    @Test
    fun testParseTimeWithDecimals() {
        val t = Time("010203.456")
        assertEquals(1, t.getHour().toLong())
        assertEquals(2, t.getMinutes().toLong())
        assertEquals(3.456, t.getSeconds(), 0.001)
    }

    /**
     * Test method for []
     * .
     */
    @Test
    fun testParseTimeWithOneDecimal() {
        val t = Time("010203.4")
        assertEquals(1, t.getHour().toLong())
        assertEquals(2, t.getMinutes().toLong())
        assertEquals(3.4, t.getSeconds(), 0.001)
    }

    /**
     * Test method for []
     * .
     */
    @Test
    fun testParseTimeWithoutDecimals() {
        val t = Time("010203")
        assertEquals(1, t.getHour().toLong())
        assertEquals(2, t.getMinutes().toLong())
        assertEquals(3.0, t.getSeconds(), 0.001)
    }

    /**
     * Test method for [Time.setHour].
     */
    @Test
    fun testSetHour() {
        time!!.setHour(12)
        assertEquals(12, time!!.getHour().toLong())
    }

    /**
     * Test method for [Time.setHour].
     */
    @Test
    fun testSetInvalidHour() {
        try {
            time!!.setHour(60)
            fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            // pass
        }
    }

    /**
     * Test method for [Time.setMinutes].
     */
    @Test
    fun testSetInvalidMinutes() {
        try {
            time!!.setMinutes(60)
            fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            // pass
        }
    }

    /**
     * Test method for
     * [Time.setSeconds].
     */
    @Test
    fun testSetInvalidSeconds() {
        try {
            time!!.setSeconds(60.0)
            fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            // pass
        }
    }

    /**
     * Test method for [Time.setMinutes].
     */
    @Test
    fun testSetMinutes() {
        time!!.setMinutes(30)
        assertEquals(30, time!!.getMinutes().toLong())
    }

    /**
     * Test method for [Time.setHour].
     */
    @Test
    fun testSetNegativeHour() {
        try {
            time!!.setHour(-1)
            fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            // pass
        }
    }

    /**
     * Test method for [Time.setMinutes].
     */
    @Test
    fun testSetNegativeMinutes() {
        try {
            time!!.setMinutes(-1)
            fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            // pass
        }
    }

    /**
     * Test method for
     * [Time.setSeconds].
     */
    @Test
    fun testSetNegativeSeconds() {
        try {
            time!!.setSeconds(-0.001)
            fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            // pass
        }
    }

    /**
     * Test method for [Time.setSeconds].
     */
    @Test
    fun testSetSeconds() {
        time!!.setSeconds(45.12345)
        assertEquals(45.12345, time!!.getSeconds(), 0.001)
    }

    /**
     * Test method for
     * [Time.setTime].
     */
    @Test
    fun testSetTime() {
        val now = Date()
        time!!.setTime(now)
        val cal = GregorianCalendar()
        cal.time = now
        val hours = cal[Calendar.HOUR_OF_DAY]
        val minutes = cal[Calendar.MINUTE]
        val fullSeconds = cal[Calendar.SECOND]
        val milliSeconds = cal[Calendar.MILLISECOND]
        val seconds = fullSeconds + milliSeconds / 1000.0
        assertEquals(hours.toLong(), time!!.getHour().toLong())
        assertEquals(minutes.toLong(), time!!.getMinutes().toLong())
        assertEquals(seconds, time!!.getSeconds(), 0.001)
    }

    /**
     * Test method for
     * [Time.toDate].
     */
    @Test
    fun testToDate() {
        val cal: Calendar = GregorianCalendar()
        val result: Calendar = GregorianCalendar()

        // set cal to reference date/time (date portion not significant)
        cal[Calendar.HOUR_OF_DAY] = 0
        cal[Calendar.MINUTE] = 0
        cal[Calendar.SECOND] = 3
        cal[Calendar.MILLISECOND] = 400

        // convert Time to Date and insert to result Calendar for comparison
        val d = time!!.toDate(cal.time)
        result.time = d
        val resultHour = result[Calendar.HOUR_OF_DAY]
        val resultMinute = result[Calendar.MINUTE]
        val resultFullSeconds = result[Calendar.SECOND]
        val resultMilliseconds = result[Calendar.MILLISECOND]
        val resultSeconds = resultFullSeconds + resultMilliseconds / 1000.0

        // Time portion, should match values in Time exactly
        assertEquals(time!!.getHour().toLong(), resultHour.toLong())
        assertEquals(time!!.getMinutes().toLong(), resultMinute.toLong())
        assertEquals(time!!.getSeconds(), resultSeconds, 0.001)

        // Date portion should not have changed
        assertEquals(cal[Calendar.YEAR].toLong(), result[Calendar.YEAR].toLong())
        assertEquals(cal[Calendar.MONTH].toLong(), result[Calendar.MONTH].toLong())
        assertEquals(
            cal[Calendar.DAY_OF_YEAR].toLong(),
            result[Calendar.DAY_OF_YEAR].toLong()
        )
    }

    @Test
    fun testEquals() {
        val a = Time(1, 2, 3.456)
        val b = Time(1, 2, 3.456)
        val c = Time(2, 3, 4.567)
        assertTrue(a == b)
        assertFalse(a == c)
        assertFalse(a == Any())
        assertEquals(a.hashCode().toLong(), b.hashCode().toLong())
    }
}