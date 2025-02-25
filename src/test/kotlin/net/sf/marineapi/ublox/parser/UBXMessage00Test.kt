/*
 * Copyright (C) 2020 Gunnar Hillert
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
package net.sf.marineapi.ublox.parser

import net.sf.marineapi.nmea.parser.DataNotAvailableException
import net.sf.marineapi.nmea.parser.SentenceFactory
import net.sf.marineapi.nmea.sentence.UBXSentence
import net.sf.marineapi.nmea.util.CompassPoint
import net.sf.marineapi.ublox.message.UBXMessage00
import net.sf.marineapi.ublox.util.UbloxNavigationStatus
import org.junit.Assert.*
import org.junit.Test

/**
 * Test the parsing of messages of type [UBXMessage00].
 *
 * @author Gunnar Hillert
 */
class UBXMessage00Test {
    @Test
    fun testParsinfOfUBXMessage00() {
        val message00 =
            "\$PUBX,00,202920.00,1932.33821,N,15555.72641,W,451.876,G3,3.3,4.0,0.177,0.00,-0.035,,1.11,1.39,1.15,17,0,0*62"
        val sf: SentenceFactory = SentenceFactory.instance
        val sentence = sf.createParser(message00)
        assertTrue("Not a UBXSentence.", sentence is UBXSentence)
        val sentenceId = sentence!!.getSentenceId()
        val messageId = (sentence as UBXSentence?)!!.getMessageId()
        assertEquals("UBX", sentenceId)
        assertEquals("Wrong messageId.", 0, messageId.toLong())
        val ubxMessage00: UBXMessage00 = UBXMessage00Parser((sentence as UBXSentence?)!!)
        assertEquals("Wrong FieldCount.", 20, (ubxMessage00 as UBXMessage00Parser).getFieldCount().toLong())
        assertEquals("Wrong UtcTime.", "202920.000", ubxMessage00.getUtcTime().toString())
        val position = ubxMessage00.getPosition()
        assertNotNull(position)
        assertEquals("Wrong Altitude.", 451.876, position.altitude, 0.1)
        assertEquals("Wrong Latitude.", 19.538970166666665, position.latitude, 0.001)
        assertEquals("Wrong LatitudeHemisphere indicator.", CompassPoint.NORTH, position.latitudeHemisphere)
        assertEquals("Wrong Longitude.", -155.9287735, position.longitude, 0.001)
        assertEquals("Wrong LongitudeHemisphere indicator.", CompassPoint.WEST, position.longitudeHemisphere)
        assertEquals(
            "Wrong UbloxNavigationStatus.",
            UbloxNavigationStatus.STAND_ALONE_3D,
            ubxMessage00.getNavigationStatus()
        )
        assertEquals(
            "Wrong HorizontalAccuracyEstimate.",
            3.3,
            ubxMessage00.getHorizontalAccuracyEstimate(),
            0.01
        )
        assertEquals("Wrong VerticaAccuracyEstimate.", 4.0, ubxMessage00.getVerticaAccuracyEstimate(), 0.01)
        assertEquals("Wrong SpeedOverGround.", 0.177, ubxMessage00.getSpeedOverGround(), 0.01)
        assertEquals("Wrong CourseOverGround.", 0.0, ubxMessage00.getCourseOverGround(), 0.01)
        assertEquals("Wrong VerticaVelocity.", -0.035, ubxMessage00.getVerticaVelocity(), 0.01)
        try {
            ubxMessage00.getAgeOfDifferentialCorrections()
            fail("Expected a DataNotAvailableException to be thrown.")
        } catch (e: DataNotAvailableException) {
            assertTrue(e is DataNotAvailableException)
        }
        assertEquals("Wrong HDOP.", 1.11, ubxMessage00.getHDOP(), 0.01)
        assertEquals("Wrong VDOP.", 1.39, ubxMessage00.getVDOP(), 0.01)
        assertEquals("Wrong TDOP.", 1.15, ubxMessage00.getTDOP(), 0.01)
        assertEquals("Wrong NumberOfSatellitesUsed.", 17, ubxMessage00.getNumberOfSatellitesUsed().toLong())
    }
}