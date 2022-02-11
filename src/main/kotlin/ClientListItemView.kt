import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.*
import model.Client

@Composable
fun ClientListItemView(
    client: Client
) {
    Column {
        Text(client.surname)
        Text(client.name)
    }
}