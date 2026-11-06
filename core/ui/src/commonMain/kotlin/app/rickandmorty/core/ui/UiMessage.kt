package app.rickandmorty.core.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString

@Immutable
public class UiMessage<out A>(
  public val text: StringResource,
  public val actionLabel: StringResource? = null,
  public val action: A? = null,
)

@Composable
public fun <A> UiMessages(
  messages: List<UiMessage<A>>,
  snackbarHostState: SnackbarHostState,
  onAction: (A) -> Unit,
  onMessageShown: (UiMessage<A>) -> Unit,
) {
  val currentOnAction by rememberUpdatedState(onAction)
  val currentOnMessageShown by rememberUpdatedState(onMessageShown)
  val message = messages.firstOrNull() ?: return
  LaunchedEffect(message) {
    val result =
      snackbarHostState.showSnackbar(
        message = getString(message.text),
        actionLabel = message.actionLabel?.let { getString(it) },
      )
    currentOnMessageShown(message)
    if (result == SnackbarResult.ActionPerformed) message.action?.let(currentOnAction)
  }
}
