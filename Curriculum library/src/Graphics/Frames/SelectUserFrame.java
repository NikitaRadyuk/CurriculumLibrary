package Graphics.Frames;

import dao.implementation.UserDaoImpl;
import entity.User;
import exception.DaoException;

import javax.swing.*;
import java.awt.*;

public class SelectUserFrame extends JFrame {
    private static UserDaoImpl userDao = UserDaoImpl.getInstance();

    SelectUserFrame(Container<User> container, String roleChoice) throws DaoException {
        //Добавить чтобы окно было главным, а все остальные окна были недействительны

        //добавлять значения в контейнер, они потом вернутся из метода Show

        // Работа с БД
        // Выбор юзвера
        // Если чел не выбран, вернётся
        setLayout(new BorderLayout());

        JLabel labelSetRole = new JLabel();
        JButton btnBack = new JButton("Назад");
        JButton btnChoose = new JButton("Выбрать");

        JPanel panelNorth = new JPanel();
        JPanel panelCenter = new JPanel();
        JPanel panelSouth = new JPanel(new FlowLayout());



        if (roleChoice.equals("Соавтор")){
            labelSetRole.setText("Выберите соавтора: ");
        }
        else if (roleChoice.equals("Рецензент")){
            labelSetRole.setText("Выберите рецензента: ");
        }
        else if (roleChoice.equals("Утвердитель")){
            labelSetRole.setText("Выберите утвердителя: ");
        }


        DefaultListModel<User> myList = new DefaultListModel<>();
        ListModel<User> UserList = userDao.findAllUsers();
        for (int i = 0; i < UserList.getSize(); i++) {
            myList.addElement(UserList.getElementAt(i));
        }
        JList<User> jList = new JList(myList);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(jList);
        jList.setLayoutOrientation(JList.VERTICAL);
        jList.setMinimumSize(new Dimension(1200,1000));

        panelNorth.add(labelSetRole);
        panelCenter.add(jList);
        panelSouth.add(btnBack);
        panelSouth.add(btnChoose);

        add(panelNorth,BorderLayout.NORTH);
        add(panelCenter,BorderLayout.CENTER);
        add(panelSouth,BorderLayout.SOUTH);

        btnBack.addActionListener(e->{
            this.dispose();
        });

        btnChoose.addActionListener(e->{
            User user = jList.getSelectedValue();

        });

        setTitle("SelectUserFrame");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400,175);
        System.out.println(getSize());
        setAlwaysOnTop(true);
        setResizable(false);
        setVisible(true);
    }


    public static User Show(String roleChoice) throws DaoException {
        Container<User> container = new Container<User>(null);
        new SelectUserFrame(container, roleChoice);
        return container.value;
    }

    private static class Container<T>{
        public T value;
        Container(T value){
            this.value = value;
        }
    }
}
