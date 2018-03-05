package webservice;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import manager.DataManager;

/**
 * Created by Raluca on 24.02.2018.
 */

public class UpdateBookViewsAndDateTask extends AsyncTask<String, String, String> implements CredentialInterface {
    private UpdateBookViewsAndDateDelegate updateBookViewsAndDateDelegate;
    private Long idBook;
    private int views;
    private String username;
    private int month;


    @Override
    protected String doInBackground(String... params) {
        try {
            return callRegisterService();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String callRegisterService() throws IOException, JSONException {
        String modelString = BASE_URL + "bookViewsAndDate/updateBookViewsAndDate?idBook=" + idBook + "&views=" + views + "&username=" + username + "&month=" + month;

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

    public UpdateBookViewsAndDateTask(Long idBook, int views, String username, int month) {

        this.idBook = idBook;
        this.views = views;
        this.username = username;
        this.month=month;

        String modelString = BASE_URL + "bookViews/updateBookViewsAndDate?idBook=" + idBook + "&views=" + views + "&username=" + username + "&month=" + month;

        Uri uri = Uri.parse(modelString).buildUpon().build();
        this.execute(uri.toString());
    }

    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);
        String response = String.valueOf(o);

        if (updateBookViewsAndDateDelegate != null) {
            updateBookViewsAndDateDelegate.onUpdateBookViewsAndDateDone(response);
        }
    }

    public UpdateBookViewsAndDateDelegate getDelegate() {
        return updateBookViewsAndDateDelegate;
    }

    public void setUpdateBookViewsAndDateDelegate(UpdateBookViewsAndDateDelegate updateBookViewsAndDateDelegate) {
        this.updateBookViewsAndDateDelegate = updateBookViewsAndDateDelegate;
    }
}
