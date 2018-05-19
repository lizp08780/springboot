package com.lizp.springboot.domain;

import java.io.Serializable;
import java.util.Date;

public class SysDictData implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict_data.dict_code
     *
     * @mbg.generated
     */
    private Integer dictCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict_data.dict_sort
     *
     * @mbg.generated
     */
    private Integer dictSort;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict_data.dict_label
     *
     * @mbg.generated
     */
    private String dictLabel;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict_data.dict_value
     *
     * @mbg.generated
     */
    private String dictValue;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict_data.dict_type
     *
     * @mbg.generated
     */
    private String dictType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict_data.status
     *
     * @mbg.generated
     */
    private Integer status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict_data.create_by
     *
     * @mbg.generated
     */
    private String createBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict_data.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict_data.update_by
     *
     * @mbg.generated
     */
    private String updateBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict_data.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict_data.remark
     *
     * @mbg.generated
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sys_dict_data
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict_data.dict_code
     *
     * @return the value of sys_dict_data.dict_code
     *
     * @mbg.generated
     */
    public Integer getDictCode() {
        return dictCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict_data.dict_code
     *
     * @param dictCode the value for sys_dict_data.dict_code
     *
     * @mbg.generated
     */
    public void setDictCode(Integer dictCode) {
        this.dictCode = dictCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict_data.dict_sort
     *
     * @return the value of sys_dict_data.dict_sort
     *
     * @mbg.generated
     */
    public Integer getDictSort() {
        return dictSort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict_data.dict_sort
     *
     * @param dictSort the value for sys_dict_data.dict_sort
     *
     * @mbg.generated
     */
    public void setDictSort(Integer dictSort) {
        this.dictSort = dictSort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict_data.dict_label
     *
     * @return the value of sys_dict_data.dict_label
     *
     * @mbg.generated
     */
    public String getDictLabel() {
        return dictLabel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict_data.dict_label
     *
     * @param dictLabel the value for sys_dict_data.dict_label
     *
     * @mbg.generated
     */
    public void setDictLabel(String dictLabel) {
        this.dictLabel = dictLabel == null ? null : dictLabel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict_data.dict_value
     *
     * @return the value of sys_dict_data.dict_value
     *
     * @mbg.generated
     */
    public String getDictValue() {
        return dictValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict_data.dict_value
     *
     * @param dictValue the value for sys_dict_data.dict_value
     *
     * @mbg.generated
     */
    public void setDictValue(String dictValue) {
        this.dictValue = dictValue == null ? null : dictValue.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict_data.dict_type
     *
     * @return the value of sys_dict_data.dict_type
     *
     * @mbg.generated
     */
    public String getDictType() {
        return dictType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict_data.dict_type
     *
     * @param dictType the value for sys_dict_data.dict_type
     *
     * @mbg.generated
     */
    public void setDictType(String dictType) {
        this.dictType = dictType == null ? null : dictType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict_data.status
     *
     * @return the value of sys_dict_data.status
     *
     * @mbg.generated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict_data.status
     *
     * @param status the value for sys_dict_data.status
     *
     * @mbg.generated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict_data.create_by
     *
     * @return the value of sys_dict_data.create_by
     *
     * @mbg.generated
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict_data.create_by
     *
     * @param createBy the value for sys_dict_data.create_by
     *
     * @mbg.generated
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict_data.create_time
     *
     * @return the value of sys_dict_data.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict_data.create_time
     *
     * @param createTime the value for sys_dict_data.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict_data.update_by
     *
     * @return the value of sys_dict_data.update_by
     *
     * @mbg.generated
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict_data.update_by
     *
     * @param updateBy the value for sys_dict_data.update_by
     *
     * @mbg.generated
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict_data.update_time
     *
     * @return the value of sys_dict_data.update_time
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict_data.update_time
     *
     * @param updateTime the value for sys_dict_data.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict_data.remark
     *
     * @return the value of sys_dict_data.remark
     *
     * @mbg.generated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict_data.remark
     *
     * @param remark the value for sys_dict_data.remark
     *
     * @mbg.generated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_dict_data
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", dictCode=").append(dictCode);
        sb.append(", dictSort=").append(dictSort);
        sb.append(", dictLabel=").append(dictLabel);
        sb.append(", dictValue=").append(dictValue);
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