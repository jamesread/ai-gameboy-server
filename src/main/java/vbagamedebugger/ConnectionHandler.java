package vbagamedebugger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

import com.aurellem.gb.Gb;

public class ConnectionHandler implements Runnable {

	private final Socket client;

	public ConnectionHandler(Socket socket) {
		this.client = socket;

		System.out.println("Started connection handler");

		new Thread(this, "connectionHandler").start();
	}

	private void handle(String line) {
		Scanner scanner = new Scanner(line);

		try {
			switch (scanner.next("\\w+")) {
			case "u":
				Main.press(Buttons.UP);
				break;
			case "d":
				Main.press(Buttons.DOWN);
				break;
			case "l":
				Main.press(Buttons.LEFT);
				break;
			case "r":
				Main.press(Buttons.RIGHT);
				break;
			case "quit":
				Main.shutdown();
				break;
			case "m":
				int start = scanner.nextInt(16);
				int stop = scanner.nextInt(16);

				int[] dmp = new int[stop - start];

				for (int currentAddress = start; currentAddress < stop; currentAddress++) {
					int i = (currentAddress - start);
					System.out.println(i);

					dmp[i] = Gb.readMemory(currentAddress);
				}

				this.send(dmp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.send("expn\n");
		}
	}

	@Override
	public void run() {
		BufferedReader bis = null;

		try {
			bis = new BufferedReader(new InputStreamReader(this.client.getInputStream()));

			String line;

			while ((line = bis.readLine()) != null) {
				System.out.println("recv: " + line);

				this.handle(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}

				this.client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void send(int[] dmp) {
		try {
			OutputStream os = this.client.getOutputStream();

			for (int element : dmp) {
				os.write(element);
			}

			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void send(String s) {
		try {
			BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(this.client.getOutputStream()));

			bos.write(s);
			bos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
