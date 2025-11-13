package thesis.CaseStudies.Users;


public class Users {
    private String name;
    private String from;
    private boolean isOnline;
    
    public Users(String name, String from, boolean isOnline) {
        this.name = name;
        this.from = from;
        this.isOnline = isOnline;
    }

    public String getName() {
        return name;
    }

    public String getFrom() {
        return from;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public Users setOnline(boolean online){
        return new Users(this.name, this.from, online);
    }

    @Override
    public String toString() {
        return "Users [name=" + name + ", from=" + from + ", isOnline=" + isOnline + "]";
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((from == null) ? 0 : from.hashCode());
        result = prime * result + (isOnline ? 1231 : 1237);
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
        Users other = (Users) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (from == null) {
            if (other.from != null)
                return false;
        } else if (!from.equals(other.from))
            return false;
        if (isOnline != other.isOnline)
            return false;
        return true;
    }

    
}
