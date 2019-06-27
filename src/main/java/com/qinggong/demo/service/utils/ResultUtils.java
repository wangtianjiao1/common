package com.qinggong.demo.service.utils;

import java.util.Random;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ResultUtils {
	// 成功时调用方法
	public static JSONObject success() {
		JSONObject success = new JSONObject();
		success.put("errcode", 0);
		success.put("errmsg", "");

		JSONObject result = new JSONObject();
		JSONObject content = new JSONObject();
		JSONArray items = new JSONArray();
		result.put("content", content);
		result.put("items", items);
		result.put("extra", "");
		success.put("result", result);

		return success;
	}

	// 失败时调用方法
	public static JSONObject failure(int errcode, String errmsg) {
		JSONObject fail = new JSONObject();
		fail.put("errcode", errcode);
		fail.put("errmsg", errmsg);

		JSONObject result = new JSONObject();
		JSONObject content = new JSONObject();
		JSONArray items = new JSONArray();
		result.put("content", content);
		result.put("items", items);
		result.put("extra", "");
		fail.put("result", result);

		return fail;
	}

	// 获得查询的返回值
	public static JSONObject jsonResult(JSONArray items, JSONObject content, String extra) {
		JSONObject fail = new JSONObject();
		fail.put("errcode", 0);
		fail.put("errmsg", "");

		JSONObject result = new JSONObject();
		result.put("content", content);
		result.put("items", items);
		result.put("extra", extra);
		fail.put("result", result);

		return fail;
	}

	public static void main(String[] args) {
		int code = new Random().nextInt(999999);
		if (code < 100000) {
			code = code + 100000;
		}
		System.out.println(code);
	}
}
