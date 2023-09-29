package smb.s18579.mb.shopbroadcast

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf


class ItemCreationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if(intent.action == "smb.s18579.mb.CREATE_PRODUCT_INTENT") {
            val channelId = createChannel(context)
            val productId = intent.extras?.getLong("ITEM_ID") as Long
            val productName = intent.extras?.getString("ITEM_NAME")
            val productQuant = intent.extras?.getInt("ITEM_QUANT")
            val productPrice = intent.extras?.getDouble("ITEM_PRICE")

            val data = workDataOf(
                "id" to productId,
                "name" to productName,
                "channelID" to channelId,
                "quant" to productQuant,
                "price" to productPrice
            )
            val worker = OneTimeWorkRequestBuilder<ItemNotification>().setInputData(data).build()
            WorkManager.getInstance(context).enqueue(worker)
        }

    }

    private fun createChannel(context: Context): String {
        val id = "ProductAddChannel"
        val channel = NotificationChannel(
            id,
            "Product Add Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        NotificationManagerCompat.from(context).createNotificationChannel(channel)
        return id
    }
}