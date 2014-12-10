package com.ubs.opsit.interviews;

/**
 * Generic Groovy Implementation of @see <a href="http://en.wikipedia.org/wiki/Mengenlehreuhr">Berlin Clock</a>
 */
public class BerlinClock implements TimeConverter {
    /**
     * Used to store literals
     */
    def charConstants = [headLampOn: "R", lampOff: "O", smallLightOn: "Y", separator: "\n"]
    /**
     * Used to store numeric constants
     */
    def numericConstants = [rowLength: 4, oneMinuteRowLength: 11, unitGroup: 5, quarterMarkers: [2, 5, 8]]

    /**
     * Converts time into Berlin clock format by separating each and applying a specific method
     * @param aTime given time
     * @return Berlin clock formatted time
     */
    @Override
    public String convertTime(String aTime) {
        return aTime.split(":").with { timeParts ->
            "${getSecondsPart(timeParts[2].toInteger())}" +
                    "$charConstants.separator" +
                    "${getHoursPart(timeParts[0].toInteger())}" +
                    "$charConstants.separator" +
                    "${getMinutesPart(timeParts[1].toInteger())}"
        }
    }

    /**
     * Switches of a lamp in case of odd number
     * @param seconds
     * @return berlin clock seconds part representation
     */
    private char getSecondsPart(int seconds) {
        return seconds % 2 == 1 ? charConstants.lampOff : charConstants.smallLightOn;
    }

    /**
     * Gets the seconds part of Berlin clock time representation
     * @param hours discrete value
     * @return berlin clock hours part representation
     */
    private String getHoursPart(int hours) {
        return "${makeLampRow(numericConstants.rowLength, div(hours), charConstants.headLampOn)}" +
                "$charConstants.separator" +
                "${makeLampRow(numericConstants.rowLength, mod(hours), charConstants.headLampOn)}";
    }

    /**
     * Gets the minutes part of Berlin clock time representation
     * @param minutes discrete value
     * @return berlin clock minutes part representation
     */
    private String getMinutesPart(int minutes) {
        def firstRow = makeLampRow(numericConstants.oneMinuteRowLength, div(minutes), charConstants.smallLightOn).toCharArray();
        numericConstants.quarterMarkers.each { quarterMarkers ->
            if (firstRow[quarterMarkers] == charConstants.smallLightOn) {
                firstRow[quarterMarkers] = charConstants.headLampOn;
            }
        }
        return "$firstRow$charConstants.separator${makeLampRow(numericConstants.rowLength, mod(minutes), charConstants.smallLightOn)}"
    }

    /**
     * Adds characters to both part of the time string row
     * @param rowLength length of the time row
     * @param lampsOn how many times to repeat
     * @param onColor what character to repeat
     * @return
     */
    private String makeLampRow(rowLength, lampsOn, onColor) {
        return "".padLeft(lampsOn, onColor).padRight(rowLength, charConstants.lampOff);
    }

    /**
     * simple closure to convert a value by dividing it to a constant
     */
    def div = { return it / numericConstants.unitGroup as int }

    /**
     * simple closure to convert a value by using modulo operation
     */
    def mod = { return it % numericConstants.unitGroup as int }
}
