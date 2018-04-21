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
 * Created by Raluca on 26.11.2017.
 */

public class SelectBookByCategoryTask extends AsyncTask<String, String, String> implements CredentialInterface {
    private SelectBookByCategoryDelegate selectBookByCategoryDelegate;
    private String category;

    @Override
    protected String doInBackground(String... params) {
        try {
            return callSelectBookByCategoryService();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String callSelectBookByCategoryService() throws IOException, JSONException {
        String modelString = BASE_URL + "book/searchBookByCategory2?category=" + category;
        Uri uri = Uri.parse(modelString).buildUpon().build();
        HttpURLConnection connection = (HttpURLConnection) new URL(uri.toString()).openConnection();

        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(100000000);
        connection.setReadTimeout(100000000);

        JSONObject object = new JSONObject();
        object.put("category", category);


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

    public SelectBookByCategoryTask(String category) {

        this.category = category;
        String modelString = BASE_URL + "book/searchBookByCategory2?category=" + category;
        Uri uri = Uri.parse(modelString).buildUpon().build();
        this.execute(uri.toString());
    }

    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);
        String response = String.valueOf(o);

        if (selectBookByCategoryDelegate != null) {
            try {
                selectBookByCategoryDelegate.onSelectBookByCategoryDone(response);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public SelectBookByCategoryDelegate getDelegate() {
        return selectBookByCategoryDelegate;
    }

    public void setSelectBookByCategoryDelegate(SelectBookByCategoryDelegate selectBookByCategoryDelegate) {
        this.selectBookByCategoryDelegate = selectBookByCategoryDelegate;
    }
}
