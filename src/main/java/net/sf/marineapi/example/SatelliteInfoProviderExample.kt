/* 
 * SatelliteInfoProviderExample.java
 * Copyright (C) 2013 Kimmo Tuukkanen
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
import net.sf.marineapi.provider.SatelliteInfoProvider
import net.sf.marineapi.provider.event.SatelliteInfoEvent
import net.sf.marineapi.provider.event.SatelliteInfoListener
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream

/**
 * @author Kimmo Tuukkanen
 */
class SatelliteInfoProviderExample(file: File?) : SatelliteInfoListener {
    var reader: SentenceReader
    var provider: SatelliteInfoProvider

    init {

        // create sentence reader and provide input stream
        val stream: InputStream = FileInputStream(file)
        reader = SentenceReader(stream)

        // create provider and register listener
        provider = SatelliteInfoProvider(reader)
        provider.addListener(this)
        reader.start()
    }

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.provider.event.SatelliteInfoListener#providerUpdate(net.sf.marineapi
	 * .provider.event.SatelliteInfoEvent)
	 */
    override fun providerUpdate(event: SatelliteInfoEvent) {
        println("-- GSV report --")
        for (si in event.satelliteInfo) {
            val ptrn = "%s: %d, %d"
            val msg = String.format(
                ptrn, si.id, si!!.azimuth, si
                    .elevation
            )
            println(msg)
        }
        println("-----")
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
                val msg = "Example usage:\njava SatelliteInfoProviderExample nmea.log"
                println(msg)
                System.exit(0)
            }
            try {
                SatelliteInfoProviderExample(File(args[0]))
                println("Running, press CTRL-C to stop..")
            } catch (e: IOException) {
                e.printStackTrace()
                System.exit(1)
            }
        }
    }
}