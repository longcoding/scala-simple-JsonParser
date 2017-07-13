import java.util.Date

import play.api.libs.json.Json

/**
  * Created by longcoding on 2017. 7. 13..
  */
class SimpleJsonParserTest {

    /*
    * Standalone Mode
    */
    def standalone = {

        case class TestCaseObj(param1: Int, param2: String, param3: Date)

        val newObj = TestCaseObj(77, "Stand Alone", new Date)

        val jsonValue = SimpleJsonParser.toJson(newObj)
        val objFromJson = SimpleJsonParser.fromJson(jsonValue, classOf[TestCaseObj])

        println(jsonValue)
        println(objFromJson)

    }

    /*
    * Extends Mode
    */
    def extendsMode = {

        case class TestCaseObj(param1: Int, param2: String, param3: Date) extends SimpleJsonParser

        val newObj = TestCaseObj(77, "extendsMode", new Date)

        val jsonValue = newObj.toJson
        val objFromJson = newObj.fromJson(jsonValue)

        println(jsonValue)
        println(objFromJson)

    }

    /*
    * Added ExtraJson
    */
    def extendsModeWithExtra = {

        case class TestCaseObj(param1: Int, param2: String, param3: Date) extends SimpleJsonParser {

            toJson

            override val extraJson = Json.obj(
                "extra_param1" -> 5,
                "extra_param2" -> 7
            )

        }

        val newObj = TestCaseObj(77, "extendsMode", new Date)

        val jsonValue = newObj.toJson
        val objFromJson = newObj.fromJson(jsonValue)

        println(jsonValue)
        println(objFromJson)

    }

}
