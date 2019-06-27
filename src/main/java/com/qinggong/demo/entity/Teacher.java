package com.qinggong.demo.entity;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 * 教师实体类
 * 
 */
@Component
public class Teacher implements Serializable, RowMapper<Teacher> {
	private static final long serialVersionUID = -7349758660714907784L;

	private int id;// 教师编码
	private int cid;// 班级编码
	private String username;// 教师登录名
	private String password;// 登录密码
	private String nickname;// 昵称
	private int gender;// 性别
	private String avatar;// 头像

	public Teacher() {
	}

	public Teacher(int id, int cid, String username, String password, String nickname, int gender, String avatar) {
		this.id = id;
		this.cid = cid;
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.gender = gender;
		this.avatar = avatar;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Override
	public Teacher mapRow(ResultSet result, int arg1) throws SQLException {
		Teacher teacher = new Teacher();

		teacher.setId(result.getInt("id"));
		teacher.setCid(result.getInt("cid"));
		teacher.setUsername(result.getString("username"));
		teacher.setPassword(result.getString("password"));
		teacher.setNickname(result.getString("nickname"));
		teacher.setGender(result.getInt("gender"));
		teacher.setAvatar(result.getString("avatar"));

		return teacher;
	}

	@Override
	public String toString() {
		return String.format("{\"id\":%d, \"cid\":%d, \"username\":\"%s\", \"password\":\"%s\", \"nickname\":\"%s\", \"gender\":%d, \"avatar\":\"%s\"}", 
				id, cid, username, password, nickname, gender, avatar);
	}
}
