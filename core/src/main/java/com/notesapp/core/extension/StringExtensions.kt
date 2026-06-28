package com.notesapp.core.extension

fun String.truncate(maxLength: Int): String {
    return if (length <= maxLength) this else substring(0, maxLength).trimEnd() + "..."
}
fun String.removeHtmlTags(): String = replace(Regex("<[^>]*>"), "")
fun String.extractPreview(maxLength: Int = 150): String {
    return removeHtmlTags().replace(Regex("\\n+"), " ").trim().truncate(maxLength)
}
