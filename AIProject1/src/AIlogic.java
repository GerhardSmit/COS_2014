import java.awt.Color;
import javax.swing.JButton;

/**
 *
 * @author Gerhard Smit
 */
public class AIlogic 
{
    Color mine;
    Color enemy;
    int amountOfBlocks = 1;
    int enemyBlocks = 1;
    Main_Board state;
    Main_Board bestState;
    int ply; 
    int dim; 
    int bestH;
    int cHeu;
    //JButton blocks[][] = state.blocks; 
    public AIlogic(Color mine, Color enemy,Main_Board s)
    {
        this.enemy = enemy;
        this.mine = mine;
        state = s;
        ply  = state.getDepth();
        dim = state.dimensions;
        System.out.println(mine+" and "+ enemy);
        
    }
    private Main_Board calculateHeuristic(Main_Board source,JButton sourceE)
    {
        cHeu = 0;
        source.changeAI(sourceE);
        return source;
    }
    private void createTree()
    {
        Main_Board currentState = new Main_Board();
        JButton blocks[][] = currentState.blocks;
        for(int depth = 0;depth<ply;depth++)
        {
           for(int x = 0;x<dim;x++)
           {
               for(int y = 0;y<dim;y++)
               {
                   if(blocks[x][y].getBackground()==mine)
                   {
                       Main_Board stateS = calculateHeuristic(currentState,blocks[x][y]);
                       if(cHeu > bestH)
                       {
                           cHeu = bestH;
                           bestState = stateS;
                       }
                   }
               }
           }
           currentState = bestState;
           blocks = currentState.blocks;
        }
    }
    public String newPos()
    {
        createTree();
        int xC = 0;
        int yC = 0;
        return xC+"#"+yC;
    }
    /*private class Tree
    {
        private Node root = null;
        public void insert(int children, int heu)
        {
            if(root==null)
                root = new Node(children,heu);
            else
            {
                Node inserted = new Node(children,heu);
                insertN(inserted);
            }
                
        }
        private void insertN(Node added)
        {
            
        }
        private class Node
        {
            private int heuristic;
            public Node children[];
            public Node(int amount,int value)
            {
                children = new Node[amount];
                for(int x = 0;x< amount;x++)
                    children[x] = null;
                heuristic = value;
            }
        }
    }*/
}
