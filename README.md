# Requirements

Gradle and a JRE that supports Java 11

# How to Build
Navigate to the root of the project and run `gradlew build`

# How to Run

At the root of the folder run `Java -jar build/libs/hotel-0.0.1-SNAPSHOT.jar`

Alternatively, if you have an IDE you can run the application from the Hotel Application class.

# Design Decisions

### File Structure
I split all the code into the message package. I structured it this way in the event that more functionality would be added in for example a `booking` suite.

### Message Structure
I structured the message templates into three seperate entities. The greeting representing the first thing said after
the clients name. The statement which comes after the room is listed. Then finally the closing to wrap up the message.

### POJOs

The pojo classes were created to represent the JSON structures. This allowed for easy translation from JSON to class. 

### Service

The `MessageService` is where I stored the majority of the code. I structured it in a way that would allow for easily readability
and maintainability. I decided to store this as a service as eventually I could refactor this to place a controller on top
of it allowing for API functionality.

### User choices
I tried to keep my approach to usability as rudimentary as possible. For the majority of the prompts, the user only has the 
option to enter a single integer corresponding with a particular value.

# Why Java?

I chose Java as it has been my most prominent language as of late. I have both professional and personal experience making
projects within Java. With trying to keep this project short, I wanted to leverage certain libraries that I was already familiar with.

# Validation

I used validation for this project by running through various inputs that I knew would cause issues. I structured my code
to prevent certain exceptions to be thrown.

# Future Changes

1. I would switch the CLI input to an angular front-end. It would greatly improve the overall user experience and allow 
    for more flexibility from a development standpoint (This approach would then have to see a Controller being added in for
   the front end to query the backend).
   

2. If we were getting serious about this, I would populate a database with the JSON information, so we would not have to 
    read from it anymore. In addition, the custom templates would be saved off to the database for future use.
   

3. Allowing for users to input new guests or companies that are not provided by the service.


4. Another change that could be made that would not require a database, would be to just save off any custom input to a
   new JSON file.