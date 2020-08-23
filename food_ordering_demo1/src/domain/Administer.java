package domain;

public class Administer {
	
	private String adminID;
	private String phone;
	private String avatarUrl;
	private String adminName;

	public String getAdminID() {
		return adminID;
	}

	public void setAdminID(String adminID) {
		this.adminID = adminID;
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

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	@Override
	public String toString() {
		return "Administer{" +
				"adminID='" + adminID + '\'' +
				", phone='" + phone + '\'' +
				", avatarUrl='" + avatarUrl + '\'' +
				", adminName='" + adminName + '\'' +
				'}';
	}
}
