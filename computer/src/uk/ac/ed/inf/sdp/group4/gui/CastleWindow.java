package uk.ac.ed.inf.sdp.group4.gui;

import uk.ac.ed.inf.sdp.group4.controller.Controller;
import uk.ac.ed.inf.sdp.group4.controller.FatController;
import uk.ac.ed.inf.sdp.group4.controller.ThinController;
import uk.ac.ed.inf.sdp.group4.gui.popup.MatchPop;
import uk.ac.ed.inf.sdp.group4.gui.popup.PenaltyButtons;
import uk.ac.ed.inf.sdp.group4.gui.popup.SimPop;
import uk.ac.ed.inf.sdp.group4.sim.Simulator;
import uk.ac.ed.inf.sdp.group4.sim.Situation;
import uk.ac.ed.inf.sdp.group4.strategy.RobotColour;
import uk.ac.ed.inf.sdp.group4.strategy.Strategy;
import uk.ac.ed.inf.sdp.group4.world.IVisionClient;
import uk.ac.ed.inf.sdp.group4.world.VisionClient;
import uk.ac.ed.inf.sdp.group4.world.WorldState;
import java.util.Random;

import javax.swing.*;
import java.awt.Color;

public class CastleWindow extends JFrame {

	private static final long serialVersionUID = 101;

    private JButton endButton;
    private JButton halfTime;
    private JScrollPane jScrollPane1;
    private JTextArea logWindow;
    private JButton matchStart;
    private JButton modeButton;
    private JButton pauseButton;
    private JButton simStart;
    private Situation situation;
	private Strategy strategy;
	private Controller controller;
	private IVisionClient client;
	private Simulator sim;
	private Animator animator;

	private boolean pause;

    public CastleWindow() {
        initComponents();
    }

	private void simStartActionPerformed(java.awt.event.ActionEvent evt) {
        
		SimPop simPop = new SimPop(this);
		simPop.setVisible(true);
    }

    private void matchStartActionPerformed(java.awt.event.ActionEvent evt) {

        MatchPop matchPop = new MatchPop(this);
		matchPop.setVisible(true);
    }

    private void halfTimeActionPerformed(java.awt.event.ActionEvent evt) {
       	
		halfTime();

		if (strategy.getFacing() == Strategy.Goals.EAST)
		{
			halfTime.setText("Facing: East");
		}

		else
		{
			halfTime.setText("Facing: West");
		}
    }

    private void pauseButtonActionPerformed(java.awt.event.ActionEvent evt) {
        
		if (!pause) resume();
		else pause();

		pause = !pause;
    }

    private void modeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        
		PenaltyButtons pen = new PenaltyButtons(strategy);
		pen.setVisible(true);
    }

    private void endButtonActionPerformed(java.awt.event.ActionEvent evt) {
        
		end();
    }

	public void simulate(Strategy.Strategies blueStrat, Strategy.Strategies yellStrat, SimPop pop)
	{
		pop.dispose();

		sim = null;//new Simulator(Strategy.makeStrat(blueStrat, ), 
			//Strategy.makeStrat(yellStrat));
		new Thread(sim).start();
		animator = new Animator(situation, sim.getVision(), true);
		new Thread(animator).start();
		halfTime.setEnabled(false);

		running();
	}

	public void connect(Strategy.Strategies strat, RobotColour colour, MatchPop pop)
	{
		pop.dispose();

		client = new VisionClient();
		controller = new FatController();
		
		System.out.println("VisionClient: " + client);
		System.out.println("Controller: " + controller);
		System.out.println("Colour: " + colour);
		
		strategy = Strategy.makeStrat(strat, client, controller, colour);
		strategy.setup(client, controller, colour, false);
		new Thread(strategy).start();
		animator = new Animator(situation, client, false);
		new Thread(animator).start();

		running();
	}

	private void halfTime()
	{
		strategy.halfTime();
	}

	private void pause()
	{
		if (sim != null) sim.pause();
		else strategy.suspend();
		pauseButton.setText("Resume");
	}

	private void resume()
	{
		if (sim != null) sim.resume();
		else strategy.resume();
		pauseButton.setText("Pause");
	}

	private void end()
	{
		if (sim == null)
		{ 
			controller.finish();
			strategy.stop();
		}

		else 
		{
			sim.stop();
		}
		
		reset();
	}

	private void running()
	{
		endButton.setVisible(true);
		halfTime.setVisible(true);
		pauseButton.setVisible(true);
		modeButton.setVisible(true);
		simStart.setVisible(false);
		matchStart.setVisible(false);

		if (strategy.getFacing() == Strategy.Goals.EAST)
		{
			halfTime.setText("Facing: East");
		}

		else
		{
			halfTime.setText("Facing: West");
		}
	}

	private void reset()
	{
		halfTime.setEnabled(true);
		endButton.setVisible(false);
		halfTime.setVisible(false);
		pauseButton.setVisible(false);
		modeButton.setVisible(false);
		simStart.setVisible(true);
		matchStart.setVisible(true);

		pauseButton.setText("Start");

		animator.stop();
		animator = null;
		sim = null;
		client = null;
		controller = null;
		strategy = null;
	}

	private class Animator implements Runnable
	{
		private Situation situation;
		private IVisionClient client;
		private boolean keepRunning;
		private boolean sim;

		public Animator(Situation situation, IVisionClient client, boolean sim)
		{
			this.situation = situation;
			this.client = client;
			this.keepRunning = true;
			this.sim = sim;
		}
	
		public void run()
		{
			while (keepRunning)
			{
				WorldState state = client.getWorldState();
				situation.setup(state.getBlue(), state.getYellow(), state.getBall(), sim);
				situation.repaint();

				try
				{
					Thread.sleep(40);
				}
				
				catch (InterruptedException e)
				{
					System.out.println("Threading is borked");
				}
			}
		}

		public void stop()
		{
			keepRunning = false;
		}
	}

	//This is just the netbeans generated layout
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        logWindow = new javax.swing.JTextArea();
        situation = new Situation();
        simStart = new javax.swing.JButton();
        matchStart = new javax.swing.JButton();
        halfTime = new javax.swing.JButton();
        pauseButton = new javax.swing.JButton();
        modeButton = new javax.swing.JButton();
        endButton = new javax.swing.JButton();
		
		//remove this once sim is fixed
		simStart.setEnabled(false);
		simStart.setVisible(false);

		logWindow.setText("At least you're not in Stephen's team...");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        logWindow.setColumns(20);
        logWindow.setEditable(true);
        logWindow.setRows(5);
        jScrollPane1.setViewportView(logWindow);

        javax.swing.GroupLayout situationLayout = new javax.swing.GroupLayout(situation);
        situation.setLayout(situationLayout);
        situationLayout.setHorizontalGroup(
            situationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 549, Short.MAX_VALUE)
        );
        situationLayout.setVerticalGroup(
            situationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 382, Short.MAX_VALUE)
        );

        simStart.setText("Start Simulator");
        simStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simStartActionPerformed(evt);
            }
        });

        matchStart.setText("Start Match");
        matchStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                matchStartActionPerformed(evt);
            }
        });

        halfTime.setText("Facing: East");
        halfTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                halfTimeActionPerformed(evt);
            }
        });

        pauseButton.setText("Start");
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseButtonActionPerformed(evt);
            }
        });

        modeButton.setText("Penalty!");
        modeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modeButtonActionPerformed(evt);
            }
        });

        endButton.setText("End");
        endButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 798, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pauseButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                            .addComponent(halfTime, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                            .addComponent(matchStart, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                            .addComponent(simStart, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                            .addComponent(modeButton, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                            .addComponent(endButton, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(situation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(simStart, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(matchStart, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(halfTime, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(pauseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(modeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(endButton, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(situation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        getContentPane().setBackground(Color.black);

		endButton.setVisible(false);
		halfTime.setVisible(false);
		pauseButton.setVisible(false);
		modeButton.setVisible(false);
    }
}

