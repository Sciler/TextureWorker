# TextureWorker

## What is TextureWorker and for what can I use it?

TextureWorker is a (very) easy to use GUI for the texture packer that comes with LibGDX.

## Are there any system requirements?

You only need to have installed Java 1.6 and above. TextureWorker should work pretty well on Windows, OS X and Linux distributions.
There isn't an installer because TextureWorker works as a portable software, that means that you just need to run the .jar file. Please see "How to compile"!

## Does this represent the final version of TextureWorker in any way?

No. The currently TextureWorker is in Alpha stage, but it should work pretty well anyway. Whatever a few features are missing at this time, but should be added in future versions.

## Any last words?

Not really, just have fun while develop and use this software when you need to pack textures for your LibGDX application/game.

## How to compile

At this time I don't offer precompiled JAR files by myself, but it's very easy to compile the source of TextureWorker to an executable JAR file. Overall we compile TextureWorker like every other LibGDX software.

### Easy going

> *NOTE: You need to use the Terminal (OS X, Linux, ...) or CMD (Windows)*

* Navigate to the downloaded TextureWorker folder

```
cd 'Path/To/The/Folder'
```

* Create a JAR

```
./gradlew desktop:dist
```

> Now you can use the fileexplorer as an alternative
