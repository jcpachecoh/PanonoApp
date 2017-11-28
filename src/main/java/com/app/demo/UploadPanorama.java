package com.app.demo;

import java.sql.Timestamp;

public class UploadPanorama {
	
	private int id;
	private int numberPanoramas;
	private Timestamp tmp;
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNumberPanoramas() {
		return numberPanoramas;
	}
	public void setNumberPanoramas(int numberPanoramas) {
		this.numberPanoramas = numberPanoramas;
	}
	public Timestamp getTmp() {
		return tmp;
	}
	public void setTmp(Timestamp tmp) {
		this.tmp = tmp;
	}
}
