package com.lizp.springboot.domain;

import java.io.Serializable;
import java.util.Date;

public class SysDictType implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict_type.dict_id
     *
     * @mbg.generated
     */
    private Integer dictId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict_type.dict_name
     *
     * @mbg.generated
     */
    private String dictName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict_type.dict_type
     *
     * @mbg.generated
     */
    private String dictType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict_type.status
     *
     * @mbg.generated
     */
    private Integer status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict_type.create_by
     *
     * @mbg.generated
     */
    private String createBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict_type.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict_type.update_by
     *
     * @mbg.generated
     */
    private String updateBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict_type.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict_type.remark
     *
     * @mbg.generated
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sys_dict_type
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict_type.dict_id
     *
     * @return the value of sys_dict_type.dict_id
     *
     * @mbg.generated
     */
    public Integer getDictId() {
        return dictId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict_type.dict_id
     *
     * @param dictId the value for sys_dict_type.dict_id
     *
     * @mbg.generated
     */
    public void setDictId(Integer dictId) {
        this.dictId = dictId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict_type.dict_name
     *
     * @return the value of sys_dict_type.dict_name
     *
     * @mbg.generated
     */
    public String getDictName() {
        return dictName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict_type.dict_name
     *
     * @param dictName the value for sys_dict_type.dict_name
     *
     * @mbg.generated
     */
    public void setDictName(String dictName) {
        this.dictName = dictName == null ? null : dictName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict_type.dict_type
     *
     * @return the value of sys_dict_type.dict_type
     *
     * @mbg.generated
     */
    public String getDictType() {
        return dictType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict_type.dict_type
     *
     * @param dictType the value for sys_dict_type.dict_type
     *
     * @mbg.generated
     */
    public void setDictType(String dictType) {
        this.dictType = dictType == null ? null : dictType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict_type.status
     *
     * @return the value of sys_dict_type.status
     *
     * @mbg.generated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict_type.status
     *
     * @param status the value for sys_dict_type.status
     *
     * @mbg.generated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict_type.create_by
     *
     * @return the value of sys_dict_type.create_by
     *
     * @mbg.generated
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict_type.create_by
     *
     * @param createBy the value for sys_dict_type.create_by
     *
     * @mbg.generated
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict_type.create_time
     *
     * @return the value of sys_dict_type.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict_type.create_time
     *
     * @param createTime the value for sys_dict_type.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict_type.update_by
     *
     * @return the value of sys_dict_type.update_by
     *
     * @mbg.generated
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict_type.update_by
     *
     * @param updateBy the value for sys_dict_type.update_by
     *
     * @mbg.generated
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict_type.update_time
     *
     * @return the value of sys_dict_type.update_time
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict_type.update_time
     *
     * @param updateTime the value for sys_dict_type.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict_type.remark
     *
     * @return the value of sys_dict_type.remark
     *
     * @mbg.generated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict_type.remark
     *
     * @param remark the value for sys_dict_type.remark
     *
     * @mbg.generated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_dict_type
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", dictId=").append(dictId);
        sb.append(", dictName=").append(dictName);
        sb.append(", dictType=").append(dictType);
        sb.append(", status=").append(status);
        sb.append(", createBy=").append(createBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", remark=").append(remark);
        sb.append("]");
        return sb.toString();
    }
}