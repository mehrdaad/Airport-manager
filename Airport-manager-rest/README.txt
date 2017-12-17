Get all destinations:
curl -X GET http://localhost:8080/pa165/rest/destination/all

Get one specific destination
curl -X GET http://localhost:8080/pa165/rest/destination/1

Create destination:
Windows cmd version:
curl -X POST -i -H "Content-Type: application/json" --data "{\"country\":\"USA\",\"city\":\"test\"}" http://localhost:8080/pa165/rest/destination/create
Linux version:
curl -X POST -i -H "Content-Type: application/json" --data '{"country":"USA","city":"test"}' http://localhost:8080/pa165/rest/destination/create

Delete destination:
curl -X DELETE http://localhost:8080/pa165/rest/destination/1

Get destinations in USA:
curl -X GET http://localhost:8080/pa165/rest/destination/all/USA
