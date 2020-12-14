# equisportarena-spring
PROJEKTARBETE | Complex Java | JU19 | ITHS
____________________________________________________________________
### NEW
POST http://localhost:8080/user/create

Content-Type: application/json

{
	"firstname": "alfred",
	"lastname": "Svensson",
	"address": "Stigen 1, 440 90, Göteborg",
	"email": "brevlada@brev.se",
	"password": "123"
}

____________________________________________________________________

### AUTHENTICATE ( get token )
POST http://localhost:8080/authenticate


Content-Type: application/json

{
	"username": "brevlada@brev.se",
	"password": "123"
}
___________________________________________________________________
### FINDALL ( users ) 
GET http://localhost:8080/user/find-all

Bearer-Token: needed
      Example: {
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJicmV2bGFkYUBicmV2LnNlIiwiZXhwIjoxNjA3OTczNDMyLCJpYXQiOjE2MDc5NTU0MzJ9.yvUGhUp-24q9P80hWDRi4ichYMkjE2mZfW9nMyfCfCW9GhA8gMRCyB0unvVqCS5AzxgcuV0WYfpDKN9U9fIw5g"
}
____________________________________________________________________

### FINDALL CONTEST
GET http://localhost:8080/admin/contest/find-all

Bearer-Token: needed with admin access
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
