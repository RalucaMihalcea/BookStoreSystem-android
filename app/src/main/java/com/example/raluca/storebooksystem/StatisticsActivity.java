package com.example.raluca.storebooksystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView textView1, textView2, textView3;
    int month, j = 1;
    Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        statisticsActivity = this;
        textView1 = (TextView) findViewById(R.id.texttView1);
        textView2 = (TextView) findViewById(R.id.texttView2);
        textView3 = (TextView) findViewById(R.id.texttView3);

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
        for (BookViewsAndDate bookViews : bookViewsForProgress){
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

    @Override
    public void onSelectBookByIdDone(String result) throws UnsupportedEncodingException {

        if (!result.isEmpty()) {
            book = DataManager.getInstance().parseBook(result);
            // bookList.add(book);
            switch (j) {
                case 1:
                    textView1.setText(book.getId() + "  -> " + book.getTitle());
                    j++;
                    break;
                case 2:
                    textView2.setText(book.getId() + "  -> " + book.getTitle());
                    j++;
                    break;
                case 3:
                    textView3.setText(book.getId() + "  -> " + book.getTitle());
                    j++;
                    break;
                case 4:
                    //textView4.setText(book.getId() + " -> " + book.getTitle());
                    j++;
                    break;
            }
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
