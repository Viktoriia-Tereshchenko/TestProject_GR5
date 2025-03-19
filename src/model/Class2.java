package model;

import java.util.Objects;

public class Class2 {
    private String userName;
    private int userAge;

    public Class2(String userName, int userAge) {
        this.userName = userName;
        this.userAge = userAge;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Class2)) return false;
        Class2 class2 = (Class2) o;
        return userAge == class2.userAge && Objects.equals(userName, class2.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, userAge);
    }

    @Override
    public String toString() {
        return "Class2{" +
                "userName='" + userName + '\'' +
                ", userAge=" + userAge +
                '}';
    }
}

