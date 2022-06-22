package net.sf.marineapi.test.util

import net.sf.marineapi.nmea.parser.SentenceParser
import net.sf.marineapi.nmea.sentence.TalkerId

/**
 * Dummy parser imaginary sentence type, for testing the inheritance of
 * SentenceParser.
 */
class FOOParser : SentenceParser, FOOSentence {
    constructor(s: String?) : super(s!!, "FOO")
    constructor(tid: TalkerId?) : super(tid, "FOO", 3)

    override val valueA: String
        get() = getStringValue(0)
    override val valueB: String
        get() = getStringValue(1)
    override val valueC: String
        get() = getStringValue(2)
}