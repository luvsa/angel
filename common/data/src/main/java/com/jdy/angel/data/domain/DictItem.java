package com.jdy.angel.data.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Dale
 * @create 2021/11/15 16:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictItem implements Serializable {

    @Serial
    private static final long serialVersionUID = -4492727293543139881L;

    /**
     * 名称
     */
    private String label;

    /**
     * 值
     */
    private String value;

}
