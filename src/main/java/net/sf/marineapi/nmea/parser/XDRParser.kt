/*
 * XDRParser.java
 * Copyright (C) 2013 Robert Huitema, Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.sentence.XDRSentence
import net.sf.marineapi.nmea.util.Measurement
import kotlin.collections.ArrayList

/**
 *
 *
 * Transducer measurements.
 * <pre>`1 2   3 4            n
 * | |   | |            |
 * $--XDR,a,x.x,a,c--c, ..... *hh<CR><LF>
`</pre> *
 *
 *
 * Where:
 *
 *  1. Transducer Type
 *  1. Measurement Data
 *  1. Units of measurement
 *  1. Name of transducer
 *
 *
 *
 * There may be any number of quadruplets like this, each describing a
 * sensor. The last field will be a checksum as usual.
 *
 * @author Robert Huitema, Kimmo Tuukkanen
 */
internal class XDRParser : SentenceParser, XDRSentence {
    /**
     * Creates new instance of XDRParser.
     *
     * @param nmea XDR sentence string
     */
    constructor(nmea: String) : super(nmea, SentenceId.XDR)

    /**
     * Creates XDR parser with empty sentence.
     *
     * @param talker TalkerId to set
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.XDR, DATA_SET_LENGTH)

    override fun addMeasurement(vararg m: Measurement?) {
        val ms = getMeasurements()
        ms.addAll(listOf(*m) as Collection<Measurement>)
        setMeasurements(ms)
    }

    override fun getMeasurements(): MutableList<Measurement> {
        val result = ArrayList<Measurement>()
        var i = 0
        while (i < getFieldCount()) {
            val value = fetchValues(i)
            if (!value.isEmpty) {
                result.add(value)
            }
            i += DATA_SET_LENGTH
        }
        return result
    }

    override fun setMeasurement(m: Measurement?) {
        setFieldCount(DATA_SET_LENGTH)
        insertValues(TYPE_INDEX, m)
    }

    override fun setMeasurements(measurements: List<Measurement>) {
        setFieldCount(measurements.size * DATA_SET_LENGTH)
        var i = 0
        for (m in measurements) {
            insertValues(i, m)
            i += DATA_SET_LENGTH
        }
    }

    /**
     * Fetch data set starting at given index.
     *
     * @param i Start position of data set, i.e. index of first data field.
     * @return XDRValue object
     */
    private fun fetchValues(i: Int): Measurement {
        val m = Measurement()
        if (hasValue(i)) {
            m.type = getStringValue(i)
        }
        if (hasValue(i + VALUE_INDEX)) {
            m.value = getDoubleValue(i + VALUE_INDEX)
        }
        if (hasValue(i + UNITS_INDEX)) {
            m.units = getStringValue(i + UNITS_INDEX)
        }
        if (hasValue(i + NAME_INDEX)) {
            m.name = getStringValue(i + NAME_INDEX)
        }
        return m
    }

    /**
     * Inserts the given data set beginning at given index. Before inserting,
     * make sure the sentence has enough fields for it.
     *
     * @param i Start position of data set, i.e. index of first data field.
     * @param m XDR data set to insert
     */
    private fun insertValues(i: Int, m: Measurement?) {
        if (m != null) {
            setStringValue(i, m.type)
            setDoubleValue(i + VALUE_INDEX, m.value!!)
            setStringValue(i + UNITS_INDEX, m.units)
            setStringValue(i + NAME_INDEX, m.name)
        }
    }

    companion object {
        // length of each data set is 4 fields
        private const val DATA_SET_LENGTH = 4

        // data set field indices, relative to first field of each set
        private const val TYPE_INDEX = 0
        private const val VALUE_INDEX = 1
        private const val UNITS_INDEX = 2
        private const val NAME_INDEX = 3
    }
}