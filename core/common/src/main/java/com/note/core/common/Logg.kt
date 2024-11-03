package com.note.core.common

import android.util.Log


object Logg {
    private const val LIMIT_LENGTH = 80

    private fun tag(): String {
        val traceElement = Thread.currentThread().stackTrace[4]
        val fileName = traceElement.fileName
        val lineNumber = traceElement.lineNumber
        val link = "($fileName:$lineNumber)"
        val className = traceElement.className.substringAfterLast(".")
        val methodName = traceElement.methodName
        val path = "App# $className.$methodName"
        val result = if(path.length + link.length > LIMIT_LENGTH) {
            val splitPath = path.take(LIMIT_LENGTH - link.length)
            "$splitPath...$link"
        }else{
            "$path$link"
        }
        return result
    }

    fun v(message: String?) {
        Log.v(tag(), message ?: "")
    }

    fun d(message: String?) {
        Log.d(tag(), message ?: "")
    }

    fun i(message: String?) {
        Log.i(tag(), message ?:"")
    }

    fun w(message: String?) {
        Log.w(tag(), message ?: "")
    }

    fun w(throwable: Throwable?) {
        Log.w(tag(), throwable?.localizedMessage ?: "")
        throwable?.printStackTrace()
    }

    fun w(exception: Exception?) {
        Log.w(tag(), exception?.localizedMessage ?: "")
        exception?.printStackTrace()
    }

    fun e(message: String?) {
        Log.e(tag(), message ?: "")
    }

    fun e(throwable: Throwable?) {
        Log.e(tag(), throwable?.localizedMessage ?: "")
        throwable?.printStackTrace()
    }

    fun e(exception: Exception?) {
        Log.e(tag(), exception?.localizedMessage ?: "")
        exception?.printStackTrace()
    }
}