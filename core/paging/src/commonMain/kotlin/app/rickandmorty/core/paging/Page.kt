package app.rickandmorty.core.paging

public const val FIRST_PAGE_KEY: Int = 1

public data class Page<out T>(
  val items: List<T>,
  val nextPage: Int?,
  val status: PageStatus = PageStatus.Idle,
)

public sealed interface PageStatus {
  public data object Idle : PageStatus

  public data object Loading : PageStatus

  public data object Loaded : PageStatus

  public data class Failed(val error: Throwable) : PageStatus
}
