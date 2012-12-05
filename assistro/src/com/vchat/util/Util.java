package com.vchat.util;

import java.util.HashMap;
import java.util.Map;

public class Util {
	
//	public static final String url="http://localhost:8888";
//	public static final String url="https://iamassistro.appspot.com";
	public static final String url="http://www.assistro.com";
	public static final String emailUrl = "assistrolivechat.appspotchat.com";
//	public static final String emailUrl = "iamassistro.appspotchat.com";

	public static final Map<String, String> plan = new HashMap<String, String>();
    static {
    	plan.put("Flying_Solo","19487");
    	plan.put("Small_Business","19488");
    	plan.put("Medium","19489");
    	plan.put("Big_Business","19490");
    	plan.put("Promotion_Medium","20483");
    	plan.put("Small_Business_Chat","21161");
    	plan.put("Medium_Chat","21162");
    	plan.put("Big_Bussiness_Chat","21163");	
    }
    
	public static final Map<String, String> inversePlan = new HashMap<String, String>();
    static {
    	inversePlan.put("19487","Free");
    	inversePlan.put("19488","Solo");
    	inversePlan.put("19489","Medium");
    	inversePlan.put("19490","Small Business");
    	inversePlan.put("19491","Big Business");
    	inversePlan.put("20483","Promotion Medium");
    	
    }
    
    public static final Map<String, String> planAgent = new HashMap<String, String>();
    static {
    	planAgent.put("Flying Solo","1");
    	planAgent.put("Small Business","2");
    	planAgent.put("Medium","4");
    	planAgent.put("Big Business","8");
    	planAgent.put("Small Business Plus","10");
    	planAgent.put("Medium Business Plus","10");
    	planAgent.put("Big Business Plus","10");
    	planAgent.put("Promotion Medium","10");
    }
    
    
}
