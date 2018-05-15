package webservice;

/**
 * Created by Raluca on 31.10.2017.
 */

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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class
LoginTask extends AsyncTask<String, String, String> implements CredentialInterface {

    private LoginDelegate loginDelegate;
    private String username;
    private String password;

    @Override
    protected String doInBackground(String... params) {
        try {
            return callLoginService();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String callLoginService() throws IOException, JSONException {
       // String modelString = BASE_URL + "login?user_name=" + username + "&password=" + password;
      //  Uri uri = Uri.parse(modelString).buildUpon().build();
        Uri uri = Uri.parse(BASE_URL).buildUpon().appendPath("login").build();
        HttpURLConnection connection = (HttpURLConnection) new URL(uri.toString()).openConnection();

        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(100000000);
        connection.setReadTimeout(100000000);

        JSONObject object = new JSONObject();
        object.put("username", username);
        object.put("password", password);

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

    public LoginTask(String username, String password) throws NoSuchAlgorithmException {


        this.username = username;
        this.password = password;

//        String modelString = BASE_URL + "login?user_name=" + username + "&password=" + password;
//        Uri uri = Uri.parse(modelString).buildUpon().build();
        Uri uri = Uri.parse(BASE_URL).buildUpon().appendPath("login").build();
        this.execute(uri.toString());
    }

    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);
        String response = String.valueOf(o);

        if (loginDelegate != null) {
            try {
                loginDelegate.onLoginDone(response);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public LoginDelegate getDelegate() {
        return loginDelegate;
    }

    public void setLoginDelegate(LoginDelegate loginDelegate) {
        this.loginDelegate = loginDelegate;
    }
}

