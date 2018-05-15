package com.example.raluca.storebooksystem.Activities.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raluca.storebooksystem.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import manager.DataManager;
import model.Book;
import model.BookViewsAndDate;
import model.User;
import webservice.SelectBookByIdDelegate;
import webservice.SelectBookByIdTask;
import webservice.SelectReviewsByIdBookTask;

public class StatisticsActivity extends AppCompatActivity implements SelectBookByIdDelegate {

    BarChart barChart;
    List<BookViewsAndDate> bookViewsForProgress = new ArrayList<>();
    ArrayList<BarEntry> barEntries;
    ArrayList<BarEntry> barEntries1;
    User userAfterLogin;
    StatisticsActivity statisticsActivity;
    TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10, textView11, textView12, textView13, textView14, textView15, textView16, textView17, textView18, textView19, textView20, textView21, textView22, textView23, textView24;
    int month, j = 1;
    Book book;
    private List<Book> books = new ArrayList<>();
    private static final String TAG = "StatisticsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        statisticsActivity = this;
        textView1 = (TextView) findViewById(R.id.texttView1);
        textView2 = (TextView) findViewById(R.id.texttView2);
        textView3 = (TextView) findViewById(R.id.texttView3);
        textView4= (TextView) findViewById(R.id.texttView4);
        textView5 = (TextView) findViewById(R.id.texttView5);
        textView6 = (TextView) findViewById(R.id.texttView6);
        textView7 = (TextView) findViewById(R.id.texttView7);
        textView8 = (TextView) findViewById(R.id.texttView8);
        textView9 = (TextView) findViewById(R.id.texttView9);
        textView10 = (TextView) findViewById(R.id.texttView10);
        textView11 = (TextView) findViewById(R.id.texttView11);
        textView12 = (TextView) findViewById(R.id.texttView12);
        textView13 = (TextView) findViewById(R.id.texttView13);
        textView14 = (TextView) findViewById(R.id.texttView14);
        textView15 = (TextView) findViewById(R.id.texttView15);
        textView16 = (TextView) findViewById(R.id.texttView16);
        textView17 = (TextView) findViewById(R.id.texttView17);
        textView18 = (TextView) findViewById(R.id.texttView18);
        textView19 = (TextView) findViewById(R.id.texttView19);
        textView20 = (TextView) findViewById(R.id.texttView20);
        textView21 = (TextView) findViewById(R.id.texttView21);
        textView22 = (TextView) findViewById(R.id.texttView22);
        textView23 = (TextView) findViewById(R.id.texttView23);
        textView24 = (TextView) findViewById(R.id.texttView24);

        Intent intent = getIntent();
        bookViewsForProgress = (List<BookViewsAndDate>) intent.getSerializableExtra("dayBookListForStatistics");

        //List<String> labels = new ArrayList<>();
        barEntries = new ArrayList<BarEntry>();
        barEntries1 = new ArrayList<BarEntry>();
        barChart = (BarChart) findViewById(R.id.barchart);
        barChart.setFitBars(true);

        for (BookViewsAndDate bookViews : bookViewsForProgress) {
            month = bookViews.getMonth();

            if (checkInbarEntries(month)) {
                barEntries1.add(new BarEntry(month, bookViews.getIdBook()));
                //labels.add("AA");
            } else {
                barEntries.add(new BarEntry(month, bookViews.getIdBook()));
                //labels.add("BB");
            }
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "Book1");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarDataSet barDataSet1 = new BarDataSet(barEntries1, "Book2");
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData data = new BarData(barDataSet, barDataSet1);
        //BarData data = new BarData(barDataSet);

        float groupSpace = 0.1f;
        float barSpace = 0.01f;
        float barWidth = 0.43f;

        barChart.setData(data);

        data.setBarWidth(barWidth);
        barChart.groupBars(1, groupSpace, barSpace);

        String[] months = new String[]{"", "Jan", "Feb", "Mar", "April", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        Toast.makeText(this, months.length + "", Toast.LENGTH_SHORT).show();
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new MyXAxisValueFormatter(months));
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setGranularity(1);
        xAxis.setCenterAxisLabels(true);
        xAxis.setAxisMinimum(1);
        barChart.setFitBars(true);
        barChart.invalidate();
        barChart.animateY(5000);
//        xAxis.setCenterAxisLabels(true);
//        xAxis.setGranularityEnabled(true);
//        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

//        LoginTask loginTask = new LoginTask(userAfterLogin.getUsername(), userAfterLogin.getPassword());
//        loginTask.setLoginDelegate(progressActivity);
        for (BookViewsAndDate bookViews : bookViewsForProgress) {

            Log.i(TAG, "Select book by id: " + bookViews.getIdBook());

            SelectBookByIdTask selectBookByIdTaskTask = new SelectBookByIdTask(bookViews.getIdBook());
            selectBookByIdTaskTask.setSelectBookByIdDelegate(statisticsActivity);

        }
    }

    public boolean checkInbarEntries(int month) {
        for (BarEntry barEntryItem : barEntries) {
            if (month == barEntryItem.getX())
                return true;
        }
        return false;
    }

    public boolean validateBook(Book book) {
        for (Book bk : books) {
            if (bk.getTitle().equals(book.getTitle()))
                return false;

        }
        return true;

    }

    public void addInTextView(Book book) {
        switch (j) {
            case 1:
                if (validateBook(book) == true) {
                    books.add(book);
                    textView1.setText(book.getId() + "  -> " + book.getTitle());
                    j++;
                }
                break;

            case 2:
                if (validateBook(book) == true) {
                    books.add(book);
                    textView2.setText(book.getId() + "  -> " + book.getTitle());
                    j++;
                }
                break;

            case 3:
                if (validateBook(book) == true) {
                    books.add(book);
                    textView3.setText(book.getId() + "  -> " + book.getTitle());
                    j++;
                }
                break;

            case 4:

                if (validateBook(book) == true) {
                    books.add(book);
                    textView4.setText(book.getId() + " -> " + book.getTitle());
                    j++;
                }
                break;

            case 5:

                if (validateBook(book) == true) {
                    books.add(book);
                    textView5.setText(book.getId() + " -> " + book.getTitle());
                    j++;
                }
                break;
            case 6:

                if (validateBook(book) == true) {
                    books.add(book);
                    textView6.setText(book.getId() + " -> " + book.getTitle());
                    j++;
                }
                break;
            case 7:

                if (validateBook(book) == true) {
                    books.add(book);
                    textView7.setText(book.getId() + " -> " + book.getTitle());
                    j++;
                }
                break;
            case 8:
                textView8.setText(book.getId() + " -> " + book.getTitle());
                if (validateBook(book) == true) {
                    books.add(book);
                    j++;
                }
                break;
            case 9:

                if (validateBook(book) == true) {
                    books.add(book);
                    textView9.setText(book.getId() + " -> " + book.getTitle());
                    j++;
                }
                break;
            case 10:

                if (validateBook(book) == true) {
                    books.add(book);
                    textView10.setText(book.getId() + " -> " + book.getTitle());
                    j++;
                }
                break;
            case 11:

                if (validateBook(book) == true) {
                    books.add(book);
                    textView11.setText(book.getId() + " -> " + book.getTitle());
                    j++;
                }
                break;
            case 12:

                if (validateBook(book) == true) {
                    books.add(book);
                    textView4.setText(book.getId() + " -> " + book.getTitle());
                    j++;
                }
                break;
            case 13:

                if (validateBook(book) == true) {
                    books.add(book);
                    textView13.setText(book.getId() + " -> " + book.getTitle());
                    j++;
                }
                break;
            case 14:

                if (validateBook(book) == true) {
                    books.add(book);
                    textView14.setText(book.getId() + " -> " + book.getTitle());
                    j++;
                }
                break;
            case 15:
                if (validateBook(book) == true) {
                    books.add(book);
                    textView15.setText(book.getId() + " -> " + book.getTitle());
                    j++;
                }
                break;
            case 16:

                if (validateBook(book) == true) {
                    books.add(book);
                    textView16.setText(book.getId() + " -> " + book.getTitle());
                    j++;
                }
                break;
            case 17:

                if (validateBook(book) == true) {
                    books.add(book);
                    textView17.setText(book.getId() + " -> " + book.getTitle());
                    j++;
                }
                break;
            case 18:

                if (validateBook(book) == true) {
                    books.add(book);
                    textView18.setText(book.getId() + " -> " + book.getTitle());
                    j++;
                }
                break;
            case 19:

                if (validateBook(book) == true) {
                    books.add(book);
                    textView19.setText(book.getId() + " -> " + book.getTitle());
                    j++;
                }
                break;
            case 20:

                if (validateBook(book) == true) {
                    books.add(book);
                    textView20.setText(book.getId() + " -> " + book.getTitle());
                    j++;
                }
                break;
            case 21:

                if (validateBook(book) == true) {
                    books.add(book);
                    textView21.setText(book.getId() + " -> " + book.getTitle());
                    j++;
                }
                break;
            case 22:

                if (validateBook(book) == true) {
                    books.add(book);
                    textView22.setText(book.getId() + " -> " + book.getTitle());
                    j++;
                }
                break;
            case 23:

                if (validateBook(book) == true) {
                    books.add(book);
                    textView23.setText(book.getId() + " -> " + book.getTitle());
                    j++;
                }
                break;
            case 24:

                if (validateBook(book) == true) {
                    books.add(book);
                    textView24.setText(book.getId() + " -> " + book.getTitle());
                    j++;
                }
                break;
        }
    }

    @Override
    public void onSelectBookByIdDone(String result) throws UnsupportedEncodingException {

        Log.d(TAG, "SelectBookById DONE DELEGATE " + result);

        if (!result.isEmpty()) {
            book = DataManager.getInstance().parseBook(result);
            addInTextView(book);
        }
    }

    public class MyXAxisValueFormatter implements IAxisValueFormatter {
        private String[] mValues;

        public MyXAxisValueFormatter(String[] values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {

            return mValues[(int) value];
        }
    }
}