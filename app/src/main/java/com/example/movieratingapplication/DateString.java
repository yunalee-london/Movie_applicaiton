package com.example.movieratingapplication;

public class DateString {
    private int mYear;
    private int mMonth;
    private int mDate;

    public DateString(String dateString) {}

    public void getInt(String dateString) {
        String[] arrSplit = dateString.split(". ");
        for (int i = 0; i < arrSplit.length; i++) {
            System.out.println(arrSplit[i]);
        }
        int year = Integer.parseInt(arrSplit[0]);
        int month = Integer.parseInt(arrSplit[1]);
        int date = Integer.parseInt(arrSplit[2]);

        mYear = year;
        mMonth = month;
        mDate = date;
    }
}
