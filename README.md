# HTTPServer
HTTPServer in Java (Purely educational) 

# Install
The project should be cloned and the user should interact with the main class like the example.

# Usage
The HttpUserInterface is the abstractisation the developer interacts with.

The add`X`Path method will receive the URL as the first parameter and the lambda function mapped to that URL.

The parameter of the lambda function (body) is the body of the client request.

`X` = {GET, POST, PUT, DELETE}

```
HttpUserInterface userInterface = new HttpUserInterface();
userInterface.addGetPath("/index.html", (body) -> {
       return "Not Implemented";
});
userInterface.runServer(5001);
```
