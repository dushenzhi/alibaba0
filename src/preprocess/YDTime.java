package preprocess;

public class YDTime {
	private int month;
	private int day;
	public static YDTime BaseTime = new YDTime("8��16��");//Ԥ���׼���ڣ����ݼ�������һ�����һ��
	
	public YDTime(String md){
		month = 0;day = 0;
		String m="",d="";
		for(int i=0;i < md.length();i++){
			if(md.charAt(i) == '��'){
				month = Integer.parseInt(m);
			}else if(md.charAt(i) == '��'){
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
		return month + "��" + day + "��";
	}
	
	/***
	 * �������㣬�����������������ydtСΪ������ydt��Ϊ������ÿ���°��̶�31�����,��ʱ�������г��룩
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
		//System.out.println("�޷����м������㣬����");
	}
	
	/* */
	public static void main(String... args){
		YDTime t = new YDTime("6��5��");
		YDTime t1 = new YDTime("7��1��");
		System.out.println(t1.minus(t1));
	}

}
