package com.chul.expandablelist

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.chul.expandablelist.model.ChildItem
import com.chul.expandablelist.model.ExpandableItem
import com.chul.expandablelist.model.ExpandableItemType

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val expandableList = listOf(
            ExpandableItemType.Group(
                item = ExpandableItem(
                    title = "Group A",
                    item = "group item A",
                    children = listOf(
                        ExpandableItemType.Child(
                            item = ChildItem(
                                title = "child A-1",
                                item = "child item A"
                            )
                        ),
                        ExpandableItemType.Child(
                            item = ChildItem(
                                title = "child A-2",
                                item = "child item A-2"
                            )
                        ),
                        ExpandableItemType.Child(
                            item = ChildItem(
                                title = "child A-3",
                                item = "child item A-3"
                            )
                        ),
                        ExpandableItemType.Group(
                            item = ExpandableItem(
                                title = "Group A-1",
                                item = "group item A-1",
                                children = listOf(
                                    ExpandableItemType.Child(
                                        item = ChildItem(
                                            title = "child A-1-1",
                                            item = "child item A-1-1"
                                        )
                                    ),
                                    ExpandableItemType.Child(
                                        item = ChildItem(
                                            title = "child A-1-2",
                                            item = "child item A-1-2"
                                        )
                                    ),
                                    ExpandableItemType.Child(
                                        item = ChildItem(
                                            title = "child A-1-3",
                                            item = "child item A-1-3"
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            ),
            ExpandableItemType.Group(
                item = ExpandableItem(
                    title = "Group B",
                    item = "group item B",
                    children = listOf(
                        ExpandableItemType.Child(
                            item = ChildItem(
                                title = "child B-1",
                                item = "child item B-1"
                            )
                        ),
                        ExpandableItemType.Child(
                            item = ChildItem(
                                title = "child B-2",
                                item = "child item B-2"
                            )
                        ),
                        ExpandableItemType.Child(
                            item = ChildItem(
                                title = "child B-3",
                                item = "child item B-3"
                            )
                        ),
                        ExpandableItemType.Group(
                            item = ExpandableItem(
                                title = "Group B-1",
                                item = "group item B-1",
                                children = listOf(
                                    ExpandableItemType.Child(
                                        item = ChildItem(
                                            title = "child B-1-1",
                                            item = "child item B-1-1"
                                        )
                                    ),
                                    ExpandableItemType.Child(
                                        item = ChildItem(
                                            title = "child B-1-2",
                                            item = "child item B-1-2"
                                        )
                                    ),
                                    ExpandableItemType.Child(
                                        item = ChildItem(
                                            title = "child B-1-3",
                                            item = "child item B-1-3"
                                        )
                                    )
                                )
                            )
                        ),
                        ExpandableItemType.Group(
                            item = ExpandableItem(
                                title = "Group B-2",
                                item = "group item B-2",
                                children = listOf(
                                    ExpandableItemType.Child(
                                        item = ChildItem(
                                            title = "child B-2-1",
                                            item = "child item B-2-1"
                                        )
                                    ),
                                    ExpandableItemType.Child(
                                        item = ChildItem(
                                            title = "child B-2-2",
                                            item = "child item B-2-2"
                                        )
                                    ),
                                    ExpandableItemType.Child(
                                        item = ChildItem(
                                            title = "child B-2-3",
                                            item = "child item B-2-3"
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            ),
            ExpandableItemType.Group(
                item = ExpandableItem(
                    title = "Group C",
                    item = "group item C",
                )
            ),
            ExpandableItemType.Group(
                item = ExpandableItem(
                    title = "Group D",
                    item = "group item D",
                )
            )
        )
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = Adapter(resources.getDimensionPixelSize(R.dimen.depth_padding))
        recyclerView.adapter = adapter
        adapter.submitList(expandableList)
    }
}