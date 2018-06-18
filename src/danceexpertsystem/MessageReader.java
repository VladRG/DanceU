/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danceexpertsystem;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author Irina
 */
public class MessageReader extends Thread {

    ServerSocket serverSocket;
    volatile Socket socket = null;//volatile ca sa fie protejat la accesul concurent al mai multor threaduri
    volatile PipedInputStream inputStream = null;
    PrologConnexion connection;

    //setteri sincronizati
    public synchronized void setSocket(Socket _s) {
        socket = _s;
        notify();
    }

    public final synchronized void setPipedInputStream(PipedInputStream _inputStream) {
        inputStream = _inputStream;
        notify();
    }
    //getteri sincronizati

    public synchronized Socket getSocket() throws InterruptedException {
        if (socket == null) {
            wait();//asteapta pana este setat un socket
        }
        return socket;
    }

    public synchronized PipedInputStream getPipedInputStream() throws InterruptedException {
        if (inputStream == null) {
            wait();
        }
        return inputStream;
    }

    //constructor
    public MessageReader(PrologConnexion _connection, ServerSocket _serverSocket) throws IOException {
        serverSocket = _serverSocket;
        connection = _connection;
    }

    @Override
    public void run() {
        try {
            //apel blocant, asteapta conexiunea
            //conexiunea clinetului se face din prolog
            Socket auxSocket = serverSocket.accept();
            setSocket(auxSocket);
            //pregatesc InputStream-ul pentru a citi de pe Socket
            InputStream auxInputStream = auxSocket.getInputStream();

            PipedOutputStream outputStream = new PipedOutputStream();
            setPipedInputStream(new PipedInputStream(outputStream));//leg un pipedInputStream de capatul in care se scrie

            int chr;
            String str = "";
            while ((chr = auxInputStream.read()) != -1) {//pana nu citeste EOF
                outputStream.write(chr);//pun date in Pipe, primite de la Prolog
                str += (char) chr;
                if (chr == '\n') {
                    final String writeString = str;

                    str = "";
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            String[] commandResponse = writeString.split("::");
                            String[] strings = commandResponse[1].split("#");

                            if (commandResponse[0].equalsIgnoreCase("dances")) {
                                connection.getWindow().setDances(strings);
                            }

                            if (commandResponse[0].equalsIgnoreCase("personality_types")) {
                                connection.getWindow().setPersonalityTypes(strings);
                            }

                            if (commandResponse[0].equalsIgnoreCase("health_statuses")) {
                                connection.getWindow().setHealthStatuses(strings);
                            }

                            if (commandResponse[0].equalsIgnoreCase("budgets")) {
                                connection.getWindow().setBudgets(strings);
                            }

                            if (commandResponse[0].equalsIgnoreCase("availabilities")) {
                                connection.getWindow().setAvailabilities(strings);
                            }

                            if (commandResponse[0].equalsIgnoreCase("ages")) {
                                connection.getWindow().setAges(strings);
                            }

                            if (commandResponse[0].equalsIgnoreCase("rythms")) {
                                connection.getWindow().setRythms(strings);
                            }

                            if (commandResponse[0].equalsIgnoreCase("purposes")) {
                                connection.getWindow().setPurposes(strings);
                            }
                        }
                    });
                }
            }
            System.out.println(str);

        } catch (IOException ex) {
            Logger.getLogger(MessageReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
