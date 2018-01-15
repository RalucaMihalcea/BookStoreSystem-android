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
 * Created by Raluca on 14.01.2018.
 */

public class DeleteFavoriteBookTask extends AsyncTask<String, String, String> implements CredentialInterface {


    private DeleteFavoriteBookDelegate deleteFavoriteBookDelegate;
    private Long idBook;
    private String username;

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
        String modelString = BASE_URL + "favoriteBook/deleteFavoriteBookParameters?idBook=" + idBook + "&username=" + username;

        Uri uri = Uri.parse(modelString).buildUpon().build();

        HttpURLConnection connection = (HttpURLConnection) new URL(uri.toString()).openConnection();

        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(1000000);
        connection.setReadTimeout(1000000);

        JSONObject object = new JSONObject();
        object.put("idBook", idBook);
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

    public DeleteFavoriteBookTask(Long idBook, String username) {

        this.idBook = idBook;
        this.username = username;


        String modelString = BASE_URL + "favoriteBook/deleteFavoriteBookParameters?idBook=" + idBook + "&username=" + username;

        Uri uri = Uri.parse(modelString).buildUpon().build();
        this.execute(uri.toString());
    }

    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);
        String response = String.valueOf(o);

        if (deleteFavoriteBookDelegate != null) {
            try {
                deleteFavoriteBookDelegate.onDeleteFavoriteBookDone(response);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if (response == null) {
            deleteFavoriteBookDelegate.onDeleteFavoriteBookError(response);
        }
    }

    public DeleteFavoriteBookDelegate getDelegate() {
        return deleteFavoriteBookDelegate;
    }


    public void setDeleteFavoriteBookDelegate(DeleteFavoriteBookDelegate deleteFavoriteBookDelegate) {
        this.deleteFavoriteBookDelegate = deleteFavoriteBookDelegate;
    }
}
