/* 
 * HeadingProviderExample.java
 * Copyright (C) 2012 Kimmo Tuukkanen
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
import net.sf.marineapi.provider.HeadingProvider
import net.sf.marineapi.provider.event.HeadingEvent
import net.sf.marineapi.provider.event.HeadingListener
import net.sf.marineapi.provider.event.ProviderListener

import java.io.*
import kotlin.system.exitProcess

/**
 * Demonstrates the usage of HeadingProvider.
 *
 * @author Kimmo Tuukkanen
 */
class HeadingProviderExample(file: File) : HeadingListener {
    private val reader: SentenceReader
    private val provider: HeadingProvider

    /**
     * Creates a new instance of FileExample
     *
     * @param file File from which to read Checksum data
     */
    init {

        // create sentence reader and provide input stream
        val stream: InputStream = FileInputStream(file)
        reader = SentenceReader(stream)

        // create provider and register listener
        provider = HeadingProvider(reader)
        provider.addListener(this as ProviderListener<HeadingEvent?>)
        reader.start()
    }

    override fun providerUpdate(evt: HeadingEvent) {
        println(evt.toString())
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
                val msg = "Example usage:\njava HeadingProviderExample nmea.log"
                println(msg)
                exitProcess(0)
            }
            try {
                HeadingProviderExample(File(args[0]))
                println("Running, press CTRL-C to stop..")
            } catch (e: IOException) {
                e.printStackTrace()
                exitProcess(1)
            }
        }
    }
}