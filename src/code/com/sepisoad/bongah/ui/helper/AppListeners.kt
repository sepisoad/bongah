package com.sepisoad.bongah.ui.helper

import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.scene.control.TextField
import javafx.scene.control.TextFormatter
import javafx.scene.control.TextInputControl
import java.util.function.UnaryOperator

class SqlChangeListener(val obj: TextInputControl) : ChangeListener<String> {
  override fun changed(observable: ObservableValue<out String>?, oldValue: String?, newValue: String?) {
    if(null != newValue){
      if(newValue.contains("'") || newValue.contains("\"") || newValue.contains(",")){
        obj.text = oldValue
      }
    }
  }
}

class IntegerNumericChangeListener(val obj: TextInputControl) : ChangeListener<String> {
  override fun changed(observable: ObservableValue<out String>?, oldValue: String?, newValue: String?) {
    if(null != newValue) {
      if (newValue.matches(Regex("[0-9]+"))) {
        obj.text = newValue
      }else{
        obj.text = newValue.dropLast(1)
      }
    }
  }
}

class FloatingNumericChangeListener(val obj: TextInputControl) : ChangeListener<String> {
  override fun changed(observable: ObservableValue<out String>?, oldValue: String?, newValue: String?) {
    if(null != newValue) {
      if (newValue.matches(Regex("([0-9]+)(.?)([0-9]+)?"))) {
        obj.text = newValue
      }else{
        obj.text = newValue.dropLast(1)
      }
    }
  }
}

class DateChangeListener(val obj: TextInputControl) : ChangeListener<String> {
  override fun changed(observable: ObservableValue<out String>?, oldValue: String?, newValue: String?) {
    if(null != newValue) {
      if (newValue.matches(Regex("[0-9.-/]+"))) {
        obj.text = newValue
      }else{
        obj.text = newValue.dropLast(1)
      }
    }
  }
}


