package com.example.raluca.storebooksystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import manager.DataManager;
import model.Book;
import model.User;
import webservice.SelectBooksDelegate;
import webservice.SelectBooksTask;

public class AudioBooksActivity  extends AppCompatActivity implements SelectBooksDelegate {
    private User userAfterLogin;
    private AudioBooksActivity audioBooksActivity;
    private List<Book> audioBooks= new ArrayList<>();
    private List<Book> books= new ArrayList<>();
    private ListView m_listAudioBooks;
    private ImageView imageViewAudioBook;
    private Button button;
    private CustomAdaptor customAdaptorr = new CustomAdaptor();
    private boolean playPause;
    private MediaPlayer mediaPlayer;
    private ProgressDialog progressDialog;
    private boolean initialStage = true;
    private String audioLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_books);

        audioBooksActivity = this;

        Intent intent = getIntent();
        userAfterLogin = (User) intent.getSerializableExtra("userAfterLogin");

        m_listAudioBooks = (ListView) findViewById(R.id.listAudioBooks);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        progressDialog = new ProgressDialog(this);


        SelectBooksTask selectBooksTask = new SelectBooksTask();
        selectBooksTask.setSelectBooksDelegate(audioBooksActivity);
    }

    @Override
    public void onSelectBooksDone(String result) throws UnsupportedEncodingException {
        if (!result.equals("[]\n")) {
            books = DataManager.getInstance().parseBooks(result);

            for(Book bk:books)
                if(!bk.getAudioLink().toString().equals(""))
                    audioBooks.add(bk);

            m_listAudioBooks.setAdapter(customAdaptorr);
        }
    }

    private class CustomAdaptor extends BaseAdapter {
        @Override
        public int getCount() {
            return audioBooks.size();
        }

        @Override
        public Object getItem(int position) {
            return audioBooks.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.template_for_audio_books, null);
            RecyclerView.ViewHolder holder;

            imageViewAudioBook = (ImageView) view.findViewById(R.id.imageViewAudioBook);
            button=(Button)view.findViewById(R.id.buttonSound);
            //button.setTag(position);

            int resID = getResources().getIdentifier(audioBooks.get(position).getNamePicture(), "drawable", getPackageName());
            imageViewAudioBook.setImageResource(resID);


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    for (int i=0;i<audioBooks.size();i++)
//                        if(position==i)
//                            button.setTag(position);

                    audioLink=audioBooks.get(position).getAudioLink();

                    if (!playPause) {
                        // button.getTag(position).notifyAll();

                        button.setText("Pause Streaming");

                        if (initialStage) {
                            //https://www.ssaurel.com/tmp/mymusic"
                            new Player().execute("https://drive.google.com/file/d/1j5a2SXWMwOwVfebZox2SfzbZv3ntsgQv/view");

                        } else {
                            if (!mediaPlayer.isPlaying())
                                mediaPlayer.start();
                        }
                        playPause = true;

                    } else {
                        button.setText("Launch Streaming");

                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.pause();
                        }
                        playPause = false;
                    }
                }
            });

            return view;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    class Player extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean prepared = false;

            try {
                String source="https://docs.google.com/uc?export=download&id="+audioLink;
                mediaPlayer.setDataSource(source);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        initialStage = true;
                        playPause = false;
                        button.setText("Launch Streaming");
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });

                mediaPlayer.prepare();
                prepared = true;

            } catch (Exception e) {
                Log.e("MyAudioStreamingApp", e.getMessage());
                prepared = false;
            }

            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (progressDialog.isShowing()) {
                progressDialog.cancel();
            }

            mediaPlayer.start();
            initialStage = false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Buffering...");
            progressDialog.show();
        }
    }
}