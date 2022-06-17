/*
 * MMBParser.java
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

import net.sf.marineapi.nmea.sentence.MMBSentenceimport

net.sf.marineapi.nmea.sentence.SentenceIdimport net.sf.marineapi.nmea.sentence.TalkerId
/**
 * MMBParser - Barometer.
 *
 * `$--MMB,x.x,I,x.x,B*hh<CR><LF>`
 *
 * @author Kimmo Tuukkanen
 */
internal class MMBParser : SentenceParser, MMBSentence {
    /**
     * Constructor for parsing MMB.
     *
     * @param nmea MMB sentence String.
     */
    constructor(nmea: String) : super(nmea, SentenceId.MMB)

    /**
     * Constructs a fresh MMB parser.
     *
     * @param tid TalkerId to use in sentence.
     */
    constructor(tid: TalkerId?) : super(tid, SentenceId.MMB, 4) {
        setCharValue(UNIT_INHG, 'I')
        setCharValue(UNIT_BARS, 'B')
    }

    override var inchesOfMercury: Double
        get() = getDoubleValue(PRESSURE_INHG)
        set(inHg) {
            setDoubleValue(PRESSURE_INHG, inHg)
        }
    override var bars: Double
        get() = getDoubleValue(PRESSURE_BARS)
        set(bars) {
            setDoubleValue(PRESSURE_BARS, bars)
        }

    companion object {
        private const val PRESSURE_INHG = 0
        private const val UNIT_INHG = 1
        private const val PRESSURE_BARS = 2
        private const val UNIT_BARS = 3
    }
}