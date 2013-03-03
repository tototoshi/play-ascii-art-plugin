# play2-ascii-art-plugin

The original idea is from https://github.com/cb372/scala-ascii-art

![Yotuba](screenshot.jpg)

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

Run with -Djava.awt.headless=true
```
 $ play -Djava.awt.headless=true run
```
