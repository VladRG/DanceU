/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danceexpertsystem;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Irina
 */
public class MessageSender extends Thread {

    Socket socket;
    MessageReader messageReader;
    volatile PipedOutputStream pos = null;
    PipedInputStream inputStream;
    OutputStream ostream;
    volatile boolean done = false;

    //setteri sincronizati
    public final synchronized void setPipedOutputStream(PipedOutputStream _pos) {
        pos = _pos;
        notify();
    }

    //getteri sincronizati
    public synchronized PipedOutputStream getPipedOutputStream() throws InterruptedException {
        if (pos == null) {
            wait();
        }
        return pos;
    }

    public MessageSender(MessageReader _messageReader) throws IOException {
        messageReader = _messageReader;
        inputStream = new PipedInputStream();
        setPipedOutputStream(new PipedOutputStream(inputStream));
    }

    public void sendMessage(String mesaj) throws Exception {
        PipedOutputStream outputStream = getPipedOutputStream();
        PrintStream ps = new PrintStream(outputStream);
        ps.println(mesaj + " .");
        ps.flush();
    }

    public void run() {
        try {
            socket = messageReader.getSocket();
            ostream = socket.getOutputStream();
            int chr;
            while ((chr = inputStream.read()) != -1) {
                ostream.write(chr);
            }

        } catch (IOException ex) {
            Logger.getLogger(MessageSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(MessageSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
