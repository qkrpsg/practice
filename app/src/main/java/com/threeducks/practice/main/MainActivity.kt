package com.threeducks.practice.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.threeducks.practice.R
import com.threeducks.practice.api.RetrofitAPI
import com.threeducks.practice.api.ResponseData
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private var list = arrayListOf<Item>()
    private var retrofit: Retrofit? = null
    private var service: RetrofitAPI? = null
    private var call: Call<ResponseData>? = null


    private val key = "26524067-620b8028f0976478ef76f7e15"
    private var page = 97
    private val perPage = "200"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setRetrofit()
        scrollCheck()
    } // onCreate()

    // 최초 데이터
    private fun setRetrofit() {
        retrofit = Retrofit.Builder()
            .baseUrl("https://pixabay.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()

        service = retrofit?.create(RetrofitAPI::class.java)

        call = service?.getDataInfo(key, perPage, page.toChar().toString())
        callData()
    } // setRetrofit()

    private fun scrollCheck() {
        // 스크롤 마지막
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(1)) {
                    Log.d("Scroll", "last position")
                    loadMorePosts()
                }
            }
        })
    } // scrollCheck()

    private fun loadMorePosts() {
        if (page < 122){
            page++
        } else {
            page = 97
        }
        callData()

    } // loadMorePosts()

    private fun callData(){
        call = service?.getDataInfo(key, perPage, page.toChar().toString())
        call?.enqueue(object: Callback<ResponseData> {
            // 성공
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
//                Toast.makeText(applicationContext, "성공", Toast.LENGTH_SHORT).show()
                var jsonList = response.body()!!.mainData

                var index = 0
                while (index < jsonList.size) {
                    list.add(Item(jsonList[index].image.toString(), jsonList[index].tags.toString()))
                    index++
                }

                val adapter = CustomAdapter(list)
                recyclerView.adapter = adapter

                val gridLayoutManager = GridLayoutManager(applicationContext, 2)
                recyclerView.layoutManager = gridLayoutManager
            }
            // 실패
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
//                Toast.makeText(applicationContext, "실패", Toast.LENGTH_SHORT).show()
            }
        })
    } // callData()

    private fun createOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)
        return builder.build()
    } // createOkHttpClient()

} // class :: MainActivity