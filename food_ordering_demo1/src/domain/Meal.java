package domain;

public class Meal {

	private int mealID;
	private String name;
	private String location;
	private double price;
	private int state;//0为该菜品可得，1为不可得

	public String getAdminID() {
		return adminID;
	}

	public void setAdminID(String adminID) {
		this.adminID = adminID;
	}

	private String adminID;//该菜品所对应的管理员ID
	
	public int getMealID() {
		return mealID;
	}
	public void setMealID(int mealID) {
		this.mealID = mealID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Meal{" +
				"mealID=" + mealID +
				", name='" + name + '\'' +
				", location='" + location + '\'' +
				", price=" + price +
				", state=" + state +
				'}';
	}
}
