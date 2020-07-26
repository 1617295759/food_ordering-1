package domain;

public class Administer {
	
	private String administerID;
	private String phone;
	private String avatarUrl;
	private String administerName;

	public String getAdministerName() {
		return administerName;
	}

	public void setAdministerName(String administerName) {
		this.administerName = administerName;
	}

	public String getAdministerID() {
		return administerID;
	}
	public void setAdministerID(String administerID) {
		this.administerID = administerID;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
}
