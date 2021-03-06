package com.example.dcloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author fifteen
 * @since 2020-05-30
 */
public class Course extends Model<Course> {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 班课名
     */
    private String name;

    /**
     * 班课号
     */
    private String code;

    /**
     * 班级
     */
    @TableField("className")
    private String className;

    /**
     * 学期
     */
    private String semester;

    /**
     * 班课封面
     */
    private String image;

    /**
     * 学习要求
     */
    @TableField("learnRequire")
    private String learnRequire;

    /**
     * 教学进度
     */
    @TableField("teachProgress")
    private String teachProgress;

    /**
     * 考试安排
     */
    @TableField("examSchedule")
    private String examSchedule;

    /**
     * 瀛︽牎鍜岄櫌绯伙紙澶栭敭锛岃繛鎺chool琛級
     */
    private String schoolCode;

    /**
     * 是否学校课表班课
     */
    private Integer flag;

    /**
     * 閻庢冻濡囬弫鎼僤闁挎稑鐭傞幗濂稿箳閵壯勬殢闁瑰鏌夐妴鍐晬瀹€鈧弫?闂傚懏鏌ㄧ槐?
     */
    private String studentId;

    /**
     * 娲诲姩锛岄摼鎺ユ椿鍔ㄨ〃
     */
    @TableField("activityId")
    private String activityId;

    /**
     * 娑堟伅锛岄摼鎺ユ秷鎭〃
     */
    @TableField("messageId")
    private String messageId;

    /**
     * 鏄惁鍙互鍔犲叆鐝锛岄粯璁ゅ彲浠ワ紝0鍙互锛?涓嶅彲浠?
     */
    @TableField("isJoin")
    private Integer isJoin;

    /**
     * 0琛ㄧず鏈垹闄わ紝1琛ㄧず鍒犻櫎
     */
    @TableField("isDelete")
    private Integer isDelete;

    private Long teacherId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLearnRequire() {
        return learnRequire;
    }

    public void setLearnRequire(String learnRequire) {
        this.learnRequire = learnRequire;
    }

    public String getTeachProgress() {
        return teachProgress;
    }

    public void setTeachProgress(String teachProgress) {
        this.teachProgress = teachProgress;
    }

    public String getExamSchedule() {
        return examSchedule;
    }

    public void setExamSchedule(String examSchedule) {
        this.examSchedule = examSchedule;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Integer getIsJoin() {
        return isJoin;
    }

    public void setIsJoin(Integer isJoin) {
        this.isJoin = isJoin;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Course{" +
        "id=" + id +
        ", name=" + name +
        ", code=" + code +
        ", className=" + className +
        ", semester=" + semester +
        ", image=" + image +
        ", learnRequire=" + learnRequire +
        ", teachProgress=" + teachProgress +
        ", examSchedule=" + examSchedule +
        ", schoolCode=" + schoolCode +
        ", flag=" + flag +
        ", studentId=" + studentId +
        ", activityId=" + activityId +
        ", messageId=" + messageId +
        ", isJoin=" + isJoin +
        ", isDelete=" + isDelete +
        ", teacherId=" + teacherId +
        "}";
    }
}
