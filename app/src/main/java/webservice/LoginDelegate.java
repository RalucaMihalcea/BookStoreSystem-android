package webservice;

import java.io.UnsupportedEncodingException;

/**
 * Created by Raluca on 31.10.2017.
 */

public interface LoginDelegate {
    public void onLoginDone(String result) throws UnsupportedEncodingException;
}
