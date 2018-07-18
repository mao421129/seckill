package com.mao.seckill_02.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mao.seckill_02.domain.User;

@Mapper
public interface UserMapper {

	@Select("Select * from user where id = ${id}")
	User getById(@Param("id") Long id);
	
	@Update("Update user set password = #{password} where id = ${id}")
	int update(@Param("id")Long id, @Param("password")String password);
}
