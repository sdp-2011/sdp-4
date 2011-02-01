import java.io.*;
import java.net.*;

import uk.ac.ed.inf.sdp.group4.Worldstate.WorldStateRequest;
import uk.ac.ed.inf.sdp.group4.Worldstate.WorldStateResponse;

class Main {
    public static void main(String[] args) throws Exception // Shut up it's a test.
    {
        WorldStateRequest.Builder requestBuilder = WorldStateRequest.newBuilder();
        requestBuilder.setLastTimestamp(1234567889);
        
        WorldStateRequest request = requestBuilder.build();
        
        Socket socket = null;
        try
        {
            socket = new Socket("localhost", 50008);
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
        
        DataInputStream input = null;
        PrintStream output = null;
        try {
           input = new DataInputStream(socket.getInputStream());
           output = new PrintStream(socket.getOutputStream());
        }
        catch (IOException e) {
           System.out.println(e);
        }
        
        request.writeTo(output);
        System.out.println(WorldStateResponse.parseFrom(input).toString());
    }
}