package com.notesapp.core.util

import android.util.Log

object Logger {
    private const val APP_TAG = "NotesApp"
    fun v(tag: String, msg: String) = Log.v("$APP_TAG:$tag", msg)
    fun d(tag: String, msg: String) = Log.d("$APP_TAG:$tag", msg)
    fun i(tag: String, msg: String) = Log.i("$APP_TAG:$tag", msg)
    fun w(tag: String, msg: String, tr: Throwable? = null) {
        if (tr != null) Log.w("$APP_TAG:$tag", msg, tr) else Log.w("$APP_TAG:$tag", msg)
    }
    fun e(tag: String, msg: String, tr: Throwable? = null) {
        if (tr != null) Log.e("$APP_TAG:$tag", msg, tr) else Log.e("$APP_TAG:$tag", msg)
    }
}
