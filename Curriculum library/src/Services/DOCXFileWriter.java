package Services;

import dao.implementation.CurriculumDaoImpl;
import entity.Curriculum;
import exception.WriterException;
import org.apache.commons.io.IOUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DOCXFileWriter implements Writer {
    private static CurriculumDaoImpl curriculumDao = CurriculumDaoImpl.getInstance();
    private static final String TITLE = "title";
    private static final String DEPARTMENT_CODE = "departmentCode";
    private static final String DEPARTMENT = "department";
    private static final String TEXT = "text";
    private static final String COORDINATION = "coordination";
    private final String filePath;

    public DOCXFileWriter(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void write(Curriculum curriculum) throws WriterException {
        String path = "resources/patternDOCX/Curriculum.docx";
        try (
                FileInputStream fis = new FileInputStream(path);
                XWPFDocument doc = new XWPFDocument(fis)
        ) {
            fillTags(doc,curriculum);
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                doc.write(fos);
                JOptionPane.showMessageDialog(new JFrame(), "Saved as " + filePath, "Success", JOptionPane.PLAIN_MESSAGE);

            } catch (Exception e) {
                throw new WriterException("Cant write in a file.", e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillTags(XWPFDocument doc, Curriculum curriculum) throws IOException {
        InputStream inputStream = curriculum.getFile();
        String contents = IOUtils.toString(inputStream, String.valueOf(StandardCharsets.UTF_8));
        findByTag(doc, TITLE, curriculum.getTitle());
        findByTag(doc, DEPARTMENT_CODE, curriculum.getDepartmentCode());
        findByTag(doc, DEPARTMENT, curriculum.getDepartment());
        findByTag(doc, TEXT, contents);
        findByTag(doc, COORDINATION, curriculum.getCoordination());
    }

private void findByTag(XWPFDocument doc, String tag, String to) {
        List<XWPFParagraph> paragraphs = doc.getParagraphs();
        for (XWPFParagraph paragraph : paragraphs) {
            String par = paragraph.getText();
            if (par != null && par.contains(tag)) {
                List<XWPFRun> runs = paragraph.getRuns();
                for (var run : runs) {
                    String text = run.getText(0);
                    if (text != null && text.contains(tag)) {
                        text = text.replace(tag, to);
                        run.setBold(true);
                        run.setText(text, 0);
                        return;
                    }
                }
            }
        }
    }
}

