package com.real.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class MemberVo {
	
	private int idx;
	private String bizno;
	private String password;
	private String ceoname;
	private String ceocall;
	

}