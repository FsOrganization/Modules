package org.jeewx.api.wxuser;

import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.wxbase.wxtoken.JwTokenAPI;
import org.jeewx.api.wxuser.user.JwUserAPI;

public class Test {

	public static void main(String[] args) {
		try 
		{
			String s = JwTokenAPI.getAccessToken("wxc016c959d3870b52","c7843e15394196bb9840b03eb1a03cfc");
			System.out.println(s);
			System.out.println(JwUserAPI.getWxuser(s, "13980051144").getNickname());
			System.out.println(s);
		} catch (WexinReqException e) {
			e.printStackTrace();
		}
	}
}
