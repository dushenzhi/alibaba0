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
		actions.add(0);atime.add(new YDTime("6��15��"));
		actions.add(0);atime.add(new YDTime("6��15��"));
		actions.add(3);atime.add(new YDTime("6��15��"));
		//actions.add(1);atime.add(new YDTime("6��17��"));
		ubi.setActions(actions);
		ubi.setAtime(atime);
		ubi.setBuy(false);
		System.out.println(ubi.genFeature(true));
		
		ubi = new UserBrandInfo("1","111");
		actions = new ArrayList<Integer>();
		atime = new ArrayList<YDTime>();
		actions.add(0);atime.add(new YDTime("6��15��"));
		actions.add(0);atime.add(new YDTime("5��16��"));
		actions.add(3);atime.add(new YDTime("6��17��"));
		actions.add(1);atime.add(new YDTime("6��18��"));
		actions.add(2);atime.add(new YDTime("6��19��"));
		actions.add(1);atime.add(new YDTime("6��20��"));
		ubi.setActions(actions);
		ubi.setAtime(atime);
		ubi.setBuy(true);
		ubi.getBuyTime().add(new YDTime("6��18��"));
		ubi.getBuyTime().add(new YDTime("6��20��"));
		System.out.println(ubi.genFeature(true));
		
		System.out.println(ubi.genFeature4AllTest());
		
	}

	
	
}
