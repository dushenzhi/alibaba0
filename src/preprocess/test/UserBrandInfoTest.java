package preprocess.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import preprocess.UserBrandInfo;
import preprocess.YDTime;

public class UserBrandInfoTest {
	private UserBrandInfo ubi;
	@Test
	public void test() {
		
	}
	
	@Test
	public void testGenFeatureWithLabel(){
		ubi = new UserBrandInfo("0","000");
		List<Integer> actions = new ArrayList<Integer>();
		List<YDTime> atime = new ArrayList<YDTime>();
		actions.add(0);atime.add(new YDTime("6月15日"));
		actions.add(0);atime.add(new YDTime("6月15日"));
		actions.add(3);atime.add(new YDTime("6月15日"));
		//actions.add(1);atime.add(new YDTime("6月17日"));
		ubi.setActions(actions);
		ubi.setAtime(atime);
		ubi.setBuy(false);
		System.out.println(ubi.genFeature(true));
		
		ubi = new UserBrandInfo("1","111");
		actions = new ArrayList<Integer>();
		atime = new ArrayList<YDTime>();
		actions.add(0);atime.add(new YDTime("6月15日"));
		actions.add(0);atime.add(new YDTime("5月16日"));
		actions.add(3);atime.add(new YDTime("6月17日"));
		actions.add(1);atime.add(new YDTime("6月18日"));
		actions.add(2);atime.add(new YDTime("6月19日"));
		actions.add(1);atime.add(new YDTime("6月20日"));
		ubi.setActions(actions);
		ubi.setAtime(atime);
		ubi.setBuy(true);
		ubi.getBuyTime().add(new YDTime("6月18日"));
		ubi.getBuyTime().add(new YDTime("6月20日"));
		System.out.println(ubi.genFeature(true));
		
		System.out.println(ubi.genFeature4AllTest());
		
	}

	
	
}
