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
package net.sf.marineapi.ublox.util

import net.sf.marineapi.nmea.util.SatelliteInfo
import net.sf.marineapi.ublox.message.UBXMessage03

/**
 * Extends [SatelliteInfo] to provide additional properties supported by
 * the proprietary u-blox NMEA extension [UBXMessage03].
 *
 * @author Gunnar Hillert
 */
class UbloxSatelliteInfo(
    id: String?, elevation: Int, azimuth: Int, noise: Int,
    private val satelliteStatus: UbloxSatelliteStatus?, private val satelliteCarrierLockTime: Int
) : SatelliteInfo(id, elevation, azimuth, noise) {
    /**
     * Satellite carrier lock time (range: 0-64)
     *
     *
     *  *  0 = code lock only
     *  * 64 = lock for 64 seconds or more
     *
     *
     * @return Numeric value 0-64
     */
    fun getSatelliteCarrierLockTime(): Int {
        return satelliteCarrierLockTime
    }

    /**
     * @return The [UbloxSatelliteStatus].
     */
    fun getSatelliteStatus(): UbloxSatelliteStatus? {
        return satelliteStatus
    }

    override fun toString(): String {
        return super.toString() + " " + String.format(
            "UbloxSatelliteInfo [satelliteStatus=%s, satelliteCarrierLockTime=%s sec]", satelliteStatus,
            satelliteCarrierLockTime
        )
    }
}