package net.sf.marineapi.nmea.parser

import org.junit.Assert.assertEquals

/**
 * @author Kimmo Tuukkanen
 */
class XDRTest {
    /**
     * Test method for [net.sf.marineapi.nmea.parser.XDRParser.XDRParser].
     */
    @Test
    fun testConstructor() {
        val xdr = XDRParser(EXAMPLE)
        assertTrue(xdr.isValid)
        assertEquals("XDR", xdr.sentenceId)
        assertEquals(4, xdr.fieldCount)
        assertEquals('P', xdr.getCharValue(0))
        assertEquals(1.02481, xdr.getDoubleValue(1), 0.00001)
        assertEquals('B', xdr.getCharValue(2))
        assertEquals("Barometer", xdr.getStringValue(3))
    }

    @Test
    fun testConstructorWithThreeFields() {
        val xdr = XDRParser("\$WIXDR,U,014.9,V,")
        val ml: List<Measurement> = xdr.getMeasurements()
        assertEquals(1, ml.size)
        val m: Measurement = ml[0]
        assertEquals("U", m.type)
        assertEquals(14.9, m.getValue(), 0.1)
        assertEquals("V", m.units)
        assertNull(m.name)
    }

    @Test
    fun testConstructorWithMultipleMeasurements() {
        val xdr = XDRParser("\$WIXDR,P,111.1,B,3,C,222.2,C,0,H,333.3,P,2,C,444.4,C,1")
        val ml: List<Measurement> = xdr.getMeasurements()
        assertEquals(4, ml.size)
        val m1: Measurement = ml[0]
        assertEquals("P", m1.type)
        assertEquals(111.1, m1.getValue(), 0.1)
        assertEquals("B", m1.units)
        assertEquals("3", m1.name)
        val m2: Measurement = ml[1]
        assertEquals("C", m2.type)
        assertEquals(222.2, m2.getValue(), 0.1)
        assertEquals("C", m2.units)
        assertEquals("0", m2.name)
        val m3: Measurement = ml[2]
        assertEquals("H", m3.type)
        assertEquals(333.3, m3.getValue(), 0.1)
        assertEquals("P", m3.units)
        assertEquals("2", m3.name)
        val m4: Measurement = ml[3]
        assertEquals("C", m4.type)
        assertEquals(444.4, m4.getValue(), 0.1)
        assertEquals("C", m4.units)
        assertEquals("1", m4.name)
    }

    /**
     * Test method for [)][net.sf.marineapi.nmea.parser.XDRParser.addMeasurement].
     */
    @Test
    fun testAddAnotherMeasurement() {
        val xdr = XDRParser(EXAMPLE)
        val value = Measurement("C", 19.9, "C", "TempAir")
        xdr.addMeasurement(value)
        assertEquals(8, xdr.fieldCount)
        assertTrue(xdr.toString().startsWith("\$IIXDR,P,1.02481,B,Barometer,C,19.9,C,TempAir*"))
    }

    /**
     * Test method for [)][net.sf.marineapi.nmea.parser.XDRParser.addMeasurement].
     */
    @Test
    fun testAddMeasurementToEmpty() {
        val xdr = XDRParser(TalkerId.II)
        val value = Measurement("C", 19.9, "C", "TempAir")
        xdr.addMeasurement(value)
        assertEquals(4, xdr.fieldCount)
        assertTrue(xdr.toString().startsWith("\$IIXDR,C,19.9,C,TempAir*"))
    }

    /**
     * Test method for [)))][net.sf.marineapi.nmea.parser.XDRParser.addMeasurement].
     */
    @Test
    fun testAddMultipleMeasurements() {
        val xdr = XDRParser(TalkerId.II)
        val m1 = Measurement("C", 19.9, "C", "TempAir")
        val m2 = Measurement("P", 1.08, "B", "Barometer")
        xdr.addMeasurement(m1, m2)
        assertEquals(8, xdr.fieldCount)
        assertTrue(xdr.toString().startsWith("\$IIXDR,C,19.9,C,TempAir,P,1.08,B,Barometer*"))
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.XDRParser.getMeasurements].
     */
    @Test
    fun testGetMeasurements() {
        val xdr = XDRParser(EXAMPLE)
        val values: List<Measurement> = xdr.getMeasurements()
        assertEquals(1, values.size)
        val value: Measurement = values[0]
        assertEquals("P", value.type)
        assertEquals(1.02481, value.getValue(), 0.00001)
        assertEquals("B", value.units)
        assertEquals("Barometer", value.name)
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.XDRParser.setMeasurement].
     */
    @Test
    fun testSetMeasurement() {
        val xdr = XDRParser(TalkerId.II)
        val value = Measurement("C", 19.9, "C", "TempAir")
        xdr.setMeasurement(value)
        assertEquals(4, xdr.fieldCount)
        assertTrue(xdr.toString().startsWith("\$IIXDR,C,19.9,C,TempAir*"))
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.XDRParser.setMeasurements].
     */
    @Test
    fun testSetMeasurementAsList() {
        val xdr = XDRParser(TalkerId.II)
        val value = Measurement("C", 19.9, "C", "TempAir")
        val values: MutableList<Measurement> = ArrayList<Measurement>()
        values.add(value)
        xdr.setMeasurements(values)
        assertEquals(4, xdr.fieldCount)
        assertTrue(xdr.toString().startsWith("\$IIXDR,C,19.9,C,TempAir*"))
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.XDRParser.setMeasurements].
     */
    @Test
    fun testSetMeasurementsList() {
        val xdr = XDRParser(TalkerId.II)
        val v1 = Measurement("C", 19.9, "C", "TempAir")
        val v2 = Measurement("P", 1.08, "B", "Barometer")
        val values: MutableList<Measurement> = ArrayList<Measurement>()
        values.add(v1)
        values.add(v2)
        xdr.setMeasurements(values)
        assertEquals(8, xdr.fieldCount)
        assertTrue(xdr.toString().startsWith("\$IIXDR,C,19.9,C,TempAir,P,1.08,B,Barometer*"))
    }

    companion object {
        const val EXAMPLE = "\$IIXDR,P,1.02481,B,Barometer"
    }
}