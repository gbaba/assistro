package com.assistro.util;


import java.util.HashMap;
import java.util.Map;


public class Util {


//	public static final String url="http://localhost:8888";
//	public static final String url="https://virtismts.appspot.com";
	public static final String url="https://iamassistro.appspot.com";
//	public static final String url="http://www.assistro.com";
//	public static final String emailUrl = "assistrolivechat.appspotchat.com";
	public static final String emailUrl = "iamassistro.appspotchat.com";

	public static final Map<String, String> plan = new HashMap<String, String>();
    static {
    	plan.put("Flying_Solo","18479");
    	plan.put("Small_Business","18476");
    	plan.put("Medium","18477");
    	plan.put("Big_Business","18478");
    	plan.put("Enterprise","19114");
    	plan.put("Promotion_Medium","20484");
    	
    }
    
	public static final Map<String, String> inversePlan = new HashMap<String, String>();
    static {
    	inversePlan.put("18479","Free");
    	inversePlan.put("18475","Solo");
    	inversePlan.put("18477","Medium");
    	inversePlan.put("18476","Small Business");
    	inversePlan.put("18478","Big Business");
    	
    }
    
    public static final Map<String, String> planAgent = new HashMap<String, String>();
    static {
    	planAgent.put("Flying Solo","1");
    	planAgent.put("Small Business","2");
    	planAgent.put("Medium","4");
    	planAgent.put("Big Business","8");
    	planAgent.put("Enterprise","20");
    	planAgent.put("Promotion Medium","4");
    }
    
    
}
