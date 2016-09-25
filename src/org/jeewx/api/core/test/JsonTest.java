package org.jeewx.api.core.test;

import java.util.ArrayList;
import java.util.List;

import org.jeewx.api.core.req.model.kfaccount.KfcustomSend;
import org.jeewx.api.core.req.model.kfaccount.MsgArticles;
import org.jeewx.api.core.req.model.kfaccount.MsgNews;

import com.google.gson.Gson;

public class JsonTest {

	public static void main(String[] args){
		
		KfcustomSend s = new KfcustomSend();
		s.setAccess_token("toke----");
		s.setMsgtype("news");
		s.setTouser("touser----");
		MsgNews n = new MsgNews();
		List<MsgArticles> lst = new ArrayList<MsgArticles>();
		MsgArticles aa = new MsgArticles();
		aa.setDescription("111-----");
		aa.setPicurl("url-----111");
		aa.setUrl("uuuu----11");
		aa.setTitle("tttt----1111");
		lst.add(aa);
		aa = new MsgArticles();
		aa.setDescription("2222-----");
		aa.setPicurl("url-----222");
		aa.setUrl("uuuu----2222");
		aa.setTitle("tttt----222");
		lst.add(aa);
		n.setArticles(lst);
		s.setNews(n);
		
		Gson gson = new Gson();
		String json = gson.toJson(s);
		System.out.println(json);
	}
}
