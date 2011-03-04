package uk.ac.ed.inf.sdp.group4.gui;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JPanel;

import uk.ac.ed.inf.sdp.group4.strategy.RobotColour;
import uk.ac.ed.inf.sdp.group4.strategy.Strategy;
import uk.ac.ed.inf.sdp.group4.strategy.TrackBallStrategy;
import uk.ac.ed.inf.sdp.group4.controller.FatController;
import uk.ac.ed.inf.sdp.group4.world.IVisionClient;
import uk.ac.ed.inf.sdp.group4.world.VisionClient;
import uk.ac.ed.inf.sdp.group4.sim.Simulator;
import uk.ac.ed.inf.sdp.group4.sim.Situation;
import uk.ac.ed.inf.sdp.group4.gui.popup.*;
import uk.ac.ed.inf.sdp.group4.controller.Controller;

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
	private Thread stratThread;
	private Controller controller;
	private IVisionClient client;

	private boolean pause;
	private boolean facingWest;

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
        
		controller.finish();
    }

	public void simulate(Strategy.Strategies blueStrat, Strategy.Strategies yellStrat, SimPop pop)
	{
		pop.dispose();

		Simulator sim = new Simulator(Strategy.makeStrat(blueStrat), 
			Strategy.makeStrat(yellStrat));
		situation.replace((Situation) sim.makePanel());
		sim.setPanel(situation);
		stratThread = new Thread(sim);
		stratThread.start();

		running();
	}

	public void connect(Strategy.Strategies strat, RobotColour colour, MatchPop pop)
	{
		pop.dispose();

		client = new VisionClient();
		controller = new FatController();
		strategy = Strategy.makeStrat(strat);
		strategy.setup(client, controller, colour, false);
		stratThread = new Thread(strategy);
		stratThread.start();
		stratThread.suspend();

		running();
	}

	private void halfTime()
	{
		strategy.halfTime();
	}

	private void pause()
	{
		stratThread.suspend();
		pauseButton.setText("Resume");
	}

	private void resume()
	{
		stratThread.resume();
		pauseButton.setText("Pause");
	}

	private void reset()
	{

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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        logWindow.setColumns(20);
        logWindow.setEditable(false);
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

		endButton.setVisible(false);
		halfTime.setVisible(false);
		pauseButton.setVisible(false);
		modeButton.setVisible(false);
    }
}

