package com.example.raluca.storebooksystem;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.net.wifi.WifiConfiguration.Status.strings;

public class PdfViewer extends AppCompatActivity {
    private PDFView pdfView;
    private String titleBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        Intent intent = getIntent();
        titleBook = (String) intent.getSerializableExtra("nameBook");
        pdfView=(PDFView)findViewById(R.id.pdfViewer);
       //This is function read PDF from Assets
        String nameBookPDF=titleBook+".pdf";
         pdfView.fromAsset(nameBookPDF).load();

      //  new RetrievePDFStream().execute("http://publicliterature.org/pdf/46.pdf"); //or any url direct from internet
    }

    class RetrievePDFStream extends AsyncTask<String, Void, InputStream>
    {

        @Override
        protected InputStream doInBackground(String... params) {
                InputStream inputStream = null;
            try{
                URL url=new URL(strings[0]);
                HttpURLConnection urlConnection= (HttpURLConnection)url.openConnection();
                if(urlConnection.getResponseCode()==200)
                {
                    inputStream=new BufferedInputStream(urlConnection.getInputStream());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).load();

        }
    }
}
