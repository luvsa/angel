package com.jdy.angel.ebook.core.labels;

import com.jdy.angel.ebook.core.Constant;
import com.jdy.angel.ebook.core.Node;

import java.util.Map;
import java.util.StringJoiner;

/**
 * 说明书 manual
 *
 * @author Aglet
 * @create 2022/7/3 23:02
 */
@Sign("!DOCTYPE")
public class Doctype extends Block {

	private String attr;

	@Override
	public void setProperty(String property) {
		this.attr = property;
	}

	@Override
	public Node toNode() {
		return new Node() {

			private Map<String, Object> map;

			@Override
			public Label getLabel() {
				return Doctype.this;
			}

			@Override
			public void set(String origin, Object host) {
				if (map == null) {
					map = Map.of(origin, host);
				}
			}

			@Override
			public String get(String key) {
				var o = map.get(key);
				return o.toString();
			}
		};
	}

	@Override
	public String getPrefix() {
		return toString();
	}

	@Override
	public String getSuffix() {
		return Constant.EMPTY;
	}

	@Override
	public Type getType() {
		return Type.OPEN;
	}

	@Override
	public int getChildTabs(int times) {
		return 0;
	}

	@Override
	public String toString() {
		var joiner = new StringJoiner(" ", "<", ">");
		joiner.add(name);
		joiner.add(attr);
		return joiner.toString();
	}
}
