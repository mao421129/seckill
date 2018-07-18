package com.mao.seckill_02.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.mao.seckill_02.domain.User;

@Mapper
public interface LoginMapper {

	@Select("Select * From user where id = #{id}")
	User getUserById(Long id);
}
