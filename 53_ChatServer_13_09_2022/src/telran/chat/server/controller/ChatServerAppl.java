package telran.chat.server.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import telran.chat.model.Message;
import telran.chat.server.tasks.ChatServerReciever;
import telran.chat.server.tasks.ChatServerSender;
import telran.mediation.BlkQueue;
import telran.mediation.IBlkQueue;


public class ChatServerAppl {

    public static void main(String[] args) {
	int port = 9000;
	IBlkQueue<Message> messageBox = new BlkQueue<>(100);
	ChatServerSender sender = new ChatServerSender(messageBox);
	Thread senderThread = new Thread(sender);
	senderThread.setDaemon(true);
	senderThread.start();
	ExecutorService exService = Executors.newFixedThreadPool(9);
	try (ServerSocket serverSocket = new ServerSocket(port)) {
	    try {
		while (true) {
		    System.out.println("Server waiting...");
		    Socket socket = serverSocket.accept();
		    System.out.println("Connection established");
		    System.out.println("Client asddress: " + socket.getInetAddress() + ":" + socket.getPort());
		    sender.addClient(socket);
		    ChatServerReciever reciever = new ChatServerReciever(socket, messageBox);
		    exService.execute(reciever);
		}
	    } finally {
		exService.shutdown();
		exService.awaitTermination(1, TimeUnit.MINUTES);
	    }
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}
