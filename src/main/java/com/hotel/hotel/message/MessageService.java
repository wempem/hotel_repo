package com.hotel.hotel.message;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.hotel.hotel.message.pojos.Company;
import com.hotel.hotel.message.pojos.Guest;
import com.hotel.hotel.message.pojos.MessageTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/*
 * This service reads in JSON files and populates the companies, guests, and messageTemplate variables.
 * Once this has been executed, the users are prompted to select guest, company, and between a pre-made or custom
 * message template.
 */

@Service
public class MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);
    private final String MESSAGE_TEMPLATE_FILE = "src/main/resources/MessageTemplates.json";
    private final String GUESTS_FILE = "src/main/resources/Guests.json";
    private final String COMPANIES_FILE = "src/main/resources/Companies.json";

    private ArrayList<Company> companies;
    private ArrayList<Guest> guests;
    private MessageTemplate messageTemplate;
    private Scanner scanner;

    /*
     * This function is the main driver for the service.
     */
    public void execute() throws IOException, InterruptedException {
        this.scanner = new Scanner(System.in);

        readInFiles();
        System.out.println("\n\nWelcome to the hotel greeting messenger program.\nHere you can generate messages to send to guests from various companies. Let's get started!\n\n");
        TimeUnit.SECONDS.sleep(3);

        while(true) {
            int guestIndex = getGuest();
            int companyIndex = getCompany();
            Message greetingMessage = messageTemplate == null || useTemplate() ? generatePreMadeMessage(guestIndex, companyIndex)
                    : generateCustomMessage(guestIndex, companyIndex);

            System.out.println("\n\nHere is the greeting message you have created!\n\n" + greetingMessage.getFormattedMessage() + "\n\n");
            System.out.println("If you are no longer working, please type \"quit\", " +
                    "any other input will send you back to the beginning:\n" );

            String quit = scanner.nextLine().trim().toLowerCase();
            if (quit.equals("quit")) {
                System.out.println("\nGoodbye");
                System.exit(0);
            }

        }

    }

    /*
     * This function reads in the specified JSON files
     */
    private void readInFiles() throws IOException {
        Gson gson = new Gson();

        try (JsonReader messageJSON = new JsonReader(new FileReader(MESSAGE_TEMPLATE_FILE));
             JsonReader guestJSON = new JsonReader(new FileReader(GUESTS_FILE));
             JsonReader companyJSON = new JsonReader(new FileReader(COMPANIES_FILE))
        ) {
            Type companyCollection = new TypeToken<List<Company>>(){}.getType();
            Type guestCollection = new TypeToken<List<Guest>>(){}.getType();

            this.companies = gson.fromJson(companyJSON, companyCollection);
            this.guests = gson.fromJson(guestJSON, guestCollection);
            this.messageTemplate = gson.fromJson(messageJSON, MessageTemplate.class);

        } catch (FileNotFoundException e) {
            logger.error("There was an issue finding one of the required files: ", e);
            throw(e);
        }

        if(companies == null || guests == null) {
            logger.info("No companies or guests were loaded in. Exiting.");
            System.exit(0);
        }
    }

    /*
     * Prompts user to type in a guest. Returns valid input that translates to a guest in the array.
     */
    private int getGuest() {

        StringBuilder guestBuilder = new StringBuilder();
        for(int i = 0; i < guests.size(); ++i) {
            Guest guest = guests.get(i);
            guestBuilder.append(i);
            guestBuilder.append(". ");
            guestBuilder.append(guest.getFirstName());
            guestBuilder.append(" ");
            guestBuilder.append(guest.getLastName());
            guestBuilder.append("\n");
        }

        return retrieveUserInput(companies.size(),
                "Please select which guest you would like to send a greeting message to: ",
                guestBuilder.toString());
    }

    /*
     * Prompts user to type in a company. Returns valid input that translates to a company in the array.
     */
    private int getCompany() {
        StringBuilder companyBuilder = new StringBuilder();

        for(int i = 0; i < companies.size(); ++i) {
            Company company = companies.get(i);
            companyBuilder.append(i);
            companyBuilder.append(". ");
            companyBuilder.append(company.getCompany());
            companyBuilder.append("\n");
        }

        return retrieveUserInput( companies.size(),
                "Please select which company you would like to send a greeting message from: ",
                companyBuilder.toString());

    }

    /*
     * Prompts user to select a pre-made template or a custom template.
     */
    private boolean useTemplate() {
        System.out.println("\nDo you want to use a provided message template? Or a custom template? (y/n)\n\n");
        while (true) {
            switch (scanner.nextLine().trim().toLowerCase()) {
                case "y":
                case "yes":
                    return true;
                case "n":
                case "no":
                    return false;
                default:
                    System.out.println("\nPlease specify by tying either \"y\" or \"n\".\n\n");
                    scanner.nextLine();
            }
        }
    }

    /*
     * Prompts user to select values from the pre-made template.
     * This will return with the Message object that will be displayed to the user.
     */
    private Message generatePreMadeMessage(int guestIndex, int companyIndex) {
        String greeting = "";
        String statement = "";
        String closing = "";
        int i = 0;

        while(i < 3) {
            switch(i){
                case 0:
                    int greetingNumber = getMessageTemplateIndex(messageTemplate.getGreetings(), "greeting");
                    greeting = messageTemplate.getGreetings().get(greetingNumber);
                    break;
                case 1:
                    int statementNumber = getMessageTemplateIndex(messageTemplate.getStatements(), "statement");
                    statement = messageTemplate.getStatements().get(statementNumber);
                    break;
                case 2:
                    int closingNumber = getMessageTemplateIndex(messageTemplate.getClosing(), "closing");
                    closing = messageTemplate.getClosing().get(closingNumber);
                    break;
            }
            i++;
        }

        Guest guest = this.guests.get(guestIndex);
        Company company = this.companies.get(companyIndex);
        String timeOfDayGreeting = determineTimeOfDay(guest.getReservation().getStartTimestamp(), company.getTimezone());
        return new Message(greeting, statement, closing, timeOfDayGreeting, guest.getFirstName(), company.getCompany(), guest.getReservation().getRoomNumber());

    }

    /*
     * Prompts user to input values for a custom template.
     * This will return with the Message object that will be displayed to the user.
     */
    private Message generateCustomMessage(int guestIndex, int companyIndex) {

        System.out.println("Please input your greeting message: \n\n");

        String greeting = scanner.nextLine().trim().toLowerCase();

        System.out.println("\n Please input your statement message: \n\n");

        String statement = scanner.nextLine().trim().toLowerCase();

        System.out.println("\n Please input your closing message: \n\n");

        String closing = scanner.nextLine().trim().toLowerCase();

        Guest guest = this.guests.get(guestIndex);
        Company company = this.companies.get(companyIndex);

        String timeOfDayGreeting = determineTimeOfDay(guest.getReservation().getStartTimestamp(), company.getTimezone());

        return new Message(greeting, statement, closing, timeOfDayGreeting, guest.getFirstName(), company.getCompany(), guest.getReservation().getRoomNumber());
    }

    /*
     * Helper method that creates the string for required array and message type.
     * Returns whatever the user selected.
     */
    private int getMessageTemplateIndex(ArrayList<String> messageTemplateArray, String messageType) {
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < messageTemplateArray.size(); ++i) {
            String type = messageTemplateArray.get(i);
            builder.append(i);
            builder.append(". ");
            builder.append(type);
            builder.append(" ");
            builder.append("\n");
        }

        return retrieveUserInput(messageTemplateArray.size(), "Please select which " + messageType + " you would like to use:", builder.toString());
    }

    /*
     * Helper method that gathers input from the user.
     * Returns whatever the user selected.
     */
    private int retrieveUserInput(int arrSize, String formattedSelectMessage, String messageOutput) {

        System.out.println("\n" + formattedSelectMessage + "\n\n" + messageOutput + "\n");

        int selectedNumber = -1;
        do {
            try {
                int currentEntry = scanner.nextInt();
                if (currentEntry < 0 || currentEntry >= arrSize) {
                    System.out.println("\nYour selection " + currentEntry + " was invalid. Please select a number from 0 to " + (arrSize - 1) + " \n\n");
                    System.out.println(messageOutput + "\n");
                } else {
                    selectedNumber = currentEntry;
                }
            } catch (InputMismatchException e) {
                System.out.println("\nYour selection was invalid. Please select a number from 0 to " + (arrSize - 1) + " \n\n");
                System.out.println(messageOutput + "\n");

            }
            scanner.nextLine();
        } while(selectedNumber == -1);

        return selectedNumber;
    }

    /*
     * Helper method that determines the correct time of day.
     * Based on the time, this method returns a specific greeting message.
     */
    private String determineTimeOfDay(long startTimestamp, String timeZone) {
        ZonedDateTime dateTime = Instant.ofEpochMilli(startTimestamp).atZone(ZoneId.of(timeZone));
        int hour = dateTime.getHour();

        if(hour == 0 || hour > 17) {
            return TimeOfDay.EVENING.getTimeOfDay();
        } else if(hour > 12) {
            return TimeOfDay.AFTERNOON.getTimeOfDay();
        } else {
            return TimeOfDay.MORNING.getTimeOfDay();
        }
    }
}
