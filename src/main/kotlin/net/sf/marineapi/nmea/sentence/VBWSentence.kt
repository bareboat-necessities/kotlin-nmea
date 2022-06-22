/*
 * VBWSentence.java
 * Copyright (C) 2015 ESRG LLC.
 * 
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
 * along with NMEA Java Marine API. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.marineapi.nmea.sentence

import net.sf.marineapi.nmea.util.DataStatus

/**
 *
 *
 * VBW Dual Ground/Water Speed Longitudinal, Transverse and Stern Ground/Water
 * Speed with Status.
 *
 *
 *
 * Example:<br></br>
 * `$IIVBW,11.0,02.0,A,06.0,03.0,A,05.3,A,01.0,A*43`
 *
 *
 * @author Jeremy Wilson
 */
interface VBWSentence : Sentence {
    /**
     * Returns the Longitudinal Water Speed.
     *
     * @return Longitudinal Water Speed
     */
    fun getLongWaterSpeed(): Double

    /**
     * Returns the Longitudinal Ground Speed.
     *
     * @return Longitudinal Ground Speed
     */
    fun getLongGroundSpeed(): Double

    /**
     * Returns the Transverse Water Speed.
     *
     * @return Transverse Water Speed
     */
    fun getTravWaterSpeed(): Double

    /**
     * Returns the Transverse Ground Speed.
     *
     * @return Transverse Ground Speed
     */
    fun getTravGroundSpeed(): Double

    /**
     * Returns the Water Speed Status.
     *
     * @return DataStatus Water Speed Status
     */
    fun getWaterSpeedStatus(): DataStatus

    /**
     * Returns the Ground Speed Status.
     *
     * @return DataStatus Ground Speed Status
     */
    fun getGroundSpeedStatus(): DataStatus

    /**
     * Returns the Stern Water Speed.
     *
     * @return Stern Water Speed
     */
    fun getSternWaterSpeed(): Double

    /**
     * Returns the Stern Water Speed Status.
     *
     * @return DataStatus Stern Water Speed Status
     */
    fun getSternWaterSpeedStatus(): DataStatus

    /**
     * Returns the Stern Ground Speed.
     *
     * @return Stern Ground Speed
     * @see .setSternGroundSpeed
     */
    fun getSternGroundSpeed(): Double

    /**
     * Returns the Stern Ground Speed Status.
     *
     * @return DataStatus Stern Ground Speed Status
     */
    fun getSternGroundSpeedStatus(): DataStatus

    /**
     * Sets Longitudinal Water Speed.
     *
     * @param speed Longitudinal Water Speed.
     */
    fun setLongWaterSpeed(speed: Double)

    /**
     * Sets Longitudinal Ground Speed.
     *
     * @param speed Longitudinal Ground Speed.
     */
    fun setLongGroundSpeed(speed: Double)

    /**
     * Sets Transverse Water Speed.
     *
     * @param speed Transverse Water Speed.
     */
    fun setTravWaterSpeed(speed: Double)

    /**
     * Sets Transverse Ground Speed.
     *
     * @param speed Transverse Ground Speed.
     */
    fun setTravGroundSpeed(speed: Double)

    /**
     * Sets Water Speed Status.
     *
     * @param status Water Speed Status
     */
    fun setWaterSpeedStatus(status: DataStatus)

    /**
     * Sets Ground Speed Status.
     *
     * @param status Ground Speed Status
     */
    fun setGroundSpeedStatus(status: DataStatus)

    /**
     * Sets Stern Water Speed.
     *
     * @param speed Stern Water Speed.
     */
    fun setSternWaterSpeed(speed: Double)

    /**
     * Sets Stern Water Speed Status.
     *
     * @param status Stern Water Speed Status.
     */
    fun setSternWaterSpeedStatus(status: DataStatus)

    /**
     * Sets Stern Ground Speed.
     *
     * @param speed Stern Ground Speed.
     */
    fun setSternGroundSpeed(speed: Double)

    /**
     * Sets Stern Ground Speed Status.
     *
     * @param status Stern Ground Speed Status.
     */
    fun setSternGroundSpeedStatus(status: DataStatus)
}