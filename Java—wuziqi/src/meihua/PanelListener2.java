package meihua;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PanelListener2 implements MouseListener {
    MyPanel1 mp1;
    public PanelListener2(MyPanel1 mp1){
        this.mp1=mp1;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if(mp1.canPlay==false){
            if(e.getX() > 170 && e.getX() < 870 && e.getY() > 20 && e.getY() < 720) {
                if (mp1.life == true) {
                    JOptionPane.showMessageDialog(mp1, "请先开始游戏！");
                } else {
                    JOptionPane.showMessageDialog(mp1, "请重新开始游戏！");
                }
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
}
