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
import net.sf.marineapi.nmea.util.Position
import net.sf.marineapi.nmea.util.Time
import org.junit.Before

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
        assertEquals(TalkerId.GN, gns.talkerId)
        assertEquals(SentenceId.GNS.name, gns.sentenceId)
        assertEquals(12, gns.fieldCount)
        empty = GNSParser(TalkerId.GP)
        assertEquals(TalkerId.GP, empty.talkerId)
        assertEquals(SentenceId.GNS.name, empty.sentenceId)
        assertEquals(12, empty.fieldCount)
    }

    @get:Throws(Exception::class)
    @get:Test
    val time: Unit
        get() {
            val t: Time = gns.time
            assertEquals(1, t.getHour())
            assertEquals(40, t.getMinutes())
            assertEquals(35.00, t.getSeconds(), 0.001)
        }

    @Test
    @Throws(Exception::class)
    fun setTime() {
        gns.time = Time(10, 20, 30.0)
        assertEquals(10, gns.time.getHour())
        assertEquals(20, gns.time.getMinutes())
        assertEquals(30.0, gns.time.getSeconds(), 0.1)
    }

    // 4332.69262,S,17235.48549,E
    @get:Throws(Exception::class)
    @get:Test
    val position: Unit
        get() {

            // 4332.69262,S,17235.48549,E
            val LAT = -(43 + 32.69262 / 60)
            val LON = 172 + 35.48549 / 60
            val p: Position = gns.getPosition()
            assertEquals(LAT, p.latitude, 0.00001)
            assertEquals(CompassPoint.SOUTH, p.latitudeHemisphere)
            assertEquals(LON, p.longitude, 0.00001)
            assertEquals(CompassPoint.EAST, p.longitudeHemisphere)
        }

    @Test
    @Throws(Exception::class)
    fun setPosition() {
        val LAT = 61.23456
        val LON = 21.23456
        empty.setPosition(Position(LAT, LON))
        val p: Position = empty.getPosition()
        assertEquals(LAT, p.latitude, 0.00001)
        assertEquals(CompassPoint.NORTH, p.latitudeHemisphere)
        assertEquals(LON, p.longitude, 0.00001)
        assertEquals(CompassPoint.EAST, p.longitudeHemisphere)
    }

    @get:Throws(Exception::class)
    @get:Test
    val gpsMode: Unit
        get() {
            assertEquals(GNSSentence.Mode.RTK, gns.getGpsMode())
            assertEquals(GNSSentence.Mode.NONE, empty.getGpsMode())
        }

    @Test
    @Throws(Exception::class)
    fun setGpsMode() {
        gns.setGpsMode(GNSSentence.Mode.DGPS)
        assertTrue(gns.toString().contains(",DR,"))
        assertEquals(GNSSentence.Mode.DGPS, gns.getGpsMode())
        assertEquals(GNSSentence.Mode.RTK, gns.getGlonassMode())
        assertEquals(0, gns.getAdditionalModes().size)
    }

    @get:Throws(Exception::class)
    @get:Test
    val glonassMode: Unit
        get() {
            assertEquals(GNSSentence.Mode.RTK, gns.getGlonassMode())
            assertEquals(GNSSentence.Mode.NONE, empty.getGlonassMode())
        }

    @Test
    @Throws(Exception::class)
    fun setGlonassMode() {
        gns.setGlonassMode(GNSSentence.Mode.FRTK)
        assertTrue(gns.toString().contains(",RF,"))
        assertEquals(GNSSentence.Mode.FRTK, gns.getGlonassMode())
        assertEquals(GNSSentence.Mode.RTK, gns.getGpsMode())
        assertEquals(0, gns.getAdditionalModes().size)
    }

    @Test
    @Throws(Exception::class)
    fun setAdditionalModes() {
        gns.setAdditionalModes(GNSSentence.Mode.AUTOMATIC, GNSSentence.Mode.ESTIMATED)
        assertTrue(gns.toString().contains(",RRAE,"))
        assertEquals(GNSSentence.Mode.RTK, gns.getGpsMode())
        assertEquals(GNSSentence.Mode.RTK, gns.getGlonassMode())
    }

    @get:Throws(Exception::class)
    @get:Test
    val additionalModes: Unit
        get() {
            gns.setAdditionalModes(GNSSentence.Mode.AUTOMATIC, GNSSentence.Mode.ESTIMATED)
            val additional: Array<GNSSentence.Mode> = gns.getAdditionalModes()
            assertEquals(2, additional.size)
            assertEquals(GNSSentence.Mode.AUTOMATIC, additional[0])
            assertEquals(GNSSentence.Mode.ESTIMATED, additional[1])
        }

    @get:Throws(Exception::class)
    @get:Test
    val satelliteCount: Unit
        get() {
            assertEquals(13, gns.satelliteCount)
        }

    @Test
    @Throws(Exception::class)
    fun setSatelliteCount() {
        gns.satelliteCount = 8
        assertTrue(gns.toString().contains(",08,"))
        assertEquals(8, gns.satelliteCount)
    }

    @get:Throws(Exception::class)
    @get:Test
    val horizontalDOP: Unit
        get() {
            assertEquals(0.9, gns.horizontalDOP, 0.001)
        }

    @Test
    @Throws(Exception::class)
    fun setHorizontalDOP() {
        gns.horizontalDOP = 0.123
        assertEquals(0.12, gns.horizontalDOP, 0.001)
    }

    @get:Throws(Exception::class)
    @get:Test
    val orthometricHeight: Unit
        get() {
            assertEquals(25.63, gns.orthometricHeight, 0.001)
        }

    @Test
    @Throws(Exception::class)
    fun setOrthometricHeight() {
        gns.orthometricHeight = 12.342
        assertEquals(12.34, gns.orthometricHeight, 0.0001)
    }

    @get:Throws(Exception::class)
    @get:Test
    val geoidalSeparation: Unit
        get() {
            assertEquals(11.24, gns.geoidalSeparation, 0.001)
        }

    @Test
    @Throws(Exception::class)
    fun setGeoidalSeparation() {
        gns.geoidalSeparation = 1.234
        assertEquals(1.23, gns.geoidalSeparation, 0.001)
    }

    @Test
    @Throws(Exception::class)
    fun testDgpsAge() {
        empty.dgpsAge = 10
        assertTrue(empty.toString().contains(",10.0,*"))
        assertEquals(10, empty.dgpsAge, 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun testDgpsStationId() {
        gns.dgpsStationId = "1234"
        assertTrue(gns.toString().contains(",1234*"))
        assertEquals("1234", gns.dgpsStationId)
    }

    companion object {
        const val EXAMPLE = "\$GNGNS,014035.00,4332.69262,S,17235.48549,E,RR,13,0.9,25.63,11.24,,*70"
    }
}