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
    public void test_time_future() {
        LocalDate today = LocalDate.now();
        LocalDate rel_date = LocalDate.of(2020,11,20);
        String result = DateCalculation.findDifference(rel_date, today);
        String expected = "Time to Premiere: 1 days left.";
        assertEquals(expected, result);
    }
    @Test
    public void test_time_future2() {
        LocalDate today = LocalDate.now();
        LocalDate rel_date = LocalDate.of(2020,12,19);
        String result = DateCalculation.findDifference(rel_date, today);
        String expected = "Time to Premiere: 1 months left.";
        assertEquals(expected, result);
    }
    @Test
    public void test_time_future3() {
        LocalDate today = LocalDate.now();
        LocalDate rel_date = LocalDate.of(2021,12,20);
        String result = DateCalculation.findDifference(rel_date, today);
        String expected = "Time to Premiere: 1 years, 1 months, 1 days left.";
        assertEquals(expected, result);
    }
    @Test
    public void test_time_future4() {
        LocalDate today = LocalDate.now();
        LocalDate rel_date = LocalDate.of(2021,11,19);
        String result = DateCalculation.findDifference(rel_date, today);
        String expected = "Time to Premiere: 1 years left.";
        assertEquals(expected, result);
    }
    @Test
    public void test_time_future5() {
        LocalDate today = LocalDate.now();
        LocalDate rel_date = LocalDate.of(2021,11,21);
        String result = DateCalculation.findDifference(rel_date, today);
        String expected = "Time to Premiere: 1 years, 2 days left.";
        assertEquals(expected, result);
    }
    @Test
    public void test_timedifferenceToday() {
        LocalDate today = LocalDate.now();
        LocalDate rel_date = LocalDate.of(2020,11,19);
        String result = DateCalculation.findDifference(rel_date, today);
        String expected = "Time to Premiere: Today";
        assertEquals(expected, result);
    }
    @Test
    public void test_timedifferencePast1() {
        LocalDate today = LocalDate.now();
        LocalDate rel_date = LocalDate.of(2019,11,19);
        String result = DateCalculation.findDifference(rel_date, today);
        String expected = "Premiered: 1 years ago.";
        assertEquals(expected, result);
    }
    @Test
    public void test_timedifferencePast2() {
        LocalDate today = LocalDate.now();
        LocalDate rel_date = LocalDate.of(2020,11,18);
        String result = DateCalculation.findDifference(rel_date, today);
        String expected = "Premiered: 1 days ago.";
        assertEquals(expected, result);
    }
    @Test
    public void test_timedifferencePast3() {
        LocalDate today = LocalDate.now();
        LocalDate rel_date = LocalDate.of(2020,10,19);
        String result = DateCalculation.findDifference(rel_date, today);
        String expected = "Premiered: 1 months ago.";
        assertEquals(expected, result);
    }
    @Test
    public void test_timedifferencePast4() {
        LocalDate today = LocalDate.now();
        LocalDate rel_date = LocalDate.of(2019,10,18);
        String result = DateCalculation.findDifference(rel_date, today);
        String expected = "Premiered: 1 years, 1 months, 1 days ago.";
        assertEquals(expected, result);
    }
    @Test
    public void test_timedifferencePast5() {
        LocalDate today = LocalDate.now();
        LocalDate rel_date = LocalDate.of(2019,11,18);
        String result = DateCalculation.findDifference(rel_date, today);
        String expected = "Premiered: 1 years, 1 days ago.";
        assertEquals(expected, result);
    }



}
