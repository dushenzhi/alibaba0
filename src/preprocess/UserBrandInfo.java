package preprocess;

import java.util.ArrayList;
import java.util.List;

public class UserBrandInfo {
	private String uid;//�û�id
	private String bid;//Ʒ��id
	private List<Integer> actions;//�û���Ϊ
	private List<YDTime> atime;//�û���Ϊʱ��
	private boolean isBuy;
	private List<YDTime> buyTime;
	private int numFeature;
	private List<String> fs;
	
	
	public UserBrandInfo(String uid, String bid) {
		this.uid = uid;
		this.bid = bid;
		this.actions = new ArrayList<Integer>();
		this.atime = new ArrayList<YDTime>();
		this.isBuy = false;//��ʼ��Ϊδ��
		this.buyTime = new ArrayList<YDTime>();
		this.numFeature = 0;
		this.fs = new ArrayList<String>();
		
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public List<Integer> getActions() {
		return actions;
	}
	public void setActions(List<Integer> actions) {
		this.actions = actions;
	}
	
	public boolean isBuy() {
		return isBuy;
	}
	public void setBuy(boolean isBuy) {
		this.isBuy = isBuy;
	}
	public List<YDTime> getAtime() {
		return atime;
	}
	public void setAtime(List<YDTime> atime) {
		this.atime = atime;
	}
	public List<YDTime> getBuyTime() {
		return buyTime;
	}
	public void setBuyTime(List<YDTime> buyTime) {
		this.buyTime = buyTime;
	}
	@Override
	public String toString() {
		return "UserBrandInfo [uid=" + uid + ", bid=" + bid + ", isBuy=" + isBuy
				+ ", buyTime=" + buyTime + ", actions="
						+ actions + ", atime=" + atime + "]";
	}
	
	public String genFeature(boolean withLable){
		//������ʼ��
		if(!isBuy)fs.add("");
		else {
			for(YDTime ydt :buyTime){
				fs.add("");
			}
		}
		this.numFeature = 0;
		String feature = "";
		//feature += uid+ " ";
		//feature += genBaseFeature();
		
		genTotalFeature();
		genClosedBuyFeature();
		
		if(!isBuy){
			if(withLable)feature += "-1 ";
			feature += fs.get(0) + "\n";
		}
		else{
			/**/
			for(int i = 0;i < buyTime.size();i++){
				feature += "+1 ";
				/*
				if(withLable){
					if(actions.get(i) == 1)feature += "+1 ";
					else feature += "-1 ";
				}*/
				feature += fs.get(i) + "\n";
			}
		}
		return feature;
	}
	/*
	private void genLessMonthFeature() {
		if(isBuy){//����
			for(int i=0 ; i < buyTime.size();i++){
				String f = "";
				int click=0,buy=0,save=0,cart=0;
				for(int j=0 ;j < actions.size();j++){
					int action = actions.get(j);
					if(action == 0 && buyTime.get(i).bigLessMonth(atime.get(j))){//�����
						click++;
					}else if(action == 1 && buyTime.get(i).bigLessMonth(atime.get(j))){//���
						buy++;
					}else if(action == 2 && buyTime.get(i).bigLessMonth(atime.get(j))){//�ղع�
						save++;
					}else if(action == 3 && buyTime.get(i).bigLessMonth(atime.get(j))){//���빺�ﳵ
						cart++;
					}
				}
				f += (numFeature + 1)+":" + click +" "+(numFeature + 2)+":"+ buy +" "+(numFeature + 3)+":"+ save +" "+(numFeature + 4)+":"+ cart+" ";
				fs.set(i, fs.get(i)+f);
			}
		}
		
	}
	*/
	/**
	 * �������и�Ʒ�Ƽ�¼����
	 */
	private void genClosedBuyFeature(){
		if(isBuy){
			for(int i=0 ; i < buyTime.size();i++){
				YDTime closeBuy = null;//���ʱ�¼����һ�ι���ʱ��
				for(YDTime t : buyTime){//���й���ʱ��
					if(buyTime.get(i).isBig(t)){//���¼�ʱ����
						if(closeBuy == null)closeBuy = t;
						else if(t.isBig(closeBuy))closeBuy = t;
					}
				}
				String f = "";
				double click=0,buy=0,save=0,cart=0;
				for(int j=0 ;j < actions.size();j++){
					int action = actions.get(j);
					if(buyTime.get(i).isBig(atime.get(j)) && (closeBuy == null || atime.get(j).isBig(closeBuy))){
						if(action == 0){//�����
							//click++;
							click += (1.0/buyTime.get(i).minus(atime.get(j)));
						}else if(action == 1){//���
							//buy++;
							buy += (1.0/buyTime.get(i).minus(atime.get(j)));
						}else if(action == 2){//�ղع�
							//save++;
							save += (1.0/buyTime.get(i).minus(atime.get(j)));
						}else if(action == 3){//���빺�ﳵ
							//cart++;
							cart += (1.0/buyTime.get(i).minus(atime.get(j)));
						}
					}
				}
				
				f += (numFeature + 1)+":" + click +" "+//(numFeature + 2)+":"+ buy +" "
						+(numFeature + 2)+":"+ save +" "+(numFeature + 3)+":"+ cart+" ";
				fs.set(i, fs.get(i)+f);
			}
		}else{
			String f = "";
			double click=0,buy=0,save=0,cart=0;
			int j = 0;
			for(int action : actions){
				if(action == 0){//�����
					//click++;
					click += (1.0/YDTime.BaseTime.minus(atime.get(j)));
				}else if(action == 2){//�ղع�
					//save++;
					save += (1.0/YDTime.BaseTime.minus(atime.get(j)));
				}else if(action == 3){//���빺�ﳵ
					//cart++;
					cart += (1.0/YDTime.BaseTime.minus(atime.get(j)));
				}
				j++;
			}
			f += (numFeature + 1)+":" + click +" "+//(numFeature + 2)+":"+ buy +" "
					+(numFeature + 2)+":"+ save +" "+(numFeature + 3)+":"+ cart +" ";
			fs.set(0, fs.get(0)+f);
		}
		numFeature += 3;
	}
	/**
	 * �������м�¼����
	 */
	private void genTotalFeature(){
		
		if(isBuy){//����
			for(int i=0 ; i < buyTime.size();i++){
				
				String f = "";
				double click=0,buy=0,save=0,cart=0;
				for(int j=0 ;j < actions.size();j++){
					int action = actions.get(j);
					if(action == 0 && buyTime.get(i).isBig(atime.get(j))){//�����
						//click++;
						click += (1.0/buyTime.get(i).minus(atime.get(j)));
					}else if(action == 1 && buyTime.get(i).isBig(atime.get(j))){//���
						//buy++;
						buy += (1.0/buyTime.get(i).minus(atime.get(j)));
					}else if(action == 2 && buyTime.get(i).isBig(atime.get(j))){//�ղع�
						//save++;
						save += (1.0/buyTime.get(i).minus(atime.get(j)));
					}else if(action == 3 && buyTime.get(i).isBig(atime.get(j))){//���빺�ﳵ
						//cart++;
						cart += (1.0/buyTime.get(i).minus(atime.get(j)));
					}
				}
				
				f += (numFeature + 1)+":" + click +" "+(numFeature + 2)+":"+ buy +" "+(numFeature + 3)+":"+ save +" "+(numFeature + 4)+":"+ cart+" ";
				fs.set(i, fs.get(i)+f);
			}
		
		}else{//û��
			String f = "";
			double click=0,buy=0,save=0,cart=0;
			int j=0;
			for(int action : actions){
				if(action == 0){//�����
					//click++;
					click += (1.0/YDTime.BaseTime.minus(atime.get(j)));
				}else if(action == 2){//�ղع�
					//save++;
					save += (1.0/YDTime.BaseTime.minus(atime.get(j)));
				}else if(action == 3){//���빺�ﳵ
					//cart++;
					cart += (1.0/YDTime.BaseTime.minus(atime.get(j)));
				}
				j++;
			}
			f += (numFeature + 1)+":" + click +" "+(numFeature + 2)+":"+ buy +" "+(numFeature + 3)+":"+ save +" "+(numFeature + 4)+":"+ cart +" ";
			fs.set(0, fs.get(0)+f);
			//System.out.println(fs.get(0));
		}
		
		numFeature += 4;
		//return fs;
	}	
	
	
	
	
	
	
	
	
	
	
	public String genFeature4AllTest(){
		this.numFeature = 0;
		String feature = "-1 ";
		feature += genTotalFeature4AllTest();
		feature += genClosedBuyFeatureAllTest();
		feature += "\n";
		return feature;
	}
	
	private String genClosedBuyFeatureAllTest(){return genClosedBuyFeatureAllTest(YDTime.BaseTime);}
	private String genClosedBuyFeatureAllTest(YDTime baseTime){
		String bf = "";
		YDTime closeBuy = null;//���ʱ�¼����һ�ι���ʱ��
		for(YDTime t : buyTime){//���й���ʱ��
			if(baseTime.isBig(t)){//���¼�ʱ����
				if(closeBuy == null)closeBuy = t;
				else if(t.isBig(closeBuy))closeBuy = t;
			}
		}
		double click=0,buy=0,save=0,cart=0;
		for(int j=0 ;j < actions.size();j++){
			int action = actions.get(j);
			if(closeBuy == null || atime.get(j).isBig(closeBuy)){
				if(action == 0){//�����
					//click++;
					click += (1.0/baseTime.minus(atime.get(j)));
				}else if(action == 1){//���
					//buy++;
					buy += (1.0/baseTime.minus(atime.get(j)));
				}else if(action == 2){//�ղع�
					//save++;
					save += (1.0/baseTime.minus(atime.get(j)));
				}else if(action == 3){//���빺�ﳵ
					//cart++;
					cart += (1.0/baseTime.minus(atime.get(j)));
				}
			}
		}
		bf += (numFeature + 1)+":" + click +" "//+(numFeature + 2)+":"+ buy +" "
				+(numFeature + 2)+":"+ save +" "+(numFeature + 3)+":"+ cart+" ";
		numFeature += 3;
		return bf;
	}
	private String genTotalFeature4AllTest(){
		String bf = "";
		double click=0,buy=0,save=0,cart=0;
		for(int i=0;i < atime.size();i++){
			int action = actions.get(i);
			if(action == 0){//�����
				//click++;
				click += (1.0/YDTime.BaseTime.minus(atime.get(i)));
			}else if(action == 1){//���
				//buy ++;
				buy += (1.0/YDTime.BaseTime.minus(atime.get(i)));;
			}else if(action == 2){//�ղع�
				//save ++;
				save += (1.0/YDTime.BaseTime.minus(atime.get(i)));;
			}else if(action == 3){//���빺�ﳵ
				//cart ++;
				cart += (1.0/YDTime.BaseTime.minus(atime.get(i)));;
			}
		}
		bf += (numFeature + 1)+":" + click +" "+(numFeature + 2)+":"+ buy +" "+(numFeature + 3)+":"+ save +" "+(numFeature + 4)+":"+ cart+" ";

		numFeature += 4;
		return bf;
	}
	

}
