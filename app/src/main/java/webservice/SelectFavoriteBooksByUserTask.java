package webservice;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Raluca on 14.01.2018.
 */

public class SelectFavoriteBooksByUserTask extends AsyncTask<String, String, String> implements CredentialInterface {
    private SelectFavoriteBooksByUserDelegate selectFavoriteBooksByUserDelegate;
    private String username;

    @Override
    protected String doInBackground(String... params) {
        try {
            return callSelectFavoriteBooksByUserService();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String callSelectFavoriteBooksByUserService() throws IOException, JSONException {
        String modelString = BASE_URL + "favoriteBook/selectFavoriteBookForUser?username=" + username;
        Uri uri = Uri.parse(modelString).buildUpon().build();
        HttpURLConnection connection = (HttpURLConnection) new URL(uri.toString()).openConnection();

        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(100000000);
        connection.setReadTimeout(2000000000);

        JSONObject object = new JSONObject();
        object.put("username", username);


        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        out.write(object.toString());
        out.close();

        StringBuilder sb = new StringBuilder();
        int httpResult = connection.getResponseCode();
        if (httpResult == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            System.out.println("" + sb.toString());
        } else {
            return "";
        }
        return sb.toString();

    }

    public SelectFavoriteBooksByUserTask(String username) {

        this.username = username;
        String modelString = BASE_URL + "favoriteBook/selectFavoriteBookForUser?username=" + username;
        Uri uri = Uri.parse(modelString).buildUpon().build();
        this.execute(uri.toString());
    }

    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);
        String response = String.valueOf(o);

        if (selectFavoriteBooksByUserDelegate != null) {
            try {
                selectFavoriteBooksByUserDelegate.onSelectFavoriteBooksByUserDone(response);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public SelectFavoriteBooksByUserDelegate getDelegate() {
        return selectFavoriteBooksByUserDelegate;
    }

    public void setSelectFavoriteBooksByUserDelegate(SelectFavoriteBooksByUserDelegate selectFavoriteBooksByUserDelegate) {
        this.selectFavoriteBooksByUserDelegate = selectFavoriteBooksByUserDelegate;
    }
}
