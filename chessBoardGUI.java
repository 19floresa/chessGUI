// Alexander Flores
/*
 image of chess pieces are from: By Cburnett - Own work, CC BY-SA 3.0,
 https://commons.wikimedia.org/w/index.php?curid=1499803;

 Each picture was renamed to their color + chess piece name.
 ex. a picture of a white king  chess piece was renamed to whiteKing.png
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class chessBoardGUI implements ActionListener, MouseListener {
    /**
     This class creates a GUI chess game with java swing, the chess game logic
     from chessBoard.java file and MyPanel file. To interact with the game you
     can click one of the buttons that either go backwards or forward one move
     or resets the board. Another way to interact with the chess game is by clicking
     on the chess board. The first click on the board lets you pick a piece and
     the second click on the board lets you move the piece there (if it is valid).
     After any interactions with the chessboard a message will pop up above the
     chess board
     */

    JFrame frame;
    // window frame

    private chessBoard playChess;
    // will hold my chess game

    private chessBoard.Color player;

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

    private int[] wins = new int[2];
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
    JLabel wWins;
    // text of white players wins
    JLabel bWins;
    // text of black players wins

    ImageIcon image;
    // will temporarily contain  image that I will use

    JButton previousMove;
    JButton newGame;
    JButton nextMove;
    // buttons that will perform whats in their name;

    public chessBoardGUI(chessBoard chessBoardClass) {

        // creates the instance variable for the chess game logic

        playChess = chessBoardClass;

        // create the current player (will be white at start)

        player = chessBoard.Color.WHITE;

        // creates the frame for chess game

        frame = new JFrame();

        // start of making button panel

        buttonPanel = new JPanel();
        buttonPanel.setBounds(80, 20, 1400, 50);
        buttonPanel.setLayout(new GridLayout(1, 3, 10, 0));

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
        textPanel.setBounds(80, 80, 1400, 50);
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
        wWins = new JLabel("Wins for White Players: " + wins[0]);
        wWins.setFont(new Font("Merriweather", Font.BOLD, 15));

        whiteWins.add(wWins);
        frame.add(whiteWins);

        blackWins = new JPanel();
        blackWins.setBounds(1240, 600, 200, 200);
        blackWins.setBackground(Color.WHITE);
        bWins = new JLabel("Wins for Black Players: " + wins[1]);
        bWins.setFont(new Font("Merriweather", Font.BOLD, 15));

        blackWins.add(bWins);
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

        frame.setSize(1600, 1000);
        // changes widthxheight of window
        frame.setLayout(null);
        // no default layout
        frame.setVisible(true);
        // lets me see window
        frame.setResizable(false);
        // doesn't let the window resize
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public void setupBoard() {
        /**
         This method  sets up each piece in the GUI and stores them in their
         respective piece.
         */
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
                // get an image for p and store it in the instance image

                pieceDisplay = new JLabel(image);
                // puts image in a label

                p.changeGUI(pieceDisplay);
                // give p an image reference

                pieceDisplay.setBounds(x + (j * 100), y + (i * 100), 50, 50);
                pieceDisplay.setFocusable(true);
                frame.add(pieceDisplay);
                // add JLabel to the board
            }
        }
    }


    public void mousePressed(MouseEvent e) {
        /**
         This method assigns a value to the instance pieceAt and pieceTo.
         pieceAtX and pieceAtY is the coordinates of the chosen piece and pieceToX
         and pieceToY are the coordinated of where the chosen piece is moving to.
         */
        if (pieceAtX == -1) {
            // only need to check one because x and y coordinate work together
            pieceAtX = (int) Math.floor(e.getX() / 100);
            pieceAtY = (int) Math.floor(e.getY() / 100);

            chessBoard.chessPiece currentP = playChess.getBoard()[pieceAtY][pieceAtX];

            if (currentP == null) {
                // chosen piece is null
                doNothing();

            } else if (!currentP.getColor().equals(player)) {
                // piece is not the same as the current player

                textDisplay.setText("Cannot Pick this Piece");

                Timer timer = new Timer(1000, t -> {
                    textDisplay.setText("Same Players Turn");
                });

                timer.setRepeats(false);
                timer.start();
                doNothing();
            } else {
                // valid piece is chosen
                textDisplay.setText("Move " + currentP.getChessPieceName() + " to");
            }

        } else if (pieceAtY != -1 && pieceToX == -1) {
            // need to check that player has chosen a piece before they pick a
            // place to move that piece.
            pieceToX = (int) Math.floor(e.getX() / 100);
            pieceToY = (int) Math.floor(e.getY() / 100);

            StringBuilder coordinate = new StringBuilder();

            coordinate.append((char) (pieceToX + 65));
            // appends the character chosen by the user
            coordinate.append((char) (56 - pieceToY));
            // appends the number chosen by the user (index 0 in the board
            // is number 8 to the user)

            textDisplay.setText(coordinate.toString());
        }
    }


    public void doNothing() {
        /**
         This method resets the piece you are looking at ((x) pieceAtX, (y) pieceAtY)
         and where you are going to move it to ((x) pieceToX, (y) pieceToY).
         */
        pieceAtX = -1;
        pieceAtY = -1;
        pieceToX = -1;
        pieceToY = -1;
    }


    public void mouseClicked(MouseEvent e) {
        if (pieceAtX != -1 && pieceToX != -1) {
            // a change in the gui board can happen if a piece is chosen (with the
            // right color) and it can move to its new spot (pieceToX/Y)
            chessBoard.chessPiece[][] oldBoard = playChess.getBoard();
            // current board

            chessBoard.chessPiece currentP = oldBoard[pieceAtY][pieceAtX];
            // chosen piece from current board

            boolean specialCaseFlag = false;

            if (pieceAtY == 0 && pieceToY == 0 || pieceAtY == 7 && pieceToY == 7) {
                // this condition is only for castling
                // check if the piece being moved either at bottom or top of the board
                if (currentP.getChessPieceName().equals(chessBoard.Piece.KING__)
                        && currentP.getSpecialMove() == 0) {
                    // make sure piece being moved is king and has not been moved
                    chessBoard.chessPiece piece = playChess.getBoard()[pieceToY][7];
                    // rook

                    if (pieceToX == 6 && piece != null &&
                            piece.getChessPieceName().equals(chessBoard.Piece.ROOK__) && piece.getSpecialMove() == 0) {
                        // check if the king is castling right and that rook exists
                        // and has not moved before

                        for (int i = 0; i < 3; i++) {
                            // check that the king is not in check, that the two squares
                            // right of the king is null, and that the king is not going
                            // to cross a place that would put the king in check
                            chessBoard.chessPiece[][] tempBoard = playChess.copyBoard(playChess.getBoard());

                            chessBoard.chessPiece king = new chessBoard.chessPiece(pieceAtY,
                                    pieceAtX + i, player, chessBoard.Piece.KING__);
                            // move the king right by 1

                            if (i != 0 && tempBoard[king.getVerticalPosition()][king.getHorizontalPosition()]
                                    != null) {
                                // check that next square is null
                                break;
                            }

                            tempBoard[king.getVerticalPosition()][king.getHorizontalPosition()] = king;
                            tempBoard[pieceAtY][pieceAtX] = null;

                            if (playChess.isMyKingInCheck(king, tempBoard)) {
                                // if current square would put the king in check then break
                                break;
                            }

                            if (i == 2) {
                                // if it reaches the end then the player can castle
                                king.changeGUI(playChess.getBoard()[pieceAtY][pieceAtX].returnGUI());

                                playChess.storePreviousMove(playChess.getBoard());
                                // king get old kings gui reference
                                specialCaseFlag = true;

                                piece.incrementSpecialMove();
                                playChess.getBoard()[pieceAtY][pieceAtX].incrementSpecialMove();
                                // mark that the pieces have moved

                                tempBoard[king.getVerticalPosition()]
                                        [king.getHorizontalPosition() - 1] = piece;
                                tempBoard[pieceToY][7] = null;

                                playChess.changeBoard(tempBoard);

                                textDisplay.setText("Current Player Castled");

                                pieceDisplay = piece.returnGUI();

                                if (player.equals(chessBoard.Color.WHITE)) {
                                    pieceDisplay.setBounds(905, 875, 50, 50);
                                } else {
                                    pieceDisplay.setBounds(905, 175, 50, 50);
                                }
                                SwingUtilities.updateComponentTreeUI(frame);

                            }

                        }

                    }

                    piece = oldBoard[pieceAtY][0];

                    if (!specialCaseFlag && pieceToX == 2 && piece != null &&
                            piece.getChessPieceName().equals(chessBoard.Piece.ROOK__) && piece.getSpecialMove() == 0) {
                        // check if the king is castling right and that rook exists
                        // and has not moved before

                        for (int i = 0; i < 4; i++) {
                            // check that the king is not in check, that the two squares
                            // right of the king is null, and that the king is not going
                            // to cross a place that would put the king in check
                            chessBoard.chessPiece[][] tempBoard = playChess.copyBoard(playChess.getBoard());

                            chessBoard.chessPiece king = new chessBoard.chessPiece(pieceAtY,
                                    pieceAtX - i, player, chessBoard.Piece.KING__);
                            // move the king right by 1

                            if (i != 0 && tempBoard[king.getVerticalPosition()][king.getHorizontalPosition()]
                                    != null) {
                                // check that next square is null
                                break;
                            }

                            tempBoard[king.getVerticalPosition()][king.getHorizontalPosition()] = king;
                            tempBoard[pieceAtY][pieceAtX] = null;

                            if (playChess.isMyKingInCheck(king, tempBoard)) {
                                // if current square would put the king in check then break
                                break;
                            }

                            if (i == 3) {
                                // if it reaches the end then the player can castle
                                king.changeGUI(playChess.getBoard()[pieceAtY][pieceAtX].returnGUI());

                                playChess.storePreviousMove(playChess.getBoard());
                                // king get old kings gui reference
                                specialCaseFlag = true;

                                piece.incrementSpecialMove();
                                playChess.getBoard()[pieceAtY][pieceAtX].incrementSpecialMove();
                                // mark that the pieces have moved

                                tempBoard[king.getVerticalPosition()]
                                        [king.getHorizontalPosition() + 2] = piece;
                                // place rook at horizontal index 3
                                tempBoard[king.getVerticalPosition()][king.getHorizontalPosition() + 1] = king;
                                // place king at horizontal index 2
                                tempBoard[king.getVerticalPosition()][king.getHorizontalPosition()] = null;
                                // make horizontal index 1 null
                                king.changeHorizontalPosition(king.getHorizontalPosition() + 1);
                                // change kings horizontal position instance to 2
                                tempBoard[pieceToY][0] = null;

                                playChess.changeBoard(tempBoard);

                                textDisplay.setText("Current Player Castled");

                                pieceDisplay = piece.returnGUI();

                                if (player.equals(chessBoard.Color.WHITE)) {
                                    pieceDisplay.setBounds(705, 875, 50, 50);
                                } else {
                                    pieceDisplay.setBounds(705, 175, 50, 50);
                                }
                                SwingUtilities.updateComponentTreeUI(frame);

                            }

                        }

                    }
                }
            }

            if (!specialCaseFlag && playChess.getBoard()[pieceToY][pieceToX] == null &&
                    currentP.getChessPieceName().equals(chessBoard.Piece.PAWN__)
                    ) {
                // check current spot has a pawn and moving spot is null
                // this is for pawns special move: en passant
                if (player.equals(chessBoard.Color.WHITE) && pieceAtY == 3 ||
                        player.equals(chessBoard.Color.BLACK) && pieceAtY == 4) {
                    // white pawn has to be at vertical position 3
                    // or black pawn has to be at vertical position 4
                    chessBoard.chessPiece[][] tempBoard = playChess.copyBoard(playChess.getBoard());

                    if (player.equals(chessBoard.Color.WHITE)) {
                        // add a black piece to intended spot on the tempBoard
                        tempBoard[pieceToY][pieceToX] = new
                                chessBoard.chessPiece(pieceToY, pieceToX, chessBoard.Color.BLACK, chessBoard.Piece.PAWN__);
                    } else {
                        // add a white piece to intended spot on the tempBoard
                        tempBoard[pieceToY][pieceToX] = new
                                chessBoard.chessPiece(pieceToY, pieceToX, chessBoard.Color.WHITE, chessBoard.Piece.PAWN__);
                    }

                    if (currentP.isMoveLegal(pieceToY, pieceToX, tempBoard)
                            && tempBoard[pieceAtY][pieceToX] != null && tempBoard[pieceAtY][pieceToX].getSpecialMove() == 1
                            && !tempBoard[pieceAtY][pieceToX].getColor().equals(player)) {
                        // check if the pawn trying to en passant can move with isMoveLegal
                        // check that there is an enemy piece (on the left or right of pawn)
                        // where en passant wil happen and that enemy piece has only move once
                        tempBoard = playChess.copyBoard(playChess.getBoard());

                        tempBoard[pieceToY][pieceToX] = playChess.getBoard()[pieceAtY][pieceAtX];

                        currentP.changeVerticalPosition(pieceToY);
                        currentP.changeHorizontalPosition(pieceToX);

                        tempBoard[pieceAtY][pieceAtX] = null;
                        tempBoard[pieceAtY][pieceToX] = null;

                        chessBoard.chessPiece king;

                        if (player.equals(chessBoard.Color.WHITE)) {
                            king = playChess.getKing("white");
                        } else {
                            king = playChess.getKing("black");
                        }

                        if (!playChess.isMyKingInCheck(king, tempBoard)) {
                            // check that king won't be in check in tempBoard
                            // else replace board with tempBoard
                            capture(playChess.getBoard()[pieceAtY][pieceToX]);

                            playChess.storePreviousMove(playChess.getBoard());

                            playChess.changeBoard(tempBoard);

                            pieceDisplay = playChess.getBoard()[pieceToY][pieceToX].returnGUI();
                            pieceDisplay.setBounds(405 + (100 * pieceToX), 175 + (100 * pieceToY), 50, 50);
                            textDisplay.setText("Current Player En Passant");

                            specialCaseFlag = true;
                        } else {
                            // else don't do anything
                            playChess.getBoard()[pieceAtY][pieceAtX].changeVerticalPosition(pieceAtY);
                            playChess.getBoard()[pieceAtY][pieceAtX].changeHorizontalPosition(pieceAtX);
                        }


                    }


                }

            }


            if (!specialCaseFlag && !playChess.movePiece(currentP, pieceToY, pieceToX)) {
                // if not special move happen or piece cannot move
                textDisplay.setText("Cannot Move Here");

                Timer timer = new Timer(1000, t -> {
                    textDisplay.setText("Same Players Turn");
                });

                timer.setRepeats(false);
                timer.start();
                doNothing();
                return;
            }

            boolean flag = false;
            // if player won then flag will stop the change player text from appearing

            if (player.equals(chessBoard.Color.WHITE)) {
                if (playChess.didIWin(playChess.getKing("black"))) {
                    // check if white  chess piece player won
                    textDisplay.setText("White Won");

                    wins[0]++;
                    wWins.setText("Wins for White Players: " + wins[0]);

                    flag = true;
                }

            } else {
                if (playChess.didIWin(playChess.getKing("white"))) {
                    // check if black chess piece player won
                    textDisplay.setText("Black Won");

                    wins[1]++;
                    bWins.setText("Wins for Black Players: " + wins[1]);

                    flag = true;
                }
            }

            if (player.equals(chessBoard.Color.WHITE)) {
                // add to the counter of special move check if they are equal to 1
                playChess.specialMoveCheck(chessBoard.Color.BLACK);
            } else {
                playChess.specialMoveCheck(chessBoard.Color.WHITE);
            }

            if (currentP.getChessPieceName().equals(chessBoard.Piece.ROOK__)
                    || currentP.getChessPieceName().equals(chessBoard.Piece.KING__)
                    || currentP.getChessPieceName().equals(chessBoard.Piece.PAWN__)) {
                // only rooks, pawns, and kings count since the last time they moved is
                // increased if they currently moved
                currentP.incrementSpecialMove();
            }

            if (currentP.getChessPieceName().equals(chessBoard.Piece.PAWN__)) {
                if ( pieceToY == 0 ||  pieceToY == 7) {
                    // promotes a pawn
                    playChess.pawnPromoteGUI(currentP, promote());
                }
            }

            player = player.equals(chessBoard.Color.WHITE) ? chessBoard.Color.BLACK : chessBoard.Color.WHITE;
            // changes current player

            Timer timer = new Timer(900, t -> {
                textDisplay.setText(player + " Players Turn");
            });

            if (!flag)  {
                timer.setRepeats(false);
                timer.start();
            }

            updateBoard(playChess.getPreviousMove());
            // move chessPieces GUI to their new spot

            playChess.clearNextMove();
            // whatever was in stackNextMove is not need b/c player chose a different move

            doNothing();
        }
    }


    public void actionPerformed(ActionEvent e) {
        /**
         This method can do 3 different things depending on the button that is
         clicked. If e.getSource() == previousMove, then this method will go back
         1 move and store the current board to next move. If e.getSource() == nextMove,
         then this method will go forward 1 move (nextMove only has something stored
         inside if previousMove was clicked) and store the current board to
         previousMove. If e.getSource() == newGame, then the whole game is reset
         except for the number of wins.

         @param e - name of the button clicked
         */
        if (e.getSource() == previousMove) {

            boolean flag = false;

            if (playChess.getPreviousMove() != null) {
                flag = findDiff(playChess.getPreviousMove(), playChess.getBoard());
                // returns true if there is a difference between current board and last board
            }

            if (playChess.previousMove()) {
                // the method previousMove returns false if there was no previous
                // move. If true then it returns true and changes the board accordingly.
                doNothing();
                // reset pieceAt and pieceTo

                textDisplay.setText("Previous Move");

                if (flag && player.equals(chessBoard.Color.WHITE)) {
                    // decrease location of capture
                    if (whiteX == 0) {
                        whiteX = 4;
                        if (whiteY != 0) whiteY--;
                    } else {
                        whiteX--;
                    }
                } else if (flag && player.equals(chessBoard.Color.BLACK)) {

                    if (blackX == 0) {
                        blackX = 4;
                        if (blackY != 0) blackY--;
                    } else  {
                        blackX--;
                    }
                }

                if (player.equals(chessBoard.Color.WHITE)) {
                    // change player
                    player = chessBoard.Color.BLACK;
                } else {
                    player = chessBoard.Color.WHITE;
                }


                Timer timer = new Timer(500, t -> {
                    remakeBoard();

                    textDisplay.setText(player + " Players Turn");
                });

                timer.setRepeats(false);
                timer.start();

            } else {
                doNothing();

                textDisplay.setText("No Previous Move Available!");

                Timer timer = new Timer(1000, t -> {
                    textDisplay.setText("Same Players Turn");
                });

                timer.setRepeats(false);
                //make sure the timer only runs once
                timer.start();
            }
        }

        if (e.getSource() == nextMove) {
            chessBoard.chessPiece[][] tempBoard = playChess.getBoard();

            if (playChess.nextMove()) {
                // go forward one move if player previously clicked the
                // previousMove button

                textDisplay.setText("Next Move");

                findDiff(playChess.getBoard(), tempBoard);
                // check if there is a difference between current board and last board

                updateBoard(tempBoard);
                // update GUI

                player = player.equals(chessBoard.Color.WHITE) ? chessBoard.Color.BLACK : chessBoard.Color.WHITE;
                //change color

                Timer timer = new Timer(1000, t -> {
                    textDisplay.setText(player + " Players Turn");
                });

                timer.setRepeats(false);
                timer.start();

                doNothing();

            } else {
                doNothing();

                textDisplay.setText("No Next Move Available!");

                Timer timer = new Timer(1000, t -> {
                    textDisplay.setText("Same Players Turn");
                });

                timer.setRepeats(false);
                timer.start();
            }
        }

        if (e.getSource() == newGame) {
            // reset board
            doNothing();
            blackX = 0;
            blackY = 0;
            whiteX = 0;
            whiteY = 0;

            frame.dispose();
            frame = new JFrame();

            playChess = new chessBoard();
            player = chessBoard.Color.WHITE;

            buttonPanel.setBounds(80, 20, 1400, 50);
            frame.add(buttonPanel);
            textPanel.setBounds(80, 80, 1400, 50);
            textDisplay.setText("White Moves First");
            frame.add(textPanel);
            whiteCapture.setBounds(120, 200, 200, 400);
            frame.add(whiteCapture);
            blackCapture.setBounds(1240, 200, 200, 400);
            frame.add(blackCapture);
            whiteWins.setBounds(120, 600, 200, 200);
            frame.add(whiteWins);
            blackWins.setBounds(1240, 600, 200, 200);
            frame.add(blackWins);

            setupBoard();

            gamePanel.setBounds(380, 150, 800, 800);
            frame.add(gamePanel);

            frame.setSize(1600, 1000);
            // changes widthxheight of window
            frame.setLayout(null);
            // no default layout
            frame.setVisible(true);
            // lets me see window
            frame.setResizable(false);
            // doesn't let the window resize
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }


    public void remakeBoard() {
        /**
         This method remakres the board according to the pieces currently in the
         board.
         */
        chessBoard.chessPiece[][] board = playChess.getBoard();
        int x = 405;
        // starting x position of the chessboard
        int y = 175;
        //starting y position of the chessboard

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessBoard.chessPiece p = board[i][j];

                if (p == null) continue;
                // nothing at position i & j then do nothing

                getImage(p);
                // assign an image to each piece on the instance image

                pieceDisplay = p.returnGUI();
                // gets image

                pieceDisplay.setIcon(image);
                // set p Icon to the instance image

                pieceDisplay.setBounds(x + (j * 100), y + (i * 100), 50, 50);
                frame.add(pieceDisplay);
            }
        }

        frame.remove(gamePanel);
        // reset game board
        frame.add(gamePanel);

    }


    private void updateBoard(chessBoard.chessPiece[][] lastBoard) {
        /**
         This method updates the GUI when a move happens. It checks if it needs
         to capture a piece (calls the method to capture if needed) and then
         moves the JLabel of the piece that was moved.

         @param lastBoard - the board before the move was performed
         */

        chessBoard.chessPiece pieceOne = lastBoard[pieceAtY][pieceAtX];
        // piece that will be moved
        chessBoard.chessPiece pieceTwo = lastBoard[pieceToY][pieceToX];
        // square it will move

        if (pieceTwo != null) {
            // if not null then move that piece to the capture square
            capture(pieceTwo);
        }

        pieceDisplay = pieceOne.returnGUI();
        pieceDisplay.setBounds(405 + (100 * pieceToX), 175 + (100 * pieceToY), 50, 50);
        // move piece to its new location

        SwingUtilities.updateComponentTreeUI(frame);

    }


    public void capture(chessBoard.chessPiece piece) {
        /**
         This method move a captured piece to the appropriate box (whiteCaptures
         or blackCaptures). It used the saved instance variable to decide how it
         should be stored (blackX/whiteX and blackY/whiteY).

         @param piece - piece that will be moved to the captured area
         */
        int x;
        int y;

        int startX;
        int startY = 225;

        if (piece.getColor().equals(chessBoard.Color.BLACK)) {
            if (blackX == 4) {
                // if x == 4 then reset it and increase y
                blackX = 0;
                blackY++;
            }
            startX = 120;
            // starting x position

            x = blackX;
            y = blackY;
        } else {
            if (whiteX == 4) {
                whiteX = 0;
                whiteY++;
            }

            startX = 1240;
            // starting x position

            x = whiteX;
            y = whiteY;
        }

        JLabel label = piece.returnGUI();
        // get piece gui reference

        label.setBounds(startX + (50 * x), startY + (50 * y), 50, 50);
        // new position in the frame

        if (piece.getColor().equals(chessBoard.Color.WHITE)) {
            frame.remove(blackCapture);
            frame.add(blackCapture);
            // need to remove/add so captured pieces show on the top of the JPanel
            whiteX++;
        } else {
            frame.remove(whiteCapture);
            frame.add(whiteCapture);
            blackX++;
        }

        SwingUtilities.updateComponentTreeUI(label);
        // updates that piece in the JFrame
    }


    public void getImage(chessBoard.chessPiece p) {
        /**
         Given a piece (p) this method looks at p chessPiece name and store
         the appropriate image to the instance image.
         */

        if (p.getChessPieceName().equals((chessBoard.Piece.PAWN__))) {
            image = new ImageIcon(getClass().getResource(p.getColor() + "Pawn.png"));
        } else if (p.getChessPieceName().equals(chessBoard.Piece.ROOK__)) {
            image = new ImageIcon(getClass().getResource(p.getColor() + "Rook.png"));
        } else if (p.getChessPieceName().equals(chessBoard.Piece.KNIGHT)) {
            image = new ImageIcon(getClass().getResource(p.getColor() + "Knight.png"));
        } else if (p.getChessPieceName().equals(chessBoard.Piece.BISHOP)) {
            image = new ImageIcon(getClass().getResource(p.getColor() + "Bishop.png"));
        } else if (p.getChessPieceName().equals(chessBoard.Piece.QUEEN_)) {
            image = new ImageIcon(getClass().getResource(p.getColor() + "Queen.png"));
        } else {
            image = new ImageIcon(getClass().getResource(p.getColor() + "King.png"));
        }

    }


    public boolean findDiff(chessBoard.chessPiece[][] newBoard, chessBoard.chessPiece[][] oldBoard) {
        /**
         This method compares two boards, the old board (usually the board stored
         in the next move) with the newBoard (the current board). When it finds a
         spot in the newBoard that is different from the oldBoard then it figures
         out how it is different and from that it decides how to store the vertical
         (i) values and the horizontal (j) value.

         @param newBoard - refers to the current board
         @param oldBoard - refers to the last board

         @return true - if a piece was moved; false - if a piece wasnt moved
         */
        boolean flag = false;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (newBoard[i][j] == null && oldBoard[i][j] == null) continue;
                // do nothing if null

                if (newBoard[i][j] == null && oldBoard[i][j] != null
                        || newBoard[i][j] != null && oldBoard[i][j] == null) {
                    // if there exists a piece on one instance and not the other
                    // than store i and j
                    if (oldBoard[i][j] != null && oldBoard[i][j].getColor().equals(player)) {
                        pieceAtY = i;
                        pieceAtX = j;
                    } else {
                        pieceToY = i;
                        pieceToX = j;
                    }
                } else if (newBoard[i][j] != null && oldBoard[i][j] != null &&
                        newBoard[i][j].getColor().equals(player) && !oldBoard[i][j].getColor().equals(player)) {
                    // if there exists two different piece in this spot then store i and j
                    flag = true;
                    pieceToY = i;
                    pieceToX = j;
                }
            }
        }

        return flag;
    }


    private String promote() {
        /**
         This method changes the GUI of the current pawn and return a string of
         the chosen promotion.

         @return string of the chosen promotion
         */
        String name;
        // String that will be returned

        while (true) {
            if (player.equals(chessBoard.Color.WHITE)) {
                // image that will be attached to the JoptionPane
                image = new ImageIcon(getClass().getResource("whitePawn.png"));
            } else {
                image = new ImageIcon(getClass().getResource("blackPawn.png"));
            }

            name = JOptionPane.showInputDialog(null, "Promote Pawn to:",
                    null, JOptionPane.QUESTION_MESSAGE, image, null, null).toString();
            // asks for the name to promote the pawn to

            if (name.equals("queen")) {
                // then the name will b compared with other string to chose to correct promotion
                image = new ImageIcon(getClass().getResource(player + "Queen.png"));
            } else if (name.equals("bishop")) {
                image = new ImageIcon(getClass().getResource(player + "Bishop.png"));
            } else if (name.equals("knight")) {
                image = new ImageIcon(getClass().getResource(player + "Knight.png"));
            } else if (name.equals("rook")) {
                image = new ImageIcon(getClass().getResource(player + "Rook.png"));
            } else {
                continue;
            }

            pieceDisplay = playChess.getBoard()[pieceToY][pieceToX].returnGUI();
            pieceDisplay.setIcon(image);
            // change the GUI of the pawn

            SwingUtilities.updateComponentTreeUI(pieceDisplay);

            break;
        }

        return name;
    }


// these methods are not needed for this file, but because it extends MouseListener
// these methods are required to be in this file.
    @Override
    public void mouseReleased(MouseEvent e) {
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