import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import preprocess.UserBrandInfo;
import preprocess.YDTime;


public class DataSet {
	private HashMap<String, UserBrandInfo> dataset;
	
	
	public Map<String, UserBrandInfo> read(String file){
		System.out.println("��ʼ��������...");
		dataset = new HashMap<String,UserBrandInfo>();
		Pattern p=Pattern.compile("(\\d+)��(\\d+)��");
		try {
			BufferedReader reader  = new BufferedReader(new FileReader(file));
			//System.out.println();
			reader.readLine();//������һ��
			String line = null;
			while((line=reader.readLine()) != null){
				String[] tks = line.split(",");
				String uid = tks[0];
				String bid = tks[1];
				String key = uid+"-"+bid;
				if(!dataset.containsKey(key)){
					UserBrandInfo info = new UserBrandInfo(uid,bid);
					dataset.put(key, info);
				}
				UserBrandInfo info = dataset.get(key);
				Integer action = new Integer(tks[2]);
				
				info.getActions().add(action);
				Matcher m=p.matcher(tks[3]);
				//System.out.println("---" + m.groupCount());
				//System.out.println("---####:" + m.group(1));
				//Integer atime = new Integer(tks[3]);
				YDTime atime = new YDTime(tks[3]);
				info.getAtime().add(atime);
				if(action == 1){//�������
					info.setBuy(true);
					info.getBuyTime().add(atime);
				}
				//
				//System.out.println(info);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int count = dataset.size();
		System.out.println("����������ɣ��ܡ�"+count+"����user-brand�ԣ�");
		
		/**/
		//������ݼ�
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("data/UserBrandInfo.txt"));
			for(UserBrandInfo i : dataset.values()){
				writer.write(i + "\n");
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dataset;
	}
	
	
	public void genTrainDataset(String outfile){
		System.out.println("��ʼ��ȡ����������ѵ������...");
		if(dataset.isEmpty()){
			System.out.println("���ݼ�Ϊ�գ������������ݼ���");
			return;
		}
		int countBuy = 0;
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(outfile));
			for(UserBrandInfo ubi : dataset.values()){
				//System.out.println(ubi.getUid()+"\t"+ubi.getBid()+"\t"+ubi.isBuy());
				if(ubi.isBuy()) countBuy += ubi.getBuyTime().size();
				//if(ubi.getBuyTime().size() < 2)continue;
				//System.out.println(ubi.getBuyTime().size() +"-->" + ubi.getBuyTime());
				//countBuy ++;
				writer.write(ubi.genFeature(true));
			}
			
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("countBuy:"+countBuy + ",ѵ������������ɣ�");
	}
	
	
	public void genTestDataset(String outfile){
		System.out.println("��ʼ��ȡ������������������...");
		BufferedWriter writer = null,UIdwriter = null;
		try {
			writer = new BufferedWriter(new FileWriter(outfile));
			UIdwriter = new BufferedWriter(new FileWriter(outfile+".uid"));
			for(UserBrandInfo ubi : dataset.values()){
				String f = ubi.genFeature4AllTest();
				writer.write(f);
				UIdwriter.write(ubi.getUid()+","+ubi.getBid() + "-----" + f);
			}
			writer.close();
			UIdwriter.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("��������������ɣ�");
	}
	
	public void prune(String in,String out){
		Map<String ,Integer> ds = new HashMap<String,Integer>();
		System.out.println("��ʼ����ȥ��...");
		int nc = 0,ac = 0;
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(out));
			BufferedReader reader  = new BufferedReader(new FileReader(in));
			String line = null;
			while((line = reader.readLine()) != null){
				if(line.trim().equals(""))continue;
				
				if(line.split(" ")[0].equals("+1")){
					ac++;
					//writer.write(line + "\n");
					//continue;
				}else nc++;
				if(!ds.containsKey(line)){
					ds.put(line, 1);
				}else{
					ds.put(line, ds.get(line) + 1);
				}//(-1)1603/175896----(+1)6984  11971
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		int npc = 0,apc=0;
		try {
			for(String l : ds.keySet()){
				if(l.split(" ")[0].equals("+1")){apc++;
				}else{npc++;}
				writer.write(l + "\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("ȥ������Ϊ(-1)"+npc+"/" + nc + "----(+1)"+apc+"/"+ac);
		System.out.println("ȥ����ǩΪ-1���ظ�������ɣ����������ļ�"+out);
	}
	
	public void train(String trainfile,String modelfile){
		System.out.println("��ʼѵ��...");
		String[] args = {trainfile,modelfile};
		try {
			svm_train.main(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("ѵ����ɣ�ģ��������ļ�"+modelfile);
	}
	
	public void test(String testfile,String modelfile,String resultfile){
		System.out.println("��ʼԤ��...");
		String[] args = {testfile,modelfile,resultfile};
		try {
			svm_predict.main(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Ԥ����ɣ�Ԥ����������ļ�"+resultfile);
	}
	/*
	public void scale(String inf, String scalef){
		System.out.println("��ʼ���ļ�"+inf+"����ת����-1��1֮��...");
		String[] args = {//"-l","-1",
				//"u","1",
				inf};//,scalef};
		try {
			svm_scale.main(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("����ת������ɣ�������ļ�"+scalef);
	}
	*/
	
	public static void compareSVMFormatFile(String f1,String f2){
		int diff = 0;
		
		try {
			BufferedReader reader1  = new BufferedReader(new FileReader(f1));
			BufferedReader reader2  = new BufferedReader(new FileReader(f2));
			String line1,line2;
			while(true){
				line1 = reader1.readLine();
				line2 = reader2.readLine();
				if(line1 == null || line2 == null)break;
				String[] tks1 = line1.split(" "),tks2 = line2.split(" ");
				if(tks1.length != tks2.length || tks1[0] != tks2[0])diff++;
				else{
					for(int i =1;i < tks1.length;i++){
						String[] iv1 = tks1[i].split(":"),iv2 = tks2[i].split(":");
						if(iv1[0] != iv2[0]){diff++;break;}
						else if(Double.parseDouble(iv1[1]) != Double.parseDouble(iv2[1])){
							diff++;
							break;
						}
					}
				}
			}
			reader1.close();
			reader2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("��ͬ������"+ diff);
	}
	
	public static void main(String... args){
		DataSet ds = new DataSet();
		ds.read("data/t_alibaba_data.txt");
		ds.genTrainDataset("data/train_dataset.txt");
		//DataSet.compareSVMFormatFile("data/train_dataset.txt", "data/0/train_dataset.txt");
		//ds.prune("data/2/train_dataset.txt", "data/2/train_dataset_p.txt");
		ds.genTestDataset("data/test_dataset.txt");
		
		
		//ds.train("data/train_dataset.txt", "data/train.model");
		//ds.test("data/test_dataset.txt", "data/train.model", "data/alltest.svm_result");
		//Result.main();
	}
}
