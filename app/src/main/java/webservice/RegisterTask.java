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
 * Created by Raluca on 30.10.2017.
 */

public class RegisterTask extends AsyncTask<String, String, String> implements CredentialInterface {

    private RegisterDelegate registerDelegate;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String contactNo;
    private String address;

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
        String modelString = BASE_URL + "register/add?username=" + username + "&firstName=" + firstName + "&lastName=" + lastName + "&password=" + password + "&email=" + "" + "&contactNo=" + "" + "&address=" + "";

        Uri uri = Uri.parse(modelString).buildUpon().build();

        HttpURLConnection connection = (HttpURLConnection) new URL(uri.toString()).openConnection();

        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(100000000);
        connection.setReadTimeout(100000000);

        JSONObject object = new JSONObject();
        object.put("username", username);
        object.put("firstName", firstName);
        object.put("lastName", lastName);
        object.put("password", password);
        object.put("email", email);
        object.put("contactNo", contactNo);
        object.put("address", address);

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

    public RegisterTask(String username, String firstName, String lastName, String password, String email, String contactNo, String address) {
        this.registerDelegate = registerDelegate;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.contactNo = contactNo;
        this.address = address;

        String modelString = BASE_URL + "register/add?username=" + username + "&firstName=" + firstName + "&lastName=" + lastName + "&password=" + password + "&email=" + "" + "&contactNo=" + "" + "&address=" + "";
        Uri uri = Uri.parse(modelString).buildUpon().build();

        this.execute(uri.toString());
    }

    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);
        String response = String.valueOf(o);

        if (registerDelegate != null) {
            registerDelegate.onRegisterDone(response);
        }
        if (response == null) {
            registerDelegate.onRegisterError(response);
        }

    }

    public RegisterDelegate getDelegate() {
        return registerDelegate;
    }

    public void setRegisterDelegate(RegisterDelegate releaseDelegate) {
        this.registerDelegate = releaseDelegate;
    }
}
