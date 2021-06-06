package com.hotel.hotel.message.pojos;

import java.util.ArrayList;

public class MessageTemplate {
    private ArrayList<String> greetings = new ArrayList<>();
    private ArrayList<String> statements = new ArrayList<>();
    private ArrayList<String> closings = new ArrayList<>();

    public MessageTemplate() {
    }

    public MessageTemplate(ArrayList<String> greetings, ArrayList<String> statements, ArrayList<String> closing) {
        this.greetings = greetings;
        this.statements = statements;
        this.closings = closing;
    }

    public ArrayList<String> getGreetings() {
        return greetings;
    }

    public void setGreetings(ArrayList<String> greetings) {
        this.greetings = greetings;
    }

    public ArrayList<String> getStatements() {
        return statements;
    }

    public void setStatements(ArrayList<String> statements) {
        this.statements = statements;
    }

    public ArrayList<String> getClosing() {
        return closings;
    }

    public void setClosing(ArrayList<String> closing) {
        this.closings = closing;
    }
}
