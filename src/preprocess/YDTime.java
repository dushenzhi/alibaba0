package preprocess;

public class YDTime {
	private int month;
	private int day;
	public static YDTime BaseTime = new YDTime("8月16日");//预测基准日期，数据集中最晚一天的下一天
	
	public YDTime(String md){
		month = 0;day = 0;
		String m="",d="";
		for(int i=0;i < md.length();i++){
			if(md.charAt(i) == '月'){
				month = Integer.parseInt(m);
			}else if(md.charAt(i) == '日'){
				day = Integer.parseInt(d);
			}else{
				if(month == 0){
					m += md.charAt(i);
				}else if(day == 0){
					d += md.charAt(i);
				}
			}
		}
	}
	
	
	
	public int getMonth() {
		return month;
	}



	public int getDay() {
		return day;
	}

	
	public boolean isBig(YDTime ydt){
		if(this.month > ydt.getMonth()){
			return true;
		}else if(this.month == ydt.getMonth() && this.day > ydt.getDay()){
			return true;
		}
		return false;
	}
	
	public boolean bigLessMonth(YDTime ydt){
		if(this.month == ydt.getMonth() && this.day > ydt.getDay()){
			return true;
		}else if(this.month == (ydt.getMonth() + 1) && this.day < ydt.getDay()){
			return true;
		}
		return false;
	}

	public String toString(){
		return month + "月" + day + "日";
	}
	
	/***
	 * 减法运算，计算相隔天数，参数ydt小为正数，ydt大为负数（每个月按固定31天计算,跟时间天数有出入）
	 * @return
	 */
	public int minus(YDTime ydt){
		//if(this.month == ydt.getMonth() && this.day == ydt.getDay()){return 0;}
		/*else if(isBig(ydt)){
			return ((this.month-ydt.getMonth())*31 + (this.day-ydt.getDay()));
		}*/
		//else{
			return ((this.month-ydt.getMonth())*31 + (this.day-ydt.getDay()));
		//}
		//System.out.println("无法进行减法运算，出错！");
	}
	
	/* */
	public static void main(String... args){
		YDTime t = new YDTime("6月5日");
		YDTime t1 = new YDTime("7月1日");
		System.out.println(t1.minus(t1));
	}

}
