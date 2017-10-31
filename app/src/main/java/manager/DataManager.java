package manager;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import model.User;

/**
 * Created by Raluca on 30.10.2017.
 */

public class DataManager {

    private static DataManager instance = new DataManager();

    public static DataManager getInstance() {
        return instance;
    }

    private String baseAuthStr;

    private DataManager() {
        Log.d("TAG", "DataManager()");
    }

    public DataManager(String baseAuthStr) {
        this.baseAuthStr = baseAuthStr;
    }

    public static void setInstance(DataManager instance) {
        DataManager.instance = instance;
    }

    public String getBaseAuthStr() {
        return baseAuthStr;
    }

    public void setBaseAuthStr(String baseAuthStr) {
        this.baseAuthStr = baseAuthStr;
    }

    public User parseUser(String inputJSON) {

        User user = new User();

        try {
            JSONObject jsonObject = new JSONObject(inputJSON);
            Log.d("TAG", "jsonObject - " + String.valueOf(jsonObject));

            user.setUsername(jsonObject.getString("username"));

            user.setFirstName(jsonObject.getString("firstName"));
            user.setLastName(jsonObject.getString("lastName"));
            user.setType(jsonObject.getString("type"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }
}
