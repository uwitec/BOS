package cn.guwei.crm.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Car")
public class Car {

	private String name;
	private String color;
	private Double money;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	@Override
	public String toString() {
		return "Car [name=" + name + ", color=" + color + ", money=" + money + "]";
	}
	
	
}
