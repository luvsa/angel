package com.jdy.angel.ibatis.handlers;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.jdy.angel.ibatis.Megadeaths;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 数据自动填充
 * @author Dale
 * @create 2022/7/16 15:12
 */
@Slf4j
@Component
@ConditionalOnClass(MetaObjectHandler.class)
public class IbatisMetaObjectHandler implements MetaObjectHandler {

	public IbatisMetaObjectHandler() {
		log.info("加载数据自动填充...");
	}

	private final Map<String, Supplier<?>> insertFills = Map.of(Megadeaths.CREATE_TIME, LocalDateTime::now, Megadeaths.CREATOR,
			() -> Megadeaths.UNKNOWN);

	private final Map<String, Supplier<?>> updateFills = Map.of(Megadeaths.UPDATE_TIME, LocalDateTime::now, Megadeaths.UPDATER,
			() -> Megadeaths.UNKNOWN);

	/**
	 * 新增数据时，需要填充的数据
	 *
	 * @param metaObject 元数据对象
	 */
	@Override
	public void insertFill(MetaObject metaObject) {
		insertFills.forEach((s, supplier) -> set(metaObject, s, supplier.get()));
	}

	/**
	 * 跟新时需要填充的数据
	 *
	 * @param metaObject 元数据对象
	 */
	@Override
	public void updateFill(MetaObject metaObject) {
		updateFills.forEach((s, supplier) -> set(metaObject, s, supplier.get()));
	}

	private void set(MetaObject object, String name, Object value) {
		var v = object.getValue(name);
		if (v == null) {
			this.setFieldValByName(name, value, object);
		}
	}

}
