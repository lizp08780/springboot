package com.lizp.springboot.persist;

import com.lizp.springboot.domain.SysJobLog;
import java.util.List;

public interface SysJobLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_job_log
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer jobLogId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_job_log
     *
     * @mbg.generated
     */
    int insert(SysJobLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_job_log
     *
     * @mbg.generated
     */
    SysJobLog selectByPrimaryKey(Integer jobLogId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_job_log
     *
     * @mbg.generated
     */
    List<SysJobLog> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_job_log
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SysJobLog record);
}