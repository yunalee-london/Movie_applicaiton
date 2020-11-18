package com.example.movieratingapplication;

import java.time.*;
import java.util.*;

class DateCalculation {

    // Function to print difference in
    static String findDifference(LocalDate rel_date, LocalDate today)
    {
        if (rel_date.isBefore(today)) {
            Period diff = Period.between(rel_date, today);
            return "Released " + diff.getYears() + "years, " + diff.getMonths() + "months, " + diff.getDays() + "days ago.";

        } else {
            Period diff = Period.between(today, rel_date);
            return "Release date:  " + diff.getYears() + "years, " + diff.getMonths() + "months, " + diff.getDays() + "days ago.";
        }
    }

    // Driver Code
    public static void main(String[] args) {
        LocalDate today = LocalDate.now();

        LocalDate rel_date = LocalDate.of(2017, 01, 13);

        // Function Call
        findDifference(rel_date, today);
    }
}