## contributions.md

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

* *07.04.2023, Friday:* People: Harris. Description: wrote two Tests for Issue 14, started test for Issue 19 but failed. 
tried several approaches that might have been missing (saving lobby, initializing empty List instead of null object)
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/14
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/19

* *08.04.2023, Saturday:* People: Elia. Description: wrote two Tests for Issue 28 and Issue 21
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/28
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/21

* *08.04.2023, Saturday:* People: Samuel. Description: wrote one Tests for Issue 25
  * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/25

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

* *17.04.2023, Monday:* People: Samuel, Elia, Harris. Description: automated postman websockets tests with javascript 
code, fixed minor bugs, did really a lot of testing and timer debugging, decided to let the client handle that. Created new development tasks
that better represent our done work:
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/65
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/64
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/63
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/62
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/61
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/60

* *20.04.2023, Monday:* People: Samuel, Elia, Harris. Description: fixed the Timer matter and brought that to be working
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/32
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/33
    * https://github.com/sopra-fs23-group-28/sopra-fs23-group-28-server/issues/35