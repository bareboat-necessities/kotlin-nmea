/*
 * HTCSentence.java
 * Copyright (C) 2015 Paweł Kozioł, Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.util.DataStatus
import net.sf.marineapi.nmea.util.Direction
import net.sf.marineapi.nmea.util.SteeringMode
import net.sf.marineapi.nmea.util.TurnMode


/**
 * Heading/Track control systems data and commands. HTC provides input to a
 * heading controller to set values, modes and references, while HTD provides
 * output from a heading controller with information about values, modes and
 * references in use
 * [[nuovamarea.com](http://www.nuovamarea.com/files/product%20manuals/nm%20manuals/NM-2C_v1.00.pdf)].
 *
 * @author Paweł Kozioł
 * @see HTDSentence
 */
interface HTCSentence : Sentence {
    /**
     * Returns the override status. Override provides direct control of the
     * steering gear, i.e. a temporary interruption of the selected steering
     * mode. In this period steering is performed by special devices and both
     * *Selected Steering Mode* and *Turn Mode* shall be ignored
     * by the heading/track controller and its computing parts shall operate as
     * if manual steering was selected.
     *
     * @return [DataStatus.ACTIVE] when in use, [DataStatus.VOID]
     * when not in use.
     */
    fun getOverride(): DataStatus?

    /**
     * Returns the commaded rudder angle.
     *
     * @return Rudder angle, in degrees.
     */
    fun getRudderAngle(): Double

    /**
     * Returns the commanded rudder direction.
     *
     * @return [Direction.RIGHT] (starboard) or [Direction.LEFT] (port)
     */
    fun getRudderDirection(): Direction?

    /**
     * Returns the selected steering mode.
     *
     * @return [SteeringMode] enum.
     */
    fun getSteeringMode(): SteeringMode?

    /**
     * Returns the current turn mode. Turn mode defines how the ship changes
     * heading, according to the selected mode values given in fields
     * *Commanded Radius of Turn* or *Commanded Rate of Turn*.
     *
     * @return [TurnMode] enum.
     * @see .getRadiusOfTurn
     * @see .getRateOfTurn
     */
    fun getTurnMode(): TurnMode?

    /**
     * Returns the commanded rudder limit.
     *
     * @return Rudder limit, in degrees.
     */
    fun getRudderLimit(): Double

    /**
     * Returns the commanded off-heading limit.
     *
     * @return Off-heading limit, in degrees.
     */
    fun getOffHeadingLimit(): Double

    /**
     * Returns the commanded radius of turn for heading changes.
     *
     * @return Radius of turn, in nautical miles.
     */
    fun getRadiusOfTurn(): Double

    /**
     * Returns the commanded rate of turn for heading changes.
     *
     * @return Rate of turn, in degrees/min.
     */
    fun getRateOfTurn(): Double

    /**
     * Returns the commanded heading to steer.
     *
     * @return Heading to steer, in degrees.
     */
    fun getHeadingToSteer(): Double

    /**
     * Returns the commanded off-track limit, can be generated if the selected
     * steering mode is [SteeringMode.TRACK_CONTROL].
     *
     * @return Off-track limit, in nautical miles.
     */
    fun getOffTrackLimit(): Double

    /**
     * Returns the commanded track, which represents the course line between two
     * waypoints. It may be altered dynamically in a track-controlled turn
     * along a pre-planned radius.
     *
     * @return Commanded track, in degrees.
     * @see .getRadiusOfTurn
     */
    fun getTrack(): Double

    /**
     * Tells if the heading reference in use is true or magnetic.
     *
     * @return True if true heading, false for magnetic.
     */
    fun isHeadingTrue(): Boolean
}