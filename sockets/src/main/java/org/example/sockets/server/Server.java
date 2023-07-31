package org.example.sockets.server;

import java.io.*;
import java.net.*;

public class Server {
    static ServerSocket serverSocket;
    static ServerDatabase serverDatabase;
    static Socket socket;
    static ObjectOutputStream outputToClient;
    static ObjectInputStream inputFromClient;

    public static void studentResponder() throws IOException, ClassNotFoundException {
        active:
        while (true) {
            int requestType = inputFromClient.readInt();
            switch (requestType) {
                case 1: {
                    int id = inputFromClient.readInt();
                    outputToClient.writeObject(serverDatabase.getAllCourses(id));
                    outputToClient.flush();
                }
                break;

                case 2: {
                    int id = inputFromClient.readInt();
                    String courseName = (String)inputFromClient.readObject();
                    outputToClient.writeObject(serverDatabase.getCourse(courseName, id));
                    outputToClient.flush();
                }
                break;

                case 3:{
                    String courseName = (String)inputFromClient.readObject();
                    outputToClient.writeDouble(serverDatabase.getCourseAverage(courseName));
                    outputToClient.flush();
                }
                break;

                case 4:{
                    String courseName = (String)inputFromClient.readObject();
                    outputToClient.writeDouble(serverDatabase.getCourseMaxMark(courseName));
                    outputToClient.flush();
                }
                break;

                case 5:{
                    String courseName = (String)inputFromClient.readObject();
                    outputToClient.writeDouble(serverDatabase.getCourseMinMark(courseName));
                    outputToClient.flush();
                }
                break;

                case 0:
                    break active;

                default:
                    throw new IllegalStateException("Operation is not supported from the server");
            }
        }
    }

    public static void authenticationResponder() throws IOException, ClassNotFoundException {
        while (true) {
            int id = inputFromClient.readInt();
            String password = (String)inputFromClient.readObject();
            System.out.println("hello");
            if (!serverDatabase.isRegisteredStudent(id)) {
                outputToClient.writeObject("Unregistered student !!");
                outputToClient.flush();
                outputToClient.writeBoolean(false);
                outputToClient.flush();
            }
            else if (!serverDatabase.isValidPassword(id, password)) {
                outputToClient.writeObject("Password is incorrect !!");
                outputToClient.flush();
                outputToClient.writeBoolean(false);
                outputToClient.flush();
            }
            else {
                outputToClient.writeObject("Authenticated :D ");
                outputToClient.flush();
                outputToClient.writeBoolean(true);
                outputToClient.flush();
                break;
            }
        }
    }

    public static void main(String[] args) {
        new Thread( () -> {
            try {
                serverSocket = new ServerSocket(8080);
                serverDatabase = new ServerDatabase();
                socket = serverSocket.accept();
                outputToClient = new ObjectOutputStream(socket.getOutputStream());
                inputFromClient = new ObjectInputStream(socket.getInputStream());
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }

            try {
                authenticationResponder();
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }

            try {
                studentResponder();
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }).start();
    }
}