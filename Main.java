import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;

class Main {
  public static void main(String[] args) throws Exception {
    // What's this "Socket" nonsense? A Socket is basically a "walkie-talkie"
    // between two parties.
    // You can think of it like a cable connecting two ports
    try (ServerSocket serverSocket = new ServerSocket(8080)) {
      // Print "Server is running!" We're now accepting incoming messages.
      System.out.println("Server started.\nListening for connections.");

      // Infinite loop - usually these are bad! When is true not true? Never!
      // We'll Run and Stop the program using repl.it's built-in feature for
      // starting/stopping
      // the server.
      // Without this, we could only handle a single request and then the program
      // would exit...
      while (true) { // Just keep listening forever...

        // This method blocks waiting on an incoming connection.
        // Think of it like sitting and waiting for a phone call to come in.
        try (Socket client = serverSocket.accept()) {
          // System.out.println("Debug: got new client ");
          System.out.println("Debug: got new client " + client.toString());

          // Reads bytes and decodes them into characters using a specified charset
          InputStreamReader isr = new InputStreamReader(client.getInputStream());

          // Reads text and buffers for efficient reading
          BufferedReader br = new BufferedReader(isr);

          // Create a place to store our string
          StringBuilder requestBuilder = new StringBuilder();

          String line;
          while (!(line = br.readLine()).isBlank()) {
            requestBuilder.append(line + "\r\n");
          }

          String request = requestBuilder.toString();
          // System.out.println("--REQUEST--");
          // System.out.println(request);

          OutputStream clientOutput = client.getOutputStream();
          clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());
          clientOutput.write(("ContentType: text/plain \r\n").getBytes());
          clientOutput.write("\r\n".getBytes());
          clientOutput.write("Testing".getBytes());
          clientOutput.flush();
          client.close();

          // System.out.println("sent response");
          // At some point, we might want to refactor and extract a method
          // handleConnection(client);
        }
      }
    }
  }
}