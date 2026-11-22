package com.qst.medical.mapper;

import com.qst.medical.entity.DoctorEntity;
import com.qst.medical.model.DoctorLevelModel;
import com.qst.medical.model.DoctorModel;
import com.qst.medical.model.TreatTypeModel;
import com.qst.medical.param.DoctorParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 医师数据访问接口
 * 对应映射文件：src/main/resources/mapper/DoctorMapper.xml
 */
@Mapper
public interface DoctorMapper {

    /*获取所有的医师信息（按查询参数中的关键字过滤）*/
    List<DoctorModel> getAllDoctor(DoctorParam param);

    /*获取所有的医师级别*/
    List<DoctorLevelModel> getAllLevel();

    /*获取所有的诊治类型*/
    List<TreatTypeModel> getAllTreatType();

    /*新增医师信息*/
    int saveDoctor(DoctorEntity param);

    /*更新医师信息*/
    int updateDoctor(DoctorEntity param);

    /*根据id删除医师，同时删除该医师关联的账号*/
    int deleteDoctorById(Long id);
}
