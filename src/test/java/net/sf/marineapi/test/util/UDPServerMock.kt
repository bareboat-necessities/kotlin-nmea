package net.sf.marineapi.test.util

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

/**
 * Dummy UDP server repeating single NMEA sentence.
 */
class UDPServerMock : Runnable {
    val TXT = "\$IITXT,1,1,UDP,TEST*0F"
    private var socket: DatagramSocket? = null
    private var running = true

    init {
        Thread(this).start()
    }

    override fun run() {
        try {
            val port = 3810
            val host = InetAddress.getLocalHost()
            val data = TXT.toByteArray()
            socket = DatagramSocket()
            while (running) {
                val packet = DatagramPacket(data, data.size, host, port)
                socket!!.send(packet)
                Thread.sleep(10)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            socket!!.close()
        }
    }

    fun stop() {
        running = false
    }
}