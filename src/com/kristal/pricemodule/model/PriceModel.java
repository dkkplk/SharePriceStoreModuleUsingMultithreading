package com.kristal.pricemodule.model;

public class PriceModel {
	String companyName;
	Double sharePrice;

	public PriceModel(String companyName, Double sharePrice) {
		super();
		this.companyName = companyName;
		this.sharePrice = sharePrice;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Double getSharePrice() {
		return sharePrice;
	}

	public void setSharePrice(Double sharePrice) {
		this.sharePrice = sharePrice;
	}

}
