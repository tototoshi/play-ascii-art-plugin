package com.github.tototoshi.play2.aa

import play.api._
import scala.math._
import java.io.{InputStream, File}
import javax.imageio._
import java.awt.{Color, RenderingHints}
import java.awt.image._
import resource._

class AAPlugin(implicit app: Application) extends Plugin {

  private val asciiChars = List("#", "A", "@", "%", "$", "+", "=", "*", ":", ",", ".", " ")

  private val JAVA_AWT_HEADLESS = "java.awt.headless"

  private def rgbMax(red: Int, green: Int, blue: Int) =
    List(red, green, blue).reduceLeft(max)

  private def chooseChar(rgbPeak: Double) = rgbPeak match {
    case 0 => asciiChars.last
    case n => {
      val index = ((asciiChars.length * (rgbPeak / 255)) - (0.5)).toInt
      asciiChars(index)
    }
  }

  private def scaleImage(image: BufferedImage, width: Int) = {
    val height = ((width.toDouble / image.getWidth) * image.getHeight).toInt
    val scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    val gfx = scaledImage.createGraphics()
    gfx.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)
    gfx.drawImage(image, 0, 0, width, height, null)
    gfx.dispose
    scaledImage
  }

  private def convertImage(image: BufferedImage) = {
    val h = image.getHeight()
    val w = image.getWidth()
    for (y <- 0 to h - 1) yield {
      for (x <- 0 to w - 1) yield {
        val pixel = new Color(image.getRGB(x, y))
        chooseChar(rgbMax(pixel.getRed, pixel.getGreen, pixel.getBlue))
      }
    }
  }

  private def createAsciiArt(imageFile: InputStream, width: Int): String = {
    val image = scaleImage(ImageIO.read(imageFile), width)
    val spans = convertImage(image)
    spans.map(_.mkString).mkString("\n")
  }

  private def imageWidth: Int = {
    val defaultWidth = 150
    app.configuration.getInt("aa.image.width").getOrElse(defaultWidth)
  }

  private def withHeadless(f: => Unit): Unit = {
    val backup = Option(System.getProperty(JAVA_AWT_HEADLESS)).getOrElse("false")
    System.setProperty(JAVA_AWT_HEADLESS, "true")
    f
    System.setProperty(JAVA_AWT_HEADLESS, backup)
  }

  private def printAA(resourceName: String): Unit = {
    for {
      r <- app.configuration.getString(resourceName)
      _ = Logger.debug(resourceName + ": " + r)
      in <- Play.resourceAsStream(r)
      managedIn <- managed(in)
    } {
      println(createAsciiArt(managedIn, imageWidth))
    }
  }


  /**
   * Called when the application starts.
   */
  override def onStart(): Unit =
    withHeadless { printAA("aa.image.onstart") }

  /**
   * Called when the application stops.
   */
  override def onStop(): Unit =
    withHeadless { printAA("aa.image.onstop") }

  /**
   * Is the plugin enabled?
   */
  override def enabled: Boolean = true

}
