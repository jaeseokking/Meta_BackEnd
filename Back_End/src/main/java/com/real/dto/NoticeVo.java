package com.real.dto;

import java.sql.Timestamp;
import java.util.Date;

import lombok.Data;

@Data
public class NoticeVo {
	
	private int idx;
	private String title;
	private String content;
	private int permission;
	private Timestamp date;

}
