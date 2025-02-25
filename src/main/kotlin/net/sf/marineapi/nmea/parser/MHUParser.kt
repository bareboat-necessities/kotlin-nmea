/*
 * MHUParser.java
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
package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.MHUSentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId

/**
 * MHUParser - Humidity & dew point.
 *
 * `$--MHU,x.x,x.x,x.x,C*hh<CR><LF>`
 *
 * @author Kimmo Tuukkanen
 */
internal class MHUParser : SentenceParser, MHUSentence {
    /**
     * Constructor for parsing MHU sentence.
     *
     * @param nmea MHU sentence String
     */
    constructor(nmea: String) : super(nmea, SentenceId.MHU)

    /**
     * Constructor for fresh MHU sentence.
     *
     * @param tid Talker ID to be used.
     */
    constructor(tid: TalkerId?) : super(tid, SentenceId.MHU, 4) {
        setDewPointUnit('C')
    }

    override fun getRelativeHumidity(): Double {
        return getDoubleValue(RELATIVE_HUMIDITY)
    }

    override fun getAbsoluteHumidity(): Double {
        return getDoubleValue(ABSOLUTE_HUMIDITY)
    }

    override fun getDewPoint(): Double {
        return getDoubleValue(DEW_POINT)
    }

    override fun getDewPointUnit(): Char {
        return getCharValue(DEW_POINT_UNIT)
    }

    override fun setRelativeHumidity(humidity: Double) {
        setDoubleValue(RELATIVE_HUMIDITY, humidity, 1, 1)
    }

    override fun setAbsoluteHumidity(humidity: Double) {
        setDoubleValue(ABSOLUTE_HUMIDITY, humidity, 1, 1)
    }

    override fun setDewPoint(dewPoint: Double) {
        setDoubleValue(DEW_POINT, dewPoint, 1, 1)
    }

    override fun setDewPointUnit(unit: Char) {
        setCharValue(DEW_POINT_UNIT, unit)
    }

    companion object {
        private const val RELATIVE_HUMIDITY = 0
        private const val ABSOLUTE_HUMIDITY = 1
        private const val DEW_POINT = 2
        private const val DEW_POINT_UNIT = 3
    }
}