package com.example.movieratingapplication;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test_timedifference() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = LocalDate.of(2020,11,19);
        String result = DateCalculation.findDifference(today, tomorrow);
        String expected = "Release Date: 1 day left.";
        assertEquals(expected, result);
    }

}
