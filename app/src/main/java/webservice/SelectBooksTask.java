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
 * Created by Raluca on 26.11.2017.
 */

public class SelectBooksTask extends AsyncTask<String, String, String> implements CredentialInterface {

    private SelectBooksDelegate selectBookDelegate;

    @Override
    protected String doInBackground(String... params) {
        try {
            return callSelectBookService();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String callSelectBookService() throws IOException, JSONException {
        String modelString = BASE_URL + "book/allBooks";
        Uri uri = Uri.parse(modelString).buildUpon().build();
        HttpURLConnection connection = (HttpURLConnection) new URL(uri.toString()).openConnection();

        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(1000000);
        connection.setReadTimeout(1000000);

        connection.addRequestProperty("Authorization", DataManager.getInstance().getBaseAuthStr());


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

    public SelectBooksTask() {

        String modelString = BASE_URL + "book/allBooks";
        Uri uri = Uri.parse(modelString).buildUpon().build();
        this.execute(uri.toString());
    }

    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);
        String response = String.valueOf(o);

        if (selectBookDelegate != null) {
            try {
                selectBookDelegate.onSelectBooksDone(response);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public SelectBooksDelegate getDelegate() {
        return selectBookDelegate;
    }

    public void setSelectBooksDelegate(SelectBooksDelegate selectBookDelegate) {
        this.selectBookDelegate = selectBookDelegate;
    }
}
