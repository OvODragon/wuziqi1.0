package meihua;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PanelListener1 implements MouseListener {
    Image WHITECHESS= new ImageIcon("pic\\white.png").getImage();
    MyPanel1 mp1;
    int colu;
    int ro;
    int x;
    int y;
    public PanelListener1(MyPanel1 mp1){
        this.mp1=mp1;
    }

    //AI联合算法
    public Integer unionWeight(Integer a,Integer b ) {
        //必须要先判断a,b两个数值是不是null
        if((a==null)||(b==null)) return 0;
            //一一:101/202
        else if(a==b&&(b>=22)&&(b<=25)) return 60;
        else if(a!=b&&(a>=22)&&(a<=25)&&(b>=22)&&(b<=25)) return 10;
            //一二、二一:1011/2022
        else if(((a>=22)&&(a<=25)&&(b>=76)&&(b<=80))||((a>=76)&&(a<=80)&&(b>=22)&&(b<=25))) return 800;
            //一三、三一、二二:10111/20222
        else if(((a>=10)&&(a<=25)&&(b>=1050)&&(b<=1100))||((a>=1050)&&(a<=1100)&&(b>=10)&&(b<=25))||((a>=76)&&(a<=80)&&(b>=76)&&(b<=80)))
            return 3000;
            //眠三连和眠一连。一三、三一
        else if(((a>=22)&&(a<=25)&&(b>=140)&&(b<=150))||((a>=140)&&(a<=150)&&(b>=22)&&(b<=25))) return 3000;
            //二三、三二:110111
        else if(((a>=76)&&(a<=80)&&(b>=1050)&&(b<=1100))||((a>=1050)&&(a<=1100)&&(b>=76)&&(b<=80))) return 3000;
        else return 0;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (mp1.canPlay) {
            System.out.println("此时鼠标在的x为：" + e.getX() + "  此时鼠标在的y为：" + e.getY());
            int x = e.getX();
            int y = e.getY();
            //计算棋子要落在棋盘的那个交叉点
            int countx = ((x-150) / 50) * 50 + 25;
            int county = (y / 50) * 50 + 25;
            //计算棋盘上棋子在数组的位置
            colu = (countx - 25) / 50;
            ro = (county - 25) / 50;
            //第一层if，是防止鼠标点击棋盘外会出现下标越界异常
            if (x > 170 && x < 870 && y > 20 && y < 720) {
                if (mp1.isChess[colu][ro] != 0) {
                    JOptionPane.showMessageDialog(mp1, "此处已经有棋子了，请重新落子！");
                } else {
                    //人人对战
                    if (mp1.choosetype == 1) {
                        if (mp1.turn == 1) {
                            mp1.isChess[colu][ro] = 1;
                            mp1.chessPositionArrayList.add(new ChessPosition(colu, ro));
                            if (this.checkWin()) {
                                JOptionPane.showMessageDialog(mp1, "游戏结束，黑方获胜！");
                                mp1.str = "黑方获胜！";
                            } else {
                                mp1.turn++;
                                mp1.str = "轮到白方";
                            }

                        } else {
                            mp1.isChess[colu][ro] = 2;
                            mp1.chessPositionArrayList.add(new ChessPosition(colu, ro));
                            if (this.checkWin()) {
                                JOptionPane.showMessageDialog(mp1, "游戏结束，白方获胜！");
                                mp1.str = "白方获胜！";
                            }else {
                                mp1.turn--;
                                mp1.str = "轮到黑方";
                            }

                        }
                        //人机对战
                    } else if(mp1.choosetype==2){
                        if(mp1.turn==1){
                            mp1.isChess[colu][ro] = 1;
                            mp1.chessPositionArrayList.add(new ChessPosition(colu, ro));
                            if (this.checkWin()) {
                                JOptionPane.showMessageDialog(mp1, "游戏结束，人类获胜！");
                                mp1.str = "人类获胜！";
                            }else {
                                mp1.turn++;
                                mp1.str = "轮到白方";
                            }
                        }
                        //不能用else！！！
                        if(mp1.turn==2){
                            //电脑落子
                            //先计算各个位置的权值
                            for(int i=0;i<mp1.hang;i++){
                                for(int j=0;j<mp1.lie;j++){
                                    //首先判断当前位置是否为空
                                    if(mp1.isChess[i][j]==0) {
                                        //往左延伸
                                        String ConnectType="0";
                                        int jmin=Math.max(0, j-4);
                                        for(int positionj=j-1;positionj>=jmin;positionj--) {
                                            //依次加上前面的棋子
                                            ConnectType=ConnectType+mp1.isChess[i][positionj];
                                        }
                                        //从数组中取出相应的权值，加到权值数组的当前位置中
                                        Integer valueleft=mp1.map.get(ConnectType);
                                        //System.out.println(valueleft);
                                        if(valueleft!=null) mp1.weightArray[i][j]+=valueleft;

                                        //往右延伸
                                        ConnectType="0";
                                        int jmax=Math.min(14, j+4);
                                        for(int positionj=j+1;positionj<=jmax;positionj++) {
                                            //依次加上前面的棋子
                                            ConnectType=ConnectType+mp1.isChess[i][positionj];
                                        }
                                        //从数组中取出相应的权值，加到权值数组的当前位置中
                                        Integer valueright=mp1.map.get(ConnectType);
                                        if(valueright!=null) mp1.weightArray[i][j]+=valueright;

                                        //联合判断，判断行
                                        mp1.weightArray[i][j]+=unionWeight(valueleft,valueright);

                                        //往上延伸
                                        ConnectType="0";
                                        int imin=Math.max(0, i-4);
                                        for(int positioni=i-1;positioni>=imin;positioni--) {
                                            //依次加上前面的棋子
                                            ConnectType=ConnectType+mp1.isChess[positioni][j];
                                        }
                                        //从数组中取出相应的权值，加到权值数组的当前位置中
                                        Integer valueup=mp1.map.get(ConnectType);
                                        if(valueup!=null) mp1.weightArray[i][j]+=valueup;

                                        //往下延伸
                                        ConnectType="0";
                                        int imax=Math.min(14, i+4);
                                        for(int positioni=i+1;positioni<=imax;positioni++) {
                                            //依次加上前面的棋子
                                            ConnectType=ConnectType+mp1.isChess[positioni][j];
                                        }
                                        //从数组中取出相应的权值，加到权值数组的当前位置中
                                        Integer valuedown=mp1.map.get(ConnectType);
                                        if(valuedown!=null) mp1.weightArray[i][j]+=valuedown;

                                        //联合判断，判断列
                                        mp1.weightArray[i][j]+=unionWeight(valueup,valuedown);

                                        //向左上方延伸，ij同减
                                        ConnectType="0";
                                        for(int position=-1;position>=-4;position--) {
                                            if(i+position>=0&&i+position<=14&&j+position>=0&&j+position<=14)
                                                ConnectType=ConnectType+mp1.isChess[i+position][j+position];
                                        }
                                        //从数组中取出相应的权值，加到权值数组的当前位置中
                                        Integer valueleftup=mp1.map.get(ConnectType);
                                        if(valueleftup!=null) mp1.weightArray[i][j]+=valueleftup;

                                        //向右下方延伸，ij同加
                                        ConnectType="0";
                                        for(int position=1;position<=4;position++){
                                            if(i+position>=0&&i+position<=14&&j+position>=0&&j+position<=14)
                                                ConnectType=ConnectType+mp1.isChess[i+position][j+position];
                                        }
                                        //从数组中取出相应的权值，加到权值数组的当前位置中
                                        Integer valuerightdown=mp1.map.get(ConnectType);
                                        if(valuerightdown!=null) mp1.weightArray[i][j]+=valuerightdown;

                                        //联合判断，判断行
                                        mp1.weightArray[i][j]+=unionWeight(valueleft,valueright);
                                    }
                                    if(mp1.weightArray[i][j]!=0)
                                    System.out.println(mp1.weightArray[i][j]+"  i="+i+"    j="+j);
                                }
                            }

                            //取出最大的权值
                            int AIi=0,AIj=0;
                            int weightmax=0;
                            for(int i=0;i<mp1.hang;i++) {
                                for(int j=0;j<mp1.lie;j++) {
                                    if(weightmax<mp1.weightArray[i][j]) {
                                        weightmax=mp1.weightArray[i][j];
                                        AIi=i;
                                        AIj=j;
                                    }
                                }
                            }
                            System.out.println("max="+weightmax);
                            System.out.println("AIi="+AIi+"   AIj="+AIj);
                            //确定位置，落子
                            //设置当前位置已经有棋子了，棋子为白子
                            mp1.isChess[AIi][AIj]=2;
                            mp1.chessPositionArrayList.add(new ChessPosition(AIi,AIj));
                            if(this.checkWin()){
                                JOptionPane.showMessageDialog(mp1, "游戏结束，电脑获胜！");
                                mp1.str = "电脑获胜！";
                            }else {
                                mp1.turn--;
                                mp1.str = "轮到黑方";
                            }
                            //落子以后重置权值数组weightArray
                            for(int i=0;i<mp1.hang;i++)
                                for(int j=0;j<mp1.lie;j++)
                                    mp1.weightArray[i][j]=0;

                        }
                    }
                }
            }
            mp1.repaint();
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

