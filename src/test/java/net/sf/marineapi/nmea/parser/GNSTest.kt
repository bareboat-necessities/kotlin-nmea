/*
 * GNSTest.java
 * Copyright (C) 2016 Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.sentence.GNSSentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.CompassPoint
import net.sf.marineapi.nmea.util.Position
import net.sf.marineapi.nmea.util.Time
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * GNS parser tests
 *
 * @author Kimmo Tuukkanen
 */
class GNSTest {
    var gns: GNSSentence? = null
    var empty: GNSSentence? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        gns = GNSParser(EXAMPLE)
        assertEquals(TalkerId.GN, gns!!.getTalkerId())
        assertEquals(SentenceId.GNS.name, gns!!.getSentenceId())
        assertEquals(12, gns!!.getFieldCount().toLong())
        empty = GNSParser(TalkerId.GP)
        assertEquals(TalkerId.GP, empty!!.getTalkerId())
        assertEquals(SentenceId.GNS.name, empty!!.getSentenceId())
        assertEquals(12, empty!!.getFieldCount().toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getTime() {
        val t = gns!!.getTime()
        assertEquals(1, t!!.getHour().toLong())
        assertEquals(40, t.getMinutes().toLong())
        assertEquals(35.00, t.getSeconds(), 0.001)
    }

    @Test
    @Throws(Exception::class)
    fun setTime() {
        gns!!.setTime(Time(10, 20, 30.0))
        assertEquals(10, gns!!.getTime()!!.getHour().toLong())
        assertEquals(20, gns!!.getTime()!!.getMinutes().toLong())
        assertEquals(30.0, gns!!.getTime()!!.getSeconds(), 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun getPosition() {

        // 4332.69262,S,17235.48549,E
        val LAT = -(43 + 32.69262 / 60)
        val LON = 172 + 35.48549 / 60
        val p = gns!!.getPosition()
        assertEquals(LAT, p!!.latitude, 0.00001)
        assertEquals(CompassPoint.SOUTH, p.latitudeHemisphere)
        assertEquals(LON, p.longitude, 0.00001)
        assertEquals(CompassPoint.EAST, p.longitudeHemisphere)
    }

    @Test
    @Throws(Exception::class)
    fun setPosition() {
        val LAT = 61.23456
        val LON = 21.23456
        empty!!.setPosition(Position(LAT, LON))
        val p = empty!!.getPosition()
        assertEquals(LAT, p!!.latitude, 0.00001)
        assertEquals(CompassPoint.NORTH, p.latitudeHemisphere)
        assertEquals(LON, p.longitude, 0.00001)
        assertEquals(CompassPoint.EAST, p.longitudeHemisphere)
    }

    @Test
    @Throws(Exception::class)
    fun getGpsMode() {
        assertEquals(GNSSentence.Mode.RTK, gns!!.getGpsMode())
        assertEquals(GNSSentence.Mode.NONE, empty!!.getGpsMode())
    }

    @Test
    @Throws(Exception::class)
    fun setGpsMode() {
        gns!!.setGpsMode(GNSSentence.Mode.DGPS)
        assertTrue(gns.toString().contains(",DR,"))
        assertEquals(GNSSentence.Mode.DGPS, gns!!.getGpsMode())
        assertEquals(GNSSentence.Mode.RTK, gns!!.getGlonassMode())
        assertEquals(0, gns!!.getAdditionalModes()!!.size.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getGlonassMode() {
        assertEquals(GNSSentence.Mode.RTK, gns!!.getGlonassMode())
        assertEquals(GNSSentence.Mode.NONE, empty!!.getGlonassMode())
    }

    @Test
    @Throws(Exception::class)
    fun setGlonassMode() {
        gns!!.setGlonassMode(GNSSentence.Mode.FRTK)
        assertTrue(gns.toString().contains(",RF,"))
        assertEquals(GNSSentence.Mode.FRTK, gns!!.getGlonassMode())
        assertEquals(GNSSentence.Mode.RTK, gns!!.getGpsMode())
        assertEquals(0, gns!!.getAdditionalModes()!!.size.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun setAdditionalModes() {
        gns!!.setAdditionalModes(GNSSentence.Mode.AUTOMATIC, GNSSentence.Mode.ESTIMATED)
        assertTrue(gns.toString().contains(",RRAE,"))
        assertEquals(GNSSentence.Mode.RTK, gns!!.getGpsMode())
        assertEquals(GNSSentence.Mode.RTK, gns!!.getGlonassMode())
    }

    @Test
    @Throws(Exception::class)
    fun getAdditionalModes() {
        gns!!.setAdditionalModes(GNSSentence.Mode.AUTOMATIC, GNSSentence.Mode.ESTIMATED)
        val additional = gns!!.getAdditionalModes()
        assertEquals(2, additional!!.size.toLong())
        assertEquals(GNSSentence.Mode.AUTOMATIC, additional[0])
        assertEquals(GNSSentence.Mode.ESTIMATED, additional[1])
    }

    @Test
    @Throws(Exception::class)
    fun getSatelliteCount() {
        assertEquals(13, gns!!.getSatelliteCount().toLong())
    }

    @Test
    @Throws(Exception::class)
    fun setSatelliteCount() {
        gns!!.setSatelliteCount(8)
        assertTrue(gns.toString().contains(",08,"))
        assertEquals(8, gns!!.getSatelliteCount().toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getHorizontalDOP() {
        assertEquals(0.9, gns!!.getHorizontalDOP(), 0.001)
    }

    @Test
    @Throws(Exception::class)
    fun setHorizontalDOP() {
        gns!!.setHorizontalDOP(0.123)
        assertEquals(0.12, gns!!.getHorizontalDOP(), 0.001)
    }

    @Test
    @Throws(Exception::class)
    fun getOrthometricHeight() {
        assertEquals(25.63, gns!!.getOrthometricHeight(), 0.001)
    }

    @Test
    @Throws(Exception::class)
    fun setOrthometricHeight() {
        gns!!.setOrthometricHeight(12.342)
        assertEquals(12.34, gns!!.getOrthometricHeight(), 0.0001)
    }

    @Test
    @Throws(Exception::class)
    fun getGeoidalSeparation() {
        assertEquals(11.24, gns!!.getGeoidalSeparation(), 0.001)
    }

    @Test
    @Throws(Exception::class)
    fun setGeoidalSeparation() {
        gns!!.setGeoidalSeparation(1.234)
        assertEquals(1.23, gns!!.getGeoidalSeparation(), 0.001)
    }

    @Test
    @Throws(Exception::class)
    fun testDgpsAge() {
        empty!!.setDgpsAge(10.0)
        assertTrue(empty.toString().contains(",10.0,*"))
        assertEquals(10.0, empty!!.getDgpsAge(), 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun testDgpsStationId() {
        gns!!.setDgpsStationId("1234")
        assertTrue(gns.toString().contains(",1234*"))
        assertEquals("1234", gns!!.getDgpsStationId())
    }

    companion object {
        const val EXAMPLE = "\$GNGNS,014035.00,4332.69262,S,17235.48549,E,RR,13,0.9,25.63,11.24,,*70"
    }
}