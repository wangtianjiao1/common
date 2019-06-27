package com.qinggong.demo.controller;

import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qinggong.demo.entity.Teacher;
import com.qinggong.demo.service.TeacherService;
import com.qinggong.demo.service.utils.ResultUtils;

/**
 * 教师控制器
 * 
 */
@RestController
public class TeacherController {
	@Autowired
	private TeacherService teacherService;

	// 发送验证码
	@PostMapping(value = "/smscode")
	public JSONObject smscode(String username) {
		int code = new Random().nextInt(999999);
		if (code < 100000) {
			code = code + 100000;
		}
		teacherService.saveObject(username, String.valueOf(code), 30000L);

		return ResultUtils.jsonResult(new JSONArray(), new JSONObject(), String.valueOf(code));
	}

	// 校验验证码
	@PostMapping(value = "/checkcode")
	public JSONObject checkcode(String username, String smscode) {
		Object object = teacherService.getObject(username);
		if (null == object) {
			return ResultUtils.failure(10003, "resource not found");
		}

		String code = (String) object;
		if (!code.equalsIgnoreCase(smscode)) {
			return ResultUtils.failure(10004, "validate code error");
		}

		return ResultUtils.success();
	}

	// 教师注册
	@PostMapping(value = "/register")
	public JSONObject register(String username, String password, String nickname) {
		if (null == username || null == password) {
			return ResultUtils.failure(10002, "params required");
		}
		if (null == nickname) {
			nickname = "dogegg";
		}

		int flag = teacherService.register(username, password, nickname);
		System.out.println(flag);

		return ResultUtils.success();
	}

	// 教师登录
	@GetMapping(value = "/login")
	public JSONObject login(String username, String password) {
		if (null == username || null == password) {
			return ResultUtils.failure(10002, "params required");
		}

		boolean flag = teacherService.login(username, password);
		System.out.println(flag);

		return ResultUtils.success();
	}

	// 依据编码查询教师
	@GetMapping(value = "/get")
	public JSONObject get(int id) {
		if (0 == id || -1 == id) {
			return ResultUtils.failure(10002, "params required");
		}

		Teacher teacher = teacherService.queryById(id);
		if (null == teacher) {
			return ResultUtils.failure(10003, "resource not found");
		}

		return ResultUtils.jsonResult(new JSONArray(), JSONObject.parseObject(teacher.toString()), "");
	}

	// 查询全部教师
	@GetMapping(value = "/list")
	public JSONObject list() {
		List<Teacher> list = teacherService.queryList();
		if (null == list || 0 == list.size()) {
			return ResultUtils.failure(10003, "resource not found");
		}

		JSONArray items = new JSONArray();
		for(Teacher teacher : list) {
			items.add(teacher);
		}
		return ResultUtils.jsonResult(items, new JSONObject(), "");
	}

	// 依据昵称查询教师
	@GetMapping(value = "/list2")
	public JSONObject list2(String nickname) {
		List<Teacher> list = teacherService.queryByNickname(nickname);
		if (null == list || 0 == list.size()) {
			return ResultUtils.failure(10003, "resource not found");
		}

		JSONArray items = new JSONArray();
		for(Teacher teacher : list) {
			items.add(teacher);
		}
		return ResultUtils.jsonResult(items, new JSONObject(), "");
	}
}
