package com.jdy.angel.data;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Dale
 * @create 2022/7/16 3:12
 */
@Data
public class Entity implements Serializable {

	@Serial
	private static final long serialVersionUID = 8303699368727698612L;

	/**
	 * 主键
	 */
	@TableId(value = "ID", type = IdType.ASSIGN_UUID)
	private String guid;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	/**
	 * 创建者, 建议使用{@link com.jdy.angle.data.sys.SysUser#guid}关联
	 * <p>
	 * 但平时也存在使用用户名称关联， 这个后期再做调整
	 */
	@TableField(fill = FieldFill.INSERT)
	private String creator;


	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.UPDATE)
	private LocalDateTime updateTime;

	/**
	 * 更新者 建议使用{@link com.jdy.angle.data.sys.SysUser#guid}关联
	 * 但平时也存在使用用户名称关联， 这个后期再做调整
	 */
	@TableField(fill = FieldFill.UPDATE)
	private String updater;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 是否删除， 这里是指逻辑删除,
	 * <p>
	 * 暂时做处理
	 */
	@TableLogic
	private Boolean deleted;

}
