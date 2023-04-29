package entity;

import java.io.InputStream;
import java.time.LocalDate;

public class Curriculum {
    private Integer idCurriculum;
    private String departmentCode;
    private String department;
    private String title;
    private InputStream file;
    private Integer idAuthor;
    private LocalDate timer;
    private String authorName;
    private String coordination;

    public Curriculum() {
    }

    public Curriculum(Integer idCurriculum, String departmentCode, String department, String title, InputStream file,
                      Integer idAuthor, LocalDate timer, String authorName, String coordination) {
        this.idCurriculum = idCurriculum;
        this.departmentCode = departmentCode;
        this.department = department;
        this.title = title;
        this.file = file;
        this.idAuthor = idAuthor;
        this.timer = timer;
        this.authorName = authorName;
        this.coordination = coordination;
    }

    public Integer getIdAuthor() {
        return idAuthor;
    }
    public void setIdAuthor(Integer idAuthor) {
        this.idAuthor = idAuthor;
    }

    public Integer getIdCurriculum() {
        return idCurriculum;
    }
    public void setIdCurriculum(Integer idCurriculum) {
        this.idCurriculum = idCurriculum;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public InputStream getFile() {
        return file;
    }
    public void setFile(InputStream file) {
        this.file = file;
    }

    public LocalDate getTimer() {
        return timer;
    }
    public void setTimer(LocalDate timer) {
        this.timer = timer;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }
    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getAuthorName() {
        return authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getCoordination() {
        return coordination;
    }
    public void setCoordination(String coordination) {
        this.coordination = coordination;
    }

    @Override
    public int hashCode() {
        int result = department.hashCode();
        result = result * 31 + title.hashCode();
        result = result * 31 + idCurriculum.hashCode();
        result = result * 31 + departmentCode.hashCode();
        result = result * 31 + file.hashCode();
        result = result * 31 + idAuthor.hashCode();
        result = result * 31 + timer.hashCode();
        result = result * 31 + authorName.hashCode();
        result = result * 31 + coordination.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null || getClass() != obj.getClass())
            return false;
        Curriculum curriculum = (Curriculum) obj;
        return department.equals(curriculum.departmentCode)
                &&
                title.equals(curriculum.title)
                &&
                department.equals(curriculum.department)
                &&
                file.equals(curriculum.file)
                &&
                idCurriculum.equals(curriculum.idCurriculum)
                &&
                idAuthor.equals(curriculum.idAuthor)
                &&
                timer.equals(curriculum.timer)
                &&
                authorName.equals(curriculum.authorName)
                &&
                coordination.equals(curriculum.coordination);
    }

    @Override
    public String toString() {
        return "Название: " + title + "; Автор: " + authorName + ";";
    }
}
