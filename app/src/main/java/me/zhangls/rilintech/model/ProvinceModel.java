package me.zhangls.rilintech.model;

import java.util.List;

public class ProvinceModel {
	private String name;
	private String id;
	private List<CityModel> cityList;
	
	public ProvinceModel() {
		super();
	}

	public ProvinceModel(String name, List<CityModel> cityList,String id) {
		super();
		this.name = name;
		this.cityList = cityList;
		this.id=id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CityModel> getCityList() {
		return cityList;
	}

	public void setCityList(List<CityModel> cityList) {
		this.cityList = cityList;
	}

	@Override
	public String toString() {
		return "ProvinceModel [name=" + name + ", cityList=" + cityList + "]";
	}
	
}
