package com.sepisoad.bongah.ui.helper

import com.sepisoad.bongah.model.ColleagueInfo

interface OnAfterSaveAction {
  fun handle(): Unit
}

interface OnWhenPicturesReady{
  fun handle(pictures: MutableList<String>): Unit
}

interface FindReferrerIdActionHandler{
  fun handle(obj: Any?)
}

interface UpdateActionHandler{
  fun handle(obj: Any): Boolean
}