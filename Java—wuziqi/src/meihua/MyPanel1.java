package meihua;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MyPanel1 extends JPanel {
    Graphics g;
    //定义一个二维数组，里面的值为0,1,2.0表示没有棋子，1表示有黑棋，2表示有白棋
    int [][] isChess=new int[15][15];
    //定义一个集合，里面装的是棋子的位置，悔棋的时候有用
    ArrayList<ChessPosition> chessPositionArrayList=new ArrayList<>();
    int turn=1;//表示顺序，1位黑棋走，2为白棋走
    int choosetype=1;//选择的类型：1为默认人人对弈，2为人机对弈
    boolean canPlay=true;//能不能下棋的标志
    boolean life=true;//开始游戏这个按钮的生命，控制只能点一次。
    String str="黑方先行";
    int x=170;
    int y=20;
    int size=50;
    int hang=15;
    int lie=15;
    int[][] weightArray=new int[hang][lie];//定义一个二维数组，保存各个点的权值
    public static HashMap<String,Integer> map = new HashMap<>();//设置不同落子情况和相应权值的数组
    Image CHESSBOARD=new ImageIcon("pic/ChessBoard.jpg").getImage();
    Image BLACKCHESS= new ImageIcon("pic\\black.png").getImage();
    Image WHITECHESS= new ImageIcon("pic\\white.png").getImage();
    public MyPanel1() {
        this.setValue();
        Dimension dimension = new Dimension(550, 700);
        this.setPreferredSize(dimension);
    }
    public void setValue(){
        //被堵住
        map.put("01", 25);//眠1连
        map.put("02", 22);//眠1连
        map.put("001", 17);//眠1连
        map.put("002", 12);//眠1连
        map.put("0001", 17);//眠1连
        map.put("0002", 12);//眠1连

        map.put("0102",25);//眠1连，15
        map.put("0201",22);//眠1连，10
        map.put("0012",15);//眠1连，15
        map.put("0021",10);//眠1连，10
        map.put("01002",25);//眠1连，15
        map.put("02001",22);//眠1连，10
        map.put("00102",17);//眠1连，15
        map.put("00201",12);//眠1连，10
        map.put("00012",15);//眠1连，15
        map.put("00021",10);//眠1连，10

        map.put("01000",25);//活1连，15
        map.put("02000",22);//活1连，10
        map.put("00100",19);//活1连，15
        map.put("00200",14);//活1连，10
        map.put("00010",17);//活1连，15
        map.put("00020",12);//活1连，10
        map.put("00001",15);//活1连，15
        map.put("00002",10);//活1连，10

        //被墙堵住
        map.put("0101",65);//眠2连，40
        map.put("0202",60);//眠2连，30
        map.put("0110",80);//眠2连，40
        map.put("0220",76);//眠2连，30
        map.put("011",80);//眠2连，40
        map.put("022",76);//眠2连，30
        map.put("0011",65);//眠2连，40
        map.put("0022",60);//眠2连，30

        map.put("01012",65);//眠2连，40
        map.put("02021",60);//眠2连，30
        map.put("01102",80);//眠2连，40
        map.put("02201",76);//眠2连，30
        map.put("01120",80);//眠2连，40
        map.put("02210",76);//眠2连，30
        map.put("00112",65);//眠2连，40
        map.put("00221",60);//眠2连，30

        map.put("01100",80);//活2连，40
        map.put("02200",76);//活2连，30
        map.put("01010",75);//活2连，40
        map.put("02020",70);//活2连，30
        map.put("00110",75);//活2连，40
        map.put("00220",70);//活2连，30
        map.put("00011",75);//活2连，40
        map.put("00022",70);//活2连，30

        //被堵住
        map.put("0111",150);//眠3连，100
        map.put("0222",140);//眠3连，80

        map.put("01112",150);//眠3连，100
        map.put("02221",140);//眠3连，80

        map.put("01110", 1100);//活3连
        map.put("02220", 1050);//活3连
        map.put("01101",1000);//活3连，130
        map.put("02202",800);//活3连，110
        map.put("01011",1000);//活3连，130
        map.put("02022",800);//活3连，110

        map.put("01111",3000);//4连，300
        map.put("02222",3500);//4连，280
    }
    public void paint(Graphics g){
        super.paint(g);
        g.drawImage(CHESSBOARD, 0, 0,this.getWidth(), this.getHeight(), this);
        g.setFont(new Font("宋体",Font.BOLD,20));
        g.setColor(Color.red);
        g.drawString("游戏信息："+str,400,750);
        //画棋盘
        g.setColor(Color.black);
        for(int i=0;i<hang;i++){
            g.drawLine(x,y+size*i,x+(lie-1)*size,y+size*i);
        }
        for(int i=0;i<lie;i++){
            g.drawLine(x+size*i,y,x+size*i,y+size*(hang-1));
        }
        //画棋子
        for(int i=0;i<hang;i++){
            for(int j=0;j<lie;j++){
                if(this.isChess[i][j]==1){
                    int tempx=i*size+x;
                    int tempy=j*size+y;
                    g.drawImage(BLACKCHESS,tempx-size/2, tempy-size/2, size, size,null);
                }
                if(this.isChess[i][j]==2){
                    int tempx=i*size+x;
                    int tempy=j*size+y;
                    g.drawImage(WHITECHESS,tempx-size/2, tempy-size/2, size, size,null);
                }
            }
        }
    }
}
