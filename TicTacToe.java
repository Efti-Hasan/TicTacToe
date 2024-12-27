import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe implements ActionListener {
    JFrame frame = new JFrame();
    JPanel titlePanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    JLabel textField = new JLabel();
    JLabel scoreLabel = new JLabel();
    JButton restartButton = new JButton("Restart Game");
    JButton[] buttons = new JButton[9];
    boolean player1Turn; // Player X's turn
    int playerXScore = 0;
    int playerOScore = 0;
    String playerXName;
    String playerOName;

    public TicTacToe() {
        // Names as input
        playerXName = JOptionPane.showInputDialog("Enter Player X's name:");
        if (playerXName == null || playerXName.trim().isEmpty()) {
            playerXName = "Player X";
        }
        playerOName = JOptionPane.showInputDialog("Enter Player O's name:");
        if (playerOName == null || playerOName.trim().isEmpty()) {
            playerOName = "Player O";
        }

        // Frame Setup
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 700);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        // Text Field for Game Status
        textField.setBackground(new Color(25, 25, 25));
        textField.setForeground(new Color(25, 255, 0));
        textField.setFont(new Font("SansSerif", Font.BOLD, 30)); 
        textField.setHorizontalAlignment(JLabel.CENTER);
        textField.setText("Tic-Tac-Toe");
        textField.setOpaque(true);

        // Score Label
        scoreLabel.setBackground(new Color(25, 25, 25));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setOpaque(true);
        updateScoreLabel();

        // Title Panel
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBounds(0, 0, 600, 80);
        titlePanel.add(textField, BorderLayout.CENTER);
        titlePanel.add(scoreLabel, BorderLayout.SOUTH);

        // Button Panel
        buttonPanel.setLayout(new GridLayout(3, 3));
        buttonPanel.setBackground(new Color(150, 150, 150));
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttonPanel.add(buttons[i]);
            buttons[i].setFont(new Font("SansSerif", Font.BOLD, 80));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
        }

        // Bottom Panel with Restart Button
        bottomPanel.setLayout(new FlowLayout());
        restartButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        restartButton.setFocusable(false);
        restartButton.addActionListener(e -> restartGame());
        bottomPanel.add(restartButton);

        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        firstTurn();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 9; i++) {
            if (e.getSource() == buttons[i] && buttons[i].getText().isEmpty()) {
                if (player1Turn) {
                    buttons[i].setText("X");
                    buttons[i].setForeground(Color.RED);
                    textField.setText(playerOName + "'s turn");
                } 
                else {
                    buttons[i].setText("O");
                    buttons[i].setForeground(Color.BLUE);
                    textField.setText(playerXName + "'s turn");
                }
                player1Turn = !player1Turn;
                checkWinner();
                return;
            }
        }
    }

    private void firstTurn() {
        player1Turn = true; // Player X starts first
        textField.setText(playerXName + "'s turn");
    }

    private void checkWinner() {
        // Win conditions for X
        int[][] winConditions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
            {0, 4, 8}, {2, 4, 6}             // Diagonals
        };

        for (int[] condition : winConditions) {
            String b1 = buttons[condition[0]].getText();
            String b2 = buttons[condition[1]].getText();
            String b3 = buttons[condition[2]].getText();

            if (!b1.isEmpty() && b1.equals(b2) && b2.equals(b3)) {
                highlightWinningButtons(condition);
                updateScores(b1);
                textField.setText((b1.equals("X") ? playerXName : playerOName) + " wins!");
                disableButtons();
                restartAfterDelay();
                return;
            }
        }

        if (isBoardFull()) {
            textField.setText("It's a tie!");
            restartAfterDelay();
        }
    }

    private void highlightWinningButtons(int[] condition) {
        for (int index : condition) {
            buttons[index].setBackground(Color.GREEN); // Highlight winning buttons
        }
    }

    private void disableButtons() {
        for (JButton button : buttons) {
            button.setEnabled(false);
        }
    }

    private boolean isBoardFull() {
        for (JButton button : buttons) {
            if (button.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void restartGame() {
        for (JButton button : buttons) {
            button.setText("");
            button.setEnabled(true);
            button.setBackground(null);
        }
        firstTurn();
    }

    private void restartAfterDelay() {
        // Delay restart after a win or tie
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void updateScores(String winner) {
        if (winner.equals("X")) {
            playerXScore++;
        } else {
            playerOScore++;
        }
        updateScoreLabel();
    }

    private void updateScoreLabel() {
        scoreLabel.setText(playerXName + ": " + playerXScore + " | " + playerOName + ": " + playerOScore);
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}
