import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DaysDiff {
    public long daysDiff(Date one, Date two) {
        long difference = (one.getTime() - two.getTime()) / 86400000; //86400000 is the number of milliseconds
        return Math.abs(difference);
    }


    public static void main(String args[]) {
        Date today = new Date();

        Calendar myNextCalendar = Calendar.getInstance();
        myNextCalendar.set(2017,0,17); // 0 is January
        Date releaseDate = myNextCalendar.getTime();

        DaysDiff myObject = new DaysDiff();
        long days = myObject.daysDiff(today, releaseDate);

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, YYYY");
        String todayDate = sdf.format(today);
        String released = sdf.format(releaseDate);

        System.out.println("Released " +days+ " ago");
    }
}