package com.bayside.app.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;


public class Descartes {
	/**
	 * 
	 * <p>方法名称：descartes</p>
	 * <p>方法描述：笛卡尔积算法</p>
	 * @param dimValue
	 * @param result
	 * @param layer
	 * @param curList
	 * @author Administrator
	 * @since  2016年8月22日
	 * <p> history 2016年8月22日 Administrator  创建   <p>
	 */
	public static void descartes1(List<List<String>> dimValue, List<List<String>> result, int layer, List<String> curList)
    {
        //大于一个集合时：
        if (layer < dimValue.size() - 1)
        {
            //大于一个集合时，第一个集合为空
        	System.out.println(dimValue.get(layer).size());
            if (dimValue.get(layer).size() == 0){
            	//result.add(curList);
            	descartes1(dimValue, result, layer + 1, curList);
            }
            else
            {
                for (int i = 0; i < dimValue.get(layer).size(); i++)
                {
                	 List<String> list = new ArrayList<String>(curList);  
                	 list.add(dimValue.get(layer).get(i));  
                	 descartes1(dimValue, result, layer + 1, list);
                }
            }
        }
        //只有一个集合时：
        else if (layer == dimValue.size() - 1)
        {
            //只有一个集合，且集合中没有元素
            if (dimValue.get(layer).size() == 0){
             //   result.add(curList);
            }
            //只有一个集合，且集合中有元素时：其笛卡尔积就是这个集合元素本身
            else
            {
                for (int i = 0; i < dimValue.get(layer).size(); i++)
                {
                	List<String> list = new ArrayList<String>(curList);  
                	if(dimValue.get(layer).get(i)!=null&&!"".equals(dimValue.get(layer).get(i))){
                		list.add(dimValue.get(layer).get(i));  
                	}
                    result.add(list); 
                }
            }
        }
    }
	/**
	 * 
	 * <p>方法名称：descartes</p>
	 * <p>方法描述：笛卡尔积算法</p>
	 * @param dimValue
	 * @param result
	 * @param layer
	 * @param curList
	 * @author Administrator
	 * @since  2016年8月22日
	 * <p> history 2016年8月22日 Administrator  创建   <p>
	 */
	public static void descartes(List<List<Map<String, String>>> dimValue, List<Map<String, String>> result, int layer, Map<String, String> curMap)
	{
		//大于一个集合时：
		if (layer < dimValue.size() - 1)
		{
			//大于一个集合时，第一个集合为空
			System.out.println(dimValue.get(layer).size());
			if (dimValue.get(layer).size() == 0){
				Map<String, String> map = new HashMap<String,String>();
				map.putAll(curMap);
				descartes(dimValue, result, layer + 1, map);
			}
			else
			{
				for (int i = 0; i < dimValue.get(layer).size(); i++)
				{
					Map<String, String> map = new HashMap<String,String>();
					map.putAll(curMap);
					map.putAll(dimValue.get(layer).get(i)); 	
					descartes(dimValue, result, layer + 1, map);
				}
			}
		}
		//只有一个集合时：
		else if (layer == dimValue.size() - 1)
		{
			//只有一个集合，且集合中没有元素
			if (dimValue.get(layer).size() == 0){
				Map<String, String> map = new HashMap<String,String>();
				map.putAll(curMap);
				   result.add(map);
			}
			//只有一个集合，且集合中有元素时：其笛卡尔积就是这个集合元素本身
			else
			{
				for (int i = 0; i < dimValue.get(layer).size(); i++)
				{
					Map<String, String> map = new HashMap<String,String>();
					map.putAll(curMap);
					map.putAll(dimValue.get(layer).get(i)); 					
					result.add(map); 
				}
			}
		}
	}
	public static List<Map<String, String>> stringToMap(String wordName,String word){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		if(word!=null&&!"".equals(word)){
		String[] words = word.split(",|，|\\s+");
		for (String string : words) {
			Map<String, String> map = new HashMap<>();
			map.put(wordName, string);
			list.add(map);
		}
		}
		return list;
		
	}
	public static void main(String[] args) throws IOException {
		//Map<String, Object> subject = mapper.readValue(subjectAttr,HashMap.class);
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> subject = new HashMap<String,Object>();
		subject.put("region_word", "中国         南海");
		subject.put("subject_word", "夺冠             冠军");
		/*subject.put("event_word", "仲裁 领土 侵占");
		subject.put("ambiguity_word", "");*/
		//List<List<String>> dimvalue  = new ArrayList<List<String>>();
		System.out.println(stringToMap("region_word", (String)subject.get("region_word")));
		
		/*List<String> region_word = new ArrayList<String>(Arrays.asList(((String)subject.get("region_word")).split(" ")));
		List<String> subject_word = new ArrayList<String>(Arrays.asList(((String)subject.get("subject_word")).split(" ")));
		List<String> event_word = new ArrayList<String>(Arrays.asList(((String)subject.get("event_word")).split(" ")));
		List<String> ambiguity_word = new ArrayList<String>(Arrays.asList(((String)subject.get("ambiguity_word")).split(" ")));
		dimvalue.add(region_word);
		dimvalue.add(subject_word);
		dimvalue.add(event_word);
		dimvalue.add(ambiguity_word);
		List<List<String>> result = new ArrayList<List<String>>();
		
		descartes(dimvalue, result, 0, new ArrayList<String>());*/
		
		/*
			String sttr = mapper.writeValueAsString(result);
			System.out.println(sttr);
			try {
				List<List<String>> list = mapper.readValue(sttr, List.class);
				//System.out.println(list.get(0).get(0));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		*/
		List<List<Map<String, String>>> dimValue  = new ArrayList<List<Map<String, String>>>();
		List<Map<String, String>> region_word = stringToMap("region_word", (String)subject.get("region_word"));
		List<Map<String, String>> subject_word = stringToMap("subject_word", (String)subject.get("subject_word"));
		List<Map<String, String>> event_word = stringToMap("event_word", (String)subject.get("event_word"));
		List<Map<String, String>> ambiguity_word = stringToMap("ambiguity_word", (String)subject.get("ambiguity_word"));
		dimValue.add(region_word);
		dimValue.add(subject_word);
		dimValue.add(event_word);
		dimValue.add(ambiguity_word);
		List<Map<String, String>> result = new ArrayList<Map<String,String>>();
		descartes(dimValue, result, 0, new HashMap<String,String>());
		System.out.println(mapper.writeValueAsString(result));
	}
}
