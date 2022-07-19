package com.jdy.angel.ebook.service.impl;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.jdy.angel.data.sys.SysDict;
import com.jdy.angel.mapper.sys.DictMapper;
import com.jdy.angel.service.DictService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 字典服务
 *
 * @author Dale
 * @create 2022/7/16 13:44
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, SysDict> implements DictService {

	private final Map<String, Map<String, String>> cache = new ConcurrentHashMap<>();

	@Override
	public String search(String name, String label) {
		var map = cache.computeIfAbsent(name, s -> {
			var list = baseMapper.find(s);
			var temp = new HashMap<String, String>(list.size());
			for (var item : list) {
				temp.put(item.getLabel(), item.getValue());
			}
			return temp;
		});
		var length = label.length();
		if (length > 2){
			for (int i = 0, j = 2; j < length; i++, j++) {
				var sub = label.substring(i, j);
				var s = map.get(sub);
				if (s == null || s.isBlank()){
					continue;
				}
				return s;
			}
		}
		return map.get(label);
	}

	/**
	 * 批量 添加 字典数据
	 *
	 * @param entityList 字典数据
	 * @return 添加结果
	 */
	@Override
	public boolean saveBatch(Collection<SysDict> entityList) {
		var statement = getSqlStatement(SqlMethod.INSERT_ONE);
		var sqlStatement = SqlHelper.getSqlStatement(DictMapper.DictItemMapper.class, SqlMethod.INSERT_ONE);
		return executeBatch(entityList, DEFAULT_BATCH_SIZE, (sqlSession, entity) -> {
			sqlSession.insert(statement, entity);
			var items = entity.getItems();
			for (SysDict.Item item : items) {
				item.setDict(entity.getGuid());
				sqlSession.insert(sqlStatement, item);
			}
		});
	}
}
