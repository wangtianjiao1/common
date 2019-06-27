package com.qinggong.demo.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qinggong.demo.dao.BaseDao;
import com.qinggong.demo.dao.RedisDao;
import com.qinggong.demo.entity.Teacher;
import com.qinggong.demo.service.TeacherService;

/**
 * 教师接口实现类
 * 
 */
@SuppressWarnings("unchecked")
@Service("teacherService")
public class TeacherServiceImpl implements TeacherService {
	@Autowired
	private BaseDao basedao;
	@Autowired
	private RedisDao redisDao;

	// 教师注册
	@Override
	public int register(String username, String password, String nickname) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO teacher(username, password, nickname) VALUES('");
		sb.append(username).append("', '");
		sb.append(password).append("', '");
		sb.append(nickname).append("')");

		return basedao.create(sb.toString());
	}

	// 教师登录
	@Override
	public boolean login(String username, String password) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT id, cid, username, password, nickname, gender, avatar FROM teacher WHERE username = '");
		sb.append(username).append("' AND password = '").append(password).append("'");

		Object obj = basedao.findOne(sb.toString(), new Teacher());
		if (null == obj) {
			return false;
		}

		return true;
	}

	// 依据编码查询教师
	@Override
	public Teacher queryById(int id) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT id, cid, username, password, nickname, gender, avatar FROM teacher WHERE id = ");
		sb.append(id);

		Object obj = basedao.findOne(sb.toString(), new Teacher());
		if (null == obj) {
			return null;
		}

		return (Teacher) obj;
	}

	// 查询教师列表
	@Override
	public List<Teacher> queryList() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT id, cid, username, password, nickname, gender, avatar FROM teacher");

		Object obj = basedao.find(sb.toString(), new Teacher());
		if (null == obj) {
			return null;
		}

		return (List<Teacher>) obj;
	}

	// 依据名字获得教师列表
	@Override
	public List<Teacher> queryByNickname(String nickname) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT id, cid, username, password, nickname, gender, avatar FROM teacher WHERE nickname LIKE '%");
		sb.append(nickname).append("%'");

		Object obj = basedao.find(sb.toString(), new Teacher());
		if (null == obj) {
			return null;
		}

		return (List<Teacher>) obj;
	}

	@Override
	public Object getObject(String key) {
		return redisDao.get(key);
	}

	@Override
	public void saveObject(String key, Object value, Long expire) {
		redisDao.set(key, value, expire);
	}
}
