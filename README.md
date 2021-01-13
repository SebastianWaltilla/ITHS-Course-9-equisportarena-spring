# equisportarena-spring
PROJEKTARBETE | Complex Java | JU19 | ITHS

To run project locally: <br>
using maven: <br>
cd contest <br>
mvn spring-boot:run <br>

with jar command: <br>
cd contest <br>
java -jar target/contest-0.0.1-SNAPSHOT.jar

Swagger ui to view endpoint info go to: <br>
http://localhost:8080/swagger-ui.html
____________________________________________________________________
### NEW USER
POST http://localhost:8080/user/register

No authorization <br>
Content-Type: application/json

{
"firstName": "Sune",
"lastName": "Rolfsson",
"address": "administrationsvägen 1",
"email": "admin@admin.com"
}

____________________________________________________________________

### AUTHENTICATE ( get token )
POST http://localhost:8080/authenticate

No authorization <br>
Content-Type: application/json

{
	"username": "brevlada@brev.se",
	"password": "123"
}
___________________________________________________________________
### FIND ALL ( users ) 
GET http://localhost:8080/user/find-all

Bearer-Token: needed with admin access
      Example: {
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJicmV2bGFkYUBicmV2LnNlIiwiZXhwIjoxNjA3OTczNDMyLCJpYXQiOjE2MDc5NTU0MzJ9.yvUGhUp-24q9P80hWDRi4ichYMkjE2mZfW9nMyfCfCW9GhA8gMRCyB0unvVqCS5AzxgcuV0WYfpDKN9U9fIw5g"
}
____________________________________________________________________

### DELETE ( users ) 

DELETE http://localhost:8080/user/delete

Delete is possible after a user is authenticated, deletes the authenticated user by its token.

____________________________________________________________________

### FIND ALL  ( contest )
GET http://localhost:8080/admin/contest/find-all

Bearer-Token: needed with admin access <br>
Content-Type: application/json
    
    	{
    	"name":"Valid horse contest",
	"description":"desc",
	"maxParticipants":3,
	"startDate":"2021-10-20",
	"endDate":"2021-10-27",
	"entryFee":10.00,
	"contestLevel":"medium"
	}
___________________________________________________________________

### CREATE CONTEST
Post http://localhost:8080/admin/contest/create

Bearer-Token: needed with admin access <br>
Content-Type: application/json

	{
	"name": "First contest, active",
	"description": "Contest with horses",
	"maxParticipants": 10,
	"startDate": "2020-12-15",
	"endDate": "2020-12-22",
	"entryFee": 10.00,
	"contestLevel": "Beginner",
	"winningAward": "A new horse",
	"adminComment": "Comment from administrator"
	}
___________________________________________________________________

### CREATE ENTRY
Post http://localhost:8080/entry/create

Bearer-Token: needed with user access <br>
Content-Type: application/json

	{
	"userId": 1,
	"contestId": 1,
	"videoLink": "<Link to video>",
	"userComment": "This is me and my horse",
	"horsename": "FlashGordon"
	}

___________________________________________________________________

### GET ALL ACTIVE CONTESTS
Get http://localhost:8080/contest/active

Bearer-Token: needed with user access <br>
No body

Response
200 OK <br>
[
{
"name": "First contest, active",
"description": "demotävling",
"startDate": "2020-12-16",
"endDate": "2020-12-23",
"entryFee": 10.00,
"contestLevel": "enkel",
"winningAward": "en kram",
"placesLeft": true
}
]


or <br> 
200 OK <br>
{
"message": "No contests",
"details": [
"Currently no contests open."
]
}

___________________________________________________________________

### GET ACTIVE CONTEST BY ID
Get http://localhost:8080/contest/id/{id}

Bearer-Token: needed with user access <br>
No body

Response
200 OK <br>
[
{
"name": "First contest, active",
"description": "demotävling",
"startDate": "2020-12-16",
"endDate": "2020-12-23",
"entryFee": 10.00,
"contestLevel": "enkel",
"winningAward": "en kram",
"placesLeft": true
}
]

or <br>
404 not found

___________________________________________________________________

### GET MY INFO (USER)
Get http://localhost:8080/user/me

Bearer-Token: needed with user access <br>
No body

Response
200 OK <br>
{
"firstName": "Sune",
"lastName": "Rolfsson",
"address": "administrationsvägen 1",
"email": "admin@admin.com"
}

or <br>
404 User does not exist!

___________________________________________________________________

### GET ALL ENTRIES BY CONTEST ID
Get http://localhost:8080/admin/entry/find-all-by-contest-id/1

Bearer-Token: needed with admin access <br>
No body

Response
200 OK <br>
[
{
"id": 1,
"userId": 1,
"videolink": null,
"userComment": "usercomment",
"score": null,
"adminComment": null,
"submissionDate": "2021-01-12",
"horseName": "Rolf"
}
]

or

404 not found

___________________________________________________________________

### DELETE CONTEST BY ID

DELETE http://localhost:8080/admin/contest/delete/{id}

Bearer-Token: needed with admin access <br>
No body

Response <br>
200 OK, "{id} was deleted"

___________________________________________________________________

### DELETE ENTRY BY ID

DELETE http://localhost:8080/admin/entry/delete/{id}

Bearer-Token: needed with admin access <br>
No body

Response <br>
200 OK, "{id} was deleted"

___________________________________________________________________
