package com.example.composeapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.state
import androidx.compose.unaryPlus
import androidx.ui.core.*
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.shape.border.Border
import androidx.ui.graphics.Color
import androidx.ui.input.ImeAction
import androidx.ui.input.KeyboardType
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.material.surface.Surface
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight

class EditTextListingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                body()
            }
        }
    }

    @Composable
    fun body() {
        val listState = +state { mutableListOf<String>() }
        val textFieldValue = +state { "" }
        Stack {
            Row(mainAxisAlignment = MainAxisAlignment.SpaceBetween) {
                Padding(padding = 8.dp) {
                    Surface(border = Border(color = Color.Black, width = 1.dp)) {
                        Container(
                            width = 300.dp,
                            height = 50.dp,
                            padding = EdgeInsets(8.dp)
                        ) {
                            TextField(
                                value = textFieldValue.value,
                                focusIdentifier = "1",
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Send,
                                onImeActionPerformed = {
                                    if (textFieldValue.value.isNotEmpty()) {
                                        val list = listState.value
                                        list.add(textFieldValue.value)
                                        listState.value = list
                                    }
                                    textFieldValue.value = ""
                                },
                                onValueChange = { stateValue ->
                                    Log.d("TAG", "TextFIeld value = $stateValue")
                                    textFieldValue.value = stateValue
                                }
                            )
                        }
                    }
                }
                Container(width = 100.dp, height = 50.dp, modifier = Spacing(8.dp)) {
                    Button(text = "Add", onClick = {
                        if (textFieldValue.value.isNotEmpty()) {
                            val list = listState.value
                            list.add(textFieldValue.value)
                            listState.value = list
                        }
                        textFieldValue.value = ""
                    })
                }
            }
            Padding(padding = EdgeInsets(top = 60.dp)) {
                VerticalScroller {
                    Column(
                        crossAxisSize = LayoutSize.Expand
                    ) {
                        val list = listState.value
                        list.forEachIndexed { index, item ->
                            Row(modifier = Spacing(16.dp)) {
                                Text(
                                    text = item,
                                    style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.W900)
                                )
                            }
                            if (list.size - 1 != index) {
                                Surface(color = Color.Black) {
                                    Container(expanded = true, height = 1.dp) {}
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
