package com.example.vicky.todolist;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class MyWidgetProvider extends AppWidgetProvider {



    public static final String ACTION_VIEW_DETAILS =
            "com.company.android.ACTION_VIEW_DETAILS";
    public static final String EXTRA_ITEM =
            "com.company.android.MyWidgetProvider.EXTRA_ITEM";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for(int i = 0; i < appWidgetIds.length; i++) {

            int widgetId = appWidgetIds[i];

            Intent intent = new Intent(context, CollectionWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

            RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            widgetView.setRemoteAdapter(R.id.listWidget, intent);
            widgetView.setEmptyView(R.id.listWidget, R.id.empty);

            Intent detailIntent = new Intent(ACTION_VIEW_DETAILS);
            PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, detailIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            widgetView.setPendingIntentTemplate(R.id.listWidget, pIntent);

            appWidgetManager.updateAppWidget(widgetId, widgetView);








        }



        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }


    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(ACTION_VIEW_DETAILS)) {
            NotesContent singleTask = (NotesContent) intent.getSerializableExtra(EXTRA_ITEM);
            if(singleTask != null) {




            }
        }

        super.onReceive(context, intent);
    }
}



