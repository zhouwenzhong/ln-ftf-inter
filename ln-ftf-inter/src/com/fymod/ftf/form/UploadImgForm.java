package com.fymod.ftf.form;

import org.springframework.web.multipart.MultipartFile;

public class UploadImgForm {

	private String id;

	private String type;

	private MultipartFile file;

	public String getId() {
		return id;
	}

	public void setId(String custid) {
		this.id = custid;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
