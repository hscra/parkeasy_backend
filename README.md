## 1. Using Database

Database connection will be established in main so no worries about that

There will be one method that you can and will actually use
#### executeQuery()
Which will execute all queries you give it so have fun

usage of executeQuery()

```
Database db = new Database();
    try {
        // Example queries
        String[] queries = {
            "INSERT INTO users (name, email) VALUES (?, ?)",
            "UPDATE users SET email = ? WHERE name = ?",
            "DELETE FROM users WHERE email = ?"
        };

        // Corresponding parameters
        Object[][] parameters = {
            {"John Doe", "john@example.com"},
            {"john.doe@example.com", "John Doe"},
            {"john.doe@example.com"}
        };

        // Execute multiple queries as a single transaction
        db.executeQuery(queries, parameters);

        System.out.println("All queries executed successfully!");
    } catch (Exception e) {
        e.printStackTrace();
    }
```

basically we use Array of strings to pass queries and array of arrays to pass params

### TODO:
Add file with credentials\
Add url