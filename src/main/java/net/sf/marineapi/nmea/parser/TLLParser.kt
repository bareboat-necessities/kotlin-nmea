package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.*

/**
 * Sent by the Radar (ARPA / MARPA) and handled by the AIS Decoder in the same way as an AIS target
 *
 * TLL - Target Latitude and Longitude
 * 0  1       2 3        4 5    6         7 8 9
 * |  |       | |        | |    |         | | |
 * $--TLL,xx,llll.ll,a,yyyyy.yy,a,c--c,hhmmss.ss,a,a*hh)
 *
 * Field Number:
 * 0-Target Number (0-999)
 * 1-Target Latitude
 * 2-N=north, S=south
 * 3-Target Longitude
 * 4-E=east, W=west
 * 5-Target name
 * 6-UTC of data
 * 7-Status (L=lost, Q=acquisition, T=tracking)
 * 8-R= reference target; null (,,)= otherwise
 * 9-Checksum
 *
 * example (`$RATLL,01,3731.51052,N,02436.00000,E,TEST1,161617.88,T,*0C`
 * @author Epameinondas Pantzopoulos
 */
internal class TLLParser : PositionParser, TLLSentence {
    constructor(nmea: String) : super(nmea, SentenceId.TLL)
    constructor(talker: TalkerId?) : super(talker, SentenceId.TLL, 9)

    override fun getPosition(): Position {
        return parsePosition(LATITUDE, LAT_HEMISPHERE, LONGITUDE, LON_HEMISPHERE)
    }

    override fun setPosition(pos: Position) {
        setPositionValues(pos, LATITUDE, LAT_HEMISPHERE, LONGITUDE, LON_HEMISPHERE)
    }

    override fun getNumber(): Int {
        return getIntValue(NUMBER)
    }

    override fun getName(): String {
        return getStringValue(NAME)
    }

    override fun getStatus(): TargetStatus {
        return TargetStatus.Companion.valueOf(getCharValue(STATUS))
    }

    override fun getReference(): Boolean {
        return getCharValue(REFERENCE) == 'R'
    }

    override fun getTime(): Time {
        val str = getStringValue(UTC_TIME)
        return Time(str)
    }

    override fun setNumber(number: Int) {
        setIntValue(NUMBER, number, 2)
    }

    override fun setName(name: String?) {
        setStringValue(NAME, name)
    }

    override fun setTime(t: Time) {
        var str = String.format("%02d%02d", t.hour, t.minutes)
        val nf = DecimalFormat("00.00")
        val dfs = DecimalFormatSymbols()
        dfs.decimalSeparator = '.'
        nf.decimalFormatSymbols = dfs
        str += nf.format(t.seconds)
        setStringValue(UTC_TIME, str)
    }

    override fun setStatus(status: TargetStatus) {
        setCharValue(STATUS, status.toChar())
    }

    override fun setReference(isReference: Boolean) {
        if (isReference) {
            setCharValue(REFERENCE, 'R')
        }
    }

    companion object {
        private const val NUMBER = 0
        private const val LATITUDE = 1
        private const val LAT_HEMISPHERE = 2
        private const val LONGITUDE = 3
        private const val LON_HEMISPHERE = 4
        private const val NAME = 5
        private const val UTC_TIME = 6
        private const val STATUS = 7
        private const val REFERENCE = 8
    }
}