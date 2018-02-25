package webservice;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

/**
 * Created by Raluca on 24.02.2018.
 */

public interface SelectBookViewsAndDateDelegate {
    public void onSelectBookViewsAndDateDone(String result) throws UnsupportedEncodingException, ParseException;
}
