package net.yoshinorin.orchard.services.github.event.json

import io.circe.Json
import io.circe.parser.parse
import net.yoshinorin.orchard.models.db.{CreateEvents, Events, Repositories}
import net.yoshinorin.orchard.utils.File
import org.scalatest.FunSuite

// testOnly *CreateEventSpec
class CreateEventSpec extends FunSuite {

  val repositoryJson = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/repository.json")
  val repositoryInstance = net.yoshinorin.orchard.services.github.event.json.Repository(parse(repositoryJson).getOrElse(Json.Null))

  val issueJson = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/issue.json")
  val eventEnstance = net.yoshinorin.orchard.services.github.event.json.Event(repositoryInstance, parse(issueJson).getOrElse(Json.Null))

  val createEventJson = File.readAll(System.getProperty("user.dir") + "/src/test/resources/data/json/createEvent.json")
  val instance = net.yoshinorin.orchard.services.github.event.json.CreateEvent(eventEnstance.event.get, parse(createEventJson).getOrElse(Json.Null))

  test("COnvertJson to CrateEvent case class") {
    val createEventCaseClass = Some(
      CreateEvents(
        eventEnstance.event.get.id,
        eventEnstance.event.get.userName,
        "tag",
        "test-tag",
        eventEnstance.event.get.createdAt
      )
    )
    assert(instance.getConvertedCaseClass == createEventCaseClass)
  }

}
