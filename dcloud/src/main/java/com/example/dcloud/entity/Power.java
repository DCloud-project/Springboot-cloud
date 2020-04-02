package com.example.dcloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author fifteen
 * @since 2020-04-01
 */
@TableName(value = "power")
public class Power extends Model<Power> {

private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 鐖惰妭鐐癸紝琛ㄥ唴鑷煡銆?琛ㄧず鏃犱笂涓€绾ф潈闄愶紝濡傜敤鎴凤紝鑿滃崟锛岀彮璇剧鐞嗐€?
     */
    private Integer parentId;

    private Integer isDelete;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Power{" +
        "id=" + id +
        ", name=" + name +
        ", parentId=" + parentId +
        ", isDelete=" + isDelete +
        "}";
    }
}
