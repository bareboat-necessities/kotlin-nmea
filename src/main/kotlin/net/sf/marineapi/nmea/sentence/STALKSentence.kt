/*
 * STALKSentence.java
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
package net.sf.marineapi.nmea.sentence

/**
 *
 *
 * Raymarine/Autohelm SeaTalk sentence. See
 * [SeaTalk Technical Reference](http://www.thomasknauf.de/seatalk.htm)
 * by Thomas Knauf for more information on the protocol.
 *
 *
 *
 * Example:<br></br>
 * `$STALK,52,A1,00,00*36`
 *
 *
 * @author Kimmo Tuukkanen
 */
interface STALKSentence : Sentence {
    /**
     * Add given parameter in sentence. Sentence field count is increased by one
     * field, notice that adding too many fields may result in invalid sentence.
     *
     * @param param Hex parameter to add
     */
    fun addParameter(param: String?)

    /**
     * Returns the SeaTalk command / datagram type.
     *
     * @return Command String, "00", "01", "02" etc.
     */
    fun getCommand(): String?

    /**
     * Sets the SeaTalk command / datagram type.
     *
     * @param cmd Command String, "00", "01", "02" etc.
     */
    fun setCommand(cmd: String?)

    /**
     * Returns the datagram payload.
     *
     * @return Parameters array containing hex values.
     */
    fun getParameters(): Array<String?>?

    /**
     * Sets the datagram payload.
     *
     * @param params Parameters array containing hex Strings.
     */
    fun setParameters(vararg params: String?)
}