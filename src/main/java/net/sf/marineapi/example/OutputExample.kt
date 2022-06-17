package net.sf.marineapi.example

import net.sf.marineapi.nmea.parser.SentenceFactory
import net.sf.marineapi.nmea.sentence.MWVSentence
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.*

/**
 * Demonstrates the usage of sentence parsers for data output.
 *
 * @author Kimmo Tuukkanen
 */
object OutputExample {
    @JvmStatic
    fun main(args: Array<String>) {

        // Create a fresh MWV parser
        val sf: SentenceFactory = SentenceFactory.getInstance()
        val mwv = sf.createParser(TalkerId.II, "MWV") as MWVSentence

        // should output "$IIMWV,,,,,V*36" 
        println(mwv.toSentence())

        // Be sure to set all needed values correctly. For instance, in this
        // example setAngle() and setTrue() have mutual dependency. Likewise,
        // pay attention to set units correctly.
        mwv.angle = 43.7
        mwv.isTrue = true
        mwv.speed = 4.54
        mwv.speedUnit = Units.METER
        mwv.status = DataStatus.ACTIVE

        // should output "$IIMWV,043.7,T,4.5,M,A*39"
        println(mwv.toSentence())
    }
}