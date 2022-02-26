// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import model.Client

@Composable
@Preview
fun App() {
    var items: List<Client> by remember { mutableStateOf(getClients()) }

    MaterialTheme {
        Row {
            ListWithDetails(
                items = items.associateBy { it.id },
                onItemDeleteClick = {
                    val mutableList = items.toMutableList()
                    mutableList.remove(it)
                    items = mutableList
                },
                onNewClick = {
                    val mutableList = items.toMutableList()
                    mutableList.add(getClient(items.size))
                    items = mutableList
                },
                listItemContent = { ClientListItemView(it) },
                detailsItemContent = {
                    ClientDetailsView(it) { updatedItem ->
                        items = items.map {
                            if (it.id == updatedItem.id) {
                                updatedItem
                            } else {
                                it
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun <T, ID> ListWithDetails(
    items: Map<ID, T>,
    onItemClick: ((T) -> Unit)? = null,
    onItemDeleteClick: ((T) -> Unit)? = null,
    onNewClick: () -> Unit,
    listItemContent: @Composable (T) -> Unit,
    detailsItemContent: @Composable (T) -> Unit
) {
    var selectedItemId: ID? by remember { mutableStateOf(null) }
    selectedItemId = selectedItemId?.takeIf { items.containsKey(selectedItemId) }

    Row(modifier = Modifier.fillMaxWidth()) {
        List(
            listItems = items.entries.toList(),
            onItemContentClick = { item ->
                selectedItemId = item.key
                onItemClick?.invoke(item.value)
            },
            onItemDeleteClick = { item ->
                if (selectedItemId == item.key) {
                    selectedItemId = null
                }
                onItemDeleteClick?.invoke(item.value)
            },
            onNewItemClick = onNewClick,
            itemContent = { listItemContent(it.value) }
        )
        Details(
            selectedItemId?.let {
                items[it]
            }?.let {
                { detailsItemContent(it) }
            }
        )
    }
}

@Composable
fun Details(
    content: (@Composable () -> Unit)? = null
) {
    if (content == null) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                textAlign=TextAlign.Center,
                text="No item selected."
            )
        }
    } else {
        content.invoke()
    }
}


@Composable
fun <T> List(
    listItems: List<T>,
    onItemContentClick: (T) -> Unit,
    onItemDeleteClick: (T) -> Unit,
    onNewItemClick: () -> Unit,
    itemContent: @Composable (T) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        LazyColumn {
            items(listItems) {
                ListItem(
                    onContentClick = { onItemContentClick(it) },
                    onDeleteClick = { onItemDeleteClick(it) }
                ) {
                    itemContent(it)
                }
            }
        }
        Button(onClick = onNewItemClick) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}

@Composable
fun ListItem(onContentClick: () -> Unit, onDeleteClick: () -> Unit, content: @Composable () -> Unit) {
    Row {
        Box(
            modifier = Modifier.clickable(onClick = onContentClick)
        ) {
            content.invoke()
        }
        Box(
            modifier = Modifier.clickable(onClick = onDeleteClick)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}