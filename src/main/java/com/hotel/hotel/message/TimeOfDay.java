package com.hotel.hotel.message;

public enum TimeOfDay {

    MORNING ("Good Morning"),
    AFTERNOON ("Good Afternoon"),
    EVENING ("Good Evening");

    public final String timeOfDay;

    TimeOfDay(String timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public String getTimeOfDay() {
        return this.timeOfDay;
    }
}
