package com.lizp.springboot.persist;

import com.lizp.springboot.domain.SysDictType;
import java.util.List;

public interface SysDictTypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_dict_type
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer dictId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_dict_type
     *
     * @mbg.generated
     */
    int insert(SysDictType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_dict_type
     *
     * @mbg.generated
     */
    SysDictType selectByPrimaryKey(Integer dictId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_dict_type
     *
     * @mbg.generated
     */
    List<SysDictType> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_dict_type
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SysDictType record);
}