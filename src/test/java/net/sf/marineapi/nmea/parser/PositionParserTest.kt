/*
 * PositionParserTest.java
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
package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.util.Position
import org.junit.Assert.assertEquals

/**
 * Unit tests for PositionParser class.
 *
 * @author Kimmo Tuukkanen
 */
class PositionParserTest {
    private var instance: PositionParser? = null

    /**
     * @throws java.lang.Exception
     */
    @Before
    @Throws(Exception::class)
    fun setUp() {
        instance = object : PositionParser(GLLTest.EXAMPLE, SentenceId.GLL) {}
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.PositionParser.parseHemisphereLat]
     * .
     */
    @Test
    fun testParseHemisphereLat() {
        assertEquals(CompassPoint.NORTH, instance!!.parseHemisphereLat(1))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.PositionParser.parseHemisphereLon]
     * .
     */
    @Test
    fun testParseHemisphereLon() {
        assertEquals(CompassPoint.EAST, instance!!.parseHemisphereLon(3))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.PositionParser.parseDegrees].
     */
    @Test
    fun testParseDegreesWithLatitude() {
        // 6011.552 = 60 deg 11.552 min
        val lat = 60 + 11.552 / 60
        assertEquals(lat, instance!!.parseDegrees(0), 0.000001)
    }

    @Test
    fun testParseDegreesLatitudeWithLeadingZero() {
        // 0611.552 = 6 deg 11.552 min
        val lat = 6 + 11.552 / 60
        val pp: PositionParser = object : PositionParser("\$GPGLL,0611.552,N,0.0,E", SentenceId.GLL) {}
        assertEquals(lat, pp.parseDegrees(0), 0.000001)
    }

    @Test
    fun testParseDegreesLatitudeWithoutLeadingZero() {
        // 611.552 = 6 deg 11.552 min
        val lat = 6 + 11.552 / 60
        val pp: PositionParser = object : PositionParser("\$GPGLL,611.552,N,0.0,E", SentenceId.GLL) {}
        assertEquals(lat, pp.parseDegrees(0), 0.000001)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.PositionParser.parseDegrees].
     */
    @Test
    fun testParseDegreesLongitudeWithLeadingZero() {
        // 02501.941 = 25 deg 1.941 min
        val lon = 25 + 1.941 / 60
        assertEquals(lon, instance!!.parseDegrees(2), 0.000001)
    }

    @Test
    fun testParseDegreesWithoutLeadingZero() {
        // one leading zero omitted
        // 2501.941 = 25 deg 1.941 min
        val lon = 25 + 01.941 / 60
        val pp: PositionParser = object : PositionParser("\$GPGLL,0.0,N,2501.941,E", SentenceId.GLL) {}
        assertEquals(lon, pp.parseDegrees(2), 0.000001)
    }

    @Test
    fun testParseDegreesWithoutLeadingZeros() {
        // two leading zeros omitted
        // 501.941 = 5 deg 1.941 min
        val lon = 5 + 1.941 / 60
        val pp: PositionParser = object : PositionParser("\$GPGLL,0.0,N,501.941,E", SentenceId.GLL) {}
        assertEquals(lon, pp.parseDegrees(2), 0.000001)
    }

    @Test
    fun testParseDegreesWithoutFullDegrees() {
        // full degrees omitted
        // 28.844957 = 0 deg 28.844957 min
        val lon = 0 + 28.844957 / 60
        val pp: PositionParser = object : PositionParser("\$GPGLL,0.0,N,28.844957,E", SentenceId.GLL) {}
        assertEquals(lon, pp.parseDegrees(2), 0.000001)
    }

    @Test
    fun testParseDegreesWithoutFullDegreesAndLeadingZero() {
        // full degrees and a leading zero of minutes omitted
        // 8.844957 = 0 deg 8.844957 min
        val lon = 0 + 8.844957 / 60
        val pp: PositionParser = object : PositionParser("\$GPGLL,0.0,N,8.844957,E", SentenceId.GLL) {}
        assertEquals(lon, pp.parseDegrees(2), 0.000001)
    }

    @Test
    fun testParseDegreesWithoutFullDegreesAndMinutes() {
        // full degrees and full minutes omitted
        // .844957 = 0 deg 0.844957 min
        val lon = 0 + 0.844957 / 60
        val pp: PositionParser = object : PositionParser("\$GPGLL,0.0,N,.844957,E", SentenceId.GLL) {}
        assertEquals(lon, pp.parseDegrees(2), 0.000001)
    }

    @Test
    fun testParseDegreesWithZeroInt() {
        // 0 = 0 deg 0 min
        val pp: PositionParser = object : PositionParser("\$GPGLL,0,N,0,E", SentenceId.GLL) {}
        assertEquals(0, pp.parseDegrees(2), 0.000001)
    }

    @Test
    fun testParseDegreesWithZeroDecimal() {
        // 0.0 = 0 deg 0 min
        val pp: PositionParser = object : PositionParser("\$GPGLL,0.0,N,0.0,E", SentenceId.GLL) {}
        assertEquals(0, pp.parseDegrees(2), 0.000001)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.PositionParser.setLatHemisphere]
     * .
     */
    @Test
    fun testSetLatHemisphere() {
        instance!!.setLatHemisphere(1, CompassPoint.SOUTH)
        assertTrue(instance.toString().contains(",S,"))
        assertEquals(CompassPoint.SOUTH, instance!!.parseHemisphereLat(1))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.PositionParser.setLatitude]
     * .
     */
    @Test
    fun testSetLatitude() {
        // 2501.941
        val lat = 25 + 01.941 / 60
        instance!!.setLatitude(0, lat)
        assertTrue(instance.toString().contains(",02501.941"))
        assertEquals(lat, instance!!.parseDegrees(0), 0.000001)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.PositionParser.setLongitude]
     * .
     */
    @Test
    fun testSetLongitude() {
        // 02801.941
        val lon = 28 + 01.941 / 60
        instance!!.setLongitude(2, lon)
        assertTrue(instance.toString().contains(",02801.941"))
        assertEquals(lon, instance!!.parseDegrees(2), 0.000001)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.PositionParser.setLonHemisphere]
     * .
     */
    @Test
    fun testSetLonHemisphere() {
        instance!!.setLonHemisphere(3, CompassPoint.WEST)
        assertTrue(instance.toString().contains(",W,"))
        assertEquals(CompassPoint.WEST, instance!!.parseHemisphereLon(3))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.PositionParser.setPositionValues]
     */
    @Test
    fun testSetPositionValuesNE() {
        val lat = 60 + 11.552 / 60
        val lon = 25 + 1.941 / 60
        val p2 = Position(lat, lon)
        instance!!.setPositionValues(p2, 0, 1, 2, 3)
        val s2 = instance.toString()
        val p = instance!!.parsePosition(0, 1, 2, 3)
        assertTrue(s2.contains(",6011.552,N,"))
        assertTrue(s2.contains(",02501.941,E,"))
        assertNotNull(p)
        assertEquals(lat, p.latitude, 0.0000001)
        assertEquals(lon, p.longitude, 0.0000001)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.PositionParser.setPositionValues]
     */
    @Test
    fun testSetPositionValuesSW() {
        val lat = -60 - 11.552 / 60
        val lon = -25 - 1.941 / 60
        val p2 = Position(lat, lon)
        instance!!.setPositionValues(p2, 0, 1, 2, 3)
        val s2 = instance.toString()
        val p = instance!!.parsePosition(0, 1, 2, 3)
        assertTrue(s2.contains(",6011.552,S,"))
        assertTrue(s2.contains(",02501.941,W,"))
        assertNotNull(p)
        assertEquals(lat, p.latitude, 0.0000001)
        assertEquals(lon, p.longitude, 0.0000001)
    }
}