package smb.s18579.mb.smb_proj5

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
/**
 * Implementation of App Widget functionality.
 */
class AppMediaWidget : AppWidgetProvider() {
    private val PIC1 = "pic1"
    private val PIC2 = "pic2"
    private val INTENT_FLAGS = PendingIntent.FLAG_MUTABLE
    private val mPlayerRequestCode = 0
    private var requestCode = 0

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        val remoteView = RemoteViews(context.packageName, R.layout.app_widget)
        val serviceIntent = Intent(context, MediaService::class.java)
        val servicePendingIntent = PendingIntent.getService(context, 0, serviceIntent, PendingIntent.FLAG_MUTABLE)

        with(remoteView) {
            setOnClickPendingIntent(R.id.btn_Play, servicePendingIntent)
            setOnClickPendingIntent(R.id.btn_Stop, servicePendingIntent)
            setOnClickPendingIntent(R.id.btn_PlayNxt, servicePendingIntent)
            setImageViewResource(R.id.widget_imageview, R.drawable.picture1)
            setOnClickPendingIntent(R.id.btn_prevImg, imgPendingIntent(context, PIC1))
            setOnClickPendingIntent(R.id.btn_nextImg, imgPendingIntent(context, PIC2))


        }
        appWidgetIds.forEach { appWidgetId ->
            appWidgetManager.updateAppWidget(appWidgetId, remoteView)
        }
    }

    private fun onNewUpdate(context: Context, remoteViews: RemoteViews) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val thisAppWidgetComponentName = ComponentName(context.packageName, javaClass.name)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidgetComponentName)

        appWidgetIds.forEach { appWidgetId ->
            appWidgetManager.partiallyUpdateAppWidget(appWidgetId, remoteViews)
        }
    }

    private fun imgPendingIntent(context: Context?, action: String?): PendingIntent? {
        val intent = Intent(context, javaClass)
        intent.action = action
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        val remoteView = RemoteViews(context.packageName, R.layout.app_widget)

        when (intent.action) {
            PIC1 -> {
                remoteView.setImageViewResource(R.id.widget_imageview, R.drawable.picture1)
            }
            PIC2 -> {
                remoteView.setImageViewResource(R.id.widget_imageview, R.drawable.picture2)
            }
        }

        val actionIntent = Intent(Intent.ACTION_VIEW)
        actionIntent.data = Uri.parse("https://www.google.com/")
        val browserIntent = PendingIntent.getActivity(context, requestCode++, actionIntent, PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        remoteView.setOnClickPendingIntent(R.id.btn_Browser, browserIntent)

        onNewUpdate(context, remoteView)

        val controlButtons = RemoteViews(context.packageName, R.layout.app_widget)
        val playIntent = Intent(context, MediaService::class.java)
        playIntent.action = "PLAY"
        val stopIntent = Intent(context, MediaService::class.java)
        stopIntent.action = "STOP"
        val playNextIntent = Intent(context, MediaService::class.java)
        playNextIntent.action = "NEXT"


        val playPendingIntent = PendingIntent.getService(context, mPlayerRequestCode, playIntent, INTENT_FLAGS)
        val stopPendingIntent = PendingIntent.getService(context, mPlayerRequestCode, stopIntent, INTENT_FLAGS)
        val playNextPendingIntent = PendingIntent.getService(context, mPlayerRequestCode, playNextIntent, INTENT_FLAGS)

        with(controlButtons){
            setOnClickPendingIntent(R.id.btn_Play, playPendingIntent)
            setOnClickPendingIntent(R.id.btn_Stop, stopPendingIntent)
            setOnClickPendingIntent(R.id.btn_PlayNxt, playNextPendingIntent)
        }

        onNewUpdate(context, controlButtons)
    }
}

