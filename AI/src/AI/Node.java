package AI;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

public class Node {
    private int[] sequence;

    private int emptyPosition;

    private int n;

    private Node formerNode;

    private int scoreOfAStar;

    private int scoreOfA;

    private int level;

    private LinkedList<Node> subNodes;

    Node(int[] sequence, int n, Node node, int level)
    {
        this.level = level;
        this.sequence = sequence;
        this.formerNode = node;
        this.n = n;

        for(int i = 0;i<n*n;i++)
        {
            if (this.sequence[i]==0)
            {
                emptyPosition = i;
                break;
            }
        }
        this.scoreOfAStar = scoreOfAStar();
        this.scoreOfA = scoreOfA();
    }

    public void setSequence(int[] sequence) {
        this.sequence = sequence;
    }

    public int[] getSequence() {
        return sequence;
    }

    int[] getMovablePosition()
    {
        int row = emptyPosition/n;
        int column = emptyPosition%n;
        ArrayList<Integer> positions = new ArrayList<>();
        if (row>0&&row!=n-1)
            positions.add((row+1)*n+column);
        if (row<n-1&&row!=0)
            positions.add((row-1)*n+column);
        if (column<n-1&&column!=0)
            positions.add(row*n+column-1);
        if (column>0&&column!=n-1)
            positions.add(row*n+column+1);
        if (row==0)
            positions.add((row+1)*n+column);
        if (row==n-1)
            positions.add((row-1)*n+column);
        if (column==n-1)
            positions.add(row*n+column-1);
        if (column==0)
            positions.add(row*n+column+1);
        int[] d = new int[positions.size()];
        for(int i = 0;i<positions.size();i++){
            d[i] = positions.get(i);
        }
        return d;
    }

    LinkedList<Node> calculateSubNodes()
    {
        int[] positions = getMovablePosition();
        LinkedList<Node> nodes = new LinkedList<>();
        for(int i = 0;i < positions.length; i++)
        {
            int[] newPosition = sequence.clone();
            newPosition[emptyPosition] = newPosition[positions[i]];
            newPosition[positions[i]] = 0;
            nodes.add(new Node(newPosition,n,this,this.level+1));
        }
        return nodes;
    }

    public int getEmptyPosition() {
        return emptyPosition;
    }

    public void setEmptyPosition(int emptyPosition) {
        this.emptyPosition = emptyPosition;
    }

    @Override
    public boolean equals(Object node){
        for (int i = 0; i<n*n; i++)
        {
            if (this.sequence[i]!=((Node) node).getSequence()[i])
            {
                return false;
            }
        }
        return true;
    }

    public int scoreOfA()
    {
        int errorNum = 0;
        int[] seq = this.getSequence();
        for (int i = 0; i<n*n; i++)
        {
            if (seq[i]!=i+1)
            {
                errorNum++;//A算法 仅仅只考虑放错的个数
            }
        }
        return errorNum;
    }

    public int scoreOfAStar()
    {
        int errorNum = 0;
        int[] seq = this.getSequence();
        for (int i = 0; i<n*n; i++)
        {
            if (seq[i]!=i+1&&seq[i]!=0)//A*算法 考虑放错后还原的难易程度
            {
                int row = (seq[i]-1)/3;
                int column = (seq[i]-1)%3;
                errorNum += Math.abs(row-(i)/3)+Math.abs(column-(i)%3);
            }
//            errorNum++;
        }
        return errorNum;
    }

    public boolean checkSolved()
    {
        for(int i = 0; i<n*n-1; i++)
        {
            if (this.sequence[i]!=i+1)
            {
                return false;
            }
        }
        return true;
    }

    public int getScoreOfAStar() {
        return scoreOfAStar;
    }

    public void setScoreOfAStar(int scoreOfAStar) {
        this.scoreOfAStar = scoreOfAStar;
    }

    public int getScoreOfA() {
        return scoreOfA;
    }

    public void setScoreOfA(int scoreOfA) {
        this.scoreOfA = scoreOfA;
    }

    public void setLevel(int level) {
        this.level = level;
    }



    public int getLevel() {
        return level;
    }

    public Node getFormerNode() {
        return formerNode;
    }

    public void setFormerNode(Node formerNode) {
        this.formerNode = formerNode;
    }

    public LinkedList<Node> getSubNodes() {
        return subNodes;
    }

    public void setSubNodes(LinkedList<Node> subNodes) {
        this.subNodes = subNodes;
    }

    @Override
    public String toString() {
        String result = "";
        for(int i = 0; i<n; i++)
        {
            for (int j = 0; j<n; j++)
            {
                result=result+" "+sequence[i*n+j];
            }
            result+='\n';
        }
        return result;
    }
}

