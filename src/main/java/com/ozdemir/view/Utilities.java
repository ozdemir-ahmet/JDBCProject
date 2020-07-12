package com.ozdemir.view;

import java.sql.Date;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Utilities {
    public void showMainMenu() {
        System.out.println("0. Exit");
        System.out.println("1. Menu for Employee Services");
        System.out.println("2. Menu for Project Services");
        System.out.println("3. Menu for WorkDone Services");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    public void showSubMenu(int mainChoice) {
        if (mainChoice == 1) {
            System.out.println("0. Back to main menu");
            System.out.println("1. Show all employees");
            System.out.println("2. Show employees with the requested last name");
            System.out.println("3. Add an employee record");
            System.out.println("4. Update an employee");
            System.out.println("5. Delete an employee");
            System.out.println("6. Show employees born today");
            System.out.println("7. Show employees born within next 7 days");
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }

        if (mainChoice == 2) {
            System.out.println("0. Back to main menu");
            System.out.println("1. Add a project");
            System.out.println("2. Delete a project");
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }

        if (mainChoice == 3) {
            System.out.println("0. Back to main menu");
            System.out.println("1. Show all work done");
            System.out.println("2. Add work done");
            System.out.println("3. Update work done");
            System.out.println("4. Delete work done");
            System.out.println("5. Show rantability of a given project");
            System.out.println("6. Show top 3 employees in a given project");
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
    }

    public int requestIntInput(int floor, int ceiling) {
        Scanner keyboard = new Scanner(System.in);
        int choise;
        do {
            try {
                System.out.print("Please enter your choice: ");
                choise = keyboard.nextInt();
            } catch (InputMismatchException e) {
                choise = -1;
            }
            keyboard.nextLine();
            if (choise < floor || choise > ceiling) System.out.println("Invalid number");
        } while (choise < floor || choise > ceiling);

        return choise;
    }

    public String requestStrInput() {
        Scanner keyboard = new Scanner(System.in);
        String input;
//        Do not accept null entries
        do{
            input = keyboard.nextLine();
        } while (input.length() == 0);
        return input;
    }

    public String requestPhoneNumber() {

        Scanner keyboard = new Scanner(System.in);
        String phoneNumber;
        while (true) {
            phoneNumber = keyboard.next();
            if (phoneNumber.toLowerCase().charAt(0) == 'n'){
                phoneNumber = null;
                break;
            } else {
                try{
                    Long.parseLong(phoneNumber);
                } catch (NumberFormatException nfe){
                    System.out.println("Invalid phone number.(All numbers please) Try again.");
                    continue;
                }

                if (phoneNumber.charAt(0) == '0'){
                    if (phoneNumber.length() == 9 || phoneNumber.length()==10){
                        break;
                    }else {
                        System.out.println("Invalid phone number.(9 or 10 numbers) Try again.");
                    }
                }else {
                    System.out.println("Invalid phone number.(Should start with 0) Try again.");
                }
            }
        }
        return phoneNumber;
    }

    public Date requestDate() {

        Scanner keyboard = new Scanner(System.in);
        Date date;
        while (true) {
            try {
                date = Date.valueOf(keyboard.next());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Format must: YYYY-MM-DD");
            }
        }
        return date;
    }

    public Date requestDateAfterToday() {

        Scanner keyboard = new Scanner(System.in);
        Date date;
        Date now = Date.valueOf(LocalDate.now());
        while (true) {
            try {
                date = Date.valueOf(keyboard.next());
                if (date.before(now)){
                    System.out.println("Date can nor be before today!");
                } else {
                    break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Format must: YYYY-MM-DD");
            }
        }
        return date;
    }
}
