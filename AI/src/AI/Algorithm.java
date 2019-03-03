package AI;

import com.sun.istack.internal.localization.NullLocalizable;
import javafx.scene.control.TextArea;

import java.util.LinkedList;
import java.util.ListIterator;

public class Algorithm {
    private LinkedList<Node> open = new LinkedList<>();
    private LinkedList<Node> closed = new LinkedList<>();
    private int level;
    private int n;
    private String searchInfos;

    public  void search(int[] sequence, boolean isAStar, TextArea textArea)
    {
        open = new LinkedList<>();
        closed = new LinkedList<>();
        level = 0;
        searchInfos = "";
        ListIterator<Node> nodeListIterator;
        n = (int)Math.sqrt(sequence.length);
        Node startNode = new Node(sequence,n,null,level);
        textArea.appendText("开始寻找如下组合的解\n"+startNode.toString());
        if (isAStar)
            textArea.appendText("使用A*算法\n");
        else
            textArea.appendText("使用A算法\n");
        open.add(startNode);
        LinkedList<Node> subNodes = new LinkedList<>();
        long begintime = System.nanoTime();
        while(open.size()>0)
        {
            nodeListIterator = open.listIterator();
            int score = 0;
            int tempScore;
            int index = 0;
            int counter = 0;
            Node node;
            Node decidedNode;
            node = nodeListIterator.next();
            score = setScore(isAStar, node);
            decidedNode = node;
            index = 0;
            counter++;
            while(nodeListIterator.hasNext())
            {
                node = nodeListIterator.next();
                tempScore = setScore(isAStar, node);
                if (tempScore<score)
                {
                    score = tempScore;
                    index = counter;
                    decidedNode = node;
                }
                counter++;
            }
            closed.add(decidedNode);
            open.remove(index);
            if (decidedNode.checkSolved())
            {
                long endtime = System.nanoTime();
                long costTime = (endtime - begintime)/1000;
                textArea.appendText("寻找成功，用时"+costTime+"ns\n");
                LinkedList<Node> result = new LinkedList<>();
                Node currentNode = decidedNode;
                while (currentNode!=null)
                {
                    result.add(0,currentNode);
                    currentNode = currentNode.getFormerNode();
                }
                textArea.appendText("一共有" + result.size() + "步\n");
                ListIterator<Node> resultIterator = result.listIterator();
                int i = 1;
                while (resultIterator.hasNext())
                {
                    currentNode = resultIterator.next();
                    textArea.appendText("第" + i + "步\n" + currentNode.toString());
                    i++;
                }
                //寻找成功
                System.out.println("success!");
                return;
            }
            subNodes = decidedNode.calculateSubNodes();
            decidedNode.setSubNodes(subNodes);
            nodeListIterator = subNodes.listIterator();
            while (nodeListIterator.hasNext())
            {
                node = nodeListIterator.next();
                if (open.contains(node))
                {
                    Node temp = open.get(open.indexOf(node));
                    if (isAStar) {
                        if (getScore(isAStar, temp) > node.getScoreOfAStar() + decidedNode.getLevel()) {
                            temp.setFormerNode(decidedNode);
                            temp.setLevel(decidedNode.getLevel() + 1);
                        }
                    }
                    else
                    {
                        if (getScore(isAStar,temp)>node.getScoreOfA()) {
                            temp.setFormerNode(decidedNode);
                            temp.setLevel(decidedNode.getLevel()+1);
                        }
                    }
                }else if (closed.contains(node))
                {
                    Node temp = closed.get(closed.indexOf(node));
                    if (isAStar) {
                        if (getScore(isAStar, temp) > node.getScoreOfAStar() + decidedNode.getLevel() + 1) {
                            temp.setFormerNode(decidedNode);
                            setLevel(temp, decidedNode.getLevel() + 1);
                        }
                    }
                    else {
                        if (getScore(isAStar, temp) > node.getScoreOfA() + decidedNode.getLevel() + 1) {
                            temp.setFormerNode(decidedNode);
                            setLevel(temp, decidedNode.getLevel() + 1);
                        }
                    }
                }else
                {
                    open.add(0,node);
                }
            }
        }
        textArea.appendText("寻找失败");
    }


    public void setLevel(Node node, int level) {
        node.setLevel(level);
        ListIterator<Node> nodeListIterator = node.getSubNodes().listIterator();
        while (nodeListIterator.hasNext())
        {
            setLevel(nodeListIterator.next(),level+1);
        }
    }

    private int setScore(boolean isAStar, Node node) {
        int score;
        if (isAStar){
            score = node.scoreOfAStar()+node.getLevel();
            node.setScoreOfAStar(score);
        }else
        {
            score = node.scoreOfA() + node.getLevel();
//            score = node.getLevel();
            node.setScoreOfA(score);
        }
        return score;
    }

    private int getScore(boolean isAStar, Node node) {
        if (isAStar){
            return node.getScoreOfAStar();
        }else
        {
            return node.getScoreOfA();
        }
    }

    public static void main(String[] args)
    {
        Algorithm algorithm = new Algorithm();
        algorithm.search(new int[]{3,1,7,8,6,4,2,5,0},false,null);
//        algorithm.search(new int[]{7,0,4,1,3,6,5,8,2},true);
    }
}
