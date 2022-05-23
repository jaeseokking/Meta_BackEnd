package com.real.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.real.dao.masterDao;
import com.real.dto.MemberVo;
import com.real.dto.NoticeVo;
import com.real.util.AES256;
import com.real.util.Criteria;
import com.real.util.PageMaker;

@Service
public class masterServiceImpl implements masterService {
	
	@Autowired
	private masterDao masterdao;
	
	//암호화용 키
	private static final String aesKey = "p8i23e4ric6y7u4ero0pa1s91k1qbv36";
	private static AES256 AES = new AES256("AES/CBC/PKCS5Padding", aesKey.substring(0,16), aesKey.substring(0,16).getBytes());
	
	
	/**
	 * Master가 관리자 추가
	 */
	@Override
	public int AdminA(MemberVo member) {
		// TODO Auto-generated method stub
		
		String password = member.getBizno();
		String en_password = AES.encryptStringToBase64(password);
		member.setPassword(en_password);
		
		int result = masterdao.AdminA(member);
		
		return result;
	}


	/**
	 * Master에서 관리가 계정 검색
	 */
	@Override
	public Map<String, Object> AdminS(String keyword) {
		// TODO Auto-generated method stub
		
		Map<String,Object> result = new HashMap<String,Object>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		list = masterdao.AdminS(keyword);
		
		result.put("list", list);
		
		return result;
	}


	/**
	 * Master 관리자 삭제
	 */
	@Override
	public int AdminD(ArrayList ListIDX) {
		// TODO Auto-generated method stub
		
//		String[] idxs = ListIDX.split(",");
		
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("idx", ListIDX);
		
		return masterdao.AdminD(param);
	}


	/**
	 * admin list 가져오기 페이징 처리된걸로
	 */
	@Override
	public Map<String, Object> AdminG(String page) {
		// TODO Auto-generated method stub
		
		Map<String,Object> result = new HashMap<String,Object>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> param = new HashMap<String,Object>();
		
	    PageMaker pageMaker = new PageMaker();
	    
	    int pages = 0;
	    if(page == null) {
	    	
	    }else {
	    	pages = Integer.parseInt(page);
	    }
	    
	    Criteria cri = new Criteria();
	    cri.setPage(pages);
	    pageMaker.setCri(cri);
	    int total = masterdao.countTotalAdmin();
	    pageMaker.setTotalCount(total);
	    
	    param.put("Page", cri.getPage());
	    param.put("PageStart", cri.getPageStart());
	    param.put("PerPageNum", cri.getPerPageNum());
		
		
		list = masterdao.AdminG(cri);
		
		result.put("list", list);
		result.put("cri", cri);
		result.put("pageMaker", pageMaker);
		
		return result;
	}

	
	/**
	 * 선택된 공지사항 가져오기
	 */
	@Override
	public MemberVo AdminO(String idx) {
		// TODO Auto-generated method stub
		return masterdao.AdminO(idx);
	}
	
	
	/**
	 *공지사항 리스트 가져오기
	 */
	@Override
	public Map<String, Object> NoticeL(String page) {
		// TODO Auto-generated method stub
		Map<String,Object> result = new HashMap<String,Object>();
		
		Map<String,Object> param = new HashMap<String,Object>();
		
		List<NoticeVo> list = new ArrayList<NoticeVo>();
		
	    PageMaker pageMaker = new PageMaker();
	    
	    int pages = 0;
	    if(page == null) {
	    	
	    }else {
	    	pages = Integer.parseInt(page);
	    }
	    
	    Criteria cri = new Criteria();
	    cri.setPage(pages);
	    pageMaker.setCri(cri);
	    int total = masterdao.countTotalNotice();
	    pageMaker.setTotalCount(total);
	    
	    param.put("Page", cri.getPage());
	    param.put("PageStart", cri.getPageStart());
	    param.put("PerPageNum", cri.getPerPageNum());
		
		list = masterdao.NoticeL(cri);
		
		result.put("list", list);
		result.put("cri", cri);
		result.put("pageMaker", pageMaker);
		
		return result;
	}


	/**
	 * Notice Insert 하기
	 */
	@Override
	public int NoticeI(NoticeVo notice) {
		// TODO Auto-generated method stub
		return masterdao.NoticeI(notice);
	}


	/**
	 * 공지사항 Update
	 */
	@Override
	public int NoticeU(NoticeVo notice) {
		// TODO Auto-generated method stub
		return  masterdao.NoticeU(notice);
	}


	/**
	 * 공지사항 DELETE
	 */
	@Override
	public int NoticeD(ArrayList listIDX) {
		// TODO Auto-generated method stub
		int result = 0; 
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("idx", listIDX);
		
		result = masterdao.NoticeD(param);
		
		return result;
	}


	/**
	 * 선택된 공지사항 가져오기
	 */
	@Override
	public NoticeVo NoticeO(String idx) {
		// TODO Auto-generated method stub
		return masterdao.NoticeO(idx);
	}


	/**
	 * 공지사항 검색
	 */
	@Override
	public Map<String, Object> NoticeS(String keyword) {
		// TODO Auto-generated method stub
		List<NoticeVo> list = masterdao.NoticeS(keyword);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("list", list);
		return result;
	}


	/**
	 *문의사항 리스트 가져오기
	 */
	@Override
	public Map<String, Object> EnquiryL(String page) {
		// TODO Auto-generated method stub
		Map<String,Object> result = new HashMap<String,Object>();
		
		Map<String,Object> param = new HashMap<String,Object>();
		
		List<NoticeVo> list = new ArrayList<NoticeVo>();
		
	    PageMaker pageMaker = new PageMaker();
	    
	    int pages = 0;
	    if(page == null) {
	    	
	    }else {
	    	pages = Integer.parseInt(page);
	    }
	    
	    Criteria cri = new Criteria();
	    cri.setPage(pages);
	    pageMaker.setCri(cri);
	    int total = masterdao.countTotalEqueryL();
	    pageMaker.setTotalCount(total);
	    
	    param.put("Page", cri.getPage());
	    param.put("PageStart", cri.getPageStart());
	    param.put("PerPageNum", cri.getPerPageNum());
		
		list =  masterdao.EnquiryL(cri);
		
		result.put("list", list);
		result.put("cri", cri);
		result.put("pageMaker", pageMaker);
		
		return result;
	}


	/**
	 * 문의사항 한개 가져오기
	 */
	@Override
	public Map<String, Object> EnquiryO(String idx) {
		// TODO Auto-generated method stub
		return masterdao.EnquiryO(idx);
	}


	/**
	 *문의사항 답글 남기기
	 */
	@Override
	public int EnquiryR(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return masterdao.EnquiryR(param);
	}

	/**
	 * 관리자 업데이트
	 */
	@Override
	public int AdminU(MemberVo member) {
		// TODO Auto-generated method stub
		return masterdao.AdminU(member);
	}


	@Override
	public  Map<String,Object> EnquiryF(Map<String, Object> param) {
		// TODO Auto-generated method stub
		 Map<String,Object> map = new HashMap<String,Object>();
		 List<Map<String,Object>> list = masterdao.EnquiryF(param);
		 map.put("list", list);
		return map;
	}


	@Override
	public int LoginM(Map<String, Object> param) {
		// TODO Auto-generated method stub
		String pass = (String)param.get("pass");
		String npass = AES.encryptStringToBase64(pass);
		param.put("npass", npass);
		
		return masterdao.LoginM(param);
		
	}
	

}
