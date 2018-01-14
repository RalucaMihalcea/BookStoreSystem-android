package webservice;

import java.io.UnsupportedEncodingException;

/**
 * Created by Raluca on 14.01.2018.
 */

public interface SelectFavoriteBooksByUserDelegate {
    public void onSelectFavoriteBooksByUserDone(String result) throws UnsupportedEncodingException;
}
