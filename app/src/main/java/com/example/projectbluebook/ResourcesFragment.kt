package com.example.projectbluebook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.google.android.youtube.player.*
import kotlinx.android.synthetic.main.fragment_resources.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResourcesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResourcesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var mVideoId: String? = null
    private val RECOVERY_DIALOG_REQUEST = 1
    private var mPlayer: YouTubePlayer? = null
   // private val youtubePlayerProvider: YouTubePlayer.Provider
        //get() = fl_youtube as YouTubePlayerView
    //= parentFragmentManager.findFragmentById(R.id.fl_youtube) as YouTubePlayerSupportFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // YouTube API

        init_youtube()
        //setupWebView()
        //val webView = v.findViewById<WebView>(R.id.wv_resources)
//        wv_resources.webViewClient = WebViewClient()
//        wv_resources.settings.javaScriptEnabled = true
//        wv_resources.loadUrl("midlmeditation.com")

    }

    private fun init_youtube() {
        //youtubeView = fl_youtube as YouTubePlayerView
        var youtubeView = youTubePlayerView as YouTubePlayerView
        youtubeView!!.initialize("AIzaSyAjcNwTQMskkTeHPZgL7sxBGepBURu8Rx0", object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider?,
                player: YouTubePlayer?,
                wasRestored: Boolean
            ) {
                if (player!! == null) return
                if (wasRestored!!) {
                    player!!.play()
                } else {
                    player!!.cueVideo("Z73zgJ1CKEc")
                    player!!.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
                }
            }

            override fun onInitializationFailure(
                provider: YouTubePlayer.Provider?,
                errorReason: YouTubeInitializationResult?
            ) {
                if (errorReason!!.isUserRecoverableError) {
                    errorReason!!.getErrorDialog(activity, 1).show()
                } else {
                    Toast.makeText(activity, errorReason.toString(), Toast.LENGTH_LONG).show()
                }
            }

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_resources, container, false)
        // Inflate the layout for this fragment
        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ResourcesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResourcesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    private fun setupWebView() {
//        wv_resources.webViewClient = WebViewClient()
//
//        wv_resources.apply {
//            settings.javaScriptEnabled = true
//            loadUrl("midlmeditation.com")
//        }
    }
}