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
import net.sf.marineapi.nmea.sentence.UBXSentence
import net.sf.marineapi.ublox.message.UBXMessage03
import net.sf.marineapi.ublox.util.UbloxSatelliteInfo
import net.sf.marineapi.ublox.util.UbloxSatelliteStatus

/**
 * Parser implementation for [UBXMessage03] (Satellite Status).
 *
 * @author Gunnar Hillert
 */
internal class UBXMessage03Parser(sentence: UBXSentence) : UBXMessageParser(sentence), UBXMessage03 {
    /**
     * @see UBXMessage03.getNumberOfTrackedSatellites
     */
    override fun getNumberOfTrackedSatellites(): Int {
        return sentence.getUBXFieldIntValue(NUMBER_OF_TRACKED_SATELLITES)
    }

    /**
     * @see UBXMessage03.getSatellites
     */
    override fun getSatellites(): List<UbloxSatelliteInfo> {
        val numberOfTrackedSatellites = this.numberOfTrackedSatellites
        val satellites: MutableList<UbloxSatelliteInfo> = ArrayList(numberOfTrackedSatellites)
        for (i in 0 until numberOfTrackedSatellites) {
            val satelliteId = sentence.getUBXFieldIntValue(UBX_SATELLITE_ID + i * 6)
            val satelliteStatus: UbloxSatelliteStatus = UbloxSatelliteStatus.Companion.fromStatusFlag(
                sentence.getUBXFieldCharValue(
                    SATELLITE_STATUS + i * 6
                )
            )
            var satelliteAzimuth: Int
            satelliteAzimuth = try {
                sentence.getUBXFieldIntValue(SATELLITE_AZIMUTH + i * 6)
            } catch (e: DataNotAvailableException) {
                -1
            }
            var satelliteElevation: Int
            satelliteElevation = try {
                sentence.getUBXFieldIntValue(SATELLITE_ELEVATION + i * 6)
            } catch (e: DataNotAvailableException) {
                -1
            }
            var signalStrength: Int
            signalStrength = try {
                sentence.getUBXFieldIntValue(SATELLITE_SIGNAL_STRENGTH + i * 6)
            } catch (e: DataNotAvailableException) {
                -1
            }
            var satelliteCarrierLockTime: Int
            satelliteCarrierLockTime = try {
                sentence.getUBXFieldIntValue(SATELLIT_CARRIER_LOCK_TIME + i * 6)
            } catch (e: DataNotAvailableException) {
                -1
            }
            val satelliteInfo = UbloxSatelliteInfo(
                satelliteId.toString(),
                satelliteElevation,
                satelliteAzimuth,
                signalStrength,
                satelliteStatus,
                satelliteCarrierLockTime
            )
            satellites.add(satelliteInfo)
        }
        return satellites
    }

    companion object {
        private const val NUMBER_OF_TRACKED_SATELLITES = 1
        private const val UBX_SATELLITE_ID = 2
        private const val SATELLITE_STATUS = 3
        private const val SATELLITE_AZIMUTH = 4
        private const val SATELLITE_ELEVATION = 5
        private const val SATELLITE_SIGNAL_STRENGTH = 6
        private const val SATELLIT_CARRIER_LOCK_TIME = 7
    }
}