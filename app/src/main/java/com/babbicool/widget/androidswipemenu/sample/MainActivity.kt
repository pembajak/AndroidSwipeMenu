package com.babbicool.widget.androidswipemenu.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.BaseAdapter
import android.widget.TextView
import com.babbicool.widget.androidswipemenu.R
import com.babbicool.widget.androidswipemenu.SwipeMenuLayout
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {


    lateinit var mLayoutManager: LinearLayoutManager

    lateinit var testAdapter: TestAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testAdapter = TestAdapter()

        mLayoutManager = LinearLayoutManager(this)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL

        recyclerView!!.let {
            it.adapter = testAdapter
            it.layoutManager = mLayoutManager
        }


        sirmulate()

    }


    private fun sirmulate(){
        recyclerView.postDelayed({
            val datas = ArrayList<FakeModel>()
            for (i in 0..5) {
                val fakeModel = FakeModel()
                fakeModel.id = i
                datas.add(fakeModel)
            }

            testAdapter.submitList(datas)

        },2000)

    }




}
