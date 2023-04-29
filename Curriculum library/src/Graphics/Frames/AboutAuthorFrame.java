package Graphics.Frames;

import Graphics.MyComponents.TitleLabel;

import javax.swing.*;
import java.awt.*;
/**
 * Класс для предоставлении информации об авторе курсовой работы
 *
 * @author Radyuk N.O.
 * @version 1.0 14.02.2023
 */
public class AboutAuthorFrame extends JFrame {
    /*Конструктор класса AboutAuthorFrame*/
    public AboutAuthorFrame(){
        /*Установка менеджера компоновки BoxLayout*/
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));

        /*Создание компонент*/
        ImageIcon authorImage = new ImageIcon("resources/images/Author.jpg");
        JLabel lblAuthor = new TitleLabel("Автор",1,20);
        JLabel lblStudent = new TitleLabel("Студент группы 10702320",1,20);
        JLabel lblInitials = new TitleLabel("Радюк Никита Олегович",1,20);
        JLabel lblGmail = new TitleLabel("radyuknikita02@gmail.com",1,20);
        JButton btnExit = new JButton("Назад");
        JLabel lblImage = new JLabel(new ImageIcon(authorImage.getImage().getScaledInstance(325,490,Image.SCALE_SMOOTH)));
        /*Расположение компонент посередине*/
        lblImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblAuthor.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblStudent.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblInitials.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblGmail.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnExit.setAlignmentX(Component.CENTER_ALIGNMENT);

        /*Подписываем слушателя на кнопку "Назад"*/
        btnExit.addActionListener(e->{
            this.dispose();
            new MenuFrame();
        });
        /*Добавляем элементы на фрейм*/
        add(lblImage);
        add(lblAuthor);
        add(lblStudent);
        add(lblInitials);
        add(lblGmail);
        add(btnExit);

        /*Задаем название фрейма*/
        setTitle("Информация об авторе");
        //Задаем размеры фрейма
        setSize(375,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
