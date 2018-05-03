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
 * Created by Raluca on 07.11.2017.
 */

public class UpdateTask extends AsyncTask<String, String, String> implements CredentialInterface {
    private UpdateDelegate updateDelegate;
    private String username;
    private String newUsername;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNo;

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
        String modelString = BASE_URL + "update/updatee?username=" + username + "&newUsername=" + newUsername + "&firstName=" + firstName + "&lastName=" + lastName + "&email=" + email + "&contactNo=" + contactNo;

        Uri uri = Uri.parse(modelString).buildUpon().build();

        HttpURLConnection connection = (HttpURLConnection) new URL(uri.toString()).openConnection();

        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(100000000);
        connection.setReadTimeout(100000000);

        JSONObject object = new JSONObject();
        object.put("username", username);
        object.put("newUsername", newUsername);
        object.put("firstName", firstName);
        object.put("lastName", lastName);
        object.put("email", email);
        object.put("contactNo", contactNo);


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

    public UpdateTask(String username, String newUsername, String firstName, String lastName, String email, String contactNo) {

        this.username = username;
        this.newUsername = newUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contactNo = contactNo;


        String modelString = BASE_URL + "update/updatee?username=" + username + "&newUsername=" + newUsername + "&firstName=" + firstName + "&lastName=" + lastName + "&email=" + email + "&contactNo=" + contactNo;

        Uri uri = Uri.parse(modelString).buildUpon().build();
        this.execute(uri.toString());
    }

    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);
        String response = String.valueOf(o);

        if (updateDelegate != null) {
            updateDelegate.onUpdateDone(response);
        }
        if (response == null) {
            updateDelegate.onUpdateError(response);
        }
    }

    public UpdateDelegate getDelegate() {
        return updateDelegate;
    }


    public void setUpdateDelegate(UpdateDelegate updateDelegate) {
        this.updateDelegate = updateDelegate;
    }
}
