# Phoenix Games Publishing Platform Backend Developer Trial Task

Our top individual contributor Gabriel Schweinsteinburgberger recently started to develop a new service called User Profile API.
Unfortunately for us, Gabriel got spontaneously married during his spiritual journey to Tibet. He decided to give up developing software in
favor of running a mountain goats farm with his newly wedded wife. A lot of changes for the good for Gabriel, but we still have to deliver
the project on time without his valuable contributions.

## Project domain

The idea of the User Profile API is to accept commands from various sources and update the user profile according to them. The user profile
is a collection of properties associated with the `userId`. User profiles can be created on the fly when user is first time seen by the
service.

User Profile API was born to live to decouple operations on the user profile and its storage from the logic responsible for extracting the
data from the outside world.

We foresee the following command types needed from the start and more types added in the future:

* `replace` to replace the value of a certain property of the user profile.
  ```json
  {
    "userId": "de4310e5-b139-441a-99db-77c9c4a5fada",
    "type": "replace",
    "properties": {
      "currentGold": 500,
      "currentGems": 800
    }
  }
  ```

* `increment` increments the current value in the profile. This command can also take negative numbers to decrement the value.
  ```json
  {
    "userId": "de4310e5-b139-441a-99db-77c9c4a5fada",
    "type": "increment",
    "properties": {
      "battleFought": 10,
      "questsNotCompleted": -1
    }
  }
  ```

* `collect` adds values to a list of the values in the user profile property:
  ```json
  {
    "userId": "de4310e5-b139-441a-99db-77c9c4a5fada",
    "type": "collect",
    "properties": {
      "inventory": ["sword1", "sword2", "shield1"],
      "tools": ["tool1", "tool2"]
    }
  }
  ```

Multiple applications generate commands to update the user profile and send them to User Profile API over HTTP. One example of such
applications is a streaming application that listens to the stream of user activity events and sent the command when certain criteria are
met. For example, for the user property called `numberOfLogins`, the streaming application can listen to `login` events and send
an `increment` command to User Profile API when such an event occurs.

Another example is an ETL application which is run periodically and extracts information from the database to
populate `averagePlayTimePerWeek` property by sending `replace` commands to User Profile API.

## What is already done

There is a project which is set up in the following way:

* Java 17 LTS (with support for [jenv](https://www.jenv.be), if it's your thing)
* [Gradle](https://gradle.org/)
  with [Shadow](https://imperceptiblethoughts.com/shadow/introduction/),
  [JaCoCo](https://docs.gradle.org/current/userguide/jacoco_plugin.html),
  and [test-logger](https://plugins.gradle.org/plugin/com.adarshr.test-logger)
  plugins
* [Dropwizard](https://www.dropwizard.io/en/latest/)
* [dropwizard-guicey](https://github.com/xvik/dropwizard-guicey) which brings [Guice](https://github.com/google/guice) power to Dropwizard
* [JUnit 5](https://junit.org/junit5/), [Mockito](https://site.mockito.org/), [AssertJ](https://assertj.github.io/doc/)
  and [JsonUnit](https://github.com/lukas-krecan/JsonUnit#assertj-integration)
* [GitHub action](https://docs.github.com/en/actions) to build, run checks and tests

These parts of the application are already implemented:

* General project setup is done
* `UserResource` is able to return the profile of the user.
* `UserProfileDaoInMemory` implements storage of the user profile in memory.
* There are examples of fixture usage, integration tests, and mocking.

## Your goal

* Add the following components:
    * Endpoint to accept commands. We also thought about receiving commands in batches.
    * Logic to process different command types and update the user profile accordingly.
* Design code in a way that will make adding new command types easy.
* Maintain a coherent coding style and keep the test coverage level.

You are expected to work in your own Git repository and send us a link to it when you are done. Feel free to commit as often and as
granularly as you want, we like to see how the progress was.

Please don't fork this repository (otherwise other candidates will see your code), but
[use it as a template instead](https://docs.github.com/en/repositories/creating-and-managing-repositories/creating-a-repository-from-a-template)
.

In the end, we will print the diff and send it to Gabriel Schweinsteinburgberger via paper mail in a desperate hope that he will decide to
come back to software development.

## A bit more serious

* This is a trial task for a developer position, so the story behind it is a fake.
* We provide feedback on the trial task regardless of the result since we think it's the least we can do for the candidates that heavily
  invested time into the trial task.
* Trial task will not be compensated or paid.

## How to?

### Run tests

```shell
./gradlew test
```

### Run checks and tests

```shell
./gradlew check
```

### Run application

```shell
./gradlew run --args='server'
```

or without Gradle:

```shell
./gradlew build
java -jar ./build/libs/userprofile-api-1.0.0-SNAPSHOT.jar server
```

### How to make a request to a running application

```shell
curl http://localhost:8080/users/some-user-id/profile
```