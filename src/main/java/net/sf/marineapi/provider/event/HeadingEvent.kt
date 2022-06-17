/* 
 * HeadingEvent.java
 * Copyright (C) 2012 Kimmo Tuukkanen
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
package net.sf.marineapi.provider.event

import net.sf.marineapi.nmea.sentence.HeadingSentence

/**
 * HeadingProvider event, reports the current magnetic/true heading of vessel in
 * degrees.
 *
 * @author Kimmo Tuukkanen
 * @see net.sf.marineapi.provider.HeadingProvider
 */
class HeadingEvent(source: Any?, s: HeadingSentence) : ProviderEvent(source) {
    private val heading: Double
    private val isTrue: Boolean

    /**
     * Creates a new heading event.
     *
     * @param source The object that sends the event.
     * @param s HeadingSentence that triggered the event.
     */
    init {
        heading = s.heading
        isTrue = s.isTrue
    }

    /**
     * Returns the current heading.
     *
     * @return Heading in degrees.
     */
    fun getHeading(): Double {
        return heading
    }

    /**
     * Tells if the heading is relative to true or magnetic north.
     *
     * @return true if true heading, otherwise false (magnetic).
     */
    fun isTrue(): Boolean {
        return isTrue
    }

    /*
	 * (non-Javadoc)
	 * @see java.util.EventObject#toString()
	 */
    override fun toString(): String {
        return "[" + getHeading() + ", " + (if (isTrue()) "T" else "M") + "]"
    }

    companion object {
        private const val serialVersionUID = 5706774741081575448L
    }
}