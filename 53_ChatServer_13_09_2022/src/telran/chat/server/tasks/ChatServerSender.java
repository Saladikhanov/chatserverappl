package telran.chat.server.tasks;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import telran.chat.model.Message;
import telran.mediation.IBlkQueue;

public class ChatServerSender implements Runnable {
    IBlkQueue<Message> messageBox;
    Set<ObjectOutputStream> clients;

    public ChatServerSender(IBlkQueue<Message> messageBox) {
	this.messageBox = messageBox;
	clients = new HashSet<>();
    }

    public synchronized boolean addClient(Socket socket) throws IOException {
	return clients.add(new ObjectOutputStream(socket.getOutputStream()));
    }

    @Override
    public void run() {
	while (true) {
	    Message message = messageBox.pop();
	    synchronized (this) {
		Iterator<ObjectOutputStream> iterator = clients.iterator();
		while (iterator.hasNext()) {
		    try {
			iterator.next().writeObject(message);
		    } catch (IOException e) {
			iterator.remove();
		    }
		}
	    }
	}
    }

}
