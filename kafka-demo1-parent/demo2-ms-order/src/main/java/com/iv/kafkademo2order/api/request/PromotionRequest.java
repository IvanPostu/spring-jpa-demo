package com.iv.kafkademo2order.api.request;

public class PromotionRequest {

	private String promotionCode;

	public String getPromotionCode() {
		return promotionCode;
	}

	public void setPromotionCode(String promotionCode) {
		this.promotionCode = promotionCode;
	}

	@Override
	public String toString() {
		return "PromotionRequest [promotionCode=" + promotionCode + "]";
	}
	
}
