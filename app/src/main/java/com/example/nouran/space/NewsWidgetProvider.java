package com.example.nouran.space;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RemoteViews;

import static android.content.Context.MODE_PRIVATE;

/**
 * Implementation of App Widget functionality.
 */
public class NewsWidgetProvider extends AppWidgetProvider {


    private static SharedPreferences sharedPrefs;
    private SharedPreferences.Editor sharedPrefsEditor;
    private static final String MY_PREFS_NAME = "MyPrefsFile";


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {



        sharedPrefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String namePref = sharedPrefs.getString("name", null);
        String newsPref = sharedPrefs.getString("NEWS_PREF", null);


        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int width = options.getInt(appWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
//        RemoteViews remoteViews;
//        if(width <300)
//            remoteViews = get


        CharSequence widgetText = namePref;
        CharSequence widgetNews = newsPref;
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.news_widget_provider);

        Intent intent = new Intent(context, MainActivity.class);



        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        views.setOnClickPendingIntent(R.id.widget_txt_view, pendingIntent);

        views.setTextViewText(R.id.widget_txt_view, widgetText);
        views.setTextViewText(R.id.widget_Text_view, widgetNews);



        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {

        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}