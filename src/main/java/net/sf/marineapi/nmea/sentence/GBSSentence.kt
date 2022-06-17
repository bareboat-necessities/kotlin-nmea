/*
 * GBSSentence.java
 * Copyright (C) 2018 Kimmo Tuukkanen
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
package net.sf.marineapi.nmea.sentence

/**
 * GNSS satellite fault detection (RAIM support).
 *
 *
 * Example:<br></br>
 * `$GPGBS,215643.00,0.1190,-0.0872,-0.3320,13,0.9009,-0.8342,2.3281*46`
 *
 * @author Kimmo Tuukkanen
 */
interface GBSSentence : TimeSentence {
    /**
     * Returns the expected error in latitude.
     *
     * @return Error in meters, due to bias, with noise = 0.
     */
    /**
     * Sets the expected error in latitude.
     *
     * @param err Error in meters, due to bias, with noise = 0
     */
    var latitudeError: Double
    /**
     * Returns the expected error in longitude.
     *
     * @return Error in meters, due to bias, with noise = 0.
     */
    /**
     * Sets the expected error in longitude.
     *
     * @param err Error in meters, due to bias, with noise = 0
     */
    var longitudeError: Double
    /**
     * Returns the expected error in altitude.
     *
     * @return Error in meters, due to bias, with noise = 0.
     */
    /**
     * Sets the expected error in altitude.
     *
     * @param err Error in meters, due to bias, with noise = 0
     */
    var altitudeError: Double
    /**
     * Returns the ID of most likely failed satellite.
     *
     * @return Satellite ID number
     */
    /**
     * Sets the ID of most likely failed satellite.
     *
     * @param id Satellite ID to set
     */
    var satelliteId: String?
    /**
     * Returns the probability of missed detection of most likely failed
     * satellite.
     *
     * @return Probability of missed detection
     */
    /**
     * Sets the probability of missed detection of most likely failed satellite.
     *
     * @param probability Probability value
     */
    var probability: Double
    /**
     * Returns the estimate of bias on the most likely failed satellite.
     *
     * @return Bias estimate, in meters.
     */
    /**
     * Sets the estimate of bias on the most likely failed satellite.
     *
     * @param estimate Bias estimate to set, in meters.
     */
    var estimate: Double
    /**
     * Returns the standard deviation of bias estimate.
     *
     * @return Standard deviation of estimate
     */
    /**
     * Sets the standard deviation of bias estimate.
     *
     * @param deviation Standard deviation value to set.
     */
    var deviation: Double
}