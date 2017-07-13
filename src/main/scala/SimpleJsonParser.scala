import java.util.Date
import play.api.libs.json._

import scala.reflect.runtime.universe._

/**
  * Created by longcoding on 2017. 7. 13..
  */
trait SimpleJsonParser {

    private val m = runtimeMirror(getClass.getClassLoader)
    private val classSymbol = m.staticClass(getClass.getName)
    private val tpe = classSymbol.selfType
    private val members = tpe.members.collect { case m: MethodSymbol if m.isPublic && m.isGetter && m.isCaseAccessor => m}.toList


    private def camel2Underscore(text: String) = text.drop(1).foldLeft(text.headOption.map(_.toLower + "") getOrElse "") {
        case (acc, c) if c.isUpper => acc + "_" + c.toLower
        case (acc, c) => acc + c
    }

    private def underscore2Camel(text: String) = "_([a-z\\d])".r.replaceAllIn(text, {m =>
        m.group(1).toUpperCase()
    })

    private def extraJson = Json.obj()

    private def defaultJson = {
        val mi = scala.reflect.runtime.currentMirror.reflect(this)

        var obj = Json.obj()
        members.map(member => (member.name.toString, mi.reflectField(member).get))
            .foreach{ case (key: String, value: Any) => value match {
                case v: Int          => obj ++= Json.obj(key -> v)
                case v: String       => obj ++= Json.obj(key -> v)
                case v: Date         => obj ++= Json.obj(key -> v.getTime)
                case Some(v: Int)    => obj ++= Json.obj(key -> v)
                case Some(v: String) => obj ++= Json.obj(key -> v)
                case Some(v: Date)   => obj ++= Json.obj(key -> v.getTime)
                case _ => obj ++= Json.obj(key -> "")
            }}

        obj
    }

    def fromJson(o: JsValue) = {

        val primaryConstructor = classSymbol.typeSignature.typeSymbol.asClass.primaryConstructor

        val args = scala.collection.mutable.ArrayBuffer.empty[Any]
        members.reverse.map { member =>

            try {
                val value = (o \ member.name.toString)
                member.typeSignature.resultType match {
                    case tpe if typeOf[Int] =:= tpe => args += value.as[Int]
                    case tpe if typeOf[String] =:= tpe => args += value.as[String]
                    case tpe if typeOf[Date] =:= tpe => args += value.as[Date]
                    case tpe if typeOf[Option[Int]] =:= tpe => args += value.asOpt[Int]
                    case tpe if typeOf[Option[Date]] =:= tpe => args += value.asOpt[Date]
                    case tpe if typeOf[Option[String]] =:= tpe => value.asOpt[String] match {
                        case Some(v) if v.length > 0 => args += value.asOpt[String]
                        case _ => args += None
                    }
                    case _ => args += None
                }
            } catch {
                case e: JsResultException => {
                    member.typeSignature.resultType match {
                        case tpe if typeOf[Int] =:= tpe => args += 0
                        case tpe if typeOf[String] =:= tpe => args += ""
                        case tpe if typeOf[Date] =:= tpe => args += new Date
                        case tpe if typeOf[Option[Int]] =:= tpe => args += None
                        case tpe if typeOf[Option[String]] =:= tpe => args += None
                        case tpe if typeOf[Option[Date]] =:= tpe => args += None
                        case _ => args += None
                    }
                }
            }
        }

        m.reflectClass(tpe.typeSymbol.asClass).reflectConstructor(primaryConstructor.asMethod).apply(args:_*)

    }

    def toJson = defaultJson ++ extraJson

}

