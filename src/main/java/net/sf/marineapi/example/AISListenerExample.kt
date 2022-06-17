/*
 * AISListenerExample.java
 * Copyright (C) 2015 Jozéph Lázár
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

import net.sf.marineapi.ais.event.AbstractAISMessageListener
import net.sf.marineapi.ais.message.AISMessage01
import net.sf.marineapi.nmea.io.SentenceReader
import java.io.*

/**
 * Simple example application that takes a filename as command-line argument and
 * prints position from VDM sentences.
 *
 * @author Jozéph Lázár
 */
class AISListenerExample(file: File?) : AbstractAISMessageListener<AISMessage01>() {
    private val reader: SentenceReader

    /**
     * Creates a new instance of AISExample
     *
     * @param file File containing NMEA data
     */
    init {

        // create sentence reader and provide input stream
        val stream: InputStream = FileInputStream(file)
        reader = SentenceReader(stream)

        // listen for for all AIS VDM messages
        reader.addSentenceListener(this)
        reader.start()
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.ais.event.AbstractAISMessageListener#onMessage(net
	 * .sf.marineapi.ais.sentence.AISMessage)
	 */
    override fun onMessage(msg: AISMessage01?) {
        println(msg!!.mMSI.toString() + ": " + msg.latitudeInDegrees)
        println("onMessage: $msg")
    }

    companion object {
        /**
         * Main method takes one command-line argument, the name of the file to
         * read.
         *
         * @param args Command-line arguments
         */
        @JvmStatic
        fun main(args: Array<String>) {
            if (args.size != 1) {
                println("Example usage:\njava AISExample ais.log")
                System.exit(1)
            }
            try {
                AISListenerExample(File(args[0]))
                println("Running, press CTRL-C to stop..")
            } catch (e: IOException) {
                e.printStackTrace()
                System.exit(1)
            }
        }
    }
}