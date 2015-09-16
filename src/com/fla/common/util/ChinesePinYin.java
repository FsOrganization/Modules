package com.fla.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public  final class ChinesePinYin {

	private static final HanyuPinyinOutputFormat hypyof = new HanyuPinyinOutputFormat();
	static 
	{
		hypyof.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		hypyof.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		hypyof.setVCharType(HanyuPinyinVCharType.WITH_V);
	}

	/**
	 * 
	 * @param str
	 * @return 返回汉字的全拼
	 */
	public static String spelling(String chinese) {
		return toPinYin(chinese, false);
	}

	/**
	 * 
	 * @param str
	 * @return 返回汉字拼音首字母
	 */
	public static String initials(String chinese) {
		return toPinYin(chinese, true);
	}
	
	/**
	 * 
	 * @param chinese
	 * @param onlyInitial
	 *  是否只获取拼音首字母
	 * @return 返回汉字的拼音，其中包含的非汉字字符原样返回
	 */
	private static String toPinYin(String chinese, boolean onlyInitial) {
		if (chinese == null || chinese.length() == 0) 
		{
			return chinese;
		}
		char[] hanzi = new char[chinese.length()];
		for (int i = 0; i < chinese.length(); i++) 
		{
			hanzi[i] = chinese.charAt(i);
		}

		StringBuilder sb = new StringBuilder();
		try {
			for (int i = 0; i < hanzi.length; i++) 
			{
				String[] py = PinyinHelper.toHanyuPinyinStringArray(hanzi[i],hypyof);
				if (py != null && py.length >= 1 && py[0] != null && py[0].length() > 0) 
				{
					sb.append(onlyInitial ? py[0].substring(0, 1) : py[0]);
				} else {
					sb.append(hanzi[i]);
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

}
