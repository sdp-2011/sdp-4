package uk.ac.ed.inf.sdp.group4.world;
import java.io.*;
import java.net.*;

import uk.ac.ed.inf.sdp.group4.WorldStateCommunication.*;
import uk.ac.ed.inf.sdp.group4.WorldStateCommunication.WorldStateRequest.*;
import uk.ac.ed.inf.sdp.group4.WorldStateCommunication.WorldStateResponse.*;

public class VisionClient
{
	// Default Connection Parameters
	private static final String DEFAULT_HOSTNAME = "localhost";
	private static final int DEFAULT_PORT = 50008;

	// Connection Parameters
	private String hostname;
	private int portNumber;

	// Last Requested Timestamp
	private long lastTimestamp = 0;

	public VisionClient()
	{
		this(DEFAULT_HOSTNAME, DEFAULT_PORT);
	}

	public VisionClient(String hostname, int portNumber)
	{
		this.hostname = hostname;
		this.portNumber = portNumber;
	}

	public WorldState getWorldState()
	{
		Socket socket = getSocket();
		DataInputStream input = null;
		PrintStream output = null;
		try
		{
			input = new DataInputStream(socket.getInputStream());
			output = new PrintStream(socket.getOutputStream());
		}
		catch (IOException e)
		{
			System.err.println("Couldn't create streams through socket");
		}
		WorldStateRequest request = createRequest();
		try
		{
			request.writeTo(output);
		}
		catch (IOException e)
		{
			System.err.println("Couldn't write to the output stream");
		}
		WorldStateResponse response = null;
		try
		{
			response = WorldStateResponse.parseFrom(input);
		}
		catch (IOException e)
		{
			System.err.println("Couldn't parse the response from the input stream");
		}
		// TODO: Turn into WorldState class and validate.
		WorldState worldState = null;
		try
		{
			worldState = new WorldState(response);
		}
		catch (BadWorldStateException e)
		{
			System.err.println(e.getMessage());
		}
		
		System.out.println(response.toString());
		
		// TODO: Set lastTimestamp
		return worldState;
	}

	public String getHostname()
	{
		return this.hostname;
	}

	public int getPortNumber()
	{
		return this.portNumber;
	}

	public long getLastTimestamp()
	{
		return this.lastTimestamp;
	}

	private Socket getSocket()
	{
		String hostname = getHostname();
		int portNumber = getPortNumber();
		Socket socket = null;
		try
		{
			socket = new Socket(hostname, portNumber);
		}
		catch (IOException e)
		{
			System.err.println(String.format("Could not create socket (%s, %d)",
			                                 hostname, portNumber));
		}
		return socket;
	}

	private WorldStateRequest createRequest()
	{
		WorldStateRequest.Builder requestBuilder = WorldStateRequest.newBuilder();
		requestBuilder.setLastTimestamp(getLastTimestamp());
		return requestBuilder.build();
	}
}
