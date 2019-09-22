package com.example.tikihometestapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.videoaudioplayer.Adapter.MyAdapter
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val client = OkHttpClient()
    private lateinit var myAdapter: MyAdapter
    private var keywordList: Array<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        run("https://raw.githubusercontent.com/tikivn/android-home-test/v2/keywords.json")
    }

    fun run(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                val stringData = response.body()?.string()
                val gson = GsonBuilder().setPrettyPrinting().create()
                keywordList = gson.fromJson(stringData, Array<String>::class.java)

                runOnUiThread {
                    myAdapter = MyAdapter(keywordList)
                    viewManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                    recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
                        layoutManager = viewManager
                        adapter = myAdapter
                    }
                }
            }
        })
    }
}
