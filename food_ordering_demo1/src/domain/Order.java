package domain;

import java.sql.Date;
import java.sql.Time;

public class Order {

	private String orderID;
	private String userID;
	private String adminID;
	private int mealID;
	private int pickUpTime; //预计多少分钟后取餐
	private String orderTime; //下单时间
	private double price;
	private int state;  //0为制作中，1为待取餐，2为已取餐
	private String name;//菜品名称

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPickUpTime() {
		return pickUpTime;
	}

	public void setPickUpTime(int pickUpTime) {
		this.pickUpTime = pickUpTime;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public int getMealID() {
		return mealID;
	}

	public void setMealID(int mealID) {
		this.mealID = mealID;
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

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getAdminID() {
		return adminID;
	}

	public void setAdminID(String adminID) {
		this.adminID = adminID;
	}
}
