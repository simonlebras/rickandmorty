# Allow Proguard to change modifiers
-allowaccessmodification

# Repackage classes into the top-level
-repackageclasses app.rickandmorty

# Preserve line number information for debugging stack traces
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Compose Composition Tracing
-assumenosideeffects public class androidx.compose.runtime.ComposerKt {
   boolean isTraceInProgress();
   void traceEventStart(int,int,int,java.lang.String);
   void traceEventStart(int,java.lang.String);
   void traceEventEnd();
}
