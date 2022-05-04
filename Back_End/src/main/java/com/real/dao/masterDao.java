package com.real.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.real.dto.MemberVo;
import com.real.dto.NoticeVo;
import com.real.util.Criteria;

@Repository
public class masterDao {
	@Autowired
	private SqlSessionTemplate sqlSession;
	private static String namespace="master_mapper";
	
	
	/**
	 * master 관리자 추가하기
	 * @param member
	 * @return
	 */
	public int AdminA(MemberVo member) {
		// TODO Auto-generated method stub
		return sqlSession.insert(namespace+".AdminA",member);
	}


	/**
	 * master 가 관리가 search 
	 * @param keyword
	 * @return
	 */
	public List<Map<String, Object>> AdminS(String keyword) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace+".AdminS",keyword);
	}


	/**master 관리자 지우기
	 * @param param
	 * @return
	 */
	public int AdminD(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return sqlSession.delete(namespace+".AdminD",param);
	}


	/**
	 * 관리자 List 가져오기
	 * @param cri
	 * @return
	 */
	public List<Map<String, Object>> AdminG(Criteria cri) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace+".AdminG",cri);
	}

	
	/**
	 * 관리자 전체수 가져오기 
	 * @return
	 */
	public int countTotalAdmin() {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace+".countTotalAdmin");
	}


	/**
	 * 공지사항 전체 가져오기
	 * @return
	 */
	public int countTotalNotice() {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace+".countTotalNotice");
	}

	/**
	 * 관리자정보  한개 가져오기
	 * @param idx
	 * @return
	 */
	public MemberVo AdminO(String idx) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace+".AdminO",idx);
	}

	/**
	 * 공지사항 리스트 가져오기
	 * @param cri
	 * @return
	 */
	public List<NoticeVo> NoticeL(Criteria cri) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace+".NoticeL",cri);
	}


	/**
	 * 공지사항 Insert
	 * @param notice
	 * @return
	 */
	public int NoticeI(NoticeVo notice) {
		// TODO Auto-generated method stub
		return sqlSession.insert(namespace+".NoticeI",notice);
	}


	/**
	 * 공지사항 Update
	 * @param notice
	 * @return
	 */
	public int NoticeU(NoticeVo notice) {
		// TODO Auto-generated method stub
		return sqlSession.update(namespace+".NoticeU",notice);
	}


	/**
	 * 공지사항 DELETE
	 * @param param
	 * @return
	 */
	public int NoticeD(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return sqlSession.delete(namespace+".NoticeD",param);
	}


	/**
	 * 공지사항 가져오기
	 * @param idx
	 * @return
	 */
	public NoticeVo NoticeO(String idx) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace+".NoticeO", idx);
	}


	/**
	 * 공지사항 검색하기
	 * @param keyword
	 * @return
	 */
	public List<NoticeVo> NoticeS(String keyword) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace+".NoticeS",keyword);
	}



}
