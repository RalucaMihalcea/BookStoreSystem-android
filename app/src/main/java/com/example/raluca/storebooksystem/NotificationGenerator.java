package com.example.raluca.storebooksystem;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import model.Book;

/**
 * Created by Raluca on 10.03.2018.
 */

public class NotificationGenerator {

    private static final int NOTIFICATION_ID_OPEN_ACTIVITY=9;
    public static void openActivityNotification(Context context, Book book)
    {
        NotificationCompat.Builder nc=new NotificationCompat.Builder(context);
        NotificationManager nm=(NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Intent notifyIntent=new Intent(context, HomeActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent= PendingIntent.getActivity(context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        nc.setContentIntent(pendingIntent);
        nc.setSmallIcon(R.drawable.nonfiction);
        nc.setAutoCancel(true);
        nc.setContentTitle("A new book appeared: "+book.getTitle());
        nc.setContentText("Do not miss this book, you will surely like it!");
        nm.notify(NOTIFICATION_ID_OPEN_ACTIVITY, nc.build());


    }
}
