package com.fla.perst;

import java.util.ArrayList;

import org.garret.perst.Index;
import org.garret.perst.Storage;
import org.garret.perst.StorageFactory;

import com.fla.common.entity.CustomerInfo;
import com.fla.perst.entity.MyRootClass;

public class PerstStoring {

	public PerstStoring() {
	}
	
	public static void init(){
		Storage db = StorageFactory.getInstance().createStorage();
		 db.open("express.configdb", 1024);
		 // do something with the database
		 MyRootClass root = (MyRootClass)db.getRoot();// get storage root
		 if (root == null) {
			 root = new MyRootClass(db);
			 db.setRoot(root);
		 }
		 db.close();
	}

	public static void main(String[] args) {
		init();
	}
}
