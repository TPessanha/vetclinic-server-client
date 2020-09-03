## What is this repository
This repository holds a server and a client for a pretend veterinarian clinic.
The server is made using Spring framework and Kotlin.
The client uses React.

## Used technologies

1. junit
2. Mockito
3. Spring Boot
4. React/Redux
5. Kotlin/typescript

## To know as a team member (Best Practices)

It is best to use IntelliJ for the server project, if you want to use other IDE you are free to do so as long as it doesn't mess up the code.

Things to know:

1. We are using [**EditorConfig**](https://editorconfig.org), please pick an IDE that supports it.
2. We are using kotlin guidelines, in the repo there is an xml with pre-applied coding style for IntelliJ (https://www.jetbrains.com/help/idea/settings-code-style.html), if you use another IDE talk to the team and we can figure something out.
3. [**ktlint**](https://ktlint.github.io) has been added for convenience , you can check your code with "mvn antrun:run@ktlint" and format it with " mvn antrun:run@ktlint-format". 
4. There is a server and a client in the repo, we are ignoring the client until the 2nd phase of the project.

Obviously if you don'f follow everything to the letter it's not a big problem but try to not go overboard.
