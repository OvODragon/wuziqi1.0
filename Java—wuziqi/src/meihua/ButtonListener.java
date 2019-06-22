package meihua;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {
    MyPanel1 mp1;
    JComboBox jComboBox;
    public ButtonListener(MyPanel1 mp1){
        this.mp1=mp1;
    }
    public ButtonListener(MyPanel1 mp1,JComboBox jComboBox){
        this.mp1=mp1;
        this.jComboBox=jComboBox;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("开始游戏")){
            if(mp1.life) {
                JOptionPane.showMessageDialog(mp1, "请开始下棋");
                mp1.canPlay = true;
                mp1.addMouseListener(new PanelListener1(mp1));
                mp1.life=false;
            }else {
                JOptionPane.showMessageDialog(mp1,"您已开始游戏");
            }
        }
        else if(e.getActionCommand().equals("认输")){
            int result = JOptionPane.showConfirmDialog(mp1, "是否确认认输？");
            if(result==0){
                if(mp1.turn==1) {
                    JOptionPane.showMessageDialog(mp1, "黑方已经认输，游戏结束");
                    mp1.str="白方获胜！";
                }else {
                    JOptionPane.showMessageDialog(mp1,"白方已经认输，游戏结束");
                    mp1.str="黑方获胜！";
                }
                mp1.canPlay=false;
                mp1.repaint();
            }
        }
        else if(e.getActionCommand().equals("悔棋" )){
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
        else if(e.getActionCommand().equals("重新开始")){
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
        else if("人人对战".equals(jComboBox.getSelectedItem())){
            mp1.choosetype=1;
            mp1.canPlay=false;
            System.out.println("aaaaaa");
        }
        else if("人机对战".equals(jComboBox.getSelectedItem())){
            mp1.choosetype=2;
            mp1.canPlay=false;
            System.out.println("bbbbbb");
        }
    }
}