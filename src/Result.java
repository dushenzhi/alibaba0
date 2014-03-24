import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Result {
	private Map<String, List<String>> rmap = new HashMap<String, List<String>>();
	
	public void genResult(String idFile,String svmResult,String outFile){
		System.out.println("开始处理结果...");
		BufferedWriter writer = null;
		BufferedReader idReader = null,resultReader = null;
		try {
			idReader = new BufferedReader(new FileReader(idFile));
			resultReader= new BufferedReader(new FileReader(svmResult));
			String idLine=null,rLine= null;
			while(true){
				idLine = idReader.readLine();
				rLine = resultReader.readLine();
				if(idLine == null || rLine == null)break;
				if(rLine.trim().equals("-1"))continue;//负类不统计
				String[] ubid = idLine.split("-----")[0].split(",");
				String uid= ubid[0],bid=ubid[1];
				if(!rmap.containsKey(uid)){
					List<String> bids = new ArrayList<String>();
					bids.add(bid);
					rmap.put(uid, bids);
				}else{
					List<String> bids = rmap.get(uid);
					bids.add(bid);
					rmap.put(uid, bids);
				}
			}
			idReader.close();
			resultReader.close();
			
			
			writer = new BufferedWriter(new FileWriter(outFile));
			for(String uid : rmap.keySet()){
				writer.write(uid + "\t");
				int flag = 0;
				for(String bid : rmap.get(uid)){
					if(flag == 0)writer.write(bid);
					flag++;
					writer.write(","+bid);
				}
				writer.write("\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("处理完成！提交结果输出到文件"+outFile);
	}
	
	
	public static void main(String... args){
		Result r = new Result();
		r.genResult("data/test_dataset.txt.uid", "data/alltest.svm_result", "data/result.txt");
		//r.genResult("data/1/test_dataset.txt.uid", "data/1/alltest_p.svm_result", "data/1/result_p.txt");
	}
}
