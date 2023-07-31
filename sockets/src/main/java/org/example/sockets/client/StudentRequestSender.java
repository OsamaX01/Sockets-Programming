package org.example.sockets.client;

import org.example.student.Course;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentRequestSender {
    private int studentId;
    private Scanner scanner = new Scanner(System.in);

    StudentRequestSender(int id) {
        studentId = id;
    }

    private void printList() {
        System.out.println("Please chose an option number from (1-5):");
        System.out.println("1) View your marks in all courses");
        System.out.println("2) View your mark in a particular course");
        System.out.println("3) View Average in a particular course");
        System.out.println("4) View Class Max mark in a particular course");
        System.out.println("5) View Class min mark in a particular course");
    }

    private void printCourses() {
        System.out.println("Please enter course number:");
        System.out.println("1) CPP");
        System.out.println("2) Java");
        System.out.println("3) Ruby");
    }

    private String getCourseName() {
        while (true) {
            int choice = scanner.nextInt();
            if (choice == 1)
                return "cpp";
            else if (choice == 2)
                return "java";
            else if (choice == 3)
                return "ruby";
        }
    }

    public void run(ObjectInputStream fromServer, ObjectOutputStream toServer) throws IOException, ClassNotFoundException {

    active:
        while (true) {
            printList();
            int requestType = scanner.nextInt();
            toServer.writeInt(requestType);
            toServer.flush();

            switch(requestType) {
                case 1: {
                    toServer.writeInt(studentId);
                    toServer.flush();
                    ArrayList<Course> courses = (ArrayList<Course>) fromServer.readObject();
                    for (Course c : courses) {
                        System.out.println(c);
                    }
                }
                break;

                case 2: {
                    printCourses();
                    String courseName = getCourseName();
                    toServer.writeInt(studentId);
                    toServer.flush();
                    toServer.writeObject(courseName);
                    toServer.flush();
                    System.out.println(fromServer.readObject());
                }
                break;

                case 3:
                case 4:
                case 5: {
                    printCourses();
                    String courseName = getCourseName();
                    toServer.writeObject(courseName);
                    toServer.flush();
                    System.out.println(fromServer.readDouble());
                }
                break;

                case 0:
                    break active;
                default:
                    System.out.println("Invalid option :(");
            }
        }
    }
}
