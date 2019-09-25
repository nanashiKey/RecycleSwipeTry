package com.ngopidev.project.rcviewtry

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.*
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import com.ngopidev.project.rcviewtry.helperz.ApiOnly
import com.ngopidev.project.rcviewtry.helperz.HelperForItemTouch
import com.ngopidev.project.rcviewtry.helperz.ItemData
import com.ngopidev.project.rcviewtry.helperz.MainClassAdapter
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() , HelperForItemTouch.RecyclerItemTouchHelperListener{
    var rcView : RecyclerView? = null

    var adapters :MainClassAdapter? = null
    var listofData : MutableList<ItemData>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        getSupportActionBar()!!.setTitle(getString(R.string.my_cart))
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)

        rcView = findViewById(R.id.rcView)
        rcView!!.setHasFixedSize(true)
        rcView!!.layoutManager = LinearLayoutManager(this@MainActivity)
        rcView!!.itemAnimator = DefaultItemAnimator()
        rcView!!.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))

        val itemTouchHelper = HelperForItemTouch(0, ItemTouchHelper.LEFT, this)
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(rcView)

        executeDataforList()
    }


    fun executeDataforList(){
        val gson  = GsonBuilder().setLenient().create()

        val retro = Retrofit.Builder()
            .baseUrl("https://api.androidhive.info/json/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val apiexecute = retro.create(ApiOnly::class.java)

        listofData = ArrayList<ItemData>()
        adapters = MainClassAdapter()

        apiexecute.getAllData().enqueue(object : Callback<MutableList<ItemData>>{
            override fun onResponse(call: Call<MutableList<ItemData>>, response: Response<MutableList<ItemData>>) {
                if(response.isSuccessful){
                    listofData = response.body()
                    adapters = MainClassAdapter(this@MainActivity, listofData!!)
                    rcView!!.adapter = adapters
                }else{
                    Toast.makeText(this@MainActivity, "there is an error in : ${response}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MutableList<ItemData>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "there is an error in : ${t.printStackTrace()}", Toast.LENGTH_SHORT).show()

            }

        })


    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
        if (viewHolder is MainClassAdapter.MainViewHolder) {
            val name = listofData!!.get(viewHolder.adapterPosition).name

            val deletedItem = listofData!!.get(viewHolder.adapterPosition)
            val deletedIndex = viewHolder.adapterPosition

            adapters!!.removeItem(viewHolder.adapterPosition)
            val snackBar = Snackbar.make(coordinator, "${name} is removed from chart", Snackbar.LENGTH_LONG)
            snackBar.setAction("UNDO", object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    adapters!!.restoreItem(deletedItem, deletedIndex)
                }
            })
            snackBar.setActionTextColor(Color.YELLOW)
            snackBar.show()
        }
    }
}
