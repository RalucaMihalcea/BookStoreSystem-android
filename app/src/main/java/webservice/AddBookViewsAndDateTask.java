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

import manager.DataManager;

/**
 * Created by Raluca on 21.02.2018.
 */

public class AddBookViewsAndDateTask extends AsyncTask<String, String, String> implements CredentialInterface {
    private AddBookViewsAndDateDelegate addBookViewsAndDateDelegate;
    private Long idBook;
    private String username;
    private int views;
    private int month;

    @Override
    protected String doInBackground(String... params) {
        try {
            return callAddBookViewsAndDateService();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String callAddBookViewsAndDateService() throws IOException, JSONException {
        String modelString = BASE_URL + "bookViewsAndDate/addBookViewsAndDateParameters?idBook=" + idBook + "&views=" + views + "&month=" + month + "&username=" + username;

        Uri uri = Uri.parse(modelString).buildUpon().build();

        HttpURLConnection connection = (HttpURLConnection) new URL(uri.toString()).openConnection();

        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(1000000);
        connection.setReadTimeout(1000000);

        JSONObject object = new JSONObject();
        object.put("idBook", idBook);
        object.put("views", views);
        object.put("month", month);
        object.put("username", username);

        connection.addRequestProperty("Authorization", DataManager.getInstance().getBaseAuthStr());

        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        out.write(object.toString());
        out.close();

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
            System.out.println(connection.getResponseMessage());
        }
        return sb.toString();
    }

    public AddBookViewsAndDateTask(Long idBook, int views, int month, String username ) {

        this.username = username;
        this.idBook = idBook;
        this.views = views;
        this.month = month;

        String modelString = BASE_URL + "bookViews/addBookViewsAndDateParameters?idBook=" + idBook + "&views=" + views + "&month=" + month + "&username=" + username;

        Uri uri = Uri.parse(modelString).buildUpon().build();
        this.execute(uri.toString());
    }

    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);
        String response = String.valueOf(o);

        if (addBookViewsAndDateDelegate != null) {
            try {
                addBookViewsAndDateDelegate.onAddBookViewsAndDateDone(response);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public AddBookViewsAndDateDelegate getDelegate() {
        return addBookViewsAndDateDelegate;
    }

    public void setAddBookViewsAndDateDelegate(AddBookViewsAndDateDelegate addBookViewsAndDateDelegate) {
        this.addBookViewsAndDateDelegate = addBookViewsAndDateDelegate;
    }
}
