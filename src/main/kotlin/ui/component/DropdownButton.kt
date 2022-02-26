package ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun <T> DropdownSelectionButton(
    value: T,
    options: Iterable<T>,
    onOptionClicked: (T) -> Unit,
    label: @Composable (() -> Unit)? = null,
    itemContent: @Composable (T) -> Unit
) {
    DropdownButton(
        content = { itemContent(value) },
        options = options,
        onOptionClicked = onOptionClicked,
        label = label,
        dropdownItemContent = itemContent
    )
}

@Composable
fun <T> DropdownButton(
    content: @Composable () -> Unit,
    options: Iterable<T>,
    onOptionClicked: ((T) -> Unit)? = null,
    label: @Composable (() -> Unit)? = null,
    dropdownItemContent: @Composable (T) -> Unit
) {
    var isDropdownExpanded by remember { mutableStateOf(false) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        label?.invoke()

        Button(onClick = {
            isDropdownExpanded = true
        }) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                content()
                Spacer(Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        DropdownMenu(
            expanded = isDropdownExpanded,
            onDismissRequest = {
                isDropdownExpanded = false
            }
        ) {
            options.forEach {
                DropdownMenuItem(onClick = {
                    isDropdownExpanded = false
                    onOptionClicked?.invoke(it)
                }) {
                    dropdownItemContent.invoke(it)
                }
            }
        }
    }
}