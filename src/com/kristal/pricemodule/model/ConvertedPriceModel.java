package com.kristal.pricemodule.model;

public class ConvertedPriceModel {

	private String companyName;
	private double price;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ConvertedPriceModel(String companyName, double price) {
		super();
		this.companyName = companyName;
		this.price = price;
	}

}
