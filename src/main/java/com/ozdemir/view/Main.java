package com.ozdemir.view;

import com.ozdemir.data.EmployeeDAO;
import com.ozdemir.data.NonUniqueResultException;
import com.ozdemir.data.ProjectDAO;
import com.ozdemir.model.Employee;
import com.ozdemir.model.Project;
import com.ozdemir.model.Rantability;
import com.ozdemir.model.WorkDone;
import com.ozdemir.service.EmployeeService;
import com.ozdemir.service.ProjectService;
import com.ozdemir.service.WorkDoneService;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        boolean exitMain = false;

        while (!exitMain) {
            showMainMenu();
            int mainChoice = requestIntInput(0, 3);
            int subChoice = -1;

            if (mainChoice != 0) {
                showSubMenu(mainChoice);
                switch (mainChoice){
                    case 1:
                        subChoice = requestIntInput(0, 7);
                        break;
                    case 2:
                        subChoice = requestIntInput(0, 2);
                        break;
                    case 3:
                        subChoice = requestIntInput(0, 6);
                        break;
                }

                //If subchoice 0 than it will go to the main menu again

                if (subChoice != 0) {
                    executeChoice(mainChoice, subChoice);
                }
                //The only way to exit both menus is to select 0 in the main menu
            } else {
                exitMain = true;
                System.out.println("Thank you. Exiting...");
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("0. Exit");
        System.out.println("1. Menu for Employee Services");
        System.out.println("2. Menu for Project Services");
        System.out.println("3. Menu for WorkDone Services");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    private static void showSubMenu(int mainChoice) {
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

    private static int requestIntInput(int floor, int ceiling) {
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

    private static String requestStrInput() {
        Scanner keyboard = new Scanner(System.in);
        String input;
//        Do not accept null entries
        do{
            input = keyboard.nextLine();
        } while (input.length() == 0);
        return input;
    }

    private static String requestPhoneNumber() {

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

    private static Date requestDate() {

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

    private static Date requestDateAfterToday() {

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

    private static void executeChoice(int mainChoice, int subChoice) {
        if (mainChoice == 1) {
            EmployeeService employeeService = new EmployeeService();

            if (subChoice == 1) {
                // show all employees
                List<Employee> employees;

                try {
                    employees = employeeService.getAllEmployees();
                    employees.forEach(e -> System.out.println(e.toString()));
                } catch (SQLException e) {
                    System.out.println("There is a problem with the database");
                }
            }

            if (subChoice == 2) {
                // select employees with the given last name
                System.out.print("Please enter the last name: ");
                String lastName = requestStrInput();
                List<Employee> employees;

                try {
                    employees = employeeService.getEmployeeByLastName(lastName);
                    if (employees.isEmpty()){
                        System.out.println("No employee with the last name: " + lastName);
                    } else {
                        employees.forEach(e -> System.out.println(e.toString()));
                    }
                } catch (SQLException e) {
                    System.out.println("There is a problem with the database");
                }
            }

            if (subChoice == 3) {
                //Add an employee record
                Employee employee = new Employee();
                System.out.println("Please enter all the information required for the record");
                System.out.println("Name:");
                employee.setName(requestStrInput()) ;
                System.out.println("Last Name:");
                employee.setLastName(requestStrInput());
                System.out.println("Phone number: \n (To skip this step press 'n'):");
                employee.setPhoneNumber(requestPhoneNumber());
                System.out.println("Phone number (ICE): \n (To skip this step press 'n'):");
                employee.setPhoneNumberICE(requestPhoneNumber());
                System.out.println("Date of Birth (YYYY-MM-DD):");
                employee.setDateOfBirth(requestDate ());
//              Need to check if older than 18
                while (employee.getDateOfBirth().after(Date.valueOf(LocalDate.now().minusYears(18)))){
                    System.out.println("Employee has to be older than 18. Reenter:");
                    employee.setDateOfBirth(requestDate ());
                }

                System.out.println("Salary:");
                employee.setSalary(requestIntInput(0,500000));
                try{
                    employeeService.saveEmployee(employee);
                } catch (SQLException e){
                    System.out.println("There is a problem with the database");
                }
            }

            if (subChoice == 4) {
                //Update an employee
                //select the record with PK: id
                System.out.println("Please enter employee id: ");
                int id = requestIntInput(1, Integer.MAX_VALUE);

                Optional<Employee> optionalEmployee = null;
                try {
                    optionalEmployee = employeeService.getEmployeeById(id);
                } catch (SQLException e) {
                    System.out.println("Problems with db...");
                } catch (NonUniqueResultException e) {
                    System.out.println(e.getMessage());
                }
                if (optionalEmployee.isPresent()) {
                    System.out.println(optionalEmployee.get().toString() + "\nwill be updated");
                    int choise;
                    //until the user chooses 0 (exit), field changes will be kept and when 0 is selected db will be updated
                    do {
                        System.out.println("Which column do you want to update\n" +
                                "Enter 0: Quit\n"+
                                "Enter 1: update name\n"+
                                "Enter 2: update last name\n"+
                                "Enter 3: update phone number\n"+
                                "Enter 4: update phone number ICE\n"+
                                "Enter 5: update date of birth\n"+
                                "Enter 6: update salary\n"+
                                ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                        choise = requestIntInput(0,6);
                        switch (choise){
                                case 1:
                                System.out.println("Please enter the new name:");
                                String newName = requestStrInput();
                                optionalEmployee.get().setName(newName);
                                break;
                            case 2:
                                System.out.println("Please enter the new last name:");
                                String newLastName = requestStrInput();
                                optionalEmployee.get().setLastName(newLastName);
                                break;
                            case 3:
                                System.out.println("Please enter the new phone number:");
                                String newPhoneNumber = requestPhoneNumber();
                                optionalEmployee.get().setPhoneNumber(newPhoneNumber);
                                break;
                            case 4:
                                System.out.println("Please enter the new phone number ICE:");
                                String newPhoneNumberICE = requestPhoneNumber();
                                optionalEmployee.get().setPhoneNumberICE(newPhoneNumberICE);
                                break;
                            case 5:
                                System.out.println("Please enter the new DoB:");
                                Date newDOB = requestDate();
                                optionalEmployee.get().setDateOfBirth(newDOB);
                                break;
                            case 6:
                                System.out.println("Please enter the new salary:");
                                double newSalary = requestIntInput(0,500000);
                                optionalEmployee.get().setSalary(newSalary);
                                break;
                        }
                    }while (choise != 0);
                    try {
                        employeeService.updateEmployee (optionalEmployee.get());
                    }catch (SQLException e){
                        System.out.println("There is a problem with the database");
                    }

                } else {
                    System.out.println("Employee with id: " + id + " was not found in database.");
                }
            }

            if (subChoice == 5) {
                //Delete an employee with id");
                System.out.println("Please enter employee id to delete: ");
                int id = requestIntInput(1, Integer.MAX_VALUE);

                Optional<Employee> optionalEmployee = null;
                try {
                    optionalEmployee = employeeService.getEmployeeById(id);
                } catch (SQLException e) {
                    System.out.println("Problems with db...");
                } catch (NonUniqueResultException e) {
                    System.out.println(e.getMessage());
                }
                if (optionalEmployee.isPresent()) {
                    System.out.println(optionalEmployee.get().toString() + "\nwill be deleted");
                    System.out.println("Do you confirm : (y/n)");
                    String choise;
                    do{
                        choise = requestStrInput();

                        if (choise.toLowerCase().charAt(0) == 'n'){
                            break;
                        }

                        if (choise.toLowerCase().charAt(0) == 'y'){
                            try{
                                employeeService.deleteById (id);
                            } catch (SQLException e){
                                System.out.println("There is a problem with the database");
                            }

                        }
                    }while (choise.toLowerCase().charAt(0) != 'n' && choise.toLowerCase().charAt(0) != 'y');
                }else {
                    System.out.println("Employee with id: " + id + " was not found in database.");
                }
            }

            if (subChoice == 6 || subChoice == 7) {
                //Show employees born today or within next 7 days
                List<Employee> employees;

                try{
                    //I need to send the parameter based on the choise (e.g: 0 for todays births or 7 for the week)
                    employees = employeeService.getEmployeeByDOB ( subChoice == 6 ? 0 : 7);
                    if (employees.isEmpty()){
                        System.out.println("There is no employee who born today");
                    }
                    employees.forEach(e -> System.out.println(e.toString()));
                }catch (SQLException e){
                    System.out.println("There is a problem with the database");
                }
            }
        }

        if(mainChoice == 2) {
            ProjectService projectService = new ProjectService();
            if (subChoice == 1) {
                //Add a new project
                Project project = new Project();
                System.out.println("Please enter all the information required for the record");
                System.out.println("Project Name:");
                project.setName(requestStrInput()) ;
                System.out.println("Start Date (YYYY-MM-DD):");
                project.setStartDate(requestDateAfterToday ());
                System.out.println("Project Description:");
                project.setDescription(requestStrInput());
                System.out.println("Project Price:");
                project.setPrice(requestIntInput(1,500000));
                System.out.println("Expected End Date (YYYY-MM-DD):");
                project.setExpectedEndDate(requestDateAfterToday ());
                while (project.getStartDate().after(project.getExpectedEndDate())){
                    System.out.println("Expected End Date can not be before the start date of the project!!\nEnter a new date:");
                    project.setExpectedEndDate(requestDateAfterToday ());
                }

                try{
                    projectService.saveProject(project);
                } catch (SQLException e){
                    System.out.println("There is a problem with the database");
                }
            }

            if (subChoice == 2) {
                //Delete a project with id");
                System.out.println("Please enter project id to delete: ");
                int id = requestIntInput(1, Integer.MAX_VALUE);

                Optional<Project> optionalProject = null;
                try {
                    optionalProject = projectService.getProjectById(id);
                } catch (SQLException e) {
                    System.out.println("Problems with db...");
                } catch (NonUniqueResultException e) {
                    System.out.println(e.getMessage());
                }
                if (optionalProject.isPresent()) {
                    System.out.println(optionalProject.get().toString() + "\nwill be deleted");
                    System.out.println("Do you confirm : (y/n)");
                    String choise;
                    do{
                        choise = requestStrInput();

                        if (choise.toLowerCase().charAt(0) == 'n'){
                            break;
                        }

                        if (choise.toLowerCase().charAt(0) == 'y'){
                            try{
                                projectService.deleteById (id);
                            } catch (SQLException e){
                                System.out.println("There is a problem with the database");
                            }

                        }
                    }while (choise.toLowerCase().charAt(0) != 'n' && choise.toLowerCase().charAt(0) != 'y');
                }else {
                    System.out.println("Employee with id: " + id + " was not found in database.");
                }
            }
        }
        if(mainChoice == 3) {
            WorkDoneService workDoneService = new WorkDoneService();
            if (subChoice == 1) {
                //1. Show all work done
                List<WorkDone> workDones;

                try {
                    workDones = workDoneService.getAllWorkDones();
                    workDones.forEach(w -> System.out.println(w.getOneLine()));
                } catch (SQLException e) {
                    System.out.println("There is a problem with the database");
                }
            }
            if (subChoice == 2) {
                //2. Add work done"
                WorkDone workDone = new WorkDone();
                EmployeeDAO employeeDAO = new EmployeeDAO();
                ProjectDAO projectDAO = new ProjectDAO();
                try{
                    System.out.println("Please enter all the information required for the record");
                    System.out.println("Employee Id:");
                    workDone.setEmployeeId(requestIntInput(1,Integer.MAX_VALUE));
                    Optional<Employee> optionalEmployee = employeeDAO.getEmployeeById(workDone.getEmployeeId());

                    while (optionalEmployee.isEmpty()){
                        System.out.println("This employee can not be found. Please try again:");
                        workDone.setEmployeeId(requestIntInput(1,Integer.MAX_VALUE));
                        optionalEmployee = employeeDAO.getEmployeeById(workDone.getEmployeeId());
                    }
                    workDone.setEmployee(optionalEmployee.get());

                    System.out.println("Project Id:");
                    workDone.setProjectId(requestIntInput(1,Integer.MAX_VALUE));
                    Optional<Project> optionalProject = projectDAO.getProjectById(workDone.getProjectId());

                    while (optionalProject.isEmpty()){
                        System.out.println("This project can not be found. Please try again:");
                        workDone.setProjectId(requestIntInput(1,Integer.MAX_VALUE));
                        optionalProject = projectDAO.getProjectById(workDone.getProjectId());
                    }

                    workDone.setProject(optionalProject.get());

                    System.out.println("Start of Work (YYYY-MM-DD):");
                    workDone.setStartOfWork(requestDate());
                    while (workDone.getStartOfWork().before(workDone.getProject().getStartDate())){
                        System.out.println("Start of work may not be before the start date of the project (" +
                                workDone.getProject().getStartDate() + ") Enter again:");
                        workDone.setStartOfWork(requestDate());
                    }

                    System.out.println("End of Work (YYYY-MM-DD):");
                    workDone.setEndOfWork(requestDate());
                    while (workDone.getEndOfWork().before(workDone.getStartOfWork())){
                        System.out.println("End of work may not be before the start of work (" +
                                workDone.getStartOfWork() + ") Enter again:");
                        workDone.setEndOfWork(requestDate());
                    }

                    System.out.println("Hours worked:");
                    workDone.setHoursWorked(requestIntInput (1,Integer.MAX_VALUE));

                    System.out.println("Remarks:");
                    workDone.setRemarks(requestStrInput());


                        workDoneService.saveWorkDone(workDone);
                } catch (SQLException | NonUniqueResultException e){
                    System.out.println("There is a problem with the database");
                }
            }
            if (subChoice == 3) {
                //3. Update work done
                //select the record with two Foreign keys
                System.out.println("Please enter employee id: ");
                int employeeId = requestIntInput(1, Integer.MAX_VALUE);
                System.out.println("Project id: ");
                int projectId = requestIntInput(1, Integer.MAX_VALUE);

                Optional<WorkDone> optionalWorkDone = null;
                try {
                    optionalWorkDone = workDoneService.getWorkDoneByFKs(employeeId,projectId);
                } catch (SQLException e) {
                    System.out.println("There is a problem with the database");
                } catch (NonUniqueResultException e) {
                    System.out.println(e.getMessage());
                }
                if (optionalWorkDone.isPresent()) {
                    System.out.println(optionalWorkDone.get().toString() + "\nwill be updated");
                    int choise;
                    //until the user chooses 0 (exit), field changes will be kept and when 0 is selected db will be updated
                    do {
                        System.out.println("Which column do you want to update\n" +
                                "Enter 0: Quit\n"+
                                "Enter 1: update start of work\n"+
                                "Enter 2: update end of work\n"+
                                "Enter 3: update hours worked\n"+
                                "Enter 4: update remarks\n"+
                                ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                        choise = requestIntInput(0,4);
                        switch (choise){
                            case 1:
                                System.out.println("Please enter the new start date of the work:");
                                Date newStartOfWork = requestDate();
                                Date projectStartDate = optionalWorkDone.get().getProject().getStartDate();
                                Date projectEndDate = optionalWorkDone.get().getProject().getExpectedEndDate();
                                while (newStartOfWork.before(projectStartDate) || newStartOfWork.after(projectEndDate)){
                                    System.out.println("Date can not be before the start of the project: " + projectStartDate +
                                            " or after the end of the project: " + projectEndDate);
                                    System.out.println("Please enter a new start date of the work:");
                                    newStartOfWork = requestDate();
                                }
                                optionalWorkDone.get().setStartOfWork(newStartOfWork);
                                break;
                            case 2:
                                System.out.println("Please enter the new end date of the work:");
                                Date newEndOfWork = requestDate();
                                Date projectsStartDate = optionalWorkDone.get().getProject().getStartDate();
                                Date projectsEndDate = optionalWorkDone.get().getProject().getExpectedEndDate();
                                while (newEndOfWork.after(projectsEndDate) || newEndOfWork.before(projectsStartDate)){
                                    System.out.println("Date can not be after the end of the project: " + projectsEndDate +
                                            " or before the start of the project: " + projectsStartDate);
                                    System.out.println("Please enter a new end date of the work:");
                                    newEndOfWork = requestDate();
                                }
                                optionalWorkDone.get().setEndOfWork(newEndOfWork);
                                break;
                            case 3:
                                System.out.println("Please enter the new hours worked:");
                                int newHoursWorked = requestIntInput(0,Integer.MAX_VALUE);
                                optionalWorkDone.get().setHoursWorked(newHoursWorked);
                                break;
                            case 4:
                                System.out.println("Please enter the new remarks:");
                                String newRemarks = requestStrInput();
                                optionalWorkDone.get().setRemarks(newRemarks);
                                break;
                        }
                    }while (choise != 0);
                    try {
                        workDoneService.updateWorkDone (optionalWorkDone.get());
                    }catch (SQLException e){
                        System.out.println("There is a problem with the database");
                    }
                }else System.out.println("No records found with the given employee and project pair");
            }
            if (subChoice == 4) {
                //4. Delete work done
                System.out.println("Please enter employee id: ");
                int employeeId = requestIntInput(1, Integer.MAX_VALUE);
                System.out.println("Project id: ");
                int projectId = requestIntInput(1, Integer.MAX_VALUE);

                Optional<WorkDone> optionalWorkDone = null;
                try {
                    optionalWorkDone = workDoneService.getWorkDoneByFKs(employeeId,projectId);
                } catch (SQLException e) {
                    System.out.println("There is a problem with the database");
                } catch (NonUniqueResultException e) {
                    System.out.println(e.getMessage());
                }

                if (optionalWorkDone.isPresent()) {
                    System.out.println(optionalWorkDone.get().toString() + "\nwill be deleted");
                    System.out.println("Do you confirm : (y/n)");
                    String choise;
                    do{
                        choise = requestStrInput();

                        if (choise.toLowerCase().charAt(0) == 'n'){
                            break;
                        }

                        if (choise.toLowerCase().charAt(0) == 'y'){
                            try{
                                workDoneService.deleteByFKs (employeeId, projectId);
                            } catch (SQLException e){
                                System.out.println("There is a problem with the database");
                            }

                        }
                    }while (choise.toLowerCase().charAt(0) != 'n' && choise.toLowerCase().charAt(0) != 'y');
                }else {
                    System.out.println("Work Done with employee Id: " + employeeId + " and project Id: " + projectId + " was not found in database.");
                }

            }
            if (subChoice == 5) {
                //5. Show rantability of a given project
                List<Rantability> rantabilities = new ArrayList<>();
                ProjectService projectService = new ProjectService();
                EmployeeService employeeService = new EmployeeService();
                List<Project> allProjects = null;
                try {
                    allProjects = projectService.getAllProjects();

                    for(int i = 0; i < allProjects.size();i++){
                        Rantability r = new Rantability();
                        r.setProjectId(allProjects.get(i).getId());
                        r.setProjectPrice(allProjects.get(i).getPrice());
                        rantabilities.add(r);
                    }

                    for (Rantability r : rantabilities){
                        List<WorkDone> workDones = workDoneService.getWorkDoneByProjectId(r.getProjectId());
                        double totalEmpCost = 0;
                        for (WorkDone w : workDones){
                            Optional<Employee> optEmployee = employeeService.getEmployeeById(w.getEmployeeId());
                            totalEmpCost += (optEmployee.get().calculateHourlyCost() * w.getHoursWorked());
                        }
                        r.setTotalEmployeeCost(totalEmpCost);
                        r.setRantability(r.getProjectPrice()-r.getTotalEmployeeCost());
                        System.out.println(r.toString());
                    }
                }catch (SQLException e) {
                    System.out.println("There is a problem with the database");
                }catch (NonUniqueResultException ne) {
                    System.out.println(ne.getMessage());
                }
            }
            if (subChoice == 6) {
                //6. Show top 3 employees in a given project
            }
        }
    }
}