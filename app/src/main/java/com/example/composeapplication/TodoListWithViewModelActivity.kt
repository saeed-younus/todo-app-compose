package com.example.composeapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.state
import androidx.compose.unaryPlus
import androidx.lifecycle.LifecycleOwner
import androidx.ui.core.*
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.shape.border.Border
import androidx.ui.graphics.Color
import androidx.ui.input.ImeAction
import androidx.ui.input.KeyboardType
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.material.TextButtonStyle
import androidx.ui.material.surface.Surface
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight

class TodoListWithViewModelActivity : AppCompatActivity(), LifecycleOwner {

    private val viewModel: TodoListViewModel = TodoListViewModel()

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
        val editItem = +state<TodoListModel?> { null }
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
                                        val text = textFieldValue.value
                                        editItem.value?.let {
                                            viewModel.editItem(it.copy(text = text))
                                        } ?: run {
                                            viewModel.addItem(TodoListModel(text = text))
                                        }
                                        editItem.value = null
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
                    Button(text = editItem.value?.let { "edit" } ?: run { "Add" }, onClick = {
                        if (textFieldValue.value.isNotEmpty()) {
                            val text = textFieldValue.value
                            editItem.value?.let {
                                viewModel.editItem(it.copy(text = text))
                            } ?: run {
                                viewModel.addItem(TodoListModel(text = text))
                            }
                            editItem.value = null
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
                        val list = viewModel.list
                        list.forEachIndexed { index, item ->
                            Row(
                                modifier = Spacing(16.dp),
                                mainAxisSize = LayoutSize.Expand,
                                mainAxisAlignment = MainAxisAlignment.SpaceBetween
                            ) {
                                Container(width = 220.dp, alignment = Alignment.TopLeft) {
                                    Text(
                                        text = item.text,
                                        style = TextStyle(
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.W600
                                        )
                                    )
                                }
                                Row(
                                    mainAxisAlignment = MainAxisAlignment.End
                                ) {
                                    Button(text = "Edit", style = TextButtonStyle(), onClick = {
                                        editItem.value = item
                                        textFieldValue.value = item.text
                                    })
                                    Button(
                                        text = "Del",
                                        style = TextButtonStyle(contentColor = Color.Red),
                                        onClick = {
                                            viewModel.removeItem(item)
                                        }
                                    )
                                }
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
