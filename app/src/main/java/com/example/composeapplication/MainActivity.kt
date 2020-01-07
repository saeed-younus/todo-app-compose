package com.example.composeapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.State
import androidx.compose.state
import androidx.compose.unaryPlus
import androidx.ui.core.Alignment
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.core.setContent
import androidx.ui.foundation.DrawImage
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.*
import androidx.ui.material.FloatingActionButton
import androidx.ui.material.MaterialTheme
import androidx.ui.material.surface.Card
import androidx.ui.res.imageResource
import androidx.ui.tooling.preview.Preview

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var counter = 0
        setContent {
            val state = +state { mutableListOf<String>() }
            MaterialTheme {
                //                persons()
                Stack {
                    observerList(state)
                    Container(
                        width = 40.dp,
                        height = 40.dp,
                        alignment = Alignment.BottomRight,
                        modifier = Spacing(16.dp)
                    ) {
                        FloatingActionButton(
                            icon = +imageResource(R.drawable.ic_add),
                            elevation = 6.dp,
                            onClick = {
                                val tempList = state.value
                                tempList.add("${counter++} Name")
                                state.value = tempList
                            })
                    }
                }
            }
        }
    }
}

val list = listOf(
    "Saeed",
    "Asad",
    "Azeem",
    "Rafay Ali",
    "Jawaid"/*, "Saeed", "Asad", "Azeem", "Rafay Ali", "Jawaid", "Saeed", "Asad", "Azeem", "Rafay Ali", "Jawaid"*/
)

@Preview
@Composable
fun persons() {
    VerticalScroller {
        Row(
            crossAxisAlignment = CrossAxisAlignment.Start, crossAxisSize = LayoutSize.Expand,
            mainAxisSize = LayoutSize.Expand
        ) {
            Column {
                list.forEach { name ->
                    val image = +imageResource(R.drawable.image)
                    Row(
                        mainAxisSize = LayoutSize.Expand,
                        modifier = Spacing(8.dp)
                    ) {
                        Container(width = 32.dp, height = 32.dp) {
                            DrawImage(image = image)
                        }
                        WidthSpacer(width = 16.dp)
                        Text(text = name)
                    }
                }
            }
        }
    }
}

@Composable
fun observerList(list: State<MutableList<String>>) {
    VerticalScroller {
        Column(mainAxisSize = LayoutSize.Expand, crossAxisSize = LayoutSize.Expand) {
            list.value.forEach { listItem ->
                Row(modifier = Spacing(8.dp)) {
                    Text(listItem)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(
        text = "Hey $name!"
    )
}

@Preview
@Composable
fun Lists() {
    Card(
        shape = RoundedCornerShape(8),
        elevation = 2.dp
    ) {
        Container(alignment = Alignment.Center, expanded = true, height = 20.dp) {
            Row {
                VerticalScroller {
                    Column {
                        Greeting(name = "Saeed")
                        Greeting(name = "Asad")
                        Greeting(name = "Saleem")
                        Greeting(name = "Azeem")
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    fun DefaultPreview() {
        MaterialTheme {
            Greeting("Saeed")
        }
    }
}
