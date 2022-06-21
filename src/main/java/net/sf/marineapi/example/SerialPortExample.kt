/* 
 * SerialPortExample.java
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

import gnu.io.CommPortIdentifier
import gnu.io.SerialPort

import net.sf.marineapi.nmea.event.SentenceEvent
import net.sf.marineapi.nmea.event.SentenceListener
import net.sf.marineapi.nmea.io.SentenceReader
import net.sf.marineapi.nmea.sentence.SentenceValidator
import java.io.BufferedReader
import java.io.IOException

import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

/**
 * Serial port example using GNU/RXTX libraries (see readme.txt). Scans through
 * all COM ports and seeks for NMEA 0183 data with default settings (4800
 * baud, 8 data bits, 1 stop bit and no parity). If NMEA data is found, starts
 * printing out all sentences the device is broadcasting.
 *
 * Notice that on Linux you may need to set read/write privileges on correct
 * port (e.g. `sudo chmod 666 /dev/ttyUSB0`) or add your user in
 * dialout group before running this example.
 *
 * @author Kimmo Tuukkanen
 */
class SerialPortExample : SentenceListener {
    /**
     * Constructor
     */
    init {
        init()
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
        // here we receive each sentence read from the port
        println(event.sentence)
    }

    /**
     * Scan serial ports for NMEA data.
     *
     * @return SerialPort from which NMEA data was found, or null if data was
     * not found in any of the ports.
     */
    private fun getSerialPort(): SerialPort? {
        try {
            val e: Enumeration<*> = CommPortIdentifier.getPortIdentifiers()
            while (e.hasMoreElements()) {
                val id: CommPortIdentifier = e.nextElement() as CommPortIdentifier
                if (id.portType === CommPortIdentifier.PORT_SERIAL) {
                    val sp: SerialPort = id.open("SerialExample", 30) as SerialPort
                    sp.setSerialPortParams(
                        4800, SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1, SerialPort.PARITY_NONE
                    )
                    sp.enableReceiveTimeout(1000)
                    sp.enableReceiveThreshold(0)
                    val `is`: InputStream = sp.inputStream
                    val isr = InputStreamReader(`is`)
                    val buf = BufferedReader(isr)
                    println("Scanning port " + sp.name)

                    // try each port few times before giving up
                    for (i in 0..4) {
                        try {
                            val data: String = buf.readLine()
                            if (SentenceValidator.isValid(data)) {
                                println("NMEA data found!")
                                return sp
                            }
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                        }
                    }
                    `is`.close()
                    isr.close()
                    buf.close()
                }
            }
            println("NMEA data was not found..")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * Init serial port and reader.
     */
    private fun init() {
        try {
            val sp: SerialPort? = getSerialPort()
            if (sp != null) {
                val `is`: InputStream = sp.inputStream
                val sr = SentenceReader(`is`)
                sr.addSentenceListener(this)
                sr.start()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {
        /**
         * Startup method, no arguments required.
         *
         * @param args None
         */
        @JvmStatic
        fun main(args: Array<String>) {
            SerialPortExample()
        }
    }
}