/*
 * Latitude17.java
 * Copyright (C) 2015 Lázár József
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
package net.sf.marineapi.ais.util

import java.text.DecimalFormat

/**
 * Checks a 17-bit signed integer latitude value for validity.
 *
 */
object Latitude17 {
    private val COORD_FORMAT = DecimalFormat("##0.000000;-##0.000000")
    private const val MINUTE_PART_MULTIPLIER = 60 * 10
    private const val MIN_VALUE = -90 * MINUTE_PART_MULTIPLIER
    private const val MAX_VALUE = 90 * MINUTE_PART_MULTIPLIER
    private const val DEFAULT_VALUE = 91 * MINUTE_PART_MULTIPLIER

    /** Valid range with default value for "no value"  */
    const val RANGE = "[$MIN_VALUE,$MAX_VALUE] + {$DEFAULT_VALUE}"

    /**
     * Converts the latitude value (in 1/10000 minutes) to degrees.
     *
     * @param value Int value to convert
     * @return The latitude value in degrees
     */
    fun toDegrees(value: Int): Double {
        return value.toDouble() / MINUTE_PART_MULTIPLIER.toDouble()
    }

    /**
     * Tells if the given latitude is available, i.e. within expected range.
     *
     * @param value Latitude value to validate
     * @return `true` if available, otherwise `false`.
     */
    fun isAvailable(value: Int): Boolean {
        return value in MIN_VALUE..MAX_VALUE
    }

    /**
     * Tells if the given value is correct, i.e. within expected range and not
     * the no-value.
     *
     * @param value Latitude value to validate
     * @return `true` if correct, otherwise `false`.
     */
    fun isCorrect(value: Int): Boolean {
        return isAvailable(value) || value == DEFAULT_VALUE
    }

    /**
     * Returns the String representation of given latitude value.
     * @param value Value to stringify
     * @return "invalid latitude", "latitude not available" or value formatted
     * in degrees.
     */
    fun toString(value: Int): String {
        return when {
            !isCorrect(value) -> "invalid latitude"
            !isAvailable(value) -> "latitude not available"
            else -> COORD_FORMAT.format(toDegrees(value))
        }
    }
}