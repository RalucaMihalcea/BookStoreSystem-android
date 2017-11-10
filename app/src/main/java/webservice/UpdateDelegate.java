package webservice;

/**
 * Created by Raluca on 10.11.2017.
 */

public interface UpdateDelegate {

    public void onUpdateDone(String result);

    void onUpdateError(String response);


}
