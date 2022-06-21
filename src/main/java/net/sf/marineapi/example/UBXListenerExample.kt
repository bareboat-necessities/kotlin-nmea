/*
 * Copyright (C) 2020 Gunnar Hillert
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

import net.sf.marineapi.nmea.io.SentenceReader
import net.sf.marineapi.ublox.event.AbstractUBXMessageListener
import net.sf.marineapi.ublox.message.UBXMessage00
import net.sf.marineapi.ublox.message.UBXMessage03
import java.io.*
import kotlin.system.exitProcess

/**
 * Simple example application that takes a filename as command-line argument and
 * prints the position as well as satellite data from UBX (Also called PUBX) sentences.
 *
 * @author Gunnar Hillert
 */
class UBXListenerExample(file: File) {
    private val reader: SentenceReader

    /**
     * Creates a new instance of the UBX Listener Example
     *
     * @param file File containing NMEA data
     */
    init {

        // create sentence reader and provide input stream
        val stream: InputStream = FileInputStream(file)
        reader = SentenceReader(stream)

        // listen for for all supported UBX messages
        reader.addSentenceListener(UBXMessage00Listener())
        reader.addSentenceListener(UBXMessage03Listener())
        reader.start()
    }

    internal inner class UBXMessage00Listener : AbstractUBXMessageListener<UBXMessage00>() {
        override fun onMessage(msg: UBXMessage00?) {
            val position = msg!!.getPosition()
            println("${position.longitude} : ${position.latitude}")
            println("onMessage: $msg")
        }
    }

    internal inner class UBXMessage03Listener : AbstractUBXMessageListener<UBXMessage03>() {
        override fun onMessage(msg: UBXMessage03?) {
            val numberOfTrackedSatellites = msg!!.getNumberOfTrackedSatellites()
            println(String.format("Tracking %s satellites.", numberOfTrackedSatellites))
            println("onMessage: $msg")
        }
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
                println("Exactly 1 argument is required. Example usage:\njava UBXListenerExample pubx.log")
                exitProcess(1)
            }
            try {
                UBXListenerExample(File(args[0]))
                println("Running, press CTRL-C to stop..")
            } catch (e: IOException) {
                e.printStackTrace()
                exitProcess(1)
            }
        }
    }
}