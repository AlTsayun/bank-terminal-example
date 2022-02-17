// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import model.Client
import java.util.UUID

typealias CallableComposable = @Composable () -> Unit
typealias IdToListDetailsItem = Map<UUID, ListDetailsItem>

@Composable
@Preview
fun App() {
    var items: List<Client> by remember { mutableStateOf(getClients()) }
    val idToItem = items
        .map { it ->
            ListDetailsItem(it.id,
                @Composable { ClientListItemView(it) },
                @Composable {
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
        .associateBy { it.id }

    MaterialTheme {
        Row {
            ListWithDetails(idToItem, true)
        }
    }
}

@Composable
fun ListWithDetails(
    idToItem: IdToListDetailsItem,
    showBothPanes: Boolean
) {
    var selectedItemId: UUID? by remember { mutableStateOf(null) }
    if (showBothPanes) {
        Row {
            List(idToItem.values.map { it.listItem }) {
                selectedItemId = it.id
            }
            Details(selectedItemId?.let { idToItem[it]?.detailsItem } )
        }
    } else {

    }
}

@Composable
fun Details(
    content: DetailsItem? = null
) {
    if (content == null) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                textAlign=TextAlign.Center,
                text="No item selected."
            )
        }
    } else {
        content.place()
    }
}


@Composable
fun List(
    items: List<ListItem>,
    onSelectionChanged: (ListItem) -> Unit
) {
    LazyColumn(Modifier.fillMaxHeight()) {
        items(items) { item ->
            ItemRow({ onSelectionChanged(item) }) @Composable {
                item.place()
            }
        }
    }
}

@Composable
fun ItemRow(
    onSelectionChange: () -> Unit,
    content: CallableComposable
) {
    Row(
        Modifier
            .clickable(onClick = onSelectionChange)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        content.invoke()
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}

open class ListDetailsItem(
    val id: UUID,
    listItemContent: CallableComposable,
    detailsContent: CallableComposable
) {
    val listItem: ListItem = ListItem(id, listItemContent)
    val detailsItem: DetailsItem = DetailsItem(id, detailsContent)
}

class ListItem(val id: UUID, private val content: CallableComposable) {
    @Composable
    fun place() {
        content.invoke()
    }
}

class DetailsItem(val id: UUID, private val content: CallableComposable) {
    @Composable
    fun place() {
        content.invoke()
    }
}