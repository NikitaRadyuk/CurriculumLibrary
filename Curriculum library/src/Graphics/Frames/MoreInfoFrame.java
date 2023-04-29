package Graphics.Frames;

import Graphics.MyComponents.TitleLabel;
import Services.DOCXFileWriter;
import Services.Writer;
import dao.implementation.CurriculumDaoImpl;
import dao.implementation.EditDaoImpl;
import dao.implementation.StatementDaoImpl;
import dao.implementation.UserDaoImpl;
import entity.*;
import exception.DaoException;
import exception.WriterException;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;

//Форматировать под 3 метода(для каждой роли свои компоненты)
/**
 * Класс
 *
 * @author Radyuk N.O.
 * @version 1.0 14.02.2023
 */
public class MoreInfoFrame extends JFrame {
    private static CurriculumDaoImpl curriculumDao = CurriculumDaoImpl.getInstance();
    private static EditDaoImpl editDao = EditDaoImpl.getInstance();
    private static UserDaoImpl userDao = UserDaoImpl.getInstance();
    private static StatementDaoImpl statementDao = StatementDaoImpl.getInstance();
    public MoreInfoFrame(Curriculum cur, User user) throws IOException, DaoException {
        rootPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));

        JLabel labelTitle = new TitleLabel(cur.getTitle(),1,20);
        JLabel labelDepartment = new TitleLabel("Специальность: " + cur.getDepartmentCode() + " " + cur.getDepartment(),2,16);
        JLabel labelAuthorName = new TitleLabel("Автор: ",2,16);
        JLabel labelCurFile = new TitleLabel("Учебная программа: ", 2, 16);
        JButton btnDownload = new JButton("Сохранить");
        JLabel labelReviewerName = new TitleLabel("Рецензия: ",2,14);
        JTextArea txtAreaReview = new JTextArea(5,10);
        txtAreaReview.setMaximumSize(new Dimension(400,200));
        txtAreaReview.add(new JScrollBar());
        txtAreaReview.setFont(new Font("Dialog",Font.PLAIN,16));
        txtAreaReview.setTabSize(10);
        txtAreaReview.setLineWrap(true);
        txtAreaReview.setEditable(false);
        txtAreaReview.setPreferredSize(new Dimension(400,200));
        txtAreaReview.setLocation(500,300);
        JCheckBox checkBoxAppr = new JCheckBox();
        checkBoxAppr.setEnabled(false);
        JButton btnToReview = new JButton("Рецензировать");
        JLabel labelApproverName = new JLabel("Утверждено: ");
        JButton btnToApprove = new JButton("Утвердить");
        JButton btnExit = new JButton("Назад");

        JPanel panelTitle = new JPanel(new GridLayout(4,1));
        panelTitle.add(labelTitle);
        panelTitle.add(labelDepartment);
        panelTitle.add(labelAuthorName);
        JPanel panelDownload = new JPanel(new FlowLayout());
        panelDownload.add(labelCurFile);
        panelDownload.add(btnDownload);
        panelTitle.add(panelDownload);

        JPanel panelReviewerName = new JPanel();
        panelReviewerName.add(labelReviewerName);
        JPanel panelTxtArea = new JPanel();
        panelTxtArea.add(txtAreaReview);

        JPanel panelReview = new JPanel(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        panelReview.add(panelReviewerName);
        panelReview.add(panelTxtArea);
        if(user.getRole() == Role.REVIEWER){
            panelReview.add(btnToReview);
        }

        JPanel panelApprove = new JPanel(new BorderLayout());
        JPanel panelCheckBox = new JPanel(new FlowLayout());
        panelCheckBox.add(checkBoxAppr);
        panelCheckBox.add(labelApproverName);
        panelApprove.add(panelCheckBox,BorderLayout.NORTH);
        panelApprove.add(btnExit,BorderLayout.SOUTH);

        btnToApprove.setSize(300,100);
        btnExit.setSize(300,100);

        add(panelTitle);
        add(panelReview);
        add(panelApprove);

        User author = userDao.findUserById(cur.getIdAuthor());
        labelAuthorName.setText("Автор: " + author.getSurname() + " " + author.getName());

        Statement statement = statementDao.getStatement(cur.getIdCurriculum());
        if (statement != null){
            checkBoxAppr.setSelected(true);
            User approver = userDao.findUserById(statement.getIdApprover());
            labelApproverName.setText("Утверждено: " + approver.getName() + " " + approver.getSurname());
        }

        if (user.getRole() == Role.REVIEWER){
            txtAreaReview.setEditable(true);
            labelReviewerName.setText("Рецензия: " + user.getName() + " " + user.getSurname());

        }

        Edit edit = editDao.getEdit(cur.getIdCurriculum());
        if (edit != null) {
            User reviewer = userDao.findUserById(edit.getIdReviewer());
            labelReviewerName.setText("Рецензия: " + reviewer.getName() + " " + reviewer.getSurname());
            txtAreaReview.setText(edit.getEditText());
        }
        if (user.getRole() == Role.APPROVER){
            labelApproverName.setText("Утверждено: " + user.getName() + " " + user.getSurname());
            panelApprove.add(btnToApprove, BorderLayout.CENTER);
        }
            btnToReview.addActionListener(e -> {
                Edit edit1 = new Edit();
                edit1.setIdReviewer(user.getUserId());
                edit1.setIdCurriculum(cur.getIdCurriculum());
                edit1.setEditText(txtAreaReview.getText());
                try {
                    if (edit != null) {
                        edit.setIdReviewer(edit1.getIdReviewer());
                        edit.setIdCurriculum(edit1.getIdCurriculum());
                        edit.setEditText(edit1.getEditText());
                        editDao.updateEdit(edit);
                    } else {
                        editDao.addEdit(edit1);
                    }
                } catch (DaoException ex) {
                    throw new RuntimeException(ex);
                }
                this.dispose();
            });

            btnExit.addActionListener(e -> {
                this.dispose();
            });

        btnDownload.addActionListener(e ->{
            try {
                JFileChooser fileChooser = new JFileChooser("resources/patternDOCX");
                fileChooser.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        String[] s = f.getName().split("\\.");
                        return s[s.length - 1].equals("docx");
                    }

                    @Override
                    public String getDescription() {
                        return "docx";
                    }
                });

                if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    String path = file.getPath();
                    if (!path.contains(".docx")) {
                        path += ".docx";
                    }

                    Writer writer = new DOCXFileWriter(path);
                    writer.write(cur);
                }
            }
            catch (WriterException ex){
                JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnToApprove.addActionListener(e->{
            if(checkBoxAppr.isSelected())
                return;
            checkBoxAppr.setSelected(true);
            Statement statement1 = new Statement();
            statement1.setIdCurriculum(cur.getIdCurriculum());
            statement1.setIdApprover(user.getUserId());
            statement1.setStatus(true);
            try {
                statementDao.addStatement(statement1);
            } catch (DaoException ex) {
                throw new RuntimeException(ex);
            }
        });

        setTitle("Pre-reading frame");
        /*setSize(500,600);*/
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
