package Graphics.Frames;

import Services.LoginService;
import Services.ServiceException;
import entity.User;
import exception.DaoException;

import javax.swing.*;
import java.awt.*;

public class MenuFrame extends JFrame {
public MenuFrame(){
    setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));

    /*CREATING COMPONENTS*/
    JLabel labelLogin = new JLabel("Логин");
    JTextField txtFieldLogin = new JTextField(10);
    JLabel labelPassword = new JLabel("Пароль");
    JPasswordField passFieldpassword = new JPasswordField(10);
    JLabel labelDoesntReg = new JLabel("Не зарегистрированы?");
    JButton btnReg = new JButton("Зарегистрироваться");
    JButton btnLogin = new JButton("Войти");
    JLabel labelVoid = new JLabel("");
    JButton btnAboutAuthor = new JButton("Об авторе");
    JButton btnAboutProgram = new JButton("О программе");
    JButton btnExit = new JButton("Выход");

    btnAboutAuthor.setSize(100,400);
    btnAboutAuthor.setAlignmentX(Component.CENTER_ALIGNMENT);
    btnAboutProgram.setSize(100,400);
    btnAboutProgram.setAlignmentX(Component.CENTER_ALIGNMENT);
    btnLogin.setSize(100,400);
    btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
    btnExit.setSize(100,400);
    btnExit.setAlignmentX(Component.CENTER_ALIGNMENT);

    JPanel panelEnter = new JPanel(new GridLayout(7,1,0,0));

    panelEnter.setBorder(BorderFactory.createTitledBorder("Войти"));

    panelEnter.add(labelLogin);
    panelEnter.add(txtFieldLogin);
    panelEnter.add(labelPassword);
    panelEnter.add(passFieldpassword);
    panelEnter.add(btnLogin);
    panelEnter.add(labelDoesntReg);
    panelEnter.add(btnReg);

    JPanel panelMenuButtons = new JPanel(new FlowLayout());

    JPanel panelInfos = new JPanel(new GridLayout(1,2,0,0));
    JPanel panelExit = new JPanel(new GridLayout(1,2,0,0));

    panelInfos.add(btnAboutProgram,0);
    panelInfos.add(btnAboutAuthor,1);

    panelExit.add(labelVoid,0);
    panelExit.add(btnExit,1);

    panelMenuButtons.add(panelInfos);
    panelMenuButtons.add(panelExit);

    add(panelEnter);
    add(panelMenuButtons);

    /*GETTING ACTIONS TO BUTTONS*/
    btnAboutAuthor.addActionListener(e->{
        this.dispose();
        new AboutAuthorFrame();
    });

    btnAboutProgram.addActionListener(e->{
        this.dispose();
        new AboutProgramFrame();
    });

    btnExit.addActionListener(e->{
        this.dispose();
    });

    btnReg.addActionListener(e->{
        this.dispose();
        new RegistrationFrame();
    });
    btnLogin.addActionListener(e->{
        this.dispose();
        try {
            LoginService loginService = LoginService.getInstance();
            User user = loginService.login(txtFieldLogin.getText(), String.valueOf(passFieldpassword.getPassword()));
            System.out.println(user.getRole());
            System.out.println(user.getName() + " " + user.getSurname());
            new ExecutableFrame(user);
        } catch (ServiceException ex) {
            System.out.println(ex);
            throw new RuntimeException(ex);
        } catch (DaoException ex) {
            throw new RuntimeException(ex);
        }
    });

    /*SETTING UP MENU FRAME*/
    setTitle("Main Frame");
    pack();
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
}
}
