package com.note.core.ui.extension

import android.content.Context
import com.note.core.ui.R
import java.util.Locale

fun Context.getLanguageColor(language: String?): Int{
    return when(language?.lowercase(Locale.getDefault())){
        "kotlin"-> R.color.kotlin_color
        "java"-> R.color.java_color
        "javascript"-> R.color.java_script_color
        "swift"-> R.color.swift_color
        "html"-> R.color.html_color
        "css"-> R.color.css_color
        "php"-> R.color.php_color
        "python"-> R.color.python_color
        "vue"-> R.color.vue_color
        "c"-> R.color.c_color
        "c#"-> R.color.c_hash_color
        "c++"-> R.color.c_2plus_color
        "ruby"-> R.color.ruby_color
        "dart"-> R.color.dart_color
        else-> R.color.language_default_color
    }
}