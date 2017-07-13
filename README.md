# scala-simple-JsonParser

## Introduction
Simple Scala Json Parser using scala reflection. </br> 

## Features
* Not good performance. ( jackson library is 3 times faster than this library )
* json to T.
* T to json.
* You can add default Json ++ your extra json. ( override extraJson function )
</br>
* It is just for studying relection. But this library do not have any problems.

## How To Use
    case class yourCustomClass(a: Int, b: String)

    //extends Mode
    val yourCustomClass = yourCustomClass(0, "5")
    YourCustomClass extends SimpleJsonParser

    val jsonValue = yourCustomClass.toJson
    val objectValue = yourCustomClass.fromJson(jsonValue)

    //standalone Mode
    val yourCustomClass = yourCustomClass(0, "5")
    val jsonValue = SimpleJsonParser.toJson(yourCustomClass)
    val objectValue = SimpleJsonParser.fromJson(jsonValue, classOf[yourCustomClass])

## Try ( override extra json )
    override def extraJson = Json.obj("extraJson" -> 1, ......)
    
    yourCustomClass.toJson

