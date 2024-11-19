## 1. Using Database

Database connection will be established in main so no worries about that

#### usage of executeQuery()

use when inserting with params (or without)

1. create string with query for ex "INSERT INTO table (where, where) VALUES (?,?)"
2. executeQuery(sqlString, param1, param2, ...)

#### usage of executeSimpleQuery()

use for simple queries like CREATE TABLE

1. create string
2. executeSimpleQuery(string)

basically we use Array of strings to pass queries and array of arrays to pass params