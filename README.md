# Backend

## 1. Endpoints and important info

### - User related endpoints
All user endpoints are mapped to
```
/member/*
```
#### - Saving user
```
/save
```
User body format:
```

```

### - Map/locations related endpoints
All map endpoints are mapped to:
```
/location/*
```
#### - Creating location
```
/location/save
```
Location body format:
```
{
    "id": 1,
    "city": "Kraków",
    "lng": 19.945,
    "lat": 50.0647
}
```
#### - Getting location by id
```
/location/get
```
Required param *id = ?*

#### - Getting all locations
```
/location/getAllLocations
```
No param required

### - Parking space related endpoints
All parking space endpoints are mapped to:
```
/parking/*
```
#### - Creating parking space
```
/parking/save
```
Parking space body format
```
{
    "city_Id": 1,
    "availability": true,
    "id": 2
}
```
#### - Getting all parking spaces
```
/parking/getAll
```
No params required

#### - Getting parking space by id
```
/parking/get
```
Required param *id = ?*

#### - Getting all in city
```
/parking/getAllInCity
```
Required param *id = ?* (id of city)

