1.registration:
POST http://localhost:8080/api/auth/register
request:
{
  "username": "aman12344",
  "password": "123456",
  "name": "Md Amanullah",
  "email": "aman@gmail.com"
}

response:
{
    "message": "User registered successfully",
    "user": {
        "username": "aman1234v4",
        "email": "aman@gmail.com",
        "name": "Md Amanullah",
        "id": 9
    },
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbWFuMTIzNHY0IiwiaWF0IjoxNzcwNTM3NDgxLCJleHAiOjE3NzA2MjM4ODF9.KbrchXPYczPXHc4ucTe8dxu6zxOThf88KilWJtD-L2oShGBpzupN-GzjeQFgLV1gRCoQyjFTLtie2bc3syId2g"
}


2.login:
POST http://localhost:8080/api/auth/login
request:
{
    "username": "aman12344",
    "password": "123456"
}

response:
{"token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbWFuMTIzNDQiLCJpYXQiOjE3NzA1MzU3OTQsImV4cCI6MTc3MDYyMjE5NH0.68xcU4sJ80PIodsLqCWZsFCdEAeR7_ZBvdpToEqHcmE41hypcAD6BE98mRG61_iHIKH0tTSySggB3DTc8ijG1w"}


3.logout:
POST http://localhost:8080/api/auth/logout
Authorization: Bearer <JWT>
Content-Type: application/json


response:
{
    "message": "Logout successful. Please remove token from client."
}


s.create_user:
POST http://localhost:8080/api/auth/register
Authorization: Bearer <JWT>
Content-Type: application/json
request:
{
  "username": "aman12344",
  "password": "123456",
  "name": "Md Amanullah",
  "email": "aman@gmail.com"
}

response:
{
    "message": "User registered successfully",
    "user": {
        "username": "aman1234v4",
        "email": "aman@gmail.com",
        "name": "Md Amanullah",
        "id": 9
    },
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbWFuMTIzNHY0IiwiaWF0IjoxNzcwNTM3NDgxLCJleHAiOjE3NzA2MjM4ODF9.KbrchXPYczPXHc4ucTe8dxu6zxOThf88KilWJtD-L2oShGBpzupN-GzjeQFgLV1gRCoQyjFTLtie2bc3syId2g"
}


1.get all:
GET http://localhost:8080/api/auth/register
Authorization: Bearer <JWT>
Content-Type: application/json

request:
{
  "username": "aman12344",
  "password": "123456",
  "name": "Md Amanullah",
  "email": "aman@gmail.com"
}

response:
[
    {
        "name": "Md Amanullah",
        "id": 2,
        "email": "aman@example.com",
        "username": "aman1234"
    },
    {
        "name": "Md Amanullah",
        "id": 3,
        "email": "aman@gmail.com",
        "username": "aman12344"
    },
    {
        "name": "New User",
        "id": 4,
        "email": "newuser@gmail.com",
        "username": "newuser1"
    },
    {
        "name": "New User",
        "id": 5,
        "email": "newuser@gmail.com",
        "username": "newuser12"
    },
    {
        "name": "New User",
        "id": 6,
        "email": "newuser@gmail.com",
        "username": "newuser123"
    },
    {
        "name": "New User",
        "id": 7,
        "email": "newuser@gmail.com",
        "username": "newuser123a"
    },
    {
        "name": "New User",
        "id": 8,
        "email": "newuser@gmail.com",
        "username": "newuser123ad"
    },
    {
        "name": "Md Amanullah",
        "id": 9,
        "email": "aman@gmail.com",
        "username": "aman1234v4"
    }
]


1.update:
PUT http://localhost:8080/api/auth/register

Authorization: Bearer <JWT>
Content-Type: application/json

request:
{
  "username": "aman12344",
  "password": "123456",
  "name": "Md Amanullah",
  "email": "aman@gmail.com"
}

response:
{
    "id": 1,
    "username": "aman123",
    "name": "Md Amanullah",
    "email": "aman@gmail.com"
}


1.delete:
DELETE http://localhost:8080/api/auth/register

Authorization: Bearer <JWT>
Content-Type: application/json


response:
"User deleted"
