# Keep Hilt
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }

# Keep Room
-keep class com.notesapp.data.local.entity.** { *; }
-keep class * extends androidx.room.RoomDatabase { *; }

# Keep Compose
-keep class androidx.compose.** { *; }

# Keep Kotlin Serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
