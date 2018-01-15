package webservice;

import java.io.UnsupportedEncodingException;

/**
 * Created by Raluca on 14.01.2018.
 */

public interface DeleteFavoriteBookDelegate {
    public void onDeleteFavoriteBookDone(String result) throws UnsupportedEncodingException;
    void onDeleteFavoriteBookError(String response);
}
