# play2-ascii-art-plugin


With this plugin, You will see cool ascii arts on starting/stopping your Play application.
They will motivate and accelerate your development. That is, AADD (Ascii Art Driven Development).


The original idea is from https://github.com/cb372/scala-ascii-art

![Yotuba](screenshot.jpg)

ref: http://yotuba.com/

## Install
In your build.sbt or Build.scala.
```scala
libraryDependencies += "com.github.tototoshi" %% "play2-ascii-art-plugin" % "0.3.0"
```

In your play.plugins.
```
1000:com.github.tototoshi.play2.aa.AAPlugin
```

## Configuration

Specify the name of resource to show as ascii-art in your conf/application.conf.
For example, write as follows if you want to show `conf/yotuba1.jpg` on starting your application
and `conf/yotuba2.jpg` on stopping.
```
aa.image.onstart=yotuba1.jpg
aa.image.onstop=yotuba2.jpg
```

URL can also be used.

```
aa.image.onstart="http://......"
```

The width of ascii-art is 80 by default. But maybe sometimes your images are too big or small.
You can specify the width of ascii-art to adjust the image to your console.
```
aa.image.width=60
```
