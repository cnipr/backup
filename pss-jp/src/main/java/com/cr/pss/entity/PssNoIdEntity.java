package com.cnipr.pss.entity;

import javax.persistence.MappedSuperclass;

/**
 * 统一定义id的entity基类.
 * 
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略. 子类可重载getId()函数重定义id的列名映射和生成策略.
 * 
 * @author calvin
 */
// JPA 基类标识
@MappedSuperclass
public abstract class PssNoIdEntity {
	
}
