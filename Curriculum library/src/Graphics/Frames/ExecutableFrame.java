package Graphics.Frames;

import dao.implementation.CurriculumDaoImpl;
import entity.Curriculum;
import entity.User;
import exception.DaoException;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.util.Objects;

public class ExecutableFrame extends JFrame {
    DefaultListModel<Curriculum> myList = new DefaultListModel<>();
    File file;
    private static CurriculumDaoImpl curriculumDao = new CurriculumDaoImpl().getInstance();
    public ExecutableFrame(User user) throws DaoException {

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);

        JPanel panel1 = new JPanel(new BorderLayout());

        JLabel titleJL = new JLabel("Название");
        JTextField titleSearchJTF = new JTextField(15);
        JLabel authorJL = new JLabel("Автор");
        JTextField authorSearchJTF = new JTextField(15);
        JButton backJB = new JButton("Назад");
        JButton searchJB = new JButton("Поиск");
        JButton resetJB = new JButton("Сброс");
        JButton moreJB = new JButton("Подробная информация");
        JButton deleteJB = new JButton("Удалить");
        JButton exitJB = new JButton("Выход");

        /*JList + JScrollPane*/
        ListModel<Curriculum> curriculumList = curriculumDao.findAllCurriculums();
        for (int i = 0; i < curriculumList.getSize(); i++) {
            myList.addElement(curriculumList.getElementAt(i));
        }
        JList<Curriculum> jList = new JList(myList);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(jList);
        jList.setLayoutOrientation(JList.VERTICAL);
        jList.setMinimumSize(new Dimension(1200,1000));

        JPanel searchFieldsJP = new JPanel(new FlowLayout());
        JPanel authorSearchFieldJP = new JPanel(new FlowLayout());
        JPanel titleSearchFieldJP = new JPanel(new FlowLayout());

        JPanel buttonsJP = new JPanel(new FlowLayout());
        buttonsJP.add(backJB);
        buttonsJP.add(moreJB);
        buttonsJP.add(exitJB);

        JPanel scrollListJP = new JPanel();
        scrollListJP.add(scrollPane);

        searchFieldsJP.add(titleSearchFieldJP);
        searchFieldsJP.add(authorSearchFieldJP);
        searchFieldsJP.add(searchJB);
        searchFieldsJP.add(resetJB);

        titleSearchFieldJP.add(titleJL);
        titleSearchFieldJP.add(titleSearchJTF);

        authorSearchFieldJP.add(authorJL);
        authorSearchFieldJP.add(authorSearchJTF);

        panel1.add(searchFieldsJP, BorderLayout.NORTH);
        panel1.add(scrollListJP, BorderLayout.CENTER);
        panel1.add(buttonsJP, BorderLayout.SOUTH);

        tabbedPane.add("Поиск", panel1);
        if (user.getRole().ordinal() == 2) {
            tabbedPane.add("Добавить", createAuthorPage(user));
            buttonsJP.add(deleteJB);
        }

        add(tabbedPane);

        backJB.addActionListener(e ->{
            this.dispose();
            new MenuFrame();
        });

        exitJB.addActionListener(e ->{
            System.exit(0);
        });

        deleteJB.addActionListener(e ->{
            System.out.println("Удаление");
            int index = jList.getSelectedValue().getIdCurriculum();
            if(jList.getSelectedValue().getIdAuthor() == user.getUserId()) {
                try {
                    curriculumDao.delete(index);
                    jList.setModel(curriculumDao.findAllCurriculums());
                } catch (DaoException ex) {
                    System.out.println("Не удалось удалить учебную программу из списка");
                    throw new RuntimeException(ex);
                }
            }
        });

        searchJB.addActionListener(e->{
            System.out.println("Searching...");
            //ListModel<Curriculum> curriculums;
            try {
                if (titleSearchJTF.getText().equals("") && !authorSearchJTF.getText().equals("")){
                    System.out.println("Поиск только по автору");
                    jList.setModel(curriculumDao.findCurriculumsByAuthor(authorSearchJTF.getText()));
                }
                if (!titleSearchJTF.getText().equals("") && authorSearchJTF.getText().equals("")){
                    System.out.println("Поиск только по названию");
                    jList.setModel(curriculumDao.findCurriculumsByTitle(titleSearchJTF.getText()));
                }
                if (!titleSearchJTF.getText().equals("") && !authorSearchJTF.getText().equals("")){
                    System.out.println("Поиск по всем критериям");
                    jList.setModel(curriculumDao.findCurriculumsByTwoCriteria(titleSearchJTF.getText(), authorSearchJTF.getText()));
                }
            }
            catch (DaoException ex){
                System.out.println("Troubles in searching");
            }
        });

        resetJB.addActionListener(e ->{
            System.out.println("Reset list...");
            jList.removeAll();
            try {
                jList.setModel(curriculumDao.findAllCurriculums());
            } catch (DaoException ex) {
                throw new RuntimeException(ex);
            }
            authorSearchJTF.setText("");
            titleSearchJTF.setText("");
        });

        moreJB.addActionListener(e ->{
            Curriculum cur = jList.getSelectedValue();
            try {
                new MoreInfoFrame(cur, user);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                throw new RuntimeException(ex);
            } catch (DaoException ex) {
                throw new RuntimeException(ex);
            }
        });

        setTitle("Executable frame");
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JPanel createAuthorPage(User user) {
        JPanel panel2 = new JPanel(new FlowLayout(9, 60, 15));

        JLabel titleSetJL = new JLabel("Название");
        titleSetJL.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField titleSetJTF = new JTextField(20);
        JLabel departmentSetJL = new JLabel("Кафедра");
        departmentSetJL.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField departmentSetJTF = new JTextField(20);
        JLabel departmentCodeSetJL = new JLabel("Код кафедры");
        departmentCodeSetJL.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField departmentCodeSetJTF = new JTextField(20);
        JLabel timerJL = new JLabel("Время действия(в днях)");
        departmentCodeSetJL.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField timerJTF = new JTextField(20);
        JLabel coordinationJL = new JLabel("Согласование с учебной дисциплиной");
        coordinationJL.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField coordinationJTF = new JTextField(20);
        JLabel chooseFileJL = new JLabel("Учебная программа");
        chooseFileJL.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton setJB = new JButton("Добавить");
        JButton chooseFileJB = new JButton("Выберите файл");
        JLabel filePathJL = new JLabel();
        JLabel errorJL = new JLabel();

        JPanel titleJP = new JPanel(new GridLayout(1,2,15,5));
        JPanel departmentJP = new JPanel(new GridLayout(1,2,15,5));
        JPanel departmentCodeJP = new JPanel(new GridLayout(1,2,15,5));
        JPanel coordinationJP = new JPanel(new GridLayout(1,2,15,5));
        JPanel timerJP = new JPanel(new GridLayout(1,2,15,5));
        JPanel chooseFileJP = new JPanel(new GridLayout(1,2,15,5));

        titleJP.add(titleSetJL);
        titleJP.add(titleSetJTF);
        departmentJP.add(departmentSetJL);
        departmentJP.add(departmentSetJTF);
        departmentCodeJP.add(departmentCodeSetJL);
        departmentCodeJP.add(departmentCodeSetJTF);
        coordinationJP.add(coordinationJL);
        coordinationJP.add(coordinationJTF);
        timerJP.add(timerJL);
        timerJP.add(timerJTF);
        chooseFileJP.add(chooseFileJL);
        chooseFileJP.add(chooseFileJB);

        JPanel parametersJP = new JPanel(new GridLayout(7, 2));
        parametersJP.add(titleJP);
        parametersJP.add(departmentJP);
        parametersJP.add(departmentCodeJP);
        parametersJP.add(coordinationJP);
        parametersJP.add(timerJP);
        parametersJP.add(chooseFileJP);

        JPanel buttonSetJP = new JPanel();
        buttonSetJP.add(setJB);

        panel2.add(parametersJP);
        panel2.add(buttonSetJP);
        panel2.add(filePathJL);
        panel2.add(errorJL);

        setJB.addActionListener(e -> {
            if (Objects.equals(titleSetJTF.getText(), "") || Objects.equals(departmentSetJTF.getText(), "") ||
                    Objects.equals(departmentCodeSetJTF.getText(), "") || file == null || Objects.equals(timerJTF.getText(), "")) {
                errorJL.setText("Перепроверьте введенные данные. Все поля должны быть заполнены");
                return;
            }
            try {
                Curriculum curriculum = new Curriculum();
                curriculum.setTitle(titleSetJTF.getText());
                curriculum.setDepartment(departmentSetJTF.getText());
                curriculum.setDepartmentCode(departmentCodeSetJTF.getText());
                curriculum.setFile(new FileInputStream(file));
                curriculum.setIdAuthor(user.getUserId());
                curriculum.setTimer(LocalDate.now().plusDays(Long.parseLong(timerJTF.getText())));
                String authorInitials = user.getName() + " " + user.getSurname();
                System.out.println(authorInitials);
                System.out.println(user.getRole());
                curriculum.setAuthorName(authorInitials);
                myList.addElement(curriculum);
                curriculumDao.add(curriculum);
                errorJL.setText("Учебная программа успешно загружена");
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }catch (DaoException ex){
                System.out.println(ex.getMessage());
                throw new RuntimeException(ex);
            }
        });

        chooseFileJB.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            int res = jFileChooser.showOpenDialog(null);
            if (res == JFileChooser.APPROVE_OPTION) {
                file = jFileChooser.getSelectedFile();
                filePathJL.setText(file.getAbsolutePath());
            }
        });
        return panel2;
    }
}
