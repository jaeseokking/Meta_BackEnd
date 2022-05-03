package com.real.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.real.dto.MemberVo;

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
}
