package webservice;

import java.io.UnsupportedEncodingException;

/**
 * Created by Raluca on 14.01.2018.
 */

public interface AddFavoriteBookDelegate {

    public void onAddFavoriteBookDone(String result) throws UnsupportedEncodingException;

    void onAddFavoriteBookError(String response);
}
