package com.ibm.us.srloomis.harfbuzz;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

/**
 * Hacky simple broadcast thing
 * 
 * @author srl
 *
 */
public class SimplePubSub {
	private static final String TMP_SOCKET_PATH = System.getProperty("java.io.tmpdir", "/tmp");
	private static final File TMP_SOCKET_FILE = new File(TMP_SOCKET_PATH);
	final String channel;
	final private DatagramSocket socket = getSocket();

	/**
	 * Set the 'channel'. Actually the prefix of some path in a temporary dir. We
	 * will create rando directories mentioning the channel name.
	 * 
	 * @param channel
	 */
	SimplePubSub(String channel) {
		this.channel = channel;
		try {
			// this.socket.bind(null);
			System.out.println("Socket: " + this.socket.getLocalPort() + ":" + this.socket.getPort());
			File f = new File(TMP_SOCKET_FILE, channel + "-" + this.socket.getLocalPort());
			f.mkdir();
			System.out.println("Created socket notify dir: " + f.getAbsolutePath());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Setup the socket.
	 * 
	 * @return
	 */
	private DatagramSocket getSocket() {
		try {
			return new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Send some stuff.
	 * 
	 * @param string
	 */
	public void send(String string) {
		final byte[] buf = string.getBytes(StandardCharsets.UTF_8);
		// find all the sockets
		for (String ss : TMP_SOCKET_FILE.list((File f, String s) -> s.startsWith(channel + "-"))) {
			int port = Integer.parseInt(ss.split("-")[1]);
			if (port == this.socket.getLocalPort())
				continue; // no self send
			System.out.println("Sending to port " + port);
			final DatagramPacket p = new DatagramPacket(buf, buf.length);
			try {
				p.setAddress(InetAddress.getLocalHost());
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			p.setPort(port);
			try {
				socket.send(p); // Does it work? Did it fail? Who knows!
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Fire up a listening thread. If you call this function twice you will probably
	 * get multiple threads that spread the incoming messages.
	 * 
	 * @param onString
	 */
	public void listen(Consumer<String> onString) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (;;) {
					byte[] buf = new byte[768];
					DatagramPacket p = new DatagramPacket(buf, buf.length);
					try {
						socket.receive(p);
						String s = new String(p.getData(), StandardCharsets.UTF_8);
						onString.accept(s);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}

		}).start();
	}
}
