package uk.ac.ed.inf.sdp.group4.strategy;

import uk.ac.ed.inf.sdp.group4.world.VisionClient;
import uk.ac.ed.inf.sdp.group4.controller.Controller;

public abstract class Strategy implements IStrategy
{
    VisionClient client;
    Controller controller;
    
    public Strategy(VisionClient client, Controller controller)
    {
        this.client = client;
        this.controller = controller;
    }
    
    public void runStrategy()
    {
        
    }
}