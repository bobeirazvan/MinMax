import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GUI implements ActionListener {
	JFrame frame;
	JPanel mainPanel;
	JButton[][] boxes;
	JButton[][] buttons;
	JButton startButton;
	Matrix board;

	boolean isAI;
	boolean isX;
	boolean isFirst;
	boolean isBlue;
    boolean started;
    
	public GUI() {
		this.boxes = new JButton[3][3];
		this.buttons = new JButton[3][2];
		this.isAI = true;
		this.isX = true;
		this.isFirst = false;
		this.isBlue = true ; 
		this.started = false ;

		board = new Matrix(3,3);
		frame = new JFrame("Minimax");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(300, 430));
		frame.setLayout(null);

		this.mainPanel = new JPanel();
		this.mainPanel.setSize(new Dimension(300, 425));
		this.mainPanel.setLayout(null);

		addBoxes();
		addButtons();

		frame.add(this.mainPanel);
		frame.pack();
		frame.setVisible(true);
	}

	// create clickable boxes
	void addBoxes() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				this.boxes[i][j] = new JButton();
				this.boxes[i][j].setBounds(i*100, j*100, 100, 100);
				this.boxes[i][j].setBackground(Color.WHITE);
				this.boxes[i][j].setEnabled(false);
				this.boxes[i][j].addActionListener(this);
				this.mainPanel.add(this.boxes[i][j]);
			}
		}
	}

	// create boxes for options
	void addButtons() {
		buttons[1][0] = new JButton("AS RED");
		buttons[1][1] = new JButton("AS BLUE");
		buttons[2][0] = new JButton("FIRST");
		buttons[2][1] = new JButton("SECOND");

		startButton = new JButton("START!");
		startButton.setBounds(0,350, 300, 50);
		startButton.addActionListener(this);
		mainPanel.add(startButton);

		for (int i = 1; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				buttons[i][j].setBounds((150*j), (25*i)+275, 150, 25);
				buttons[i][j].setBackground(Color.WHITE);
				buttons[i][j].addActionListener(this);
				mainPanel.add(buttons[i][j]);
			}
		}
	}

	// enable/disable boxes
	void enableBoxes(boolean bool) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				boxes[i][j].setEnabled(bool);
			}
		}

		if (!bool) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					boxes[i][j].setBackground(Color.WHITE);
				}
			}
		}
	}

	// enable/disable buttons
	void enableButtons(boolean bool) {
		for (int i = 1; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				buttons[i][j].setEnabled(bool);
			}
		}
		startButton.setEnabled(bool);

		if (bool) {
			for (int i = 1; i < 3; i++) {
				for (int j = 0; j < 2; j++) {
					buttons[i][j].setBackground(Color.WHITE);
				}
			}

			startButton.setBackground(Color.WHITE);
		}
	}

	// recolor boxes depends on board placements
	void recolor() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board.board[i][j] == board.maximize) {
					if(isBlue)
					   boxes[i][j].setBackground(Color.BLUE);
					else
					   boxes[i][j].setBackground(Color.RED);	
					boxes[i][j].setEnabled(false);
				} else if (board.board[i][j] == board.minimize) {
					if(!isBlue)
					   boxes[i][j].setBackground(Color.BLUE);
				    else
					   boxes[i][j].setBackground(Color.RED);
					boxes[i][j].setEnabled(false);
				} else boxes[i][j].setBackground(Color.WHITE);
			}
		}
	}
    int WhatTyped(ActionEvent e) {
    	int line = -1 ;
    	int column = -1 ;
    	for(int i = 0; i < 3; i++)  
   		 for(int j = 0; j < 3; j++) 
   		  if(boxes[i][j].equals(e.getSource())) {
   			 line = i ;
   			 column = j ;
   		  }
    	if(line != -1 && column != -1)
    	   return 3*line+column ;
    	else
    	   return -1 ;	 
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		
		char turn = 'x';
		if(!isFirst)
		  turn = 'o';	
	    int line = WhatTyped(e);
	    int column = line%3;
		if(line != -1) { 
	       
		   line = line/3 ;
		   board.setBordValue(line, column, turn);	
		   recolor();
		   if(turn == 'x') turn = 'o' ;else turn = 'x';
		   int position = board.findBestMove(board.board);
		   board.setBordValue(position/3, position%3, turn);
		   recolor();
		   int toCheck = board.evaluate(board.board) ;
	       if (toCheck != -1) {
				 JOptionPane.showMessageDialog(frame, 
							toCheck == 0 ? 
								"Human WON!" 
							: "AI WON!");
				 enableBoxes(false);
				 enableButtons(true);
		   }
		   else { 
					 
			     int BusyCells = board.Depth(board.board) ;
				 if(BusyCells == 9) { 
						 JOptionPane.showMessageDialog(frame,"DRAW");
						 enableBoxes(false);
						 enableButtons(true);
				 }	 
					 
			  }}			  
		if (buttons[1][0].equals(e.getSource())) {
			isBlue = false;
			buttons[1][0].setBackground(Color.ORANGE);
			buttons[1][1].setBackground(Color.WHITE);
		} else if (buttons[1][1].equals(e.getSource())) {
			isBlue = true;
			buttons[1][1].setBackground(Color.ORANGE);
			buttons[1][0].setBackground(Color.WHITE);
		} else if (buttons[2][0].equals(e.getSource())) {
			isFirst = true;
			buttons[2][0].setBackground(Color.ORANGE);
			buttons[2][1].setBackground(Color.WHITE);
		} else if (buttons[2][1].equals(e.getSource())) {
			isFirst = false;
			buttons[2][1].setBackground(Color.ORANGE);
			buttons[2][0].setBackground(Color.WHITE);
		} else if (startButton.equals(e.getSource())) {
			board = new Matrix(3,3);
			if(!isFirst) {
				isBlue = !isBlue ;
				board.maximize = 'x' ;
				board.minimize = 'o' ;
			}
			else {
			    board.maximize = 'o';
		        board.minimize = 'x';
		    }
		    enableBoxes(true);
		    if(!isFirst) {
			   
		       int position = board.findBestMove(board.board);
			   board.setBordValue(position/3, position%3, 'x');
			   recolor();
		    
		    }
			enableButtons(false);
		}
	}
}

