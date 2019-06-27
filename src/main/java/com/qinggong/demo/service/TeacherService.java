package com.qinggong.demo.service;

import java.util.List;
import com.qinggong.demo.entity.Teacher;

/**
 * 教师接口
 * 
 */
public interface TeacherService {
	// 注册
	public int register(String username, String password, String nickname);

	// 登录
	public boolean login(String username, String password);

	// 查询单个教师
	public Teacher queryById(int id);

	// 教师列表
	public List<Teacher> queryList();

	// 依据姓名获得教师列表
	public List<Teacher> queryByNickname(String nickname);

	// 从redis中获取数据
	public Object getObject(String key);

	// 保存数据到redis
	public void saveObject(String key, Object value, Long expire);
}
