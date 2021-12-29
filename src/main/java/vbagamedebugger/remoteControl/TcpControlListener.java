package vbagamedebugger.remoteControl;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import vbagamedebugger.Main;

public class TcpControlListener implements Runnable {
	private ServerSocket sock;

	@Override
	public void run() {
		try {
			this.sock = new ServerSocket(1337, 5, InetAddress.getLocalHost());

			while (Main.run) {
				System.out.println("running");

				Socket client = this.sock.accept();

				new ConnectionHandler(client);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
