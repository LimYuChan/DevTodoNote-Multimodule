package com.note.core.common.annotation

import com.note.core.common.NoteDispatcher
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: NoteDispatcher)
