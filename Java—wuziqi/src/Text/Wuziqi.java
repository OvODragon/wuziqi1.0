package Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class Wuziqi extends JFrame {
        MyPanel1 mp1;
        MyPanel2 mp2;
        //得到屏幕的长宽
        int sceenwidth=(int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int sceenheight=(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    public Wuziqi(){
        //把界面放在屏幕中间
        this.setBounds((sceenwidth-800)/2,(sceenheight-700)/2,800,700);
        this.setLayout(new BorderLayout());
        this.setTitle("五子棋-单机版");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        mp1=new MyPanel1();
        this.add(mp1,BorderLayout.CENTER);
        mp2=new MyPanel2(mp1);
        this.add(mp2,BorderLayout.EAST);
    }

    public static void main(String[] args) {
        Wuziqi wuziqi=new Wuziqi();
    }
}
class MyPanel1 extends JPanel{
    //定义一个二维数组，里面的值为0,1,2.0表示没有棋子，1表示有黑棋，2表示有白棋
    int [][] isChess=new int[15][15];
    //定义一个集合，里面装的是棋子的位置，悔棋的时候有用
    ArrayList<ChessPosition> chessPositionArrayList=new ArrayList<>();
    int turn=1;//表示顺序，1位黑棋走，2为白棋走
    boolean canPlay=true;//能不能下棋的标志
    String str="黑方先行";
    int x=20;
    int y=20;
    int size=40;
    int hang=15;
    int lie=15;
    public MyPanel1(){
        this.setBackground(Color.lightGray);
        Dimension dimension=new Dimension(550,700);
        this.setPreferredSize(dimension);
    }
    public void paint(Graphics g){
        super.paint(g);
        //画游戏提示信息
        g.setFont(new Font("宋体",Font.BOLD,40));
        g.setColor(Color.black);
        g.drawString("游戏信息："+str,100,640);
        //画棋盘
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
                    g.setColor(Color.black);
                    g.fillOval(tempx-size/2,tempy-size/2,size,size);
                }
                if(this.isChess[i][j]==2){
                    int tempx=i*size+x;
                    int tempy=j*size+y;
                    g.setColor(Color.white);
                    g.fillOval(tempx-size/2,tempy-size/2,size,size);
                }
            }
        }
    }
}
class MyPanel2 extends JPanel{
    MyPanel1 mp1;
    JButton[] jButtons;
    public  MyPanel2(MyPanel1 mp1){
        this.mp1=mp1;
        this.setLayout(new FlowLayout());
        Dimension dimension1=new Dimension(150,700);//设置右边Panel的大小
        Dimension dimension2=new Dimension(140,40);//设置里面组件的大小
        this.setPreferredSize(dimension1);
        jButtons=new JButton[4];
        String[] jbnames={"开始游戏","悔棋","认输","重新开始"};
        for(int i=0;i<jButtons.length;i++) {
            jButtons[i] = new JButton();
            jButtons[i].addActionListener(new ButtonListener(mp1));
            jButtons[i].setText(jbnames[i]);
            jButtons[i].setPreferredSize(dimension2);
            this.add(jButtons[i]);
        }
    }
}
class ButtonListener implements ActionListener{
    MyPanel1 mp1;
    public ButtonListener(MyPanel1 mp1){
        this.mp1=mp1;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("开始游戏")){
            JOptionPane.showMessageDialog(mp1,"请开始下棋");
           PanelListener p=new PanelListener(mp1);
           mp1.addMouseListener(p);
        }
        if(e.getActionCommand().equals("认输")) {
            int result = JOptionPane.showConfirmDialog(mp1, "是否确认认输？");
            if (result == 0) {
                if (mp1.turn == 1) {
                    JOptionPane.showMessageDialog(mp1, "黑方已经认输，游戏结束");
                } else {
                    JOptionPane.showMessageDialog(mp1, "白方已经认输，游戏结束");
                }
                mp1.canPlay = false;
            }
        }
        if(e.getActionCommand().equals("重新开始")){
            int result = JOptionPane.showConfirmDialog(mp1, "是否重新开始游戏？");
            if(result==0){
                for(int i=0;i<mp1.hang;i++){
                    for(int j=0;j<mp1.lie;j++){
                        mp1.isChess[i][j]=0;
                    }
                }
                mp1.turn=1;
                mp1.str="黑方先行";
                //可能已经有胜负手了
                mp1.canPlay=true;
                mp1.repaint();
            }
        }
        if(e.getActionCommand().equals("悔棋" )){
            if(mp1.chessPositionArrayList.size()>1){
                ChessPosition l=mp1.chessPositionArrayList.remove(mp1.chessPositionArrayList.size()-1);
                mp1.isChess[l.Listi][l.Listj]=0;
                if(mp1.turn==1){
                    mp1.turn++;
                }else {
                    mp1.turn--;
                }
                mp1.repaint();
            }else {
                JOptionPane.showMessageDialog(mp1,"不能悔棋");
            }
        }
    }
}
class PanelListener implements MouseListener{
    MyPanel1 mp1;
    int colu;
    int ro;
    int x;
    int y;
    public PanelListener(MyPanel1 mp1){
        this.mp1=mp1;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (mp1.canPlay) {
            System.out.println("此时鼠标在的x为：" + e.getX() + "  此时鼠标在的y为：" + e.getY());
            Graphics g = mp1.getGraphics();
            int x = e.getX();
            int y = e.getY();
            //计算棋子要落在棋盘的那个交叉点
            int countx = (x / 40) * 40 + 20;
            int county = (y / 40) * 40 + 20;
            //计算棋盘上棋子在数组的位置
            colu = (countx - 20) / 40;
            ro = (county - 20) / 40;
            //第一层if，是防止鼠标点击棋盘外会出现下标越界异常
            if (x > 20 && x < 580 && y > 20 && y < 580) {
                if (mp1.isChess[colu][ro] != 0) {
                    JOptionPane.showMessageDialog(mp1, "此处已经有棋子了，请重新落子！");
                } else {
                    if (mp1.turn == 1) {
                        mp1.isChess[colu][ro] = 1;
                        mp1.chessPositionArrayList.add(new ChessPosition(colu,ro));
                        mp1.turn++;
                        mp1.str = "轮到白方";
                        if (this.checkWin()) {
                            JOptionPane.showMessageDialog(mp1, "游戏结束，黑方获胜！");
                            mp1.str="黑方获胜！";
                        }
                    } else {
                        mp1.isChess[colu][ro] = 2;
                        mp1.chessPositionArrayList.add(new ChessPosition(colu,ro));
                        mp1.turn--;
                        mp1.str = "轮到黑方";
                        if (this.checkWin()) {
                            JOptionPane.showMessageDialog(mp1, "游戏结束，白方获胜！");
                            mp1.str="白方获胜！";
                        }
                    }
                }
                mp1.repaint();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    public boolean checkWin(){
        boolean flag=false;
        int color=mp1.isChess[colu][ro];

        //进行判断时首先防止下标越界！
        //行判断
        //先判断右边
        int count1=1;
        int i=1;
        while(colu+i<15&&color==mp1.isChess[colu+i][ro]){
            count1++;
            i++;
        }
        //再判断左边
        i=1;
        while(colu-i>=0&&color==mp1.isChess[colu-i][ro]){
            count1++;
            i++;
        }
        if(count1>=5){
            flag=true;
            mp1.canPlay=false;
            return flag;
        }

        //列判断
        int count2=1;
        //先判断上面
        i=1;
        while(ro+i<15&&color==mp1.isChess[colu][ro+i]){
            count2++;
            i++;
        }
        //再判断下面
        i=1;
        while(ro-i>=0&&color==mp1.isChess[colu][ro-i]){
            count2++;
            i++;
        }
        if(count2>=5){
            flag=true;
            mp1.canPlay=false;
            return flag;
        }

        //135度判断，同加同减
        int count3=1;
        //先判断左上方
        i=1;
        while(colu-i>=0&&ro-i>=0&&color==mp1.isChess[colu-i][ro-i]){
            count3++;
            i++;
        }
        //再判断左下方
        i=1;
        while(colu+i<15&&ro+i<15&&color==mp1.isChess[colu+i][ro+i]){
            count3++;
            i++;
        }
        if(count3>=5){
            flag=true;
            mp1.canPlay=false;
            return flag;
        }

        //45度判断，此增彼减
        int count4=1;
        //先判断右上方
        i=1;
        while(colu-i>=0&&ro+i<15&&color==mp1.isChess[colu-i][ro+i]){
            count4++;
            i++;
        }
        //再判断右下方
        i=1;
        while(colu+i<15&&ro-i>=0&&color==mp1.isChess[colu+i][ro-i]){
            count4++;
            i++;
        }
        if(count4>=5){
            flag=true;
            mp1.canPlay=false;
            return flag;
        }

        return flag;
    }
}
class ChessPosition {
    public int Listi, Listj;
    public ChessPosition(int Listi, int Listj) {
        this.Listi = Listi;
        this.Listj = Listj;
    }
}