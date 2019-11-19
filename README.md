# Video converter

[![Build Status](https://travis-ci.com/AndreyBychkov/VideoConverter.svg?branch=master)](https://travis-ci.com/AndreyBychkov/VideoConverter)

HTTP server for converting video into different formats, like MP4, AVI and etc.

There are two ways of usage Video converter at the moment:

1. [Native](#getting-started)
2. [User Interface](#user-interface)

## Getting Started

### Installing dependencies

1. Server runs on Java Virtual Machine so be sure you have Java on your computer. 
You can download latest version [here](https://java.com/ru/download/).

2. Adding jar to your project
    1. If you use build system like Maven or Gradle follow [this guide](https://jitpack.io/#AndreyBychkov/VideoConverter/1.0).
    2. Your can download jar directly from our [latest release](https://github.com/AndreyBychkov/VideoConverter/releases/tag/1.0).
    
### Running server
Here you can see sample pipeline.
```kotlin
val server = Server(host = "127.0.0.1", port = 8080)
server.start()
...
server.stop()
```

Remember that server occupies thread it is started in. 
So you usually run it something like this:
```kotlin
thread {
    server.run()
}
```


### Using server

#### Roots

You can convert video using following request template: `"/convert/${format}"`, 
where format is video format you want to convert your file into. Format must be in lowercase.

Example: `/convert/avi`

#### Requests

You make a request using [POST](https://en.wikipedia.org/wiki/POST_(HTTP)) method submitting a form
with no more than one video file item. 

#### Response

If request if correct server response to you with a content of converted file, represented as array of bytes.

Example of usage:
```kotlin
val response: ByteArray = request.post(...)
val videoFile = File("path/to/file")
videoFile.writeBytes(response)
```

### Building from source

1. clone project: `git clone https://github.com/AndreyBychkov/VideoConverter.git`
2. run `gradle build` from project directory.
3. run tests: `gradle test`



## User Interface

### Running script

1. Open project folder in IDE.

2. Run /src/main/kotlin/runServer.kt to launch server on 127.0.0.1:8080.

   *Or you can run server any way you prefer.*

![jpg](/src/main/resources/static/images/ui.jpg)

If the server returns an Internal Error 500 it will be displayed on the page.
