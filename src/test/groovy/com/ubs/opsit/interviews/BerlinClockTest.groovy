package com.ubs.opsit.interviews

import spock.lang.Specification

/**
 * Tests {@code BerlinClock}
 */
class BerlinClockTest extends Specification {

    def berlinClock = new BerlinClock();


    def "test convertTime"() {
        when: "i try to convert some time to berlin clock format"
        def berlinClockTimeMidnight = berlinClock.convertTime("00:00:00")
        def berlinClockTimeNoon = berlinClock.convertTime("12:00:00")
        def berlinClockTimeConcrete = berlinClock.convertTime("18:15:34")
        then: "it should convert"
        berlinClockTimeMidnight != "YOOOOOOOOOOOOOOOOOOOOOOO"
        berlinClockTimeMidnight == "Y\nOOOO\nOOOO\nOOOOOOOOOOO\nOOOO"
        berlinClockTimeNoon == "Y\nRROO\nRROO\nOOOOOOOOOOO\nOOOO"
        berlinClockTimeConcrete == "Y\nRRRO\nRRRO\nYYROOOOOOOO\nOOOO"

    }

    def "test top lamp"() {
        when: "i ask for blinking on and off"
        def zeroSeconds = berlinClock.getSecondsPart(0)
        def oneSecond = berlinClock.getSecondsPart(1)
        then: "it should blink"
        zeroSeconds == 'Y'
        oneSecond == 'O'
    }

    def "test second and third row"() {
        when: "i ask for an hour"
        def zero = berlinClock.getHoursPart(0)
        def five = berlinClock.getHoursPart(5)
        def eleven = berlinClock.getHoursPart(11)
        def seventeen = berlinClock.getHoursPart(17)
        def twentyOne = berlinClock.getHoursPart(21)
        then:
        "it should show red in the second row for every 5 hour also it should show red in the third row for every" +
                "hour greater than 5 but less than 10"
        zero == "OOOO\nOOOO"
        five == "ROOO\nOOOO"
        eleven == "RROO\nROOO"
        seventeen == "RRRO\nRROO"
        twentyOne == "RRRR\nROOO"
    }

    def "test fourth and fifth row"() {
        when: "i ask for an minute"
        def zero = berlinClock.getMinutesPart(0)
        def six = berlinClock.getMinutesPart(6)
        def fifteen = berlinClock.getMinutesPart(15)
        def forty = berlinClock.getMinutesPart(40)
        def fiftyNine = berlinClock.getMinutesPart(59)
        then:
        "it should show yellow for in the fourth row for every 5 minutes with red on 15, 30 and 45," +
                "the fifth row should show yellow for every minute greater than 5 but less than 10"
        zero == "OOOOOOOOOOO\nOOOO"
        six == "YOOOOOOOOOO\nYOOO"
        fifteen == "YYROOOOOOOO\nOOOO"
        forty == "YYRYYRYYOOO\nOOOO"
        fiftyNine == "YYRYYRYYRYY\nYYYY"
    }

}
