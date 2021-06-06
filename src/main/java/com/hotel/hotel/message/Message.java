package com.hotel.hotel.message;

public class Message {

    private String greeting;
    private String statement;
    private String closing;
    private String timeOfDayGreeting;
    private String entityName;
    private String companyName;
    private int roomNumber;

    public Message(String greeting, String statement, String closing, String timeOfDayGreeting, String entityName, String companyName, int roomNumber) {
        this.greeting = greeting;
        this.statement = statement;
        this.closing = closing;
        this.timeOfDayGreeting = timeOfDayGreeting;
        this.entityName = entityName;
        this.companyName = companyName;
        this.roomNumber = roomNumber;
    }

    public String getTimeOfDayGreeting() {
        return timeOfDayGreeting;
    }

    public void setTimeOfDayGreeting(String timeOfDayGreeting) {
        this.timeOfDayGreeting = timeOfDayGreeting;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int rooms) {
        this.roomNumber = roomNumber;
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getClosing() {
        return closing;
    }

    public void setClosing(String closing) {
        this.closing = closing;
    }

    public void setRemainingValues(String timeOfDayGreeting, String entityName, String companyName, int roomNumber) {
        this.timeOfDayGreeting = timeOfDayGreeting;
        this.entityName = entityName;
        this.companyName = companyName;
        this.roomNumber = roomNumber;
    }

    public String getFormattedMessage() {
        return timeOfDayGreeting + " " + entityName + ", and " + greeting + " " +
                companyName + "! Room " + roomNumber + " " + statement + ". " + closing + ".";
    }
}
