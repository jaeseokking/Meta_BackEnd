package com.real.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class masterDao {
	@Autowired
	private SqlSessionTemplate sqlSession;
	private static String namespace="master_mapper";
}
