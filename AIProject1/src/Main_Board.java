
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Gerhard Smit
 */
public class Main_Board extends javax.swing.JFrame
{

    int dimensions = -1;
    JButton[][] blocks;
    boolean aiActive = true;
    Container container;
    int player1 = 1;
    int firstMove = 1;
    int prevX;
    int prevY;
    boolean movesOpen = false;
    Color previous;
    int blueS;
    int redS;
    int winner = 0;
    boolean AI = false;
    boolean gameRunning;
    AIlogic logic; 
    private int plyDepth = 0;
    /**
     * Creates new form Main_Board
     */
    public Main_Board()
    {
        initComponents();
        setBounds(550, 80, 900, 900);
    }
    public int getDepth()
    {
        return plyDepth;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        playerTurn = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        setBoardSize = new javax.swing.JMenuItem();
        isAlpha = new javax.swing.JCheckBoxMenuItem();
        AIvsHumanCheck = new javax.swing.JCheckBoxMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        playerTurn.setText("Player's Turn");

        jMenu1.setText("New Game");

        setBoardSize.setText("Set board size");
        setBoardSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setBoardSizeActionPerformed(evt);
            }
        });
        jMenu1.add(setBoardSize);

        isAlpha.setSelected(true);
        isAlpha.setText("Alpha-beta pruning");
        jMenu1.add(isAlpha);

        AIvsHumanCheck.setText("AI vs Human");
        AIvsHumanCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AIvsHumanCheckActionPerformed(evt);
            }
        });
        jMenu1.add(AIvsHumanCheck);

        jMenuItem3.setText("Start Game");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 505, Short.MAX_VALUE)
                .addComponent(playerTurn))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 440, Short.MAX_VALUE)
                .addComponent(playerTurn))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    public void changeAI(JButton source)
    {      
        Color colSource = source.getBackground();
        showOptions(source);
        addClone(source);
        cleanUp();
        takeOverAdj(source);
        calculateScore();
    }
    
    public void cleanUp()
    {
        for (int x = 0; x < dimensions; x++)
        {
            for (int y = 0; y < dimensions; y++)
            {
                //System.out.println(blocks[x][y].getBackground()+"x:"+x+" y:"+y);
                if (blocks[x][y].getBackground() == Color.orange || blocks[x][y].getBackground() == Color.green)
                {
                    blocks[x][y].setBackground(Color.gray);
                }
            }
        }
    }

    public void addClone(JButton source)
    {
        try
        {
            if (source.getBackground() == Color.orange)
            {
                switch (player1)
                {
                    case 1:
                    {
                        source.setBackground(Color.red);
                        if(AI)
                        {
                             System.out.println("here we go red");
                            String coords = logic.newPos();
                        }
                        else
                        {
                            player1 = 0;
                        }
                        break;
                    }
                    case 0:
                    {
                        source.setBackground(Color.blue);
                        player1 = 1;
                        break;
                    }
                }
                firstMove = 1;
            }
            else if (source.getBackground() == Color.GREEN)
            {
                blocks[prevY][prevX].setBackground(Color.gray);
                switch (player1)
                {
                    case 1:
                    {
                        source.setBackground(Color.red);
                        if(AI)
                        {
                            System.out.println("here we go blue");
                            String coords = logic.newPos();
                        }
                        else
                        {
                            player1 = 0;                            
                        }
                        break;
                    }
                    case 0:
                    {
                        source.setBackground(Color.blue);
                        player1 = 1;
                        break;
                    }
                }
                firstMove = 1;
            }
            else if (source.getBackground()==previous)
            {
                displayOpts(source);
                firstMove = 1;
            }
            else
            {
                throw new Exception();
            }
        }
        catch (Exception excp)
        {
            JOptionPane.showMessageDialog(rootPane, "That move can't be done");
        }
    }

    public void displayOpts(JButton source)
    {
        boolean inBounds;
        String coords = source.getActionCommand();
        String x_y[] = coords.split("#");
        int x = Integer.parseInt(x_y[1]);
        prevX = x;
        int y = Integer.parseInt(x_y[0]);
        prevY = y;

        inBounds = ((x + 1 >= 0) && (y >= 0) && (x + 1 <= dimensions - 1) && (y <= dimensions - 1));
        if (inBounds && blocks[y][x + 1].getBackground() == Color.GRAY)
        {
            blocks[y][x + 1].setBackground(Color.orange);
            movesOpen = true;
        }
        inBounds = ((x + 1 >= 0) && (y + 1 >= 0) && (x + 1 <= dimensions - 1) && (y + 1 <= dimensions - 1));
        if (inBounds && blocks[y + 1][x + 1].getBackground() == Color.GRAY)
        {
            blocks[y + 1][x + 1].setBackground(Color.orange);
            movesOpen = true;
        }
        inBounds = ((x >= 0) && (y + 1 >= 0) && (x <= dimensions - 1) && (y + 1 <= dimensions - 1));
        if (inBounds && blocks[y + 1][x].getBackground() == Color.GRAY)
        {
            blocks[y + 1][x].setBackground(Color.orange);
            movesOpen = true;
        }
        inBounds = ((x - 1 >= 0) && (y + 1 >= 0) && (x - 1 <= dimensions - 1) && (y + 1 <= dimensions - 1));
        if (inBounds && blocks[y + 1][x - 1].getBackground() == Color.GRAY)
        {
            blocks[y + 1][x - 1].setBackground(Color.orange);
            movesOpen = true;
        }
        inBounds = ((x - 1 >= 0) && (y >= 0) && (x - 1 <= dimensions - 1) && (y <= dimensions - 1));
        if (inBounds && blocks[y][x - 1].getBackground() == Color.GRAY)
        {
            blocks[y][x - 1].setBackground(Color.orange);
            movesOpen = true;
        }
        inBounds = ((x - 1 >= 0) && (y - 1 >= 0) && (x - 1 <= dimensions - 1) && (y - 1 <= dimensions - 1));
        if (inBounds && blocks[y - 1][x - 1].getBackground() == Color.GRAY)
        {
            blocks[y - 1][x - 1].setBackground(Color.orange);
            movesOpen = true;
        }
        inBounds = ((x >= 0) && (y - 1 >= 0) && (x <= dimensions - 1) && (y - 1 <= dimensions - 1));
        if (inBounds && blocks[y - 1][x].getBackground() == Color.GRAY)
        {
            blocks[y - 1][x].setBackground(Color.orange);
            movesOpen = true;
        }
        inBounds = ((x + 1 >= 0) && (y - 1 >= 0) && (x + 1 <= dimensions - 1) && (y - 1 <= dimensions - 1));
        if (inBounds && blocks[y - 1][x + 1].getBackground() == Color.GRAY)
        {
            blocks[y - 1][x + 1].setBackground(Color.orange);
            movesOpen = true;
        }

        inBounds = ((x + 2 >= 0) && (y >= 0) && (x + 2 <= dimensions - 1) && (y <= dimensions - 1));
        if (inBounds && blocks[y][x + 2].getBackground() == Color.GRAY)
        {
            blocks[y][x + 2].setBackground(Color.green);
            movesOpen = true;
        }
        inBounds = ((x + 2 >= 0) && (y + 1 >= 0) && (x + 2 <= dimensions - 1) && (y + 1 <= dimensions - 1));
        if (inBounds && blocks[y + 1][x + 2].getBackground() == Color.GRAY)
        {
            blocks[y + 1][x + 2].setBackground(Color.green);
            movesOpen = true;
        }
        inBounds = ((x + 2 >= 0) && (y + 2 >= 0) && (x + 2 <= dimensions - 1) && (y + 2 <= dimensions - 1));
        if (inBounds && blocks[y + 2][x + 2].getBackground() == Color.GRAY)
        {
            blocks[y + 2][x + 2].setBackground(Color.green);
            movesOpen = true;
        }
        inBounds = ((x + 1 >= 0) && (y + 2 >= 0) && (x + 1 <= dimensions - 1) && (y + 2 <= dimensions - 1));
        if (inBounds && blocks[y + 2][x + 1].getBackground() == Color.GRAY)
        {
            blocks[y + 2][x + 1].setBackground(Color.green);
            movesOpen = true;
        }
        inBounds = ((x >= 0) && (y + 2 >= 0) && (x <= dimensions - 1) && (y + 2 <= dimensions - 1));
        if (inBounds && blocks[y + 2][x].getBackground() == Color.GRAY)
        {
            blocks[y + 2][x].setBackground(Color.green);
            movesOpen = true;
        }
        inBounds = ((x - 1 >= 0) && (y + 2 >= 0) && (x - 1 <= dimensions - 1) && (y + 2 <= dimensions - 1));
        if (inBounds && blocks[y + 2][x - 1].getBackground() == Color.GRAY)
        {
            blocks[y + 2][x - 1].setBackground(Color.green);
            movesOpen = true;
        }
        inBounds = ((x - 2 >= 0) && (y + 2 >= 0) && (x - 2 <= dimensions - 1) && (y + 2 <= dimensions - 1));
        if (inBounds && blocks[y + 2][x - 2].getBackground() == Color.GRAY)
        {
            blocks[y + 2][x - 2].setBackground(Color.green);
            movesOpen = true;
        }
        inBounds = ((x - 2 >= 0) && (y + 1 >= 0) && (x - 2 <= dimensions - 1) && (y + 1 <= dimensions - 1));
        if (inBounds && blocks[y + 1][x - 2].getBackground() == Color.GRAY)
        {
            blocks[y + 1][x - 2].setBackground(Color.green);
            movesOpen = true;
        }
        inBounds = ((x - 2 >= 0) && (y >= 0) && (x - 2 <= dimensions - 1) && (y <= dimensions - 1));
        if (inBounds && blocks[y][x - 2].getBackground() == Color.GRAY)
        {
            blocks[y][x - 2].setBackground(Color.green);
            movesOpen = true;
        }
        inBounds = ((x - 2 >= 0) && (y - 1 >= 0) && (x - 2 <= dimensions - 1) && (y - 1 <= dimensions - 1));
        if (inBounds && blocks[y - 1][x - 2].getBackground() == Color.GRAY)
        {
            blocks[y - 1][x - 2].setBackground(Color.green);
            movesOpen = true;
        }
        inBounds = ((x - 2 >= 0) && (y - 2 >= 0) && (x - 2 <= dimensions - 1) && (y - 2 <= dimensions - 1));
        if (inBounds && blocks[y - 2][x - 2].getBackground() == Color.GRAY)
        {
            blocks[y - 2][x - 2].setBackground(Color.green);
            movesOpen = true;
        }
        inBounds = ((x - 1 >= 0) && (y - 2 >= 0) && (x - 1 <= dimensions - 1) && (y - 2 <= dimensions - 1));
        if (inBounds && blocks[y - 2][x - 1].getBackground() == Color.GRAY)
        {
            blocks[y - 2][x - 1].setBackground(Color.green);
            movesOpen = true;
        }
        inBounds = ((x >= 0) && (y - 2 >= 0) && (x <= dimensions - 1) && (y - 2 <= dimensions - 1));
        if (inBounds && blocks[y - 2][x].getBackground() == Color.GRAY)
        {
            blocks[y - 2][x].setBackground(Color.green);
            movesOpen = true;
        }
        inBounds = ((x + 1 >= 0) && (y - 2 >= 0) && (x + 1 <= dimensions - 1) && (y - 2 <= dimensions - 1));
        if (inBounds && blocks[y - 2][x + 1].getBackground() == Color.GRAY)
        {
            blocks[y - 2][x + 1].setBackground(Color.green);
            movesOpen = true;
        }
        inBounds = ((x + 2 >= 0) && (y - 2 >= 0) && (x + 2 <= dimensions - 1) && (y - 2 <= dimensions - 1));
        if (inBounds && blocks[y - 2][x + 2].getBackground() == Color.GRAY)
        {
            blocks[y - 2][x + 2].setBackground(Color.green);
            movesOpen = true;
        }
        inBounds = ((x + 2 >= 0) && (y - 1 >= 0) && (x + 2 <= dimensions - 1) && (y - 1 <= dimensions - 1));
        if (inBounds && blocks[y - 1][x + 2].getBackground() == Color.GRAY)
        {
            blocks[y - 1][x + 2].setBackground(Color.green);
            movesOpen = true;
        }
    }

    public void takeOverAdj(JButton source)
    {
        boolean inBounds;
        String coords = source.getActionCommand();
        String x_y[] = coords.split("#");
        int x = Integer.parseInt(x_y[1]);
        int y = Integer.parseInt(x_y[0]);
        
        inBounds = ((x + 1 >= 0) && (y >= 0) && (x + 1 <= dimensions - 1) && (y <= dimensions - 1));
        if (inBounds && blocks[y][x + 1].getBackground() != Color.GRAY && blocks[y][x + 1].getBackground() != source.getBackground())
        {
            blocks[y][x + 1].setBackground(source.getBackground());
        }
        
        inBounds = ((x >= 0) && (y + 1 >= 0) && (x <= dimensions - 1) && (y + 1 <= dimensions - 1));
        if (inBounds && blocks[y + 1][x].getBackground() != Color.GRAY && blocks[y + 1][x].getBackground() != source.getBackground())
        {
            blocks[y + 1][x].setBackground(source.getBackground());
        }
        
        inBounds = ((x - 1 >= 0) && (y >= 0) && (x - 1 <= dimensions - 1) && (y <= dimensions - 1));
        if (inBounds && blocks[y][x - 1].getBackground() != Color.GRAY && blocks[y][x - 1].getBackground() != source.getBackground())
        {
            blocks[y][x - 1].setBackground(source.getBackground());
        }
        
        inBounds = ((x >= 0) && (y - 1 >= 0) && (x <= dimensions - 1) && (y - 1 <= dimensions - 1));
        if (inBounds && blocks[y - 1][x].getBackground() != Color.GRAY && blocks[y - 1][x].getBackground() != source.getBackground())
        {
            blocks[y - 1][x].setBackground(source.getBackground());
        }
        
        inBounds = ((x + 1 >= 0) && (y + 1 >= 0) && (x + 1 <= dimensions - 1) && (y +1 <= dimensions - 1));
        if (inBounds && blocks[y+1][x + 1].getBackground() != Color.GRAY && blocks[y+1][x + 1].getBackground() != source.getBackground())
        {
            blocks[y+1][x + 1].setBackground(source.getBackground());
        }
        
        inBounds = ((x -1 >= 0) && (y + 1 >= 0) && (x -1 <= dimensions - 1) && (y + 1 <= dimensions - 1));
        if (inBounds && blocks[y + 1][x-1].getBackground() != Color.GRAY && blocks[y + 1][x-1].getBackground() != source.getBackground())
        {
            blocks[y + 1][x-1].setBackground(source.getBackground());
        }
        
        inBounds = ((x - 1 >= 0) && (y -1 >= 0) && (x - 1 <= dimensions - 1) && (y - 1 <= dimensions - 1));
        if (inBounds && blocks[y-1][x - 1].getBackground() != Color.GRAY && blocks[y-1][x - 1].getBackground() != source.getBackground())
        {
            blocks[y-1][x - 1].setBackground(source.getBackground());
        }
        
        inBounds = ((x+1 >= 0) && (y - 1 >= 0) && (x + 1 <= dimensions - 1) && (y - 1 <= dimensions - 1));
        if (inBounds && blocks[y - 1][x+1].getBackground() != Color.GRAY && blocks[y - 1][x+1].getBackground() != source.getBackground())
        {
            blocks[y - 1][x+1].setBackground(source.getBackground());
        }
        
        
        
    }

    public void showOptions(JButton source)
    {
        try
        {
            switch (player1)
            {
                case 1:
                {
                    if (source.getBackground() == Color.red)
                    {
                        firstMove = 0;
                        displayOpts(source);
                    }
                    else
                    {
                        throw new Exception();
                    }
                    break;
                }
                case 0:
                {
                    if (source.getBackground() == Color.blue)
                    {
                        firstMove = 0;
                        displayOpts(source);
                    }
                    else
                    {
                        throw new Exception();
                    }
                    break;
                }
            }
        }
        catch (Exception excp)
        {
            firstMove = 1;
            JOptionPane.showMessageDialog(rootPane, "Please select your block");
        }
    }
    private void setBoardSizeActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_setBoardSizeActionPerformed
    {//GEN-HEADEREND:event_setBoardSizeActionPerformed
        try
        {
            dimensions = Integer.parseInt(JOptionPane.showInputDialog("Please insert the dimensions of the board: (N x N; Max 12 x 12; Min 4 x 4)"));
            if (dimensions < 4 || dimensions > 12)
            {
                dimensions = -1;
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException nfe)
        {
            dimensions = -1;
            JOptionPane.showMessageDialog(rootPane, "That was not a valid entry please try again");
        }
    }//GEN-LAST:event_setBoardSizeActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem3ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem3ActionPerformed
        if(AI)
        {
            if(plyDepth==0)
            {
                plyDepth = 1;
                logic = new AIlogic(Color.BLUE,Color.RED,this);
            }
            else
                logic = new AIlogic(Color.BLUE,Color.RED,this);
        }
        if (container != null)
        {
            this.remove(container);
            container.removeAll();
            firstMove = 1;
            player1 = 0;
            redS = 0;
            blueS = 0;
            winner = -1;
        }
        container = new Container();
        int blockDim;
        blockDim = 650 / dimensions;
        int colPlace = 0, rowPlace = 0;
        container = this.getContentPane();
        try
        {
            if (dimensions == -1)
            {
                throw new IndexOutOfBoundsException();
            }
            else
            {
                gameRunning = true;
                blocks = new JButton[dimensions][dimensions];
                for (int x = 0; x < dimensions; x++)
                {
                    for (int y = 0; y < dimensions; y++)
                    {
                        blocks[x][y] = new JButton();
                        blocks[x][y].setActionCommand(x + "#" + y);
                        blocks[x][y].addActionListener(new java.awt.event.ActionListener()
                        {
                            public void actionPerformed(ActionEvent e)
                            {
                                if(firstMove != 2)
                                {
                                JButton temp = (JButton) e.getSource();
                                if (firstMove == 1)
                                {
                                    previous = temp.getBackground();
                                    showOptions(temp);
                                }
                                else                                
                                    if(movesOpen==true)
                                    {
                                    movesOpen = false;
                                    addClone(temp);
                                    if (firstMove == 1)
                                    {
                                        cleanUp();
                                        takeOverAdj(temp);
                                        
                                        if (player1 == 1)
                                        {
                                            playerTurn.setText("Player 1's turn");
                                        }
                                        else
                                        {
                                            playerTurn.setText("Player 2's turn");
                                        }
                                        calculateScore();
                                        checkWinner();
                                        if(winner == 1)
                                        {
                                            firstMove = 2;
                                            gameRunning = false;
                                        }
                                    }
                                    }
                                    else
                                    {
                                       firstMove = 1; 
                                    }
                                    
                            }
                                else
                                {
                                    JOptionPane.showMessageDialog(rootPane, "Please restart the game");
                                }
                        }
                        });
                        blocks[x][y].setContentAreaFilled(true);
                        blocks[x][y].setBackground(Color.gray);
                        blocks[x][y].setBounds(colPlace, rowPlace, blockDim, blockDim);
                        container.add(blocks[x][y]);
                        colPlace += blockDim;

                    }
                    colPlace = 0;
                    rowPlace += blockDim;
                }
                blocks[0][0].setBackground(Color.red);
                blocks[dimensions - 1][dimensions - 1].setBackground(Color.blue);

                this.pack();
                setBounds(550, 80, 680, 900);
                if (player1 == 1)
                {
                    playerTurn.setText("Player 1's turn");
                }
                else
                {
                    playerTurn.setText("Player 2's turn");
                }
            }
        }
        catch (IndexOutOfBoundsException ioe)
        {
            JOptionPane.showMessageDialog(rootPane, "Please enter the correct dimensions of board");
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void AIvsHumanCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AIvsHumanCheckActionPerformed
        if(gameRunning == false)
        {
            plyDepth = Integer.parseInt(JOptionPane.showInputDialog("Please enter the ply depth:"));
        if(AI == true)
        {
            AI = false;
        }
        else
            if(AI == false)
            {
                AI = true;
            }
        }
        else
        {
            AIvsHumanCheck.setState(AI);            
            JOptionPane.showMessageDialog(rootPane, "Game state is running, cannot change state");
        }
        
        //System.out.println(AIvsHumanCheck.getState());
        //System.out.println(AI);
    }//GEN-LAST:event_AIvsHumanCheckActionPerformed
    public void calculateScore()
    {
        redS = 0;
        blueS = 0;
        for(int x = 0;x< dimensions;x++)
            for(int y = 0;y<dimensions;y++)
            {
                if(blocks[x][y].getBackground()==Color.RED)
                    redS++;
                else
                    if(blocks[x][y].getBackground()==Color.BLUE)
                        blueS++;
            }
    }
    public void checkWinner()
    {
        if(redS==0)
        {
            JOptionPane.showMessageDialog(rootPane, "Player 2 is the winner");
            winner = 1;
        }
        else
            if(blueS == 0)
            {
                JOptionPane.showMessageDialog(rootPane, "Player 1 is the winner");
                winner = 1;
            }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(Main_Board.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(Main_Board.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(Main_Board.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(Main_Board.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new Main_Board().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem AIvsHumanCheck;
    private javax.swing.JCheckBoxMenuItem isAlpha;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JLabel playerTurn;
    private javax.swing.JMenuItem setBoardSize;
    // End of variables declaration//GEN-END:variables
}