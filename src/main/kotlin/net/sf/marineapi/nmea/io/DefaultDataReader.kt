/*
 * DefaultDataReader.java
 * Copyright (C) 2010-2014 Kimmo Tuukkanen
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
package net.sf.marineapi.nmea.io

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

/**
 * The default data reader implementation using InputStream as data source.
 *
 * @author Kimmo Tuukkanen
 */
internal class DefaultDataReader(source: InputStream, parent: SentenceReader?) : AbstractDataReader(parent) {
    private val buffer: BufferedReader

    /**
     * Creates a new instance of DefaultDataReader.
     *
     * @param source InputStream to be used as data source.
     * @param parent SentenceReader dispatching events for this reader.
     */
    init {
        buffer = BufferedReader(InputStreamReader(source))
    }

    @Throws(Exception::class)
    override fun read(): String? {
        return if (buffer.ready()) buffer.readLine() else null
    }
}