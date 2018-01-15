package webservice;

import java.io.UnsupportedEncodingException;

/**
 * Created by Raluca on 26.11.2017.
 */

public interface SelectBooksDelegate {

    public void onSelectBooksDone(String result) throws UnsupportedEncodingException;
}
