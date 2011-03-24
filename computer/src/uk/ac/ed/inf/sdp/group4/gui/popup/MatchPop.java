package uk.ac.ed.inf.sdp.group4.gui.popup;

import uk.ac.ed.inf.sdp.group4.gui.CastleWindow;
import uk.ac.ed.inf.sdp.group4.strategy.RobotColour;
import uk.ac.ed.inf.sdp.group4.strategy.Strategy;

import javax.swing.*;

public class MatchPop extends JFrame {

	private static final long serialVersionUID = 101;

    private JComboBox colourBox;
    private JButton connectButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JComboBox stratBox;
	private CastleWindow window;

    public MatchPop(CastleWindow window) {

		this.window = window;
        initComponents();
    }

	private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {
		
		window.connect((Strategy.Strategies) stratBox.getSelectedItem(),
			(RobotColour) colourBox.getSelectedItem(), this);
    }

    private void stratBoxActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void colourBoxActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        stratBox = new javax.swing.JComboBox();
        colourBox = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        connectButton = new javax.swing.JButton();

        //setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        stratBox.setModel(new javax.swing.DefaultComboBoxModel(new Strategy.Strategies[] { 
			Strategy.Strategies.SIMPLE, Strategy.Strategies.TRACKBALL, Strategy.Strategies.KEYBOARD, 
				Strategy.Strategies.INTERCEPT,}));
        stratBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stratBoxActionPerformed(evt);
            }
        });

        colourBox.setModel(new javax.swing.DefaultComboBoxModel(new RobotColour[] { RobotColour.BLUE, RobotColour.YELLOW}));
        colourBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colourBoxActionPerformed(evt);
            }
        });

        jLabel1.setText("Strategy:");

        jLabel2.setText("Colour:");

        connectButton.setText("Connect");
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(colourBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, 201, Short.MAX_VALUE)
                            .addComponent(stratBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, 201, Short.MAX_VALUE))
                        .addGap(33, 33, 33))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(connectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stratBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(colourBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addComponent(connectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }
}

