contributions.md

# Milestone 3

## 28.03. - 02.04.2023

Backend:
* *29.03.2023, Wednesday:* People: Elia, Samuel, Harris. Description: created user entity and lobby entity, 
talked through API design questions. Issues: NoIssue (API setup)

* *30.03.2023, Thursday:* People: Elia, Samuel, Harris, Kusi. Description: Call about API coordination & design 
(FrontEnd & BackEnd) Issues: NoIssue (API setup)

* *30.03.2023, Thursday:* People: Elia, Samuel, Harris. Description: pair programming: went through API design, changed User & Lobby entites, created Camelcolor & bonustool enums, went through gameController logic,
created two UserController endpoints with mock data created Player entity, fitted DTOMapper to User, updated REST specifications. Issues:
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/14
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/19
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/21
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/22
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/25
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/26

* *31.03.2023, Friday Morning:* People: Elia, Samuel, Harris. Description: pair programming: adjust 
UserGetDTO/UserPostDTO, created LobbyGetDTO, adjust DTOMapper, created additional get All Users Endpoint,
created Mock Lobby Endpoints, updated REST specifications. Issues:
  * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/14
  * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/19
  * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/21
  * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/22
  * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/25
  * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/26

* *31.03.2023, Friday Afternoon:* People: Elia, Samuel, Harris. Description: pair programming: Created startPostDTO, 
adjusted LobbyGetDTO, Implemented Lobby Endpoints (PUT joinLobby, GET lobbyInfo, PUT startGame), adjusted User 
Endpoints (POST createUser, PUT setCamelColor, GET getAllUsers), updated REST specifications, adjusted DTOMapper 
(lobbyGetDTO and startPostDTO), added methods to LobbyService and UserService. Issues:
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/14
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/19
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/21
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/22
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/25
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/26
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/28


FrontEnd:
* *28.03. - 02.04.* People: Markus, Cedric. Working on the view templates in TeleportHQ.


## 03.04. - 09.04.

BackEnd:
* *07.04.2023, Friday:* People: Harris. Description: wrote two Tests for Issue 14, started test for Issue 19 but failed. 
tried several approaches that might have been missing (saving lobby, initializing empty List instead of null object)
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/14
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/19

* *08.04.2023, Saturday:* People: Elia. Description: wrote two Tests for Issue 28 and Issue 21
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/28
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/21

* *08.04.2023, Saturday:* People: Samuel. Description: wrote one Tests for Issue 25
  * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/25


FrontEnd:
* *03.04. - 09.04.* People: Cedric, Markus. Continous work and fixes on designs.

* *03.04.2023, Monday:* People: Cedric. Initializing views by transfering templates into GitHub.
Creating and adding assets.

* *04.04.2023, Tuesday:* People: Markus. Setting up routing and redirecting.
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/issues/22

* *07.04.2023, Friday:* People: Cedric. Creating the slider for ChooseAvatar. Added functionality
with tokens.
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/issues/20
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/issues/21


## 10.04. - 16.04.

BackEnd:
* *12.04.2023, Wednesday:* People: Samuel, Elia, Harris. Description: fixed Test for Issue 19, created delete mapping
for user and renamed endpoints. Started with socket.io 
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/19

* *12.04.2023, Thursday:* People: Samuel, Elia, Harris, Cédric. Description: finished socketIO configuration -> able to
start Server
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/32
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/33
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/35
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/36
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/38
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/39
    
* *12.04.2023, Thursday:* People: Samuel, Elia, Harris. Description: started with the socketIO connection and its user authentication. As the user
joins, a message is being sent to all Users. GameMaster receives notification about game is ready to start. updating SocketIO specifications.
Lobbyservice updated with regard to user entity changes for lobby ready management. 
All Clients receive message as the game was started. Socket/endpoints tests with postman and minor fixes in lobbyController/socketmodule
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/32
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/33
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/35
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/36
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/38
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/39

* *15.04.2023, Saturday:* People: Samuel, Elia, Harris. Description: return something with the CamelColor PUT request. Create Round entity, DTOMapping,
create Endpoints to retrieve question incl. authentication. started with timer to receive questions in a timeframe. Timer logic
complete, logic of category choosing complete, first testing session with postman which lead to troubles. 
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/32
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/33
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/35
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/36
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/38
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/39


FrontEnd:
* *10.04. - 16.04.* People: Cedric, Markus. Continous work and fixes on designs.

* *11.04.2023, Tuesday:* People: Cedric, Markus. Login part up until common lobby works.
Upgrading react-router-dom to v6^, making first adjustments and testing possible compatability issues.
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/issues/19
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/issues/20
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/issues/22
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/issues/23

* *14.04.2023, Friday:* People: Cedric. Redesigning Overview to be the new hub for future work with websockets.
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/issues/16

* *15.04.2023, Saturday:* People: Cedric, Markus. Reworking Overview, adding minor routing stuff and reading into Routing 6.10.
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/issues/16
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/issues/23

* *16.04.2023, Sunday:* People: Markus. Finishing router upgrades, relocating RouteGuards.


## 17.04. - 23.04

BackEnd:
* *17.04.2023, Monday:* People: Samuel, Elia, Harris. Description: automated postman websockets tests with javascript 
code, fixed minor bugs, did really a lot of testing and timer debugging, decided to let the client handle that. Created new development tasks
that better represent our done work:
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/65
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/64
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/63
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/62
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/61
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/60

* *20.04.2023, Monday:* People: Samuel, Elia, Harris. Description: fixed the Timer matter and brought that to be working. Refactored RoundService.
Fetching API questions and storing them in a Java object works properly. Possible to retrieve the questions and answers via RestAPI. Pushed all 3.
receive question answers.
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/32
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/33
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/35
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/36


FrontEnd:
* *17.04. - 23.04.* People: Cedric -> Mainly working on websocket integration; Markus -> designing and setting up framework for second half of the game.
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/issues/25
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/issues/26
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/issues/2
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/issues/3
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/issues/11


## 24.04. - 27.04.

BackEnd:
* *24.04.2023, Monday:* People: Samuel, Elia, Harris. Description: receive answer questions, handle player punishment / advance based on
answers, notify FrontEnd at state changes, worked heavily on GCloud and sockets
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/38
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/39

* *25.04.2023, Tuesday:*: People: Samuel, Elia, Harris, Cedric. Description: tried everything possible to get socket.io to run on GCloud.
the problem is that it is possible to connect via the IP address of the AppEngine instance, but not via the domain as the domain handler
does not allow portforwarding for what we needed. A static IP was also not possible for our GCloud plan without having to pay a fortune per day
and with reverse proxies in a separate VM. This is we went with different approaches to get this IP via an API to the FrontEnd.

* *26.04.2023, Wednesday:*: People: Samuel, Elia, Harris. Description: Small refactoring to support FrontEnd Flow, worked heavily on Unit tests
Finished socket.Io to work on Google cloud.
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/40
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/68
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/69
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/70
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/71
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/72
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/73

* *27.04.2023, Thursday:*: People: Samuel, Elia, Harris. Description: Send finish info to frontend via socket event 
in order to show the winner screen.
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/74


FrontEnd:
* *24.04.2023, Monday:* People: Cedric. Placing sockets and working on getting them to work (connect to the backend).
Local tests work and let the game progress past the lobby.
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/issues/15
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/issues/16
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/issues/18

* *25+26.04.2023:* People: Markus. Readjusting designs for prototype in Google Chrome.
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/issues/27

* *27.04.2023, Thurday:* People: Cedric, Markus. Last push efforts to get the prototype up to standards. Finishing documentations.



# Milestone 4

## 01.05. - 07.05.

BackEnd:
* *06.05.2023, Saturday:* People: Samuel, Elia, Harris. Description: tried to get SSL to run with some GCloud settings but failed. Went on to
 overview endpoint / socket auth, PUT Mapping for question difficulty, API HTTP Status Code refactoring, tests.
  * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/75
  * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/78
  * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/79
  * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/80
  * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/81
  * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/82
  * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/83


FrontEnd:
* *05.05.2023, Friday:* People: Markus. Description: Designing the difficulty wheel and implementing it in JSX + CSS.
	People: Cedric. Description: Working on correct display of leaving and joining players.
	* https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/issues/28
	* https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/issues/14

* *06.05.2023, Saturday:* People: Markus. Description: Implementing the animation logic and first attempts for socket connection.
	* https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/issues/29


## 08.05. - 14.05.

BackEnd:
* *11.05.2023, Thursday:* People: Elia, Samuel, Harris. Description: Changed parts of code so that game logic works with 2-4 people. Fixed leaveLobby endpoint
  * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/91
  * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/90
  * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/89
  * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/86
  * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/85


FrontEnd:
* Took joker week since not a lot was left to be added on the frontend part. Worked both on minor fixes on the side when backend needed us.


## 15.05. - 21.05.

BackEnd:
* *15.05.2023, Monday:* People: Elia, Samuel, Harris, Cédric. Description: set init value for difficulty, tried maany different things to get SSl to run without success
  *https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/88

* *20.05.2023, Saturday:* People: Elia, Samuel, Harris. Description: Implemented functionality for the loser to chose the punishment steps for the next round. Refactored 
* code (increased encapsulation) and decreased coupling. Added a new socket call "WHEEL" which tells the client how much the difficulty wheel shall spin.
  * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/95
  * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/66
  * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/97

* *21.05.2023, Sunday:* People: Elia, Samuel, Harris. Description: fixed bugs with the functionality for the loser to chose the punishment steps for the next round. Refactored
* code (increased encapsulation) and decreased coupling. Changed the method calls to be more consistent (id's instead of whole objects) and for better encapsulation.
  * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/94
  * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/95
  * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/96
  * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/97
  * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/66


FrontEnd:
* *15.05.2023, Monday:* People: Cedric. Description: Finished the winner screen with variable user amount and continues working on SSL problem.
	* https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/issues/2
	* https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/issues/3

* *18.05.2023, Thursday:* People: Cedric. Description: PunishmentSlider and API call is created and bugfixed.
	* https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/issues/6
	* https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/issues/8

* *20.05.2023, Saturday:* People: Cedric. Description: PunishmentSlider tested with the Socket Messages.
	* https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/issues/6
	* https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/issues/8

## 22.05. - 25.05.

BackEnd:
* *23.05.2023, Tuesday:* People: Elia, Samuel, Harris, Markus. Description: giving back the punishment Steps in the Round. Inform Clients that they were set.
  * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/67


FrontEnd:

* *22.05.2023, Monday:* People: Cedric. Description: Refactored Socket in overview.
* *22.05.2023, Monday:* People: Cedric. Description: testing the workflow and bugfixed Wheel and Punishment socket problems.
	* https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/issues/6
	* https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/issues/8
	* https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/issues/9
	* https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-client/issues/29

* *23.05.2023, Tuesday:* People: Markus. Description: Working on small fixes and started documentation.
* *23.05.2023, Tuesday:* People: Cedric. Description: fix smaller bugs.
* *24.05.2023, Wednesday:* People: Markus. Description: Collection last issues, working on documentation and preparing last CSS overhauls.

BackEnd:
* *24.05.2023, Wednesday:* People: Elia, Samuel, Harris. Description: Wrote tests for gameService, socketModule, lobbyService and userService
