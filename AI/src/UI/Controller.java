package UI;

import AI.Algorithm;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;
// 3 1 7 8 6 4 2 5 0
public class Controller {
    @FXML
    public TextField num1;
    @FXML
    public TextField num2;
    @FXML
    public TextField num3;
    @FXML
    public TextField num4;
    @FXML
    public TextField num5;
    @FXML
    public TextField num6;
    @FXML
    public TextField num7;
    @FXML
    public TextField num8;
    @FXML
    public TextField num9;
    @FXML
    public RadioButton AStarAlgorithmBox;
    @FXML
    public RadioButton AAlgorithmBox;
    @FXML
    public TextArea output;
    @FXML
    public Button randomButton;
    @FXML
    public Button runButton;
    @FXML
    public TextArea output1;
    @FXML
    public Button aboutButton;

    public LinkedList<TextField> nums;

    void init()
    {
        nums = new LinkedList<>();
        nums.add(num1);
        nums.add(num2);
        nums.add(num3);
        nums.add(num4);
        nums.add(num5);
        nums.add(num6);
        nums.add(num7);
        nums.add(num8);
        nums.add(num9);
        randomButton.setOnMouseClicked(event -> {
            int[] seq = initStart();
            while(reverse(seq) % 2 == 1||finish(seq)){
                seq = initStart();
            }
            setNums(seq);
        });
        runButton.setOnMouseClicked(event -> {
            search();
        });
        aboutButton.setOnMouseClicked(event -> {
            Alert information = new Alert(Alert.AlertType.INFORMATION,"Author:2016302580229\nemail:807587186@qq.com\n@WHU Ice Bear\n©copyright WHU Ice Bear");
            information.setTitle("About");         //设置标题，不设置默认标题为本地语言的information
            information.setHeaderText("About");    //设置头标题，默认标题为本地语言的information
            information.showAndWait();
        });
        output.appendText("欢迎使用！\n" +
                "使用方法：\n" +
                "可在九个方框里输入初态（0~8）\n" +
                "或者点击Random生成初态\n" +
                "然后点击Run\n" +
                "选择不同算法比较结果\n" +
                "提示：\n" +
                "A算法可能非常慢 \n" +
                "请等待一会儿！");
    }

    //逆序数
    int reverse(int arrange[]){
        int reverse = 0;
        for(int i = 1; i < 9; i++){
            if(arrange[i] != 0){
                for(int j = 0; j < i; j++){
                    //arrange[j]表示前面的数
                    if(arrange[j] > arrange[i]) reverse++;
                }
            }

        }
        return reverse;
    }

    void search()
    {
        int[] seq;
        //逆序数为偶数才能有解，随机的排列不可以是结束状态
        seq = getNums();
        if (finish(seq))
        {
            Alert information = new Alert(Alert.AlertType.INFORMATION,"你的输入已经是最后的解");
            information.setTitle("Tip");         //设置标题，不设置默认标题为本地语言的information
            information.setHeaderText("Tip");    //设置头标题，默认标题为本地语言的information
            information.showAndWait();
            return;
        }
        if (reverse(seq) % 2 == 1)
        {
            Alert information = new Alert(Alert.AlertType.INFORMATION,"你的输入可能无解，将为您生成一个初始状态");
            information.setTitle("Tip");         //设置标题，不设置默认标题为本地语言的information
            information.setHeaderText("Tip");    //设置头标题，默认标题为本地语言的information
            information.showAndWait();
        }
        while(reverse(seq) % 2 == 1){
            seq = initStart();
        }
        Algorithm algorithm = new Algorithm();
        if (AAlgorithmBox.isSelected())
        {
            output.clear();
            algorithm.search(seq,false,output);
        }
        if (AStarAlgorithmBox.isSelected())
        {
            output1.clear();
            algorithm.search(seq,true,output1);
        }
    }


    private int[] initStart(){
        //初始化ArrayList
        int arrayRange = 9;
        int[] currentStatus = new int[9];
        ArrayList<Integer> array = new ArrayList<Integer>();
        for(int i = 0; i < 10; i++)
            array.add(i);
        Random random = new Random();
        for(int i = 0; i < 9; i++){
            int singleNumIndex = random.nextInt(arrayRange--);
            currentStatus[i] = array.get(singleNumIndex);
            array.remove(singleNumIndex);
        }
        return currentStatus;
    }

    int[] getNums()
    {
        int[] visited = new int[9];
        int[] result = new int[9];
        try
        {
            ListIterator<TextField> textFieldListIterator = nums.listIterator();
            TextField textField;
            int num;
            int i = 0;
            while (textFieldListIterator.hasNext())
            {
                textField = textFieldListIterator.next();
                num = Integer.parseInt(textField.getText());
                if (num<0||num>8)
                    throw new Exception();
                if (visited[num]==0)
                {
                    visited[num]=1;
                    result[i] = num;
                }else
                {
                    throw new Exception();
                }
                i++;
            }
            return result;
        }catch (Exception e)
        {
            Alert information = new Alert(Alert.AlertType.ERROR,"请输入0~8的整数,并且不能重复!");
            information.setTitle("Error");         //设置标题，不设置默认标题为本地语言的information
            information.setHeaderText("Error");    //设置头标题，默认标题为本地语言的information
            information.showAndWait();
        }
        return null;
    }


    void setNums(int[] seq)
    {
        int[] visited = new int[9];
        int[] result = new int[9];
        try
        {
            ListIterator<TextField> textFieldListIterator = nums.listIterator();
            TextField textField;
            int i = 0;
            while (textFieldListIterator.hasNext())
            {
                textField = textFieldListIterator.next();
                textField.setText(seq[i]+"");
                i++;
            }

        }catch (Exception e)
        {
            Alert information = new Alert(Alert.AlertType.ERROR,"请输入0~8的整数,并且不能重复!");
            information.setTitle("Error");         //设置标题，不设置默认标题为本地语言的information
            information.setHeaderText("Error");    //设置头标题，默认标题为本地语言的information
            information.showAndWait();
        }
    }

    public boolean finish(int[]seq)
    {
        for(int i = 0; i<8; i++)
        {
            if (seq[i]!=i+1)
            {
                return false;
            }
        }
        return true;
    }

}
