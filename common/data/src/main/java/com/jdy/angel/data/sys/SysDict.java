package com.jdy.angel.data.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jdy.angel.data.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Collection;

/**
 * predictable
 * Dictionary
 *
 * @author Dale
 * @create 2021/11/8 14:57
 */
@Data
@TableName("Sys_Dict")
@EqualsAndHashCode(callSuper = true)
public class SysDict extends Entity {

    @Serial
    private static final long serialVersionUID = 684866203002560929L;

    /**
     * 字典别名， 可以是中文也可以是英文的
     */
    private String alias;

    /**
     * 字典名称， 一般是英文的， 比如 类型: type、 状态: status、 性别: sex等
     */
    private String name;

    @TableField(exist = false)
    private Collection<Item> items;

    /**
     * sys.dict.item.sql
     */
    @Data
    @TableName("Sys_Dict_Item")
    @EqualsAndHashCode(callSuper = true)
    public static class Item extends Entity {

        @Serial
        private static final long serialVersionUID = 440597144771857433L;

        /**
         * 字典的标识符， 属于外键
         */
        @TableField("Dict_ID")
        private String dict;

        /**
         * 字典项名称
         */
        private String label;

        /**
         * 字典项的值
         */
        private String value;
    }
}
