package webservice;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import manager.DataManager;

/**
 * Created by Raluca on 22.02.2018.
 */

public class SelectAllBookViewsAndDateTask extends AsyncTask<String, String, String> implements CredentialInterface {

    private SelectAllBookViewsAndDateDelegate selectAllBookViewsAndDateDelegate;
    private String username;

    @Override
    protected String doInBackground(String... params) {
        try {
            return callSelectAllBookViewsAndDateService();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String callSelectAllBookViewsAndDateService() throws IOException, JSONException {
        //String modelString = BASE_URL + "bookViewsAndDate/searchBooksViewsAndDateByUsername?username=" + username;
        String modelString = BASE_URL + "bookViewsAndDate/allBookViewsAndDate";
        Uri uri = Uri.parse(modelString).buildUpon().build();
        //Uri uri = Uri.parse(BASE_URL).buildUpon().appendPath("food/all").build();
        HttpURLConnection connection = (HttpURLConnection) new URL(uri.toString()).openConnection();

        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
       // connection.setRequestMethod("POST");
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(100000000);
        connection.setReadTimeout(100000000);

//        JSONObject object = new JSONObject();
//        object.put("username", username);

        connection.addRequestProperty("Authorization", DataManager.getInstance().getBaseAuthStr());

//        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
//        out.write(object.toString());
//        out.close();


        StringBuilder sb = new StringBuilder();
        int httpResult = connection.getResponseCode();
        if (httpResult == HttpURLConnection.HTTP_OK || httpResult == HttpURLConnection.HTTP_CREATED || httpResult == HttpURLConnection.HTTP_CLIENT_TIMEOUT) {
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

    public SelectAllBookViewsAndDateTask(/*String username*/) {

        //this.username = username;

        //String modelString = BASE_URL + "bookViewsAndDate/searchBooksViewsAndDateByUsername?username=" + username;
        String modelString = BASE_URL + "bookViewsAndDate/allBookViewsAndDate";
        Uri uri = Uri.parse(modelString).buildUpon().build();
        this.execute(uri.toString());
    }

    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);
        String response = String.valueOf(o);

        if (selectAllBookViewsAndDateDelegate != null) {
            try {
                selectAllBookViewsAndDateDelegate.onSelecAllBookViewsAndDateDone(response);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public SelectAllBookViewsAndDateDelegate getDelegate() {
        return selectAllBookViewsAndDateDelegate;
    }

    public void setSelectAllBookViewsAndDateDelegate(SelectAllBookViewsAndDateDelegate selectAllBookViewsAndDateDelegate) {
        this.selectAllBookViewsAndDateDelegate = selectAllBookViewsAndDateDelegate;
    }
}