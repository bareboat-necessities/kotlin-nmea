/* 
 * FileExample.java
 * Copyright (C) 2011 Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.event.SentenceEvent
import net.sf.marineapi.nmea.event.SentenceListener
import net.sf.marineapi.nmea.io.SentenceReader
import net.sf.marineapi.nmea.sentence.GGASentence
import net.sf.marineapi.nmea.sentence.SentenceId
import java.io.*

/**
 * Simple example application that takes a filename as command-line argument and
 * prints Position from received GGA sentences.
 *
 * @author Kimmo Tuukkanen
 */
class FileExample(file: File?) : SentenceListener {
    private val reader: SentenceReader

    /**
     * Creates a new instance of FileExample
     *
     * @param file File containing NMEA data
     */
    init {

        // create sentence reader and provide input stream
        val stream: InputStream = FileInputStream(file)
        reader = SentenceReader(stream)

        // register self as a listener for GGA sentences
        reader.addSentenceListener(this, SentenceId.GGA)
        reader.start()
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.event.SentenceListener#readingPaused()
	 */
    override fun readingPaused() {
        println("-- Paused --")
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.event.SentenceListener#readingStarted()
	 */
    override fun readingStarted() {
        println("-- Started --")
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.event.SentenceListener#readingStopped()
	 */
    override fun readingStopped() {
        println("-- Stopped --")
    }

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.event.SentenceListener#sentenceRead(net.sf.marineapi
	 * .nmea.event.SentenceEvent)
	 */
    override fun sentenceRead(event: SentenceEvent) {

        // Safe to cast as we are registered only for GGA updates. Could
        // also cast to PositionSentence if interested only in position data.
        // When receiving all sentences without filtering, you should check the
        // sentence type before casting (e.g. with Sentence.getSentenceId()).
        val s = event.sentence as GGASentence

        // Do something with sentence data..
        println(s.position)
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
                println("Example usage:\njava FileExample nmea.log")
                System.exit(1)
            }
            try {
                FileExample(File(args[0]))
                println("Running, press CTRL-C to stop..")
            } catch (e: IOException) {
                e.printStackTrace()
                System.exit(1)
            }
        }
    }
}