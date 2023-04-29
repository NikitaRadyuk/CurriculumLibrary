package entity;

public class Statement {
    private Integer idStatement;
    private Integer idCurriculum;
    private Integer idApprover;
    private Boolean status;
    public Statement() {
    }

    public Statement(Integer idStatement, Integer idApprover, Integer idCurriculum, Boolean status) {
        this.idStatement = idStatement;
        this.idApprover = idApprover;
        this.idCurriculum = idCurriculum;
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getIdStatement() {
        return idStatement;
    }
    public void setIdStatement(int idStatement) {
        this.idStatement = idStatement;
    }

    public Integer getIdApprover() {
        return idApprover;
    }
    public void setIdApprover(int idApprover) {
        this.idApprover = idApprover;
    }
    public Integer getIdCurriculum() {
        return idCurriculum;
    }
    public void setIdCurriculum(Integer idCurriculum) {
        this.idCurriculum = idCurriculum;
    }

    @Override
    public int hashCode() {
        int result = idStatement.hashCode();
        result = result * 31 + idApprover.hashCode();
        result = result * 31 + idCurriculum.hashCode();
        result = result * 31 + status.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null || getClass() != obj.getClass())
            return false;
        Statement statement = (Statement) obj;
        return idStatement.equals(statement.idStatement)
                &&
                idApprover.equals(statement.idApprover)
                &&
                idCurriculum.equals(statement.idCurriculum)
                &&
                status.equals(statement.status);
    }

    @Override
    public String toString() {
        return "Statement{" +
                "idStatement=" + idStatement +
                ", idCurriculum=" + idCurriculum +
                ", idApprover=" + idApprover +
                ", status=" + status +
                '}';
    }
}
