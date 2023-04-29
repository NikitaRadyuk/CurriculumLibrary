package entity;

public enum Role {
    REVIEWER,
    APPROVER,
    AUTHOR;

    public static Role get(int i){
        for (Role role : Role.values()) {
            if(role.ordinal() == i)
                return role;
        }
        return null;
    }
}
