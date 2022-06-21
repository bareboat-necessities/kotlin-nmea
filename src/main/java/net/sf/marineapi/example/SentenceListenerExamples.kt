/* 
 * SentenceListenerExamples.java
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
import net.sf.marineapi.nmea.event.SentenceEvent
import net.sf.marineapi.nmea.event.SentenceListener
import net.sf.marineapi.nmea.io.SentenceReader
import net.sf.marineapi.nmea.sentence.*
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import kotlin.system.exitProcess

/**
 * Demonstrates the different ways to use SentenceListeners.
 *
 * @author Kimmo Tuukkanen
 */
class SentenceListenerExamples(file: File?) {
    /**
     * Constructor
     */
    init {
        val stream: InputStream = FileInputStream(file)
        val reader = SentenceReader(stream)
        reader.addSentenceListener(GSAListener())
        reader.addSentenceListener(MultiSentenceListener())
        reader.addSentenceListener(SingleSentenceListener(), SentenceId.GSV)
        reader.start()
    }

    inner class MultiSentenceListener : SentenceListener {
        override fun readingPaused() {}
        override fun readingStarted() {}
        override fun readingStopped() {}
        override fun sentenceRead(event: SentenceEvent) {
            val s = event.sentence
            if ("GLL" == s.getSentenceId()) {
                val gll = s as GLLSentence
                println("GLL position: " + gll.getPosition())
            } else if ("GGA" == s.getSentenceId()) {
                val gga = s as GGASentence
                println("GGA position: " + gga.getPosition())
            }
        }
    }

    inner class SingleSentenceListener : SentenceListener {
        override fun readingPaused() {}
        override fun readingStarted() {}
        override fun readingStopped() {}
        override fun sentenceRead(event: SentenceEvent) {
            val gsv = event.sentence as GSVSentence
            println("GSV satellites in view: " + gsv.getSatelliteCount())
        }
    }

    inner class GSAListener : AbstractSentenceListener<GSASentence>() {
        override fun sentenceRead(gsa: GSASentence?) {
            println("GSA position DOP: " + gsa!!.getPositionDOP())
        }
    }

    companion object {
        /**
         * @param args File to read
         */
        @JvmStatic
        fun main(args: Array<String>) {
            if (args.size != 1) {
                println("Example usage:\njava FileExample nmea.log")
                exitProcess(1)
            }
            try {
                SentenceListenerExamples(File(args[0]))
                println("Running, press CTRL-C to stop..")
            } catch (e: IOException) {
                e.printStackTrace()
                exitProcess(1)
            }
        }
    }
}