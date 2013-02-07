package com.github.tototoshi.play2.aa

import play.api._
import scala.math._
import java.io.File
import javax.imageio._
import java.awt.{ Color, RenderingHints }
import java.awt.image._

class AAPlugin(app: Application) extends Plugin {

  private val asciiChars = List("#","A","@","%", "$","+","=","*", ":",",","."," ")

  private def rgbMax(red:Int, green:Int, blue:Int) =
    List(red, green, blue).reduceLeft(max)

  private def chooseChar(rgbPeak:Double) = rgbPeak match {
    case 0 => asciiChars.last
    case n => {
      val index = ((asciiChars.length * (rgbPeak / 255)) - (0.5)).toInt
      asciiChars(index)
    }
  }

  private def scaleImage(image:BufferedImage, width:Int) = {
    val height = ((width.toDouble / image.getWidth) * image.getHeight).toInt
    val scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    val gfx = scaledImage.createGraphics()
    gfx.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)
    gfx.drawImage(image, 0, 0, width, height, null)
    gfx.dispose
    scaledImage
  }

  private def convertImage(image:BufferedImage) = {
    val h = image.getHeight()
    val w = image.getWidth()
    for (y <- 0 to h-1) yield {
      for (x <- 0 to w-1) yield {
        val pixel = new Color(image.getRGB(x, y))
        chooseChar(rgbMax(pixel.getRed, pixel.getGreen, pixel.getBlue))
      }
    }
  }

  private def createAsciiArt(imageFile: File, width: Int): String = {
    val image = scaleImage(ImageIO.read(imageFile), width)
    val spans = convertImage(image)
    spans.map(_.mkString).mkString("\n")
  }

  private def imageWidth: Int = {
    val defaultWidth = 150
    app.configuration.getInt("aa.image.width").getOrElse(defaultWidth)
  }

  /**
   * Called when the application starts.
   */
  override def onStart(): Unit = {
    app.configuration.getString("aa.image.onstart").foreach { image =>
      println(createAsciiArt(new File(image), imageWidth))
    }
  }

  /**
   * Called when the application stops.
   */
  override def onStop(): Unit = {
    app.configuration.getString("aa.image.onstop").foreach { image =>
      println(createAsciiArt(new File(image), imageWidth))
    }
  }

  /**
   * Is the plugin enabled?
   */
  override def enabled: Boolean = true

}
