package com.real.dao;

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
}
