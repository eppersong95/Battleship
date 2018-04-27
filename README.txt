—Battleship Game—

—System Requirements—
Windows 10
JRE Installed

—To Run—
1. Unzip Source.zip and move the folder "Battleship Final" to your Desktop
2. Open the command prompt and change directory to just inside of the Source folder
3. Run server.bat
4. On the ServerGUI that runs, click the Listen button.
5. Open another command prompt and change directory to inside of the Source folder
6. Run client.bat
7. Repeat steps 5-6 to open a second client
8A. If running on same machine:
	-In each client, enter "localhost" for the IP Address and "8080" for the port number
        -This will connect the client to the server
8B. If running on separate computers:
	-Open another command prompt on the machine running the server and enter "ipconfig"
        -Find the IP Address in the results
        -In each client, input this IP address in the IP field and 8080 as the port number
9. The game will begin once both clients confirm their ships' locations

—Database—
Edit db.properties in the following format: pathToDB,username,password
This will point the program to your database for testing
Running server.bat will automatically configure the database to store usernames and passwords

—Bat Files—
1. Run server.bat from command line
2. Run client.bat from command line