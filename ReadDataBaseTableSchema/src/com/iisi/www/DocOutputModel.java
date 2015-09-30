package com.iisi.www;

import java.lang.reflect.Field;

public class DocOutputModel {
	
	private String key;
	
	private String colEnglish;
	
	private String colChinese;
	
	private String format;
	
	private String defaultVal;
	
	private String allowNull;
	
	private String remark;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getColEnglish() {
		return colEnglish;
	}

	public void setColEnglish(String colEnglish) {
		this.colEnglish = colEnglish;
	}

	public String getColChinese() {
		return colChinese;
	}

	public void setColChinese(String colChinese) {
		this.colChinese = colChinese;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getDefaultVal() {
		return defaultVal;
	}

	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}

	public String getAllowNull() {
		return allowNull;
	}

	public void setAllowNull(String allowNull) {
		this.allowNull = allowNull;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("key = ").append(key).append(",");
		sb.append("colEnglish = ").append(colEnglish).append(",");
		sb.append("colChinese = ").append(colChinese).append(",");
		sb.append("format = ").append(format).append(",");
		sb.append("defaultVal = ").append(defaultVal).append(",");
		sb.append("allowNull = ").append(allowNull).append(",");
		sb.append("remark = ").append(remark).append(",");
		return sb.toString();
	}	
}
