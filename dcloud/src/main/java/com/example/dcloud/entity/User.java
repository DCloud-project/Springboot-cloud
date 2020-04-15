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
@TableName(value = "user")
public class User extends Model<User> {

private static final long serialVersionUID=1L;

    /**
     * id,自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String image;

    /**
     * 学号
     */
    private String sno;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 电话号码
     */
    private String telphone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 出生年月
     */
    private String birth;

    /**
     * 瑙掕壊锛堝閿級锛岃繛鎺ヨ鑹茶〃
     */
    private Integer roleId;

    /**
     * 经验值
     */
    private Integer exp;

    /**
     * 状态（0-正常；1-禁用）
     */
    private Integer state;

    /**
     * 瀛︽牎闄㈢郴锛堝閿級锛岃繛鎺chool琛?
     */
    private Integer schoolId;

    /**
     * 鏉冮檺锛屼腑闂寸敤/鍒嗗紑锛岃繛鎺ユ潈闄愯〃
     */
    private String powerId;

    /**
     * 学历（0-高中；1-大学本科；2-大学专科；3-硕士；4-博士）
     */
    private Integer education;

    /**
     * 0-未删除1-已删除
     */
    private Integer isDelete;

    public User(){
        this.birth = "0";
        this.education = 0;
        this.email = "0";
        this.exp = 0;
        this.image = "0";
        this.isDelete =0;
        this.name = "0";
        this.nickname = "0";
        this.password = "0";
        this.powerId = "0";
        this.roleId = 0;
        this.schoolId = 0;
        this.state = 0;
        this.telphone = "0";
        this.sex = 0;
    }

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getPowerId() {
        return powerId;
    }

    public void setPowerId(String powerId) {
        this.powerId = powerId;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
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
        return "User{" +
        "id=" + id +
        ", name=" + name +
        ", nickname=" + nickname +
        ", image=" + image +
        ", sno=" + sno +
        ", sex=" + sex +
        ", telphone=" + telphone +
        ", email=" + email +
        ", password=" + password +
        ", birth=" + birth +
        ", roleId=" + roleId +
        ", exp=" + exp +
        ", state=" + state +
        ", schoolId=" + schoolId +
        ", powerId=" + powerId +
        ", education=" + education +
        ", isDelete=" + isDelete +
        "}";
    }
}
