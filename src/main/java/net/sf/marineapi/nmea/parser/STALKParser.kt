/*
 * STALKParser.java
 * Copyright (C) 2017 Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.sentence.STALKSentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import java.util.*
import kotlin.collections.ArrayList

/**
 * SeaTalk $STALK sentence parser.
 *
 * $STALK,cc,p1,p2..,pn*xx
 *
 * @author Kimmo Tuukkanen
 */
internal class STALKParser : SentenceParser, STALKSentence {
    /**
     * Construct with sentence.
     *
     * @param nmea `$STALK` sentence String.
     */
    constructor(nmea: String) : super(nmea, SentenceId.ALK) {}

    /**
     * Constructor with TalkerId, mostly for compatibility with SentenceFactory.
     * Does not set given talker id, but uses the STALK default 'ST'.
     * Creates a sentence with two fields, command and one parameter.
     *
     * @param tid Any TalkerId may given, but does not affect the resulting
     * "talker id" as sentence identifier is always `$STALK`.
     */
    constructor(tid: TalkerId) : super(TalkerId.ST, SentenceId.ALK, 2) {
        require(tid == TalkerId.ST) { "\$STALK talker id 'ST' is mandatory (got $tid)" }
    }

    override fun getCommand(): String {
        return getStringValue(COMMAND)
    }

    override fun setCommand(cmd: String?) {
        setStringValue(COMMAND, cmd)
    }

    override fun getParameters(): Array<String?> {
        return getStringValues(FIRST_PARAM) as Array<String?>
    }

    override fun setParameters(vararg params: String?) {
        setStringValues(FIRST_PARAM, params)
    }

    override fun addParameter(param: String?) {
        val params: MutableList<String> = ArrayList(Arrays.asList(getParameters()))
        params.add(param)
        setParameters(*params.toTypedArray())
    }

    companion object {
        private const val COMMAND = 0
        private const val FIRST_PARAM = 1
    }
}