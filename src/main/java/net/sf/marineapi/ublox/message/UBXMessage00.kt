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
package net.sf.marineapi.ublox.message

import net.sf.marineapi.nmea.util.Position
import net.sf.marineapi.nmea.util.Time
import net.sf.marineapi.ublox.util.UbloxNavigationStatus

/**
 * Proprietary u-blox NMEA extension "Lat/Long position data".
 *
 * @author Gunnar Hillert
 */
interface UBXMessage00 : UBXMessage {
    /**
     * @return UTC  time
     */
    fun getUtcTime(): Time

    /**
     * Holds all positional data:
     *
     *
     *  * Latitude (degrees and minutes)
     *  * North/South Indicator
     *  * Longitude (degrees and minutes)
     *  * East/West indicator
     *  * Altitude above user datum ellipsoid
     *
     *
     * @return Position
     */
    fun getPosition(): Position

    /**
     * Navigation Status:
     *
     * •NF = No Fix
     * •DR = Dead reckoning only solution
     * •G2 = Stand alone 2D solution
     * •G3 = Stand alone 3D solution
     * •D2 = Differential 2D solution
     * •D3 = Differential 3D solution
     * •RK = Combined GPS + dead reckoning solution
     * •TT = Time only solution
     *
     * @return UbloxNavigationStatus
     */
    fun getNavigationStatus(): UbloxNavigationStatus?

    /**
     * @return Horizontal accuracy estimate
     */
    fun getHorizontalAccuracyEstimate(): Double

    /**
     * @return Vertical accuracy estimate
     */
    fun getVerticaAccuracyEstimate(): Double

    /**
     * @return Speed over ground
     */
    fun getSpeedOverGround(): Double

    /**
     * @return Course over ground
     */
    fun getCourseOverGround(): Double

    /**
     * @return Vertical velocity (positive downwards)
     */
    fun getVerticaVelocity(): Double

    /**
     * @return Age of differential corrections (blank when DGPS is notused)
     */
    fun getAgeOfDifferentialCorrections(): Int

    /**
     * @return HDOP, Horizontal Dilution of Precision
     */
    fun getHDOP(): Double

    /**
     * @return VDOP, Vertical Dilution of Precision
     */
    fun getVDOP(): Double

    /**
     * @return TDOP, Time Dilution of Precision
     */
    fun getTDOP(): Double

    /**
     * @return Number of satellites used in the navigation solution.
     */
    fun getNumberOfSatellitesUsed(): Int
}