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

import net.sf.marineapi.nmea.parser.PositionParser
import net.sf.marineapi.nmea.sentence.UBXSentence
import net.sf.marineapi.nmea.util.*
import net.sf.marineapi.ublox.message.UBXMessage00
import net.sf.marineapi.ublox.util.UbloxNavigationStatus

/**
 * Parser implementation for [UBXMessage00] (Lat/Long Position Data).
 *
 * @author Gunnar Hillert
 */
internal class UBXMessage00Parser(sentence: UBXSentence) : UBXMessageParser(sentence), UBXMessage00 {

    override fun getUtcTime(): Time {
        val str = getStringValue(UTC_TIME)
        return Time(str)
    }

    override fun getPosition(): Position {
        val latitudeField = sentence.getUBXFieldStringValue(LATITUDE)
        val latitudeHemisphereIndicatorField = sentence.getUBXFieldCharValue(LAT_HEMISPHERE)
        val longitudeField = sentence.getUBXFieldStringValue(LONGITUDE)
        val longitudeHemisphereIndicatorField = sentence.getUBXFieldCharValue(LON_HEMISPHERE)
        val position: Position = PositionParser.parsePosition(
            latitudeField, latitudeHemisphereIndicatorField,
            longitudeField, longitudeHemisphereIndicatorField
        )
        val altitude = sentence.getUBXFieldDoubleValue(ALTITUDE)
        position.altitude = altitude
        return position
    }

    override fun getNavigationStatus(): UbloxNavigationStatus? {
        return UbloxNavigationStatus.fromNavigationStatusCode(
            sentence.getUBXFieldStringValue(NAVIGATION_STATUS)
        )
    }

    override fun getHorizontalAccuracyEstimate(): Double {
        return sentence.getUBXFieldDoubleValue(HORIZONTAL_ACCURACY_ESTIMATE)
    }

    override fun getVerticaAccuracyEstimate(): Double {
        return sentence.getUBXFieldDoubleValue(VERTICAL_ACCURACY_ESTIMATE)
    }

    override fun getSpeedOverGround(): Double {
        return sentence.getUBXFieldDoubleValue(SPEED_OVER_GROUND)
    }

    override fun getCourseOverGround(): Double {
        return sentence.getUBXFieldDoubleValue(COURSE_OVER_GROUND)
    }

    override fun getVerticaVelocity(): Double {
        return sentence.getUBXFieldDoubleValue(VERTICA_VELOCITY)
    }

    override fun getAgeOfDifferentialCorrections(): Int {
        return sentence.getUBXFieldIntValue(AGE_OF_DIFFERENTIAL_CORRECTIONS)
    }

    override fun getHDOP(): Double {
        return sentence.getUBXFieldDoubleValue(HDOP)
    }

    override fun getVDOP(): Double {
        return sentence.getUBXFieldDoubleValue(VDOP)
    }

    override fun getTDOP(): Double {
        return sentence.getUBXFieldDoubleValue(TDOP)
    }

    override fun getNumberOfSatellitesUsed(): Int {
        return sentence.getUBXFieldIntValue(NUMBER_OF_SATELLITES_USED)
    }

    companion object {
        private const val UTC_TIME = 1
        private const val LATITUDE = 2
        private const val LAT_HEMISPHERE = 3
        private const val LONGITUDE = 4
        private const val LON_HEMISPHERE = 5
        private const val ALTITUDE = 6
        private const val NAVIGATION_STATUS = 7
        private const val HORIZONTAL_ACCURACY_ESTIMATE = 8
        private const val VERTICAL_ACCURACY_ESTIMATE = 9
        private const val SPEED_OVER_GROUND = 10
        private const val COURSE_OVER_GROUND = 11
        private const val VERTICA_VELOCITY = 12
        private const val AGE_OF_DIFFERENTIAL_CORRECTIONS = 13
        private const val HDOP = 14
        private const val VDOP = 15
        private const val TDOP = 16
        private const val NUMBER_OF_SATELLITES_USED = 17
    }
}