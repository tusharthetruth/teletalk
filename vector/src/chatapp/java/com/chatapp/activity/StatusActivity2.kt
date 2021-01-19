package com.chatapp.activity

import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.VideoView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bolaware.viewstimerstory.Momentz
import com.bolaware.viewstimerstory.MomentzCallback
import com.bolaware.viewstimerstory.MomentzView
import com.bumptech.glide.Glide
import com.chatapp.CR
import com.chatapp.sip.utils.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import im.vector.R
import kotlinx.android.synthetic.chatapp.activity_self_status2.*
import java.lang.System.load

class StatusActivity2 : AppCompatActivity(), MomentzCallback {

    var seen: FloatingActionButton? = null
    val counter: Int = 0
    lateinit var currentMomentz: Momentz
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status2)
        val internetLoadedImageView = ImageView(this)

        val internetLoadedVideo = VideoView(this)
        val l = ArrayList<MomentzView>()

//        CR.resources.clear()
//        CR.resources.add("https://homepages.cae.wisc.edu/~ece533/images/airplane.png")
//        CR.resources.add("https://cdn.searchenginejournal.com/wp-content/uploads/2018/04/durable-urls.png")
//        CR.resources.add("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
//        CR.resources.add("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4")
//        CR.resources.add("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4")

        for (path in CR.resources) {
                if (path.contains(".mp4", ignoreCase = true)
                        || path.contains(".avi", ignoreCase = true)
                        || path.contains(".flv", ignoreCase = true)
                        || path.contains(".flv", ignoreCase = true)
                ){

                l.add(MomentzView(internetLoadedVideo, 60))
            } else {
                l.add(MomentzView(internetLoadedImageView, 15))
            }
        }
        Momentz(this, l, container, this).start()
    }

    override fun done() {
        finish()
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onNextCalled(view: View, momentz: Momentz, index: Int) {
        currentMomentz = momentz
        if (view is VideoView) {
            momentz.pause(true)
            playVideo(view, index, momentz)
        } else if ((view is ImageView)) {
            momentz.pause(true)
            val url = CR.resources.get(index)
            Picasso.with(this).invalidate(url);
            Picasso.with(this).load(CR.resources.get(index))
                    .networkPolicy(NetworkPolicy.NO_CACHE,NetworkPolicy.NO_STORE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(view, object : Callback {
                        override fun onSuccess() {
                            momentz.resume()
                        }
                        override fun onError() {

                        }
                    })
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun playVideo(videoView: VideoView, index: Int, momentz: Momentz) {
        val str = CR.resources.get(index)
        val uri = Uri.parse(str)
        videoView.setVideoURI(uri)
        videoView.requestFocus()
        videoView.start()
        videoView.setOnInfoListener(object : MediaPlayer.OnInfoListener {
            override fun onInfo(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                    momentz.editDurationAndResume(index, (videoView.duration) / 1000)
                    return true
                }
                return false
            }
        })
    }


}