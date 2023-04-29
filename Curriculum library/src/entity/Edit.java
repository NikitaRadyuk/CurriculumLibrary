package entity;

public class Edit {
    private Integer idEdit;
    private Integer idCurriculum;
    private String text;
    private Boolean editRequirement;
    private Integer idReviewer;

    public Edit() {
    }

    public Edit(Integer idEdit, Integer idCurriculum, String text, Boolean editRequirement, Integer idReviewer) {
        this.idEdit = idEdit;
        this.idCurriculum = idCurriculum;
        this.text = text;
        this.editRequirement = editRequirement;
        this.idReviewer = idReviewer;
    }

    public Integer getIdEdit() {
        return idEdit;
    }
    public void setIdEdit(Integer idEdit) {
        this.idEdit = idEdit;
    }

    public Integer getIdCurriculum() {
        return idCurriculum;
    }
    public void setIdCurriculum(Integer idCurriculum) {
        this.idCurriculum = idCurriculum;
    }

    public String getEditText() {
        return text;
    }
    public void setEditText(String text) {
        this.text = text;
    }

    public Boolean getEditRequirement() {
        return editRequirement;
    }
    public void setEditRequirement(Boolean editRequirement) {
        this.editRequirement = editRequirement;
    }

    public Integer getIdReviewer() {
        return idReviewer;
    }
    public void setIdReviewer(Integer idReviewer) {
        this.idReviewer = idReviewer;
    }

    @Override
    public int hashCode() {
        int result = idEdit.hashCode();
        result = result * 31 + idCurriculum.hashCode();
        result = result * 31 + text.hashCode();
        result = result * 31 + editRequirement.hashCode();
        result = result * 31 + idReviewer.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null || getClass() != obj.getClass())
            return false;
        Edit edit = (Edit) obj;
        return idEdit.equals(edit.idEdit)
                &&
                text.equals(edit.text)
                &&
                editRequirement.equals(edit.editRequirement)
                &&
                idCurriculum.equals(edit.idCurriculum)
                &&
                idReviewer.equals(edit.idReviewer);
    }

    @Override
    public String toString() {
        return "Edit{" +
                "idEdit=" + idEdit +
                ", idCurriculum=" + idCurriculum +
                ", text='" + text + '\'' +
                ", editRequirement=" + editRequirement +
                ", idReviewer=" + idReviewer +
                '}';
    }
}
