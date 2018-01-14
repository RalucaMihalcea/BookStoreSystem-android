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

public class AddFavoriteBookTask extends AsyncTask<String, String, String> implements CredentialInterface {

    private AddFavoriteBookDelegate addFavoriteBookDelegate;
    private Long idBook;
    private String title;
    private String author;
    private String category;
    private String namePicture;
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
        String modelString = BASE_URL + "favoriteBook/addFavoriteBookParameters?idBook=" + idBook + "&title=" + title + "&author=" + author + "&category=" + category + "&namePicture=" + namePicture + "&username=" + username;

        Uri uri = Uri.parse(modelString).buildUpon().build();

        HttpURLConnection connection = (HttpURLConnection) new URL(uri.toString()).openConnection();

        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(1000000);
        connection.setReadTimeout(1000000);

        JSONObject object = new JSONObject();
        object.put("idBook", idBook);
        object.put("title", title);
        object.put("author", author);
        object.put("category", category);
        object.put("namePicture", namePicture);
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

    public AddFavoriteBookTask(Long idBook, String title, String author, String category, String namePicture, String username) {

        this.idBook = idBook;
        this.title = title;
        this.author = author;
        this.category = category;
        this.namePicture = namePicture;
        this.username = username;


        String modelString = BASE_URL + "favoriteBook/addFavoriteBookParameters?idBook=" + idBook + "&title=" + title + "&author=" + author + "&category=" + category + "&namePicture=" + namePicture + "&username=" + username;

        Uri uri = Uri.parse(modelString).buildUpon().build();
        this.execute(uri.toString());
    }

    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);
        String response = String.valueOf(o);

        if (addFavoriteBookDelegate != null) {
            try {
                addFavoriteBookDelegate.onAddFavoriteBookDone(response);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if (response == null) {
            addFavoriteBookDelegate.onAddFavoriteBookError(response);
        }
    }

    public AddFavoriteBookDelegate getDelegate() {
        return addFavoriteBookDelegate;
    }


    public void setAddFavoriteBookDelegate(AddFavoriteBookDelegate addFavoriteBookDelegate) {
        this.addFavoriteBookDelegate = addFavoriteBookDelegate;
    }
}
