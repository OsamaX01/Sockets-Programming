package org.example.sockets.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class AuthenticationRequest {
    private int id;
    private String password;
    private boolean isAuthenticated = false;
    private Scanner scanner = new Scanner(System.in);

    private void getInfo() {
        System.out.println("Please enter Your student id:");
        id = scanner.nextInt();
        System.out.println("Please enter Your student password:");
        password = scanner.next();
    }

    public boolean isAuthenticated() {
        return isAuthenticated();
    }

    public int getId() {
        return id;
    }

    public void run(ObjectInputStream fromServer, ObjectOutputStream toServer) throws IOException, ClassNotFoundException {
        while (!isAuthenticated) {
            getInfo();
            toServer.writeInt(id);
            toServer.flush();
            toServer.writeObject(password);
            toServer.flush();
            System.out.println(fromServer.readObject());
            isAuthenticated = fromServer.readBoolean();
        }
    }
}
