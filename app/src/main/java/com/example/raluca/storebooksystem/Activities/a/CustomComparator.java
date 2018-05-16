package com.example.raluca.storebooksystem.Activities.a;

import java.util.Comparator;

import model.BookViewsAndDate;

/**
 * Created by Raluca on 22.02.2018.
 */

public class CustomComparator implements Comparator<BookViewsAndDate> {
    @Override
    public int compare(BookViewsAndDate o1, BookViewsAndDate o2) {
        return o2.getViews()- o1.getViews();
    }
}
