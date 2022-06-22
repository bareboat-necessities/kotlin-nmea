package net.sf.marineapi.test.util

import net.sf.marineapi.nmea.parser.SentenceParser
import net.sf.marineapi.nmea.sentence.TalkerId

/**
 * Dummy parser simulating AIVDM parser with alternative begin character, for
 * testing the inheritance of SentenceParser and custom parsers registering in
 * SentenceFactory.
 */
class VDMParser : SentenceParser {
    constructor(s: String?) : super(s!!, "VDM") {
        // just like any other parser, begin char comes in String param
    }

    constructor(tid: TalkerId?) : super('!', tid, "VDM", 3) {
        // alternative begin char is set here for empty sentences
    }

    val valueA: String
        get() = getStringValue(0)
    val valueB: String
        get() = getStringValue(1)
    val valueC: String
        get() = getStringValue(2)
}