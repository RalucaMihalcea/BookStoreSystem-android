package com.example.raluca.storebooksystem.Activities.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.raluca.storebooksystem.R;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import manager.DataManager;
import model.Book;
import model.User;
import webservice.SelectBooksDelegate;
import webservice.SelectBooksTask;

public class AudioBooksActivity extends AppCompatActivity implements SelectBooksDelegate {
    private User userAfterLogin;
    private AudioBooksActivity audioBooksActivity;
    private List<Book> audioBooks = new ArrayList<>();
    private List<Book> books = new ArrayList<>();
    private List<String> imageLinkList = new ArrayList<>();
    private ListView m_listAudioBooks;
    private ImageView imageViewAudioBook;
    private Button button;
    private CustomAdaptor customAdaptorr;
    private boolean playPause, stopButtonActivate;
    private MediaPlayer mediaPlayer;
    private ProgressDialog progressDialog;
    private boolean initialStage = true;
    private String audioLink;
    private TextView titleAudioBook;
    private String title = "";
    private String auxString;
    private static final String TAG = "AudioBooksActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_books);

        audioBooksActivity = this;

        Intent intent = getIntent();
        userAfterLogin = (User) intent.getSerializableExtra("userAfterLogin");

        m_listAudioBooks = (ListView) findViewById(R.id.listViewAudioBooks);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        progressDialog = new ProgressDialog(this);

        Log.i(TAG, "Select books task: " + userAfterLogin.getUsername());
        SelectBooksTask selectBooksTask = new SelectBooksTask();
        selectBooksTask.setSelectBooksDelegate(audioBooksActivity);
    }

    @Override
    public void onSelectBooksDone(String result) throws UnsupportedEncodingException {
        Log.d(TAG, "SelectBooks DONE DELEGATE " + result);
        if (!result.equals("[]\n")) {
            books = DataManager.getInstance().parseBooks(result);

            for (Book bk : books)
                if (!bk.getAudioLink().toString().equals("")) {
                    audioBooks.add(bk);
                    imageLinkList.add(bk.getImageLink());
                }

            customAdaptorr = new CustomAdaptor(getApplicationContext(), audioBooks, imageLinkList);
            m_listAudioBooks.setAdapter(customAdaptorr);
        }
    }

    private class CustomAdaptor extends BaseAdapter {

        private Context context;
        private List<Book> audioBooks = new ArrayList<>();
        private List<Book> books = new ArrayList<>();
        private List<String> imageLinkList = new ArrayList<>();
        private ListView m_listAudioBooks;
        private ImageView imageViewAudioBook;
        private Button button, buttonCloseSound;
        private TextView titleAudioBook;
        LayoutInflater inflater;

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

        public CustomAdaptor(@NonNull Context context, List<Book> audioBooks, List<String> imageLinkList) {
            this.context = context;
            this.audioBooks = audioBooks;
            this.imageLinkList = imageLinkList;
        }

        public class ViewHolder {
            ImageView imageViewAudioBook;
            TextView titleAudioBook;
            Button button, buttonCloseSound;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.template_for_audio_books, null);
            }

            final ViewHolder holder = new ViewHolder();
            holder.imageViewAudioBook = (ImageView) convertView.findViewById(R.id.imageViewAudioBook);
            holder.titleAudioBook = (TextView) convertView.findViewById(R.id.titleAudioBook);
            holder.button = (Button) convertView.findViewById(R.id.buttonSound);
            holder.buttonCloseSound = (Button) convertView.findViewById(R.id.buttonCloseSound);

            title = audioBooks.get(position).getTitle();
            holder.titleAudioBook.setText(title);

//            int resID = getResources().getIdentifier(audioBooks.get(position).getNamePicture(), "drawable", getPackageName());
//            holder.imageViewAudioBook.setImageResource(resID);
            auxString = imageLinkList.get(position);
            Glide.with(context).load("https://docs.google.com/uc?export=download&id=" + auxString).into(holder.imageViewAudioBook);

            holder.buttonCloseSound.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    stopButtonActivate = true;

                }

            });


            holder.button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    for (Book bk : audioBooks)
                        if (bk.getTitle().equals(holder.titleAudioBook.getText().toString())) {
                            audioLink = bk.getAudioLink();
                            //Toast.makeText(context, "audioLink:"+audioLink, Toast.LENGTH_SHORT).show();
                            break;
                        }

                    //  audioLink = audioBooks.get(position).getAudioLink();

                    if (!playPause || stopButtonActivate == true) {

                        holder.button.setText("Pause Streaming");

                        if (initialStage) {
                            //https://www.ssaurel.com/tmp/mymusic"
                            new Player().execute("https://drive.google.com/file/d/1j5a2SXWMwOwVfebZox2SfzbZv3ntsgQv/view");

                        } else {
                            if (!mediaPlayer.isPlaying())
                                mediaPlayer.start();
                        }
                        playPause = true;

                    } else {
                        holder.button.setText("Launch Streaming");

                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.pause();
                        }
                        playPause = false;
                    }
                }
            });

            return convertView;
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
                String source = "https://docs.google.com/uc?export=download&id=" + audioLink;
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
