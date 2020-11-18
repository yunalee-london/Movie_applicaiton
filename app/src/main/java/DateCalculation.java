import java.time.*;
import java.util.*;

class DateCalculation {

    // Function to print difference in
    static void findDifference(LocalDate rel_date, LocalDate today)
    {
        if (rel_date.isBefore(today)) {
            Period diff = Period.between(rel_date, today);
            System.out.printf("Released %d years, %d months, %d days ago.",
                    diff.getYears(), diff.getMonths(), diff.getDays());
        } else {
            Period diff = Period.between(today, rel_date);
            System.out.printf("Release Date: %d years, %d months, %d days left",
                    diff.getYears(), diff.getMonths(), diff.getDays());
        }
        Period diff = Period.between(rel_date, today);
    }

    // Driver Code
    public static void main(String[] args) {
        LocalDate today = LocalDate.now();

        LocalDate rel_date = LocalDate.of(2017, 01, 13);

        // Function Call
        findDifference(rel_date, today);
    }
}