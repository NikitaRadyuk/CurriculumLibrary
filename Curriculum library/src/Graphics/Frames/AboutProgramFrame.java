package Graphics.Frames;

import Graphics.MyComponents.TitleLabel;

import javax.swing.*;
import java.awt.*;

public class AboutProgramFrame extends JFrame {
    public AboutProgramFrame(){
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));

        /*Создаём компоненты*/
        JLabel titleJL = new TitleLabel("Библиотека учебных программ",1,20);
        JPanel titleJP = new JPanel(new BorderLayout());
        titleJP.add(titleJL,BorderLayout.WEST);

        String line = new String("Программа позволяет:\n1. Искать нужные учебные программы.\n2. Добавлять учебные программы.\n3. Скачивать их.");

        JTextArea mainJTA = new JTextArea(line,6,10);
        mainJTA.setFont(new Font("Dialog",Font.PLAIN,16));
        mainJTA.setTabSize(10);
        mainJTA.setLineWrap(true);
        mainJTA.setEditable(false);

        JPanel bottomJP = new JPanel(new FlowLayout());
        JTextField versionJTF = new JTextField("Версия ver.1.0.0.2023");
        JButton exitJB = new JButton("Назад");

        exitJB.addActionListener(e->{
            this.dispose();
            new MenuFrame();
        });

        bottomJP.add(versionJTF);
        bottomJP.add(exitJB);

        add(titleJP);
        add(mainJTA);
        add(bottomJP);

        /*SETTING UP ABOUT AUTHOR FRAME*/
        setTitle("About program");
        setSize(400,200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
