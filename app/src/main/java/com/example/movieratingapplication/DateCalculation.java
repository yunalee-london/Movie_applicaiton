package com.example.movieratingapplication;

import java.time.LocalDate;
import java.time.Period;

class DateCalculation {

    // Function to print difference in
    static String findDifference(LocalDate rel_date, LocalDate today) {
        if (rel_date.isEqual(today)) {
            return "Time to Premiere: Today";
        } else if (rel_date.isBefore(today)) {
            Period diff = Period.between(rel_date, today);
            if (diff.getYears() == 0 && diff.getMonths() == 0) {
                return "Premiered: " + diff.getDays() + " days ago.";
            } else if (diff.getMonths() == 0 && diff.getDays() == 0) {
                return "Premiered: " + diff.getYears() + " years ago.";
            } else if (diff.getYears() == 0 && diff.getDays() == 0) {
                return "Premiered: " + diff.getMonths() + " months ago.";
            } else if (diff.getYears() == 0) {
                return "Premiered: " + diff.getMonths() + " months, " + diff.getDays() + " days " +
                        "ago.";
            } else if (diff.getMonths() == 0) {
                return "Premiered: " + diff.getYears() + " years, " + diff.getDays() + " days ago.";
            } else if (diff.getDays() == 0) {
                return "Premiered: " + diff.getYears() + " years, " + diff.getMonths() + " months" +
                        " ago.";
            } else {
                return "Premiered: " + diff.getYears() + " years, " + diff.getMonths() + " " +
                        "months, " + diff.getDays() + " days ago.";
            }
        } else {
            Period diff = Period.between(today, rel_date);
            if (diff.getYears() == 0 && diff.getMonths() == 0) {
                return "Time to Premiere: " + diff.getDays() + " days left.";
            } else if (diff.getMonths() == 0 && diff.getDays() == 0) {
                return "Time to Premiere: " + diff.getYears() + " years left.";
            } else if (diff.getYears() == 0 && diff.getDays() == 0) {
                return "Time to Premiere: " + diff.getMonths() + " months left.";
            } else if (diff.getYears() == 0) {
                return "Time to Premiere: " + diff.getMonths() + " months, " + diff.getDays() +
                        " days left.";
            } else if (diff.getMonths() == 0) {
                return "Time to Premiere: " + diff.getYears() + " years, " + diff.getDays() + " " +
                        "days left.";
            } else if (diff.getDays() == 0) {
                return "Time to Premiere: " + diff.getYears() + " years, " + diff.getMonths() +
                        " months left.";
            } else {
                return "Time to Premiere: " + diff.getYears() + " years, " + diff.getMonths() +
                        " months, " + diff.getDays() + " days left.";
            }
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