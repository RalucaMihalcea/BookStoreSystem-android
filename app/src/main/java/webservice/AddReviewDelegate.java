package webservice;

import java.io.UnsupportedEncodingException;

/**
 * Created by Raluca on 30.12.2017.
 */

public interface AddReviewDelegate {
    public void onAddReviewDone(String result) throws UnsupportedEncodingException;

    void onAddReviewError(String response);
}
