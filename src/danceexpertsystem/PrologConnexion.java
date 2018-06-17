/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danceexpertsystem;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Irina
 */
public class PrologConnexion {

    final String execPath = "spwin";
    final String fileName = "prolog_database.pl";
    final String goal = "start_process.";
    Process sisctusProcess;
    MessageSender sender;
    MessageReader reader;
    Window window;

    int port;

    public Window getWindow() {
        return window;
    }

    public PrologConnexion(int _port, Window _window) throws IOException, InterruptedException {
        InputStream processIs, processStreamErr;
        port = _port;
        window = _window;
        //obtin cale executabil
        //String caleExecutabilSicstus = System.getProperty("sicstusProgram", "C:\\Users\\Irina\\Desktop\\SICStus Prolog 4.0.2\\SICStus Prolog 4.0.2\\bin\\sicstus.exe");
        //acces la mediul curent de rulare
        ServerSocket serverSocket = new ServerSocket(port);
        //Socket sock_s=servs.accept();
        reader = new MessageReader(this, serverSocket);
        reader.start();
        sender = new MessageSender(reader);
        sender.start();

        Runtime rtime = Runtime.getRuntime();

        String comanda = execPath + " -f -l " + fileName + " --goal " + goal + " -a " + port;

        sisctusProcess = rtime.exec(comanda);

        //InputStream-ul din care citim ce scrie procesul
        processIs = sisctusProcess.getInputStream();
        //stream-ul de eroare
        processStreamErr = sisctusProcess.getErrorStream();

    }

    void stopPrologProcess() throws InterruptedException {
        PipedOutputStream pos = this.sender.getPipedOutputStream();
        PrintStream ps = new PrintStream(pos);
        ps.println("exit.");
        ps.flush();
    }
}
