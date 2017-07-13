# scala-simple-JsonParser

## Introduction
Simple Scala Json Parser using scala reflection. </br> 

## Features
* Not good performance. ( jackson library is 3 times faster than this library )
* json to T.
* T to json.
* You can add default Json ++ your extra json. ( overrde extraJson function )
</br>
* It is just for studying relection. But this library do not have any problems.

## How To Use
    case class yourCustomClass(......)

    yourCustomClass extends SimpleJsonParser

    val jsonValue = yourCustomClass.toJson
    val objectValue = yourCustomClass.fromJson(jsonValue)

## Try ( override extra json )
    override def extraJson = Json.obj("extraJson" -> 1, ......)
    
    yourCustomClass.toJson

