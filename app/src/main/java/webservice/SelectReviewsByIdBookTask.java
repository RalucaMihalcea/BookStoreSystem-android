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
 * Created by Raluca on 28.12.2017.
 */

public class SelectReviewsByIdBookTask extends AsyncTask<String, String, String> implements CredentialInterface  {
    private SelectReviewsByIdBookDelegate selectReviewsByIdBookDelegate;
    private Long idBook;

    @Override
    protected String doInBackground(String... params) {
        try {
            return callSelectReviewsByIdBookService();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String callSelectReviewsByIdBookService() throws IOException, JSONException {
        String modelString = BASE_URL + "review/searchReviewByIdBook?idBook="+idBook;
        Uri uri = Uri.parse(modelString).buildUpon().build();
        HttpURLConnection connection = (HttpURLConnection) new URL(uri.toString()).openConnection();

        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(100000000);
        connection.setReadTimeout(100000000);

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

    public SelectReviewsByIdBookTask(Long idBook) {

        this.idBook=idBook;
        String modelString = BASE_URL + "review/searchReviewByIdBook?idBook="+idBook;
        Uri uri = Uri.parse(modelString).buildUpon().build();
        this.execute(uri.toString());
    }

    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);
        String response = String.valueOf(o);

        if (selectReviewsByIdBookDelegate != null) {
            try {
                selectReviewsByIdBookDelegate.onSelectReviewsByIdBookDone(response);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public SelectReviewsByIdBookDelegate getDelegate() {
        return selectReviewsByIdBookDelegate;
    }

    public void setSelectReviewsByIdBookDelegate(SelectReviewsByIdBookDelegate selectReviewsByIdBookDelegate) {
        this.selectReviewsByIdBookDelegate = selectReviewsByIdBookDelegate;
    }
}
