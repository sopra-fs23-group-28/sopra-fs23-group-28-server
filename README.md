# Group 28 - Camel Race 

## Introduction

This is the backend for the trivia quiz based game for 2 - 4 players. It was initially designed to be played locally with other people around you
but it can also be done remotely. We were inspired to do this by a carnival game.

## Technologies that are used

* [Java Spring Boot](https://spring.io/projects/spring-boot) - JAVA BackEnd
* [Netty socket.io for Java](https://github.com/mrniko/netty-socketio) - Java implementation for socket.io

## High-level Components

These are the main files necessary to get a good overview for the game from a backend perspective :

1. Our API-endpoints can be found in the [Controller package](https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/tree/main/src/main/java/ch/uzh/ifi/hase/soprafs23/controller)
2. The [config](https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/tree/main/src/main/java/ch/uzh/ifi/hase/soprafs23/config) package contains our socket listeners and emitters, the most important class here is socketModule which is responsible for the interaction with the frontend
3. The [Service](https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/tree/main/src/components/views/overview.js) package contains all service classes is the main hub for the websocket, there you can see all components involved in the question & answer game flow right up until the end of the game.

### Setup
You can start the game under the following link: http://sopra-fs23-group-28-client.oa.r.appspot.com

Or copy this and the client side repo (https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client) to your machine.
Then set it all up with the respective set-up instructions.

### Launch and deployment

1. git clone this repository to your local repository: https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server.git
2. open your favorite IDE and import the cloned repository as a 'gradle project'. Gradle will then manage all the dependencies for you.
3. To build, right-click the `build.gradle` file and choose `Run Build`
4. To run the application, run the following line of code in the project root directory

```
./gradlew bootrun
```

## Running the tests

You can run the tests with the built-in "run Tests" feature of your IDE or by using

```
./gradlew test
```

## Roadmap

These features would be a good choice if one would want to further develop our project:

* Gadgets: Power-ups that are rewarded for correct answer streaks (example names: camel round house kick, vodka bottle)
* Random events: with a small chance on the diffuclty wheel, this event redistributes the players on the field
* Tilting spin button: when the difficulty wheel spin button hits a new section it tilts for a bit

## Authors

FrontEnd authors:

* **Cédric Styner** - *Main contributor* - [glt-cs](https://github.com/glt-cs)
* **Markus Senn** - *Main contributor* - [iKusii](https://github.com/iKusii)

BackEnd authors:
* **Elia Aeberhard** - *Main contributor* - [Elyisha](https://github.com/Elyisha)
* **Harris** - *Main contributor* - [so-ri](https://github.com/so-ri)
* **Samuel Frank** - *Main contributor* - [samuelfrnk](https://github.com/samuelfrnk)

Team:
* **Dennys Huber** - *Responsible TA* - [devnnys](https://github.com/devnnys)
* **Roy Rutishauser** - *template provider* - [royru](https://github.com/royru)
* **Luis Torrejón** - *template provider* - [luis-tm](https://github.com/luis-tm)

## Acknowledgments

* Thanks to all the people that helped, on- and offline
* No camels were harmed in the making of this game (well not directly)

## License

This project is licensed under the Apache 2.0 License - see the [LICENSE](LICENSE.md) file for details
