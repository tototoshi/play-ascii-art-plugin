# play2-ascii-art-plugin


With this plugin, You will see cool ascii arts on starting/stopping your Play application. 
They will motivate and accelerate your development. That is, AADD (Ascii Art Driven Development).


The original idea is from https://github.com/cb372/scala-ascii-art

![Yotuba](screenshot.jpg)

ref: http://yotuba.com/

## Install
```scala
libraryDependencies ++= "com.github.tototoshi" %% "play2-ascii-art-plugin" % "0.1.0"
```

## Configuration

play.plugins
```
1000:com.github.tototoshi.play2.aa.AAPlugin
```

conf/application.conf
```
aa.image.onstart=conf/yotuba1.jpg
aa.image.onstop=conf/yotuba2.jpg
aa.image.width=60
```

