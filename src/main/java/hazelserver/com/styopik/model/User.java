package hazelserver.com.styopik.model;

import java.io.Serializable;

public class User implements Serializable, Comparable<User> {
    
    private static final long serialVersionUID = 1L;
    
    private int id;
    private int level;
    private Double result;
    
    public User() {
        
    }

    public User(int id, int level, double result) {
        super();
        this.id = id;
        this.level = level;
        this.result = result;
    }
    public Integer getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Integer getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public Double getResult() {
        return result;
    }
    public void setResult(double result) {
        this.result = result;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + level;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id != other.id)
            return false;
        if (level != other.level)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", level=" + level + ", result=" + result + "]";
    }

    public int compareTo(User user) {

        return user.getResult().compareTo(this.result);
    }

}
