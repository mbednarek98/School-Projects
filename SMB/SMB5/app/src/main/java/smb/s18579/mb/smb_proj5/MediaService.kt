package smb.s18579.mb.smb_proj5

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log


class MediaService : Service() {
    private var player: MediaPlayer? = null
    override fun onBind(intent: Intent): IBinder? {
        return null
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(intent == null || intent.action.equals("")){
            return super.onStartCommand(intent, flags, startId)
        }

        when (intent.action) {
            "PLAY" -> {
                player = MediaPlayer.create(this, R.raw.fart)
                player?.start()
            }
            "STOP" -> {
                player?.stop()
            }
            "NEXT" -> {
                player?.stop()
                player = MediaPlayer.create(this, R.raw.beep2)
                player?.start()
            }
            "PREV" -> {
                player?.stop()
                player = MediaPlayer.create(this, R.raw.beep1)
                player?.start()
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }


    override fun onDestroy() {
        player!!.stop()
        player!!.release()
    }
}