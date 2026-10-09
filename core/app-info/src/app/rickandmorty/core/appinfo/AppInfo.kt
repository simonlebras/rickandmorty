package app.rickandmorty.core.appinfo

public interface AppInfo {
  public val versionName: String
  public val versionCode: Long
  public val packageName: String
  public val isDebug: Boolean
}
