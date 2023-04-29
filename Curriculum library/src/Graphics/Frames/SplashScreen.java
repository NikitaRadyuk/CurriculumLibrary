package Graphics.Frames;

import Graphics.MyComponents.*;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Класс для стартового окна
 *
 * @author Radyuk N.O.
 * @version 1.0 14.02.2023
 */
public class SplashScreen extends JFrame{
    public static void main(String[] args) {
        SplashScreen splashScreen = new SplashScreen();
        splashScreen.setTitle("Curriculum library");
        splashScreen.setSize(800,600);
        splashScreen.setLocationRelativeTo(null);
        splashScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        splashScreen.setVisible(true);
    }
    /*Конструктор класса SplashScreen*/
    public SplashScreen(){
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));

        /*Установка таймера выключения программы*/
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Прошло 60 секунд. Программа завершена");
                System.exit(0);
            }
        };
        timer.schedule(timerTask,60000);

        /*Создание компонент*/
        TitleLabel labelBNTU = new TitleLabel("Белорусский национальный технический универистет",1,20);
        TitleLabel labelFITR = new TitleLabel("Факультет информационных технологий и робототехники",1,16);
        TitleLabel labelDepartment = new TitleLabel("Кафедра программного обеспечения информационных информационных систем и технологий",1,16);
        TitleLabel labelCourseWork = new TitleLabel("Курсовая Работа", 1, 20);
        TitleLabel labelDiscipline = new TitleLabel("по дисциплине \"Программирование на языке Java\"",3,15);
        TitleLabel labelTitleName = new TitleLabel("Библиотека учебных программ",1,25);
        TitleLabel labelAuthorInfo = new TitleLabel("Выполнил:Студент группы 10702320",1,16);
        TitleLabel labelAuthorInitials = new TitleLabel("Радюк Никита Олегович",1,16);
        TitleLabel labelReviewerInfo = new TitleLabel("Преподаватель:к.ф.-м.н.,доц.",1,16);
        TitleLabel labelReviewerInitials = new TitleLabel("Сидорик Валерий Владимирович",1,16);
        TitleLabel labelPlace = new TitleLabel("Минск, 2023",1,16);

        /*Создание панелей для расположения компонент*/
        JPanel panelTop = new JPanel(new BorderLayout());
        JPanel panelTitle = new JPanel(new BorderLayout());
        JPanel panelMiddle = new JPanel(new GridLayout(1,2,15,0));
        JPanel panelButtons = new JPanel(new GridLayout(1,2,0,0));
        JPanel panelPlace = new JPanel(new BorderLayout());

        /*Добавление панелей на фрейм*/
        add(panelTop,0);
        add(panelTitle,1);
        add(panelMiddle,2);
        add(panelPlace,3);
        add(panelButtons,4);

        /*Добавление компонент на верхнюю панель*/
        //Наименование ВУЗа
        JPanel panelBNTU = new JPanel();
        panelBNTU.setPreferredSize(new Dimension(WIDTH,30));
        panelBNTU.add(labelBNTU);
        //Наименование факультета
        JPanel panelFitr = new JPanel();
        panelFitr.setPreferredSize(new Dimension(WIDTH,25));
        panelFitr.add(labelFITR);
        //Наименование кафедры
        JPanel panelDepartment = new JPanel();
        panelDepartment.setPreferredSize(new Dimension(WIDTH,30));
        panelDepartment.add(labelDepartment);
        //Расположение компонент на верхней панели
        panelTop.add(panelBNTU,BorderLayout.NORTH);
        panelTop.add(panelFitr,BorderLayout.CENTER);
        panelTop.add(panelDepartment,BorderLayout.SOUTH);

        /*Добавление компонент на панель с заголовком*/
        //"Курсовая работа"
        JPanel panelCourseWork = new JPanel();
        panelCourseWork.add(labelCourseWork);
        //Наименование дисциплины
        JPanel panelDiscipline = new JPanel();
        panelDiscipline.add(labelDiscipline);
        //Тема курсовой работы
        JPanel panelTitleName = new JPanel();
        panelTitleName.add(labelTitleName);
        //Расположение компонент на панели с заголовком
        panelTitle.add(panelCourseWork,BorderLayout.NORTH);
        panelTitle.add(panelDiscipline,BorderLayout.CENTER);
        panelTitle.add(panelTitleName,BorderLayout.SOUTH);

        /*Добавление компонент на центральную панель*/
        //Создание панели и логотипа
        JPanel panelIconImage = new JPanel();
        ImageIcon logoImage = new ImageIcon("resources/images/CarriculumLibrary.jpg");
        JLabel labelIcon = new JLabel(new ImageIcon(logoImage.getImage().getScaledInstance(170,170,Image.SCALE_SMOOTH)));
        //Добавление логотипа на панель
        panelIconImage.add(labelIcon);
        //Создание панелей с информацией об авторе и преподавателе
        JPanel panelOtherInfo = new JPanel(new GridLayout(2,1,0,0));
        JPanel panelAuthorInfo = new JPanel(new BorderLayout());
        //Добавление информации об авторе на соответствующую панель
        panelAuthorInfo.add(labelAuthorInfo,BorderLayout.NORTH);
        panelAuthorInfo.add(labelAuthorInitials,BorderLayout.CENTER);
        //Добавление информации о преподавателетна соответствующую панель
        JPanel panelReviewerInfo = new JPanel(new BorderLayout());
        panelReviewerInfo.add(labelReviewerInfo,BorderLayout.CENTER);
        panelReviewerInfo.add(labelReviewerInitials,BorderLayout.SOUTH);
        panelOtherInfo.add(panelAuthorInfo,0);
        panelOtherInfo.add(panelReviewerInfo,1);
        //Добавление панелей с информацией на центральную панель
        panelMiddle.add(panelIconImage,0);
        panelMiddle.add(panelOtherInfo,1);

        /*Добавление надписи "Минск,2023"*/
        panelPlace.add(labelPlace,BorderLayout.CENTER);
        panelPlace.setPreferredSize(new Dimension(WIDTH,10));

        /*Создание и добавление кнопок "Далее" и "Выход" на соответствущую панель*/
        JButton btnNext = new JButton("Далее");
        JButton btnExit = new JButton("Выход");
        panelButtons.add(btnNext,0);
        panelButtons.add(btnExit,1);
        //Подписываем слушателя на кнопку "Далее"
        btnNext.addActionListener(e->{
            this.dispose();
            new MenuFrame();
            timer.cancel();
        });
        //Подписываем слушателя на кнопку "Назад"
        btnExit.addActionListener(e->{
            System.exit(0);
        });
    }
}