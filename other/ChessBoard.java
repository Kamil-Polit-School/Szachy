import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ChessBoard extends JFrame implements MouseListener, MouseMotionListener
{
    JLayeredPane layeredPane;
    JPanel chessBoard;
    JLabel chessPiece;
    int xAdjustment;
    int yAdjustment;

    public ChessBoard()
    {
        Dimension boardSize = new Dimension(600, 600);

        //  Use a Layered Pane for this this application

        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize( boardSize );
        layeredPane.addMouseListener( this );
        layeredPane.addMouseMotionListener( this );
        getContentPane().add(layeredPane);

        //  Add a chess board to the Layered Pane

        chessBoard = new JPanel();
        chessBoard.setLayout( new GridLayout(8, 8) );
        chessBoard.setPreferredSize( boardSize );
        chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);
        layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);

        //  Build the Chess Board squares

        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                JPanel square = new JPanel( new BorderLayout() );
                square.setBackground( (i + j) % 2 == 0 ? Color.gray : Color.white );
                chessBoard.add( square );
            }
        }

        // Add a few pieces to the board

        ImageIcon pawn = new ImageIcon("img/Pawn.png"); // add an image here
        ImageIcon king = new ImageIcon("img/King.png");
        ImageIcon queen = new ImageIcon("img/Queen.png");
        ImageIcon rook = new ImageIcon("img/Rook.png");
        ImageIcon knight = new ImageIcon("img/Knight.png");
        ImageIcon bishop = new ImageIcon("img/Bishop.png");

        JLabel piece = new JLabel();
        JPanel panel = (JPanel)chessBoard;
        //king
            piece = new JLabel( king );
            panel = (JPanel)chessBoard.getComponent( 4 );
            panel.add( piece );
        //queen
            piece = new JLabel( queen );
            panel = (JPanel)chessBoard.getComponent( 3 );
            panel.add( piece );
        //knights
            piece = new JLabel( bishop );
            panel = (JPanel)chessBoard.getComponent( 2 );
            panel.add( piece );
            piece = new JLabel( bishop );
            panel = (JPanel)chessBoard.getComponent( 5 );
            panel.add( piece );
        //knights
            piece = new JLabel( knight );
            panel = (JPanel)chessBoard.getComponent( 1 );
            panel.add( piece );
            piece = new JLabel( knight );
            panel = (JPanel)chessBoard.getComponent( 6 );
            panel.add( piece );
        //rooks
            piece = new JLabel( rook );
            panel = (JPanel)chessBoard.getComponent( 0 );
            panel.add( piece );
            piece = new JLabel( rook );
            panel = (JPanel)chessBoard.getComponent( 7 );
            panel.add( piece );
        //pawns
        for (int i=8; i<=15; i++){
            piece = new JLabel( pawn );
            panel = (JPanel)chessBoard.getComponent( i );
            panel.add( piece );
        }
    }

    /*
    **  Add the selected chess piece to the dragging layer so it can be moved
    */
    public void mousePressed(MouseEvent e)
    {
        chessPiece = null;
        Component c =  chessBoard.findComponentAt(e.getX(), e.getY());

        if (c instanceof JPanel) return;

        Point parentLocation = c.getParent().getLocation();
        xAdjustment = parentLocation.x - e.getX();
        yAdjustment = parentLocation.y - e.getY();
        chessPiece = (JLabel)c;
        chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);

        layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
        layeredPane.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
    }

    /*
    **  Move the chess piece around
    */
    public void mouseDragged(MouseEvent me)
    {
        if (chessPiece == null) return;

        //  The drag location should be within the bounds of the chess board

        int x = me.getX() + xAdjustment;
        int xMax = layeredPane.getWidth() - chessPiece.getWidth();
        x = Math.min(x, xMax);
        x = Math.max(x, 0);

        int y = me.getY() + yAdjustment;
        int yMax = layeredPane.getHeight() - chessPiece.getHeight();
        y = Math.min(y, yMax);
        y = Math.max(y, 0);

        chessPiece.setLocation(x, y);
     }

    /*
    **  Drop the chess piece back onto the chess board
    */
    public void mouseReleased(MouseEvent e)
    {
        layeredPane.setCursor(null);

        if (chessPiece == null) return;

        //  Make sure the chess piece is no longer painted on the layered pane

        chessPiece.setVisible(false);
        layeredPane.remove(chessPiece);
        chessPiece.setVisible(true);

        //  The drop location should be within the bounds of the chess board

        int xMax = layeredPane.getWidth() - chessPiece.getWidth();
        int x = Math.min(e.getX(), xMax);
        x = Math.max(x, 0);

        int yMax = layeredPane.getHeight() - chessPiece.getHeight();
        int y = Math.min(e.getY(), yMax);
        y = Math.max(y, 0);

        Component c =  chessBoard.findComponentAt(x, y);

        if (c instanceof JLabel)
        {
            Container parent = c.getParent();
            parent.remove(0);
            parent.add( chessPiece );
            parent.validate();
        }
        else
        {
            Container parent = (Container)c;
            parent.add( chessPiece );
            parent.validate();
        }
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public static void main(String[] args)
    {
        JFrame frame = new ChessBoard();
        frame.setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        frame.setResizable( false );
        frame.pack();
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
     }
}