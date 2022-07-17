package com.jdy.angel.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdy.angel.data.domain.DictItem;
import com.jdy.angel.data.sys.SysDict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * 数据字典 mybatis 的 Mapper 接口，
 *
 * @author Dale
 * @create 2021/11/8 15:08
 */
@Mapper
public interface DictMapper extends BaseMapper<SysDict> {

	/**
	 * 查询字典值
	 *
	 * @param name  字典名称
	 * @param label 字典key
	 * @return 字典值
	 */
	@Select("""
			SELECT sys_dict_item.value FROM sys_dict INNER JOIN sys_dict_item ON(sys_dict.ID = sys_dict_item.Dict_ID) 
			    WHERE sys_dict.Name = #{name}
			    AND sys_dict_item.Label = #{label}
			""")
	String search(@Param("name") String name, @Param("label") Object label);

	/**
	 * 查询字典key
	 *
	 * @param name  字典名称
	 * @param value 字典值
	 * @return 字典的key
	 */
	@Select("""
			SELECT sys_dict_item.Label FROM sys_dict INNER JOIN sys_dict_item ON(sys_dict.ID = sys_dict_item.Dict_ID) 
			    WHERE sys_dict.Name = #{name}
			    AND sys_dict_item.value = #{value}
			""")
	String fetch(String name, Object value);

	@Select("""
			SELECT sys_dict_item.label, sys_dict_item.value FROM sys_dict INNER JOIN sys_dict_item ON(sys_dict.ID = sys_dict_item.Dict_ID) 
			    WHERE sys_dict.Name = #{name}
			""")
	List<DictItem> find(String name);

	/**
	 * 数据字典 的Item 项 mybatis Mapper 接口
	 */
	interface DictItemMapper extends BaseMapper<SysDict.Item> {

	}
}
