package meihua;

import javax.swing.*;
import java.awt.*;

public class MyPanel2 extends JPanel {
    MyPanel1 mp1;
    JButton[] jButtons;
    public  MyPanel2(MyPanel1 mp1){
        this.mp1=mp1;
        this.setLayout(new FlowLayout());
        Dimension dimension1=new Dimension(150,0);//设置右边Panel的大小
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
        JComboBox<String> jComboBox=new JComboBox<>();
        jComboBox.addItem("请选择游戏模式");
        jComboBox.addItem("人人对战");
        jComboBox.addItem("人机对战");
        jComboBox.setPreferredSize(dimension2);
        jComboBox.addActionListener(new ButtonListener(mp1,jComboBox));
        this.add(jComboBox);
    }
}
