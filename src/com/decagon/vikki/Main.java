package com.decagon.vikki;

import java.io.*;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) {
	// write your code here
        final int PORT = 3000;

        //Create server that is listening for request
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started. " + "\n" + "Listening for messages...");
            System.out.println();
            while(true) {
                //handle a new incoming messages
                var client = serverSocket.accept();
                System.out.println("New message: " + client.toString());

                //Read the request - listen to the message
                InputStreamReader inputStreamReader = new InputStreamReader(client.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder request = new StringBuilder();

                String  line = bufferedReader.readLine();
                while(!line.isBlank()) {
                    request.append(line + "\r\n");
                    line = bufferedReader.readLine();
                }


                System.out.println("#### REQUEST ####");
//                System.out.println(request);

                //Respond to the message

                //Loading image from the file system
                FileInputStream image = new FileInputStream("myself.png");

                //Routing based on resource identifier
                var splittedRequest = request.toString().split("\n");
                var firstLine = splittedRequest[0];
                System.out.println(firstLine);
                var uri = firstLine.split(" ")[1];
                System.out.println(uri);
                System.out.println();

                if(uri.equals("/vikki")) {
                    //this bunch of code outputs an image to the output stream.
                    OutputStream clientOutput = client.getOutputStream();
                    clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());
                    clientOutput.write(("\r\n").getBytes());
                    clientOutput.write(image.readAllBytes());
                    clientOutput.flush();

                } else if(uri.equals("/hello")) {
                    //this bunch of statements just outputs Hello world to the output stream
                    OutputStream clientOutput = client.getOutputStream();
                    clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());
                    clientOutput.write(("\r\n").getBytes());
                    clientOutput.write(("Hello world").getBytes());
                    clientOutput.flush();
                } else {
                    OutputStream clientOutput = client.getOutputStream();
                    clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());
                    clientOutput.write(("\r\n").getBytes());
                    clientOutput.write(("What do you want to do?").getBytes());
                    clientOutput.flush();
                }

                client.close();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
