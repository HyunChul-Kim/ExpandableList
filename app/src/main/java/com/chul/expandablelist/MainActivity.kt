package com.chul.expandablelist

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.chul.expandablelist.databinding.ActivityMainBinding
import com.chul.expandablelist.model.ExpandableItem

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val adapter = Adapter(resources.getDimensionPixelSize(R.dimen.depth_padding))
        binding.a1.setOnClickListener {
            adapter.selectChild("child item A-1")
        }
        binding.a12.setOnClickListener {
            adapter.selectChild("child item A-1-2")
        }
        binding.b11.setOnClickListener {
            adapter.selectChild("child item B-1-1")
        }
        binding.d.setOnClickListener {
            adapter.selectChild("group item D")
        }
        val expandableList = listOf(
            ExpandableItem(
                title = "Group A",
                item = "group item A",
                children = listOf(
                    ExpandableItem(
                        title = "child A-1",
                        item = "child item A-1"
                    ),
                    ExpandableItem(
                        title = "child A-2",
                        item = "child item A-2"
                    ),
                    ExpandableItem(
                        title = "child A-3",
                        item = "child item A-3"
                    ),
                    ExpandableItem(
                        title = "Group A-1",
                        item = "group item A-1",
                        children = listOf(
                            ExpandableItem(
                                title = "child A-1-1",
                                item = "child item A-1-1"
                            ),
                            ExpandableItem(
                                title = "child A-1-2",
                                item = "child item A-1-2"
                            ),
                            ExpandableItem(
                                title = "child A-1-3",
                                item = "child item A-1-3"
                            )
                        )
                    )
                )
            ),
            ExpandableItem(
                title = "Group B",
                item = "group item B",
                children = listOf(
                    ExpandableItem(
                        title = "child B-1",
                        item = "child item B-1"
                    ),
                    ExpandableItem(
                        title = "child B-2",
                        item = "child item B-2"
                    ),
                    ExpandableItem(
                        title = "child B-3",
                        item = "child item B-3"
                    ),
                    ExpandableItem(
                        title = "Group B-1",
                        item = "group item B-1",
                        children = listOf(
                            ExpandableItem(
                                title = "child B-1-1",
                                item = "child item B-1-1"
                            ),
                            ExpandableItem(
                                title = "child B-1-2",
                                item = "child item B-1-2"
                            ),
                            ExpandableItem(
                                title = "child B-1-3",
                                item = "child item B-1-3"
                            )
                        )
                    ),
                    ExpandableItem(
                        title = "Group B-2",
                        item = "group item B-2",
                        children = listOf(
                            ExpandableItem(
                                title = "child B-2-1",
                                item = "child item B-2-1"
                            ),
                            ExpandableItem(
                                title = "child B-2-2",
                                item = "child item B-2-2"
                            ),
                            ExpandableItem(
                                title = "child B-2-3",
                                item = "child item B-2-3"
                            )
                        )
                    )
                )
            ),
            ExpandableItem(
                title = "Group C",
                item = "group item C",
            ),
            ExpandableItem(
                title = "Group D",
                item = "group item D",
            )
        )
        binding.recyclerView.adapter = adapter
        adapter.submitList(expandableList)
        adapter.selectChild("child item B-1-2")
    }
}