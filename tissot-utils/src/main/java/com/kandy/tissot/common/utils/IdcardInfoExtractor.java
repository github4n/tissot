/**
 * 文件名称   : com.cheng.common.utils.IdcardInfoExtractor.java
 * 项目名称   : kesu
 * 创建日期   : 2014-5-16
 * 更新日期   :
 * 作       者   : newwork
 *
 */
package com.kandy.tissot.common.utils;

import com.kandy.tissot.core.utils.validate.IdcardValidator;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <PRE>
 * 
 * 中文描述：
 * 
 * </PRE>
 * 
 * @version 1.0.0
 */
public class IdcardInfoExtractor {
	
	// 省份
	private String province;
	// 城市
	private String city;
	// 区县
	private String region;
	// 年份
	private int year;
	// 月份
	private int month;
	// 日期
	private int day;
	// 性别
	private String gender;
	// 出生日期
	private Date birthDate;
	// 年龄
	private int age;

	@SuppressWarnings("serial")
	private Map<String, String> cityCodeMap = new HashMap<String, String>() {
		{
			this.put("11", "北京");
			this.put("12", "天津");
			this.put("13", "河北");
			this.put("14", "山西");
			this.put("15", "内蒙古");
			this.put("21", "辽宁");
			this.put("22", "吉林");
			this.put("23", "黑龙江");
			this.put("31", "上海");
			this.put("32", "江苏");
			this.put("33", "浙江");
			this.put("34", "安徽");
			this.put("35", "福建");
			this.put("36", "江西");
			this.put("37", "山东");
			this.put("41", "河南");
			this.put("42", "湖北");
			this.put("43", "湖南");
			this.put("44", "广东");
			this.put("45", "广西");
			this.put("46", "海南");
			this.put("50", "重庆");
			this.put("51", "四川");
			this.put("52", "贵州");
			this.put("53", "云南");
			this.put("54", "西藏");
			this.put("61", "陕西");
			this.put("62", "甘肃");
			this.put("63", "青海");
			this.put("64", "宁夏");
			this.put("65", "新疆");
			this.put("71", "台湾");
			this.put("81", "香港");
			this.put("82", "澳门");
			this.put("91", "国外");
		}
	};


	/**
	 * 通过构造方法初始化各个成员属性
	 */
	public IdcardInfoExtractor(String idcard) {
		if (!IdcardValidator.isValidatedAllIdcard(idcard)) {
			throw new IllegalArgumentException(idcard+" 不是一个有效的身份证号");
		}
		if (idcard.length() == 15) {
			idcard = IdcardValidator.convertIdcarBy15bit(idcard);
		}
		// 获取省份
		String provinceId = idcard.substring(0, 2);
		province = cityCodeMap.get(provinceId);
		if(province == null){
			throw new IllegalArgumentException(idcard+" 不是一个有效的身份证号");
		}

		// 获取市,区县(预留)

		// 获取性别
		String id17 = idcard.substring(16, 17);
		if (Integer.parseInt(id17) % 2 != 0) {
			this.gender = "男";
		} else {
			this.gender = "女";
		}
		
		try {
			// 获取出生日期
			String birthDateStr = idcard.substring(6, 14);
			birthDate = new SimpleDateFormat("yyyyMMdd").parse(birthDateStr);
			GregorianCalendar birthCalendar = new GregorianCalendar();
			birthCalendar.setTime(birthDate);
			
			this.year = birthCalendar.get(Calendar.YEAR);
			this.month = birthCalendar.get(Calendar.MONTH) + 1;
			this.day = birthCalendar.get(Calendar.DAY_OF_MONTH);

			// 获取年龄
			this.age = Calendar.getInstance().get(Calendar.YEAR) - this.year;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthDate;
	}

	@Override
	public String toString() {
		return "省份：" + this.province + ",性别：" + this.gender + ",出生日期：" + this.birthDate;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * 判断用户有没有成年：大于18周岁算成年
	 * @return
	 */
	public boolean isAdult(){
		Calendar today = Calendar.getInstance();
		
		int currentYear = today.get(Calendar.YEAR);
		int currentMonth = today.get(Calendar.MONTH)+1;
		int currentDate = today.get(Calendar.DATE);
		if(currentYear - year > 18){
			return true;
		}
		if(currentYear - year < 18){
			return false;
		}
		if(currentMonth > month){
			return true;
		}
		if(currentMonth < month){
			return false;
		}
		if(currentDate >= day){
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
//		String idcard = "110101200906042957";
		String idcard = "11010119970323681X";
		IdcardInfoExtractor ie = new IdcardInfoExtractor(idcard);
		System.out.println(ie.isAdult());
	}
}
