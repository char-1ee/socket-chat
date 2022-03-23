# socket-chat
A group chat application realized by socket programming in java.

## File hierarchy
`application` : socket messaging run on localhost  
`multithreaded` : practices on multi-thread socket programming  
`singlethreaded` : practices on single-thread socket programming  
`GroupChat.java` : A complete chat application in a single file  

## Run
1. Compile `Server.java` and `Client.java` in two different shell processes  

   ```
   $ javac Server.java
   ```
   ```
   $ javac Client.java
   ```
3. Run `Server.java`, which calls on a ServerSocket listening to a client request  
   ```
   $ java Server
   ```
5. Run `Client.java`, which sends a request to Server class, `ClientHandler` creates a new socket  
   ```
   
   $ java Client
   ```
4. Enter messages in a Client terminal, which can be seen in other Client terminals
