package com.sepisoad.bongah.ui.widget

import javafx.scene.image.ImageView
import java.net.URI

class ImageViewEx(_uri: URI) : ImageView(_uri.toString()) {
  var uri: URI = _uri
    private set
}
