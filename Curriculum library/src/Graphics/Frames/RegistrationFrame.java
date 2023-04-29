package Graphics.Frames;

import dao.implementation.UserDaoImpl;
import entity.User;
import exception.DaoException;

import javax.swing.*;
import java.awt.*;
/**
 * Класс для регистрации пользователей
 *
 * @author Radyuk N.O.
 * @version 1.0 14.02.2023
 */
public class RegistrationFrame extends JFrame {
    /*Создаём объекта интерфейса UserDao для связи с базой данных(таблица User)*/
    private static UserDaoImpl userDao = UserDaoImpl.getInstance();
    /*Конструктор класса RegistrationFrame*/
    public RegistrationFrame(){
        /*Установка менеджера компоновки BoxLayout с ориентацией по горизонтали*/
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        /*Создание компонент*/
        JLabel nameJL = new JLabel("Имя ");
        JLabel surnameJL = new JLabel("Фамилия ");
        JLabel roleJL = new JLabel("Роль ");
        JLabel loginJL = new JLabel("Логин ");
        JLabel passwordJL = new JLabel("Пароль ");
        JLabel emailJL = new JLabel("Эл.Почта ");
        JTextField nameJTF = new JTextField(15);
        JTextField surnameJTF = new JTextField(15);
        JTextField loginJTF = new JTextField(15);
        JTextField passwordJTF = new JTextField(15);
        JTextField emailJTF = new JTextField(15);
        String[] roles = {"Рецензент","Утвердитель", "Автор"};
        JComboBox combobox = new JComboBox(roles);
        JButton backJB = new JButton("Назад");
        JButton regJB = new JButton("Зарегистрироваться");
        /*Создание панелей для расположения компонент*/
        JPanel nameJP = new JPanel(new FlowLayout());
        JPanel surnameJP = new JPanel(new FlowLayout());
        JPanel roleJP = new JPanel(new FlowLayout());
        JPanel loginJP = new JPanel(new FlowLayout());
        JPanel passwordJP = new JPanel(new FlowLayout());
        JPanel emailJP = new JPanel(new FlowLayout());
        JPanel buttonsJP = new JPanel(new FlowLayout());
        /*Добавление компонент, связанных с именем пользователя на соответствующую панель*/
        nameJP.add(nameJL);
        nameJP.add(nameJTF);
        /*Добавление компонент, связанных с фамилией пользователя на соответствующую панель*/
        surnameJP.add(surnameJL);
        surnameJP.add(surnameJTF);
        /*Добавление компонент, связанных с ролью пользователя на соответствующую панель*/
        roleJP.add(roleJL);
        roleJP.add(combobox);
        /*Добавление компонент, связанных с логином пользователя на соответствующую панель*/
        loginJP.add(loginJL);
        loginJP.add(loginJTF);
        /*Добавление компонент, связанных с паролем пользователя на соответствующую панель*/
        passwordJP.add(passwordJL);
        passwordJP.add(passwordJTF);
        /*Добавление компонент, связанных с электронной почтой пользователя на соответствующую панель*/
        emailJP.add(emailJL);
        emailJP.add(emailJTF);
        /*Добавление кнопок на соответствующую панель*/
        buttonsJP.add(backJB);
        buttonsJP.add(regJB);
        /*Добавление панелей на фрейм*/
        add(nameJP);
        add(surnameJP);
        add(roleJP);
        add(loginJP);
        add(passwordJP);
        add(emailJP);
        add(buttonsJP);
        /*Подписываем слушателя на кнопку "Назад"*/
        backJB.addActionListener(e->{
            this.dispose();
            new MenuFrame();
        });
        /*Подписываем слушателя на кнопку "Зарегистрироваться"*/
        regJB.addActionListener(e -> {
            //Создаём объект класса User
            User user = new User();
            //Устанавливаем имя пользователя в соответствии с введенными данными
            user.setName(nameJTF.getText());
            //Устанавливаем фамилию пользователя в соответствии с введенными данными
            user.setSurname(surnameJTF.getText());
            //Устанавливаем роль пользователя в соответствии с выбранными данными
            if(combobox.getSelectedItem() == "Рецензент"){
                user.setRole(0);
            } else if(combobox.getSelectedItem() == "Утвердитель") {
                user.setRole(1);
            } else if (combobox.getSelectedItem() == "Автор") {
                user.setRole(2);
            }
            //Устанавливаем логин пользователя в соответствии с введенными данными
            user.setLogin(loginJTF.getText());
            //Устанавливаем пароль пользователя в соответствии с введенными данными
            user.setPassword(passwordJTF.getText());
            //Устанавливаем электронную почту пользователя в соответствии с введенными данными
            user.setEmail(emailJTF.getText());
            /*Загружаем новый объект класса User в базу данных*/
            try {
                userDao.add(user);
            } catch (DaoException ex) {
                System.out.println(ex.getMessage());
                throw new RuntimeException(ex);
            }
            this.dispose();
            new MenuFrame();
        });

        setTitle("Registration");
        setSize(400,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
