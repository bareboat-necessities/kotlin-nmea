/*
 * GNSSentence.java
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
package net.sf.marineapi.nmea.sentence

/**
 *
 *
 * GNSS capable receivers will always output this message with the `GN` talker ID.
 * GNSS capable receivers will also output this message with the `GP` and/or `GL`
 * talker ID when using more than one constellation for the position fix.
 *
 *
 *
 * Example:
 * <pre>
 * $GNGNS,014035.00,4332.69262,S,17235.48549,E,RR,13,0.9,25.63,11.24,,*70
 * $GPGNS,014035.00,,,,,,8,,,,1.0,23*76
 * $GLGNS,014035.00,,,,,,5,,,,1.0,23*67
</pre> *
 *
 * @author Kimmo Tuukkanen
 */
interface GNSSentence : PositionSentence, TimeSentence {
    /**
     * GNS operational modes, a mix of [net.sf.marineapi.nmea.util.FaaMode]
     * and [net.sf.marineapi.nmea.util.GpsFixQuality] with some omitted
     * values.
     */
    enum class Mode(private val ch: Char) {
        /** Operating in autonomous mode (automatic 2D/3D).  */
        AUTOMATIC('A'),

        /** Operating in manual mode (forced 2D or 3D).  */
        MANUAL('M'),

        /** Operating in differential mode (DGPS).  */
        DGPS('D'),

        /** Operating in estimating mode (dead-reckoning).  */
        ESTIMATED('E'),

        /** Real Time Kinetic, satellite system used in RTK mode with fixed integers.  */
        RTK('R'),

        /** Float RTK, satellite system used in real time kinematic mode with floating integers.  */
        FRTK('F'),

        /** Precise mode, no deliberate degradation like Selective Availability (NMEA 4.00 and later).  */
        PRECISE('P'),

        /** Simulated data (running in simulator/demo mode).  */
        SIMULATED('S'),

        /** No valid GPS data available.  */
        NONE('N');

        /**
         * Returns the operational mode character.
         *
         * @return char indicator of mode
         */
        fun toChar(): Char {
            return ch
        }

        companion object {
            /**
             * Returns the operational mode for given char.
             * @param ch Char indicator
             * @return Operational mode enum
             */
            fun valueOf(ch: Char): Mode {
                for (m in values()) {
                    if (m.toChar() == ch) {
                        return m
                    }
                }
                return valueOf(ch.toString())
            }
        }
    }

    /**
     * Returns the current GPS mode.
     *
     * @return GPS operational mode
     */
    fun getGpsMode(): Mode

    /**
     * Sets the current GPS mode.
     *
     * @param gps GPS operational mode to set.
     */
    fun setGpsMode(gps: Mode)

    /**
     * Gets the current GLONASS mode.
     *
     * @return GLONASS operational mode
     */
    fun getGlonassMode(): Mode

    /**
     * Sets the current GLONASS mode.
     *
     * @param gns GLONASS operational mode to set.
     */
    fun setGlonassMode(gns: Mode)

    /**
     * Returns all additional operation modes, excluding GPS and GLONASS.
     *
     * @return Array of additional modes or empty array if no modes are set.
     */
    fun getAdditionalModes(): Array<Mode?>

    /**
     * Sets the additional operational modes, leaving GPS and GLONASS modes unaffected
     * or setting them both `NONE` if field is empty.
     *
     * @param modes Array of additional modes to set
     */
    fun setAdditionalModes(vararg modes: Mode)
    /**
     * Get the number of active satellites in use for currect fix.
     *
     * @return Number of satellites 0..99
     */
    /**
     * Sets the number of satellites used for current fix.
     *
     * @param count Number of satellites to set
     * @throws IllegalArgumentException If given count is out of bounds 0..99
     */
    var satelliteCount: Int
    /**
     * Returns the Horizontal Dilution Of Precision, calculated using all available
     * satellites (GPS, GLONASS and any future satellites).
     *
     * @return HDOP value
     */
    /**
     * Sets the Horizontal Dilution Of Precision value, calculated using all available
     * satellites (GPS, GLONASS and any future satellites).
     *
     * @param hdop HDOP value to set
     */
    var horizontalDOP: Double
    /**
     * Returns the orthometric height (MSL reference).
     *
     * @return Height in meters
     */
    /**
     * Sets the orthometric height (MSL reference).
     *
     * @param height Height to set, in meters.
     */
    var orthometricHeight: Double
    /**
     * Returns geoidal separation, the difference between the earth ellipsoid surface
     * and mean-sea-level (geoid) surface defined by the reference datum used in the
     * position solution. Negative values denote mean-sea-level surface below ellipsoid.
     *
     * @return Geoidal separation in meters.
     */
    /**
     * Returns geoidal separation, the difference between the earth ellipsoid surface
     * and mean-sea-level (geoid) surface defined by the reference datum used in the
     * position solution. Negative values denote mean-sea-level surface below ellipsoid.
     *
     * @param separation Geoidal separation in meters.
     */
    var geoidalSeparation: Double
    /**
     * Returns the age of differential GPS data.
     *
     *
     * Notice: field is `null` when Talker ID is `GN`, additional GNS messages
     * follow with `GP` and/or `GL` age of differential data.
     *
     * @return Age of differential data.
     */
    /**
     * Sets the age of differential GPS data.
     *
     *
     * Notice: field is `null` when Talker ID is `GN`, additional GNS messages
     * follow with `GP` and/or `GL` age of differential data.
     *
     * @param age Age to set, negative values will reset the field empty.
     */
    var dgpsAge: Double
    /**
     * Returns the differential reference station ID.
     *
     * @return Station ID, 0000..4095
     */
    /**
     * Sets the differential reference station ID.
     *
     * @param id Station ID to set, 0-4095
     */
    var dgpsStationId: String?
}