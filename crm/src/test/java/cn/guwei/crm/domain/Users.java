package cn.guwei.crm.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Users")
public class Users {

	private int id;
	private String name;
	private Car car;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
	@Override
	public String toString() {
		return "Users [id=" + id + ", name=" + name + ", car=" + car + "]";
	}
	
	
}
