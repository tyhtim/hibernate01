package org.fkjava.bean;
/**
 * 组件 1
 *
 * @author hanfeili
 *  www.fkjava.org
 */
public class Address {
	/**
	 * 家庭地址
	 */
	private String homeAddress;
	/**
	 * 公司地址
	 */
	private String companyAddress;
	
	
	public String getHomeAddress() {
		return homeAddress;
	}
	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
}
