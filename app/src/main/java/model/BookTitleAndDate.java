package model;

import java.util.Calendar;

/**
 * Created by Raluca on 21.02.2018.
 */

public class BookTitleAndDate {
    private String title;
    private Calendar date;

    public BookTitleAndDate(String title, Calendar date) {
        this.title = title;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
