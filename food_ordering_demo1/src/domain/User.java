package domain;

/**
 * 用户的实体类
 */
public class User {
    private String userID;//学号
    private String phone;//用户手机号
    private String userName;//用户名
    private String avatarURL;//用户头像地址

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", phone='" + phone + '\'' +
                ", userName='" + userName + '\'' +
                ", avatarURL='" + avatarURL + '\'' +
                '}';
    }
}
