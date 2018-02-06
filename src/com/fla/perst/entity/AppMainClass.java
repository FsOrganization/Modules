package com.fla.perst.entity;

import org.garret.perst.Key;
import org.garret.perst.Persistent;
import org.garret.perst.Storage;
import org.garret.perst.StorageFactory;

public class AppMainClass extends Persistent {
	static public void main(String[] args) {
		Storage db = StorageFactory.getInstance().createStorage();
		db.open("express.configdb", 1024);

		MyRootClass root = (MyRootClass) db.getRoot();
		if (root == null) {
			root = new MyRootClass(db);
			db.setRoot(root);
		}

		MyPersistentClass obj = new MyPersistentClass();
		obj.intKey = 1;
		obj.strKey = "A.B";
		obj.body = "Hello world";

		root.intKeyIndex.put(obj);
		root.strKeyIndex.put(obj);
		root.foreignIndex.put(new Key(1001), obj);
		System.out.println(root.getStorage().getMaxOid());
		db.close();
	}
}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
