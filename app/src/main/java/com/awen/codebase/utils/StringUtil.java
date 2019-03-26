package com.awen.codebase.utils;

import java.util.ArrayList;

public final class StringUtil
{
	/**
	 * 判断字符串是否 不为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str)
	{
		return (null != str && str.length() > 0);
	}

	/**
	 * @param resource
	 *            原始字符串
	 * @param startTag
	 *            要截取目标字符串的开始标签
	 * @param endTag
	 *            要截取目标字符串的结束标签
	 * @return
	 */
	public static String truncateString(String resource, String startTag, String endTag)
	{
		int start = resource.indexOf(startTag);
		int end = resource.indexOf(endTag, start + startTag.length());
		if (start != -1 && end != -1)
		{
			resource = resource.substring(start + startTag.length(), end);
			return resource;
		}
		return null;
	}

	/**
	 * 半角转为全角
	 * 
	 * @param input
	 *            输入
	 * @return
	 */
	public static String ToDBC(String input)
	{
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++)
		{
			if (c[i] == 12288)
			{
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}
	
	
	public static ArrayList<Integer> getIntegerArrayList(String strInput)
	{
		ArrayList<Integer> ret=new ArrayList<Integer>();
		String []shareIDs=strInput.split(",");
		if(null!=shareIDs&&shareIDs.length>0)
		{
			for(int i=0;i<shareIDs.length;i++)
			{
				try {
					ret.add(Integer.valueOf(shareIDs[i]));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}
	
	public static String getAppendIntergerString( ArrayList<Integer> shareIdList)
	{
		if(shareIdList.size()<=0)
		{
			return "";
		}
		
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<shareIdList.size();i++)
		{
			sb.append(shareIdList.get(i));
			sb.append(",");
		}
		if(sb.length()>0)
		{
			sb.deleteCharAt(sb.length()-1);
		}
		
		return sb.toString();
	}


	/***
	 * 根据Unicode编码判断中文汉字和符号
 	 */
	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	/**
	 * 判断中文汉字和符号
 	 */
	public static boolean isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}
	
}
