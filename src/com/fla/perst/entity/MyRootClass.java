package com.fla.perst.entity;

import org.garret.perst.FieldIndex;
import org.garret.perst.Index;
import org.garret.perst.Persistent;
import org.garret.perst.Storage;

public class MyRootClass extends Persistent {

	public MyRootClass() {
	}

	public FieldIndex<MyPersistentClass> intKeyIndex;
	public FieldIndex<MyPersistentClass> strKeyIndex;
	public Index<MyPersistentClass> foreignIndex;

	public MyRootClass(Storage db) {
		super(db);
		intKeyIndex = db.<MyPersistentClass> createFieldIndex(MyPersistentClass.class,"intKey",true);
		strKeyIndex = db.<MyPersistentClass> createFieldIndex(MyPersistentClass.class,"strKey",false);
		foreignIndex = db.<MyPersistentClass> createIndex(int.class,false);
	}

}
