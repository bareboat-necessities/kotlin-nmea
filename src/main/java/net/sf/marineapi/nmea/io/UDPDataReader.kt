/*
 * UDPDataReader.java
 * Copyright (C) 2010-2014 Kimmo Tuukkanen, Ludovic Drouineau
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

import java.net.DatagramPacketimport

java.net.DatagramSocket java.lang.Exceptionimport java.util.*
/**
 * DataReader implementation using DatagramSocket as data source.
 *
 * @author Kimmo Tuukkanen, Ludovic Drouineau
 */
internal class UDPDataReader
/**
 * Creates a new instance of StreamReader.
 *
 * @param socket DatagramSocket to be used as data source.
 * @param parent SentenceReader dispatching events for this reader.
 */(private val socket: DatagramSocket, parent: SentenceReader?) : AbstractDataReader(parent) {
    private val buffer = ByteArray(1024)
    private val queue: Queue<String> = LinkedList()
    @Throws(Exception::class)
    override fun read(): String? {
        var data: String? = null
        while (true) {
            // If there is a backlog of sentences in the queue, then return the old sentences first so that each packet is uploaded compete.
            if (queue.poll().also { data = it } != null) break

            // Once the backlog is cleared, then read the port, split the packet into sentences,
            // and store the individual sentences in the queue.  Queue will always start empty here.
            data = receive()
            val lines = data!!.split("\\r?\\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            queue.addAll(Arrays.asList(*lines))
        }
        return data
    }

    /**
     * Receive UDP packet and return as String.  Blocks until data is received.
     * Exceptions bubble up to the [AbstractDataReader]
     */
    @Throws(Exception::class)
    private fun receive(): String {
        val pkg = DatagramPacket(buffer, buffer.size)
        socket.receive(pkg)
        return String(pkg.data, 0, pkg.length)
    }
}