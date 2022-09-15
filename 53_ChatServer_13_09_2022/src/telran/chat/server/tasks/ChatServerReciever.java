package telran.chat.server.tasks;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import telran.chat.model.Message;
import telran.mediation.IBlkQueue;

public class ChatServerReciever implements Runnable {
    Socket socket;
    IBlkQueue<Message> messageBox;

    public ChatServerReciever(Socket socket, IBlkQueue<Message> messageBox) {
	this.socket = socket;
	this.messageBox = messageBox;
    }

    @Override
    public void run() {
	try(Socket socket = this.socket){
	    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
	    while(true) {
		Message message = (Message) ois.readObject();
		messageBox.push(message);
	    }
	    
	} catch (IOException e) {
	    System.out.println("Client: " + socket.getInetAddress()
		+ ":" + socket.getLocalPort()
		+ "connection closed");
//	    e.printStackTrace();
	}
	catch (ClassNotFoundException e) {
	    e.printStackTrace();
	}

    }

}
