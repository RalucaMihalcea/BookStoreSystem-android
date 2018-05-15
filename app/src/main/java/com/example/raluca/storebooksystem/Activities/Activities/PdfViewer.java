package com.example.raluca.storebooksystem.Activities.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.raluca.storebooksystem.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import model.Book;

public class PdfViewer extends AppCompatActivity {
    private PDFView pdfView;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        Intent intent = getIntent();
        book = (Book) intent.getSerializableExtra("book");
        pdfView = (PDFView) findViewById(R.id.pdfViewer);
        //This is function read PDF from Assets
       // String nameBookPDF = titleBook + ".pdf";
       // pdfView.fromAsset(nameBookPDF).load();

         //new RetrievePDFStream().execute("http://publicliterature.org/pdf/46.pdf"); //or any url direct from internet
        //new RetrievePDFStream(this).execute("https://www.readingstudios.com/uploads/5/2/4/6/52467441/insurgent2.pdf");
        new RetrievePDFStream(this).execute(book.getPdfLink());
    }

        class RetrievePDFStream extends AsyncTask<String, Void, InputStream> {

            private final PdfViewer pdfViewer;

            public RetrievePDFStream(PdfViewer pdfViewer) {
                this.pdfViewer=pdfViewer;
            }

            @Override
            protected InputStream doInBackground(String... strings) {
                InputStream inputStream = null;
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    if (urlConnection.getResponseCode() == 200) {
                        inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    }
                } catch (IOException e) {
                    return null;
                }
                return inputStream;
            }

            @Override
            protected void onPostExecute(InputStream inputStream) {
                pdfView.fromStream(inputStream).load();

            }
        }
    }


