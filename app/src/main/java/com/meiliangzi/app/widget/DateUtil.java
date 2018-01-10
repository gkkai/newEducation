package com.meiliangzi.app.widget;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {
	
	public static final String YYYYMMDD = "yyyy-MM-dd";
	public static final String YYYYMMDDhhmm = "yyyy-MM-dd   HH:mm:ss";
	public static final String HHMM = "HH:mm";
	
	/**
	 * 返回制定格式的时间串
	 * @param l
	 * @param pattern
	 * @return
	 */
	public  static String getFormatData(long l, String pattern){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return  simpleDateFormat.format(new Date(l));
	}
	
	
	/**
	 * 根据2个日期进行中间的日期取值
	 * @param beginTime 开始日期
	 * @param endTime 结束日期
	 * @return 返回字符串类型的日期集合
	 */
	public List<String> between(String beginTime, String endTime) {

		List<String> result = new ArrayList<String>();
		
		result.add(beginTime);

		try {

			Date startDate = new SimpleDateFormat("yyyy-MM-dd")
					.parse(beginTime);

			Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endTime);

			Calendar startCal = Calendar.getInstance();

			startCal.setTime(startDate);

			Calendar endCal = Calendar.getInstance();

			endCal.setTime(endDate);

			do {

				startCal.add(Calendar.DAY_OF_MONTH, 1);

				result.add(new SimpleDateFormat("yyyy-MM-dd").format(startCal
						.getTime()));

			} while (!new SimpleDateFormat("yyyy-MM-dd").format(
					startCal.getTime())
					.equals(new SimpleDateFormat("yyyy-MM-dd").format(endCal
							.getTime())));

		} catch (ParseException e) {

			e.printStackTrace();

		}

		return result;

	}
	
	/**
	 * 根据2个日期进行中间的月份取值
	 * @param beginTime 开始月份
	 * @param endTime 结束月份
	 * @return 返回字符串类型的月份集合
	 */
	public List<String> betweenMonth(String beginTime, String endTime) {

		List<String> result = new ArrayList<String>();
		
		result.add(beginTime);

		try {

			Date startDate = new SimpleDateFormat("yyyy-MM")
					.parse(beginTime);

			Date endDate = new SimpleDateFormat("yyyy-MM").parse(endTime);

			Calendar startCal = Calendar.getInstance();

			startCal.setTime(startDate);

			Calendar endCal = Calendar.getInstance();

			endCal.setTime(endDate);

			do {

				startCal.add(Calendar.MONTH, 1);

				result.add(new SimpleDateFormat("yyyy-MM").format(startCal
						.getTime()));

			} while (!new SimpleDateFormat("yyyy-MM").format(
					startCal.getTime())
					.equals(new SimpleDateFormat("yyyy-MM").format(endCal
							.getTime())));

		} catch (ParseException e) {

			e.printStackTrace();

		}

		return result;

	}
	
	/**
	 * 根据2个日期进行中间的月份取值
	 * @param beginTime 开始月份
	 * @param endTime 结束月份
	 * @return 返回字符串类型的月份集合
	 */
	public List<String> betweenWeek(String beginTime, String endTime) {

		List<String> result = new ArrayList<String>();
		
		result.add(beginTime);

		try {

			Date startDate = new SimpleDateFormat("yyyy-ww")
					.parse(beginTime);

			Date endDate = new SimpleDateFormat("yyyy-ww").parse(endTime);

			Calendar startCal = Calendar.getInstance();

			startCal.setTime(startDate);

			Calendar endCal = Calendar.getInstance();

			endCal.setTime(endDate);

			do {

				startCal.add(Calendar.WEEK_OF_YEAR, 1);

				result.add(new SimpleDateFormat("yyyy-ww").format(startCal
						.getTime()));

			} while (!new SimpleDateFormat("yyyy-ww").format(
					startCal.getTime())
					.equals(new SimpleDateFormat("yyyy-ww").format(endCal
							.getTime())));

		} catch (ParseException e) {

			e.printStackTrace();

		}

		return result;

	}
	
	/**
	 * 取得当前日期是一年中的第几个星期
	 * @return
	 */
	public static int getWeek(){
		
		return Integer.parseInt(new SimpleDateFormat("ww").format(new Date()));
		
	}
	
	/**
	 * 取得当前日期是一年中的第几个星期
	 * @return
	 */
	public static int getYear(){
		
		return Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
		
	}
	
	/**
	 * 取得当前日期是一年中的第几个星期
	 * @return
	 */
	public static int getMonth(){
		
		return Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
		
	}
	/**
	 *  根据指定的格式返回日期格式
	 * @param format  格式
	 * @param date   字符串日期
	 * @return
	 * @throws ParseException
	 */
	public static Date StringToDate(String format, String date) {
		  SimpleDateFormat df = new SimpleDateFormat(format);
		  try {
			return df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 字符串转Date类型
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String dateToString(Date date, String pattern){
		if(date==null){
			   return "";
		   }
		   SimpleDateFormat format=new SimpleDateFormat(pattern);
		   return format.format(date);
	}
	/**
	 * 处理发布时间的   如果小于minute分钟就就返回多少分钟的格式 否则返回日期
	 * @param beginTime 
	 * @param endTime  目标时间
	 * @param minute
	 * @return
	 */
	 
	
	public static String deailTime(Date beginTime, Date nowTime, int minute){
		long s=(nowTime.getTime()-beginTime.getTime())/1000/60;
		if(s<=minute){
			if(s<=2){
				return "刚刚";
			}
			return  String.valueOf(s)+"分钟";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String time = sdf.format(beginTime);
	    return time;
	} 
	/**
	 * 是不是指定日期之后的   如 todayDate是今天时间 ，amount等于0 表示查看是不是今天之后的
	 * @param time
	 * @param amount  0
	 */
	public static boolean isTodayAfter(Date time, Date todayDate, int amount){
		 Calendar cal2 = Calendar.getInstance();
		 cal2.setTime(time);
		 
		 Calendar calendar = Calendar.getInstance(); //得到日历
		 Date dBefore =null;
		 
		//是不是今天以后的
		 calendar = Calendar.getInstance(); //得到日历
	     calendar.setTime(todayDate);//把当前时间赋给日历
		 calendar.add(Calendar.DAY_OF_MONTH, amount);
		 calendar.set( Calendar.HOUR_OF_DAY, 0);
		 calendar.set( Calendar.MINUTE, 0);
		 calendar.set(Calendar.SECOND, 0);
		 dBefore=calendar.getTime();
		 if(cal2.after(calendar)){
			 return true;
		 }
		 return false;
	}
	
	
	public static Date todayAfter(Date todayDate, int day_of_month){
		 
		 Calendar calendar = Calendar.getInstance(); //得到日历
		 Date dBefore =null;
		 
		//是不是今天以后的
		 calendar = Calendar.getInstance(); //得到日历
	     calendar.setTime(todayDate);//把当前时间赋给日历
		 calendar.add(Calendar.DAY_OF_MONTH, day_of_month);
		 calendar.set( Calendar.HOUR_OF_DAY, 0);
		 calendar.set( Calendar.MINUTE, 0);
		 calendar.set(Calendar.SECOND, 0);
		 dBefore=calendar.getTime();
		return dBefore;
		
	}
	
	
	/**
	 * 返回昨天日期
	 */
	public static Date getYesterTime(Date time){
		 Calendar calendar = Calendar.getInstance(); //得到日历
		 calendar = Calendar.getInstance(); //得到日历
		 calendar.setTime(time);
		 calendar.add(Calendar.DAY_OF_MONTH, -1);
		 calendar.set( Calendar.HOUR_OF_DAY, 0);
		 calendar.set( Calendar.MINUTE, 0);
		 calendar.set(Calendar.SECOND, 0);
		 return calendar.getTime();
	}
	/**
	 * 返回本周的第一天
	 */
	public static Date getFirstWeekDayTime(Date time){
		 Calendar calendar = Calendar.getInstance(); //得到日历
		 calendar = Calendar.getInstance(); //得到日历
		 calendar.setTime(time);
		 
		//一周第一天是否为星期天  
			boolean isFirstSunday = (calendar.getFirstDayOfWeek() == Calendar.SUNDAY);
			//获取周几  
			int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
			//若一周第一天为星期天，则-1  
			if(isFirstSunday){  
			    weekDay = weekDay - 1;  
			    if(weekDay == 0){  
			        weekDay = 7;  
			    }  
			} 
			
		 calendar.add(Calendar.DAY_OF_MONTH, -(weekDay-1));  //设置为前一天
		 calendar.set( Calendar.HOUR_OF_DAY, 0);
		 calendar.set( Calendar.MINUTE, 0);
		 calendar.set(Calendar.SECOND, 0);
		 
		return calendar.getTime();
		
	}
	/**
	 * 返回本月的第一天
	 */
	public static Date getFirstMonthDay(Date time){
		 Calendar calendar = Calendar.getInstance(); //得到日历
		 calendar = Calendar.getInstance(); //得到日历
		 calendar.setTime(time);
		 calendar.set(Calendar.DAY_OF_MONTH, 1);
		 calendar.set( Calendar.HOUR_OF_DAY, 0);
		 calendar.set( Calendar.MINUTE, 0);
		 calendar.set(Calendar.SECOND, 0);
		 return calendar.getTime();
	}
}
