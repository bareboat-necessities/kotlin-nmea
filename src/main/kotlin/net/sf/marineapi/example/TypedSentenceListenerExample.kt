/* 
 * TypedSentenceListenerExample.java
 * Copyright (C) 2014 Kimmo Tuukkanen
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
package net.sf.marineapi.example

import net.sf.marineapi.nmea.event.AbstractSentenceListener
import net.sf.marineapi.nmea.io.SentenceReader
import net.sf.marineapi.nmea.sentence.RMCSentence
import java.io.*
import kotlin.system.exitProcess

/**
 * Example application demonstrating the usage of AbstractSentenceListener.
 *
 * @author Kimmo Tuukkanen
 */
class TypedSentenceListenerExample(file: File) : AbstractSentenceListener<RMCSentence>() {
    private val reader: SentenceReader

    /**
     * Creates a new instance.
     *
     * @param file File containing NMEA data
     */
    init {
        // create reader and provide input stream
        val stream: InputStream = FileInputStream(file)
        reader = SentenceReader(stream)
        // register self as a generic listener
        reader.addSentenceListener(this)
        reader.start()
    }

    override fun sentenceRead(sentence: RMCSentence?) {

        // AbstractSentenceListener requires you to implement this method.
        // Only RMC sentences are broadcasted here as abstract listener is
        // filtering all the others. Thus, no need for checking sentence type
        // and casting. You can also override sentenceRead(SentenceEvent e),
        // but you really shouldn't.
        println(sentence!!.getPosition())
    }

    companion object {
        /**
         * Main method that takes single file name as argument.
         *
         * @param args Command-line arguments
         */
        @JvmStatic
        fun main(args: Array<String>) {
            if (args.size != 1) {
                println("Usage:\njava TypedSentenceListenerExample <file>")
                exitProcess(1)
            }
            try {
                TypedSentenceListenerExample(File(args[0]))
                println("Running, press CTRL-C to stop..")
            } catch (e: IOException) {
                e.printStackTrace()
                exitProcess(1)
            }
        }
    }
}