import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

// image of chess pieces are from: By Cburnett - Own work, CC BY-SA 3.0,
// https://commons.wikimedia.org/w/index.php?curid=1499803

public class chessBoardGUI implements ActionListener, MouseListener {

    JFrame frame;
    // window frame

    private chessBoard playChess;
    // will hold my chess game

    private int pieceAtX = -1;
    private int pieceAtY = -1;
    private int pieceToX = -1;
    private int pieceToY = -1;
    // These values are -1 because they don't refer to any possible move

    private int blackX = 0;
    private int blackY = 0;
    private int whiteX = 0;
    private int whiteY = 0;
    // these number help tell where to put the captured chess piece by helping
    // with the dimensions

    private int[] wins = {0,0};
    // white wins is contained in index 0; black wins is contained in index 1

    JPanel buttonPanel;
    MyPanel gamePanel;
    JPanel textPanel;
    JPanel whiteCapture;
    JPanel blackCapture;
    JPanel whiteWins;
    JPanel blackWins;
    // panels in the frame

    JLabel pieceDisplay;
    // picture that will be displayed
    JLabel textDisplay;
    // text telling the player what to do

    ImageIcon image;
    // saves image that i will use

    JButton previousMove;
    JButton newGame;
    JButton nextMove;
    // buttons that will perform what in the text;

    public chessBoardGUI(chessBoard chessBoardClass) {

        // creates the instance variable for the chess game logic

        playChess = chessBoardClass;

        // creates the frame for chess game

        frame = new JFrame();

        // start of making button panel

        buttonPanel = new JPanel();
        buttonPanel.setBounds(80,20, 1400, 50);
        //buttonPanel.setSize(new Dimension(845, 50));
        buttonPanel.setLayout(new GridLayout(1,3, 10, 0));

        previousMove = new JButton("Previous Move");
        previousMove.addActionListener(this);
        // user input
        newGame = new JButton("New Game");
        newGame.addActionListener(this);
        nextMove = new JButton("Next Move");
        nextMove.addActionListener(this);

        buttonPanel.add(previousMove);
        buttonPanel.add(newGame);
        buttonPanel.add(nextMove);
        frame.add(buttonPanel);

        // start of making text panel

        textPanel = new JPanel();
        //textPanel.setBackground(Color.WHITE);
        textPanel.setBounds(80,80, 1400,50);
        textPanel.setLayout(new BorderLayout());

        textDisplay = new JLabel("White Moves first");
        textDisplay.setHorizontalAlignment(JLabel.CENTER);
        textDisplay.setVerticalAlignment(JLabel.CENTER);
        textDisplay.setFont(new Font("Merriweather", Font.BOLD, 20));

        textPanel.add(textDisplay);
        frame.add(textPanel);

        // start of container for captured pieces

        whiteCapture = new JPanel();
        whiteCapture.setBounds(120, 200, 200, 400);
        whiteCapture.setBackground(Color.WHITE);
        JLabel name = new JLabel("White Players Captures");
        name.setFont(new Font("Merriweather", Font.BOLD, 15));

        whiteCapture.add(name);
        frame.add(whiteCapture);

        blackCapture = new JPanel();
        blackCapture.setBounds(1240, 200, 200, 400);
        blackCapture.setBackground(Color.MAGENTA);
        name = new JLabel("Black Players Captures");
        name.setFont(new Font("Merriweather", Font.BOLD, 15));

        blackCapture.add(name);
        frame.add(blackCapture);

        // start of container for win score

        whiteWins = new JPanel();
        whiteWins.setBounds(120, 600, 200, 200);
        whiteWins.setBackground(Color.MAGENTA);
        name = new JLabel("Wins for White Players: " + wins[0]);
        name.setFont(new Font("Merriweather", Font.BOLD, 15));

        whiteWins.add(name);
        frame.add(whiteWins);

        blackWins = new JPanel();
        blackWins.setBounds(1240, 600, 200, 200);
        blackWins.setBackground(Color.WHITE);
        name = new JLabel("Wins for Black Players: " + wins[1]);
        name.setFont(new Font("Merriweather", Font.BOLD, 15));

        blackWins.add(name);
        frame.add(blackWins);

        // start of setting up the piece inside the frame

        setupBoard();

        // start of chessboard panel

        MyPanel panel = new MyPanel();
        // creates a chessboard panel
        panel.setBounds(380, 150, 800, 800);
        gamePanel = panel;
        gamePanel.addMouseListener(this);

        frame.add(gamePanel);

        // set up for the frame

        frame.setSize(1600,1000);
        // changes widthxheight of window
        frame.setLayout(null);
        // no default layout
        frame.setVisible(true);
        // lets me see window
        frame.setResizable(false);
        // doesn't let the window resize
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public void capture(chessBoard.chessPiece piece) {
        int x;
        int y;

        int startX;
        int startY = 225;

        if (piece.getColor().equals(chessBoard.Color.BLACK)) {
            if (blackX == 4) {
                blackX = 0;
                blackY++;
            }
            startX = 1240;

            x = blackX;
            y = blackY;
        } else {
            if (whiteX == 4) {
                whiteX = 0;
                whiteY++;
            }

            startX = 120;

            x = whiteX;
            y = whiteY;
        }

        JLabel label = piece.returnGUI();

        label.setBounds(startX + (50*x), startY + (50*y), 50, 50);

        if (piece.getColor().equals(chessBoard.Color.BLACK)) {
            blackX++;
        } else {
            whiteX++;
        }
    }

    public void setupBoard() {

        chessBoard.chessPiece[][] board = playChess.getBoard();
        int x = 405;
        // starting x position of board
        int y = 175;
        //starting y position of board

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessBoard.chessPiece p = board[i][j];

                if (p == null) continue;

                getImage(p);

                pieceDisplay = new JLabel(image);
                // puts image in a label

                p.changeGUI(pieceDisplay);

                pieceDisplay.setBounds(x+(j*100), y+(i*100), 50, 50);
                pieceDisplay.setFocusable(true);
                frame.add(pieceDisplay);
            }
        }
    }

    public void remakeBoard() {
        chessBoard.chessPiece[][] board = playChess.getBoard();
        int x = 405;
        // starting x position of the chessboard
        int y = 175;
        //starting y position of the chessboard

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessBoard.chessPiece p = board[i][j];

                if (p == null) continue;

                getImage(p);

                pieceDisplay = p.returnGUI();
                // gets image

                pieceDisplay.setIcon(image);
                // this is done to reset any pawn promotions

                //p.changeGUI(pieceDisplay);


                pieceDisplay.setBounds(x+(j*100), y+(i*100), 50, 50);
                //pieceDisplay.setFocusable(true);
                frame.add(pieceDisplay);
            }
        }

        frame.remove(gamePanel);
        frame.add(gamePanel);

    }

    public void getImage(chessBoard.chessPiece p) {

        if (p.compareTo("pawn")) {
            image = new ImageIcon(getClass().getResource(p.getColor()+"Pawn.png"));
        } else if (p.compareTo("rook")) {
            image = new ImageIcon(getClass().getResource(p.getColor()+"Rook.png"));
        } else if (p.compareTo("knight")) {
            image = new ImageIcon(getClass().getResource(p.getColor()+"Knight.png"));
        } else if (p.compareTo("bishop")) {
            image = new ImageIcon(getClass().getResource(p.getColor()+"Bishop.png"));
        } else if (p.compareTo("queen")) {
            image = new ImageIcon(getClass().getResource(p.getColor()+"Queen.png"));
        } else {
            image = new ImageIcon(getClass().getResource(p.getColor()+"King.png"));
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == previousMove) {
            if (playChess.previousMove()) {
                doNothing();
                //setupBoard();
            } else {
                doNothing();
                textDisplay.setText("No Previous Move Available!");
                Timer timer = new Timer( 1000, t -> {
                    textDisplay.setText("Same players turn");

                    // does not belong here************************************************************************** this was a test
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            chessBoard.chessPiece p = playChess.getBoard()[i][j];

                            if (p == null) continue;
                            capture(p);
                        }
                    }
                    frame.remove(whiteCapture);
                    frame.remove(blackCapture);
                    frame.add(whiteCapture);
                    frame.add(blackCapture);
                    // need to reset x and y coordinate
                    // does not belong here************************************************************************** this was a test

                } );

                timer.setRepeats( false );
                //make sure the timer only runs once
                timer.start();
            }
        }

        if (e.getSource() == nextMove ) {
            if (playChess.nextMove()) {
                doNothing();
                setupBoard();
            } else {
                doNothing();

                textDisplay.setText("No Next Move Available!");

                Timer timer = new Timer( 1000, t -> {
                    textDisplay.setText("Same players turn");

                    remakeBoard(); // does not belong here****************************** this was a test

                } );

                timer.setRepeats( false );
                //make sure the timer only runs once
                timer.start();


            }
        }
    }

    public void doNothing() {
        /**
         This method resets the piece you are looking at ((x) pieceAtX, (y) pieceAtY)
         and where you are going to move it to ((x) pieceToX, (y) pieceToY)
         */
        pieceAtX = -1;
        pieceAtY = -1;
        pieceToX = -1;
        pieceToY = -1;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("mouseClicked");
        System.out.println("(" + (int)Math.floor(e.getX()/100) + "," + (int) Math.floor(e.getY()/100) + ")");

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("mousePressed");
        System.out.println("(" + (int)Math.floor(e.getX()/100) + "," + (int) Math.floor(e.getY()/100) + ")");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("mouseReleased");
        System.out.println("(" + (int)Math.floor(e.getX()/100) + "," + (int) Math.floor(e.getY()/100) + ")");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }
            }
        });
        chessBoard playGame = new chessBoard();
        chessBoardGUI playGUI = new chessBoardGUI(playGame);
    }
}