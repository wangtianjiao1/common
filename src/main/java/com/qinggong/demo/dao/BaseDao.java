package com.qinggong.demo.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BaseDao {
	@Autowired
	@Qualifier("masterJdbcTemplate")
	protected JdbcTemplate masterJdbcTemplate;

	// 判断数据是否存在
	public boolean exist(String sql) {
		try {
			return 1 <= masterJdbcTemplate.queryForObject(sql, Integer.class);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		return false;
	}

	// 查询对象数量
	public Integer count(String sql) {
		try {
			return masterJdbcTemplate.queryForObject(sql, Integer.class);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		return -1;
	}

	// 查找某一个对象
	public Object findOne(String sql, RowMapper<?> rowMapper) {
		try {
			List<?> list = masterJdbcTemplate.query(sql, rowMapper);
			if (null != list && 0 < list.size()) {
				return list.get(0);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		return null;
	}

	// 获取对象列表
	public List<?> find(String sql, RowMapper<?> rowMapper) {
		try {
			List<?> list = masterJdbcTemplate.query(sql, rowMapper);
			if (null != list && 0 < list.size()) {
				return list;
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		return null;
	}

	// 创建对象
	public int create(String sql) {
		try {
			if (1 <= masterJdbcTemplate.update(sql)) {
				return 0;
			}
		} catch (DuplicateKeyException e) {
			e.printStackTrace();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		return -1;
	}

	// 更新对象
	public boolean update(String sql) {
		try {
			return 0 <= masterJdbcTemplate.update(sql);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		return false;
	}
}
