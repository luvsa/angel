package com.jdy.angel.ebook.service.impl;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdy.angel.data.sys.SysDict;
import com.jdy.angel.data.sys.SysDict.Item;
import com.jdy.angel.mapper.sys.DictMapper;
import com.jdy.angel.service.DictService;
import com.jdy.angel.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.luvsa.vary.Vary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

/**
 * @author Dale
 * @create 2022/7/16 14:30
 */
@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("字典服务测试")
class DictServiceImplTest {
	private static final ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private DictService dictService;

	@Test
	void initial() {
		try (var stream = FileUtil.readResourceAsStream("dict.json")) {
			var roots = mapper.readTree(stream);
			var list = new ArrayList<SysDict>(roots.size());
			for (var node : roots) {
				var dict = new SysDict();
				fill(dict, node);

				var children = new ArrayList<Item>();
				for (var child : node.get("items")) {
					var item = new Item();
					fill(item, child);
					children.add(item);
				}
				dict.setItems(children);
				list.add(dict);
			}

//			System.out.println(list);

			if (dictService instanceof DictServiceImpl service) {
				service.saveBatch(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void fill(Object o, JsonNode item) {
		var clazz = o.getClass();
		ReflectionUtils.doWithFields(clazz, field -> {
			var name = field.getName();
			var node = item.get(name);
			if (node == null) {
				return;
			}
			var value = node.asText();
			if (value == null) {
				return;
			}
			var type = field.getType();
			var method = ReflectionUtils.findMethod(clazz,
					"set" + StringUtils.capitalize(name), type);
			if (method == null) {
				return;
			}
			// 一些简单的数据类型转换
			var convert = Vary.change(value, type);
			ReflectionUtils.invokeMethod(method, o, convert);
		});
	}


	@Test
	void insert() {
		try (var session = SqlHelper.sqlSession(SysDict.Item.class)){
			var mapper = session.getMapper(DictMapper.DictItemMapper.class);
			var item = new SysDict.Item();

			item.setDict("15482208340577443860000000000004");
			item.setLabel("穿越");
			item.setValue("9");
			mapper.insert(item);
		} catch (Exception e){
			//
		}
	}

	@Test
	void search() {


	}
}