package com.example.composeapplication

import androidx.compose.Model
import androidx.lifecycle.ViewModel
import java.util.*

@Model
class TodoListViewModel {

    var list = listOf<TodoListModel>()

    fun addItem(item: TodoListModel) {
        val tempList = list.toMutableList()
        tempList.add(item)
        list = tempList
    }

    fun editItem(item: TodoListModel) {
        val tempList = list.map { oldItem ->
            if (oldItem.id == item.id) {
                item
            } else {
                oldItem
            }
        }
        list = tempList
    }

    fun removeItem(item: TodoListModel) {
        val tempList = list.filter { it.id != item.id }
        list = tempList
    }

}

data class TodoListModel(val id: Long = UUID.randomUUID().leastSignificantBits, var text: String)