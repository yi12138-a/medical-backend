package com.qst.medical.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qst.medical.domain.City;
import com.qst.medical.entity.CityEntity;
import com.qst.medical.mapper.CityMapper;
import com.qst.medical.mapper.MedicalPolicyMapper;
import com.qst.medical.model.CityModel;
import com.qst.medical.util.Msg;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 城市信息业务层
 *
 * @author qst
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CityService {

    @Autowired
    private CityMapper cityMapper;

    /** 删除城市时需要同步删除该城市下的医保政策（任务12要求） */
    @Autowired
    private MedicalPolicyMapper medicalPolicyMapper;

    /**
     * 获取所有城市信息并分页，name 不为空则模糊查询，当 pn 和 size 为 null，则整页查询
     *
     * @param pn   页码
     * @param size 每页大小
     * @param name 查询关键字（城市名或上级省份名）
     * @return 分页信息
     */
    public PageInfo<CityModel> getCityWithPage(Integer pn, Integer size, String name) {
        // 规格原写法为 if (pn == null && size == null) { pn = 1; size = 0; }
        // 为兼容只传其中一个参数的场景（避免自动拆箱 NPE），改为分别判空，行为与规格一致
        if (pn == null) {
            pn = 1;
        }
        if (size == null) {
            size = 0;
        }
        PageHelper.startPage(pn, size);
        List<CityModel> list = cityMapper.getAllCity(name);
        PageInfo<CityModel> info = new PageInfo<>(list, 5);
        return info;
    }

    /**
     * 根据 id 查找一个城市
     *
     * @param id 城市 id
     * @return 统一响应体，查到则把城市信息放入 data 的 city 中
     */
    public Msg getCityById(Integer id) {
        City city = cityMapper.getCityById(id);

        if (city == null) {
            return Msg.fail().mess("没有找到");
        }
        return Msg.success().data("city", city);
    }

    /**
     * 添加一个城市
     *
     * @param cityNumber 城市编号
     * @return 统一响应体，添加成功时把添加后前端应跳转的页数放入 data 的 pages 中
     */
    public Msg saveCity(Integer cityNumber) {
        City city = new City();
        Date d = new Date();
        city.setCityNumber(cityNumber);
        city.setCreatetime(d);
        city.setUpdatetime(d);
        CityEntity ce = new CityEntity();
        // 对象拷贝
        BeanUtils.copyProperties(city, ce);
        int i = cityMapper.saveCity(ce);
        if (i > 0) {
            Long num = ce.getTotal() % 5 == 0 ? (ce.getTotal() / 5) : (ce.getTotal() / 5) + 1;
            return Msg.success().data("pages", num).mess("添加成功");
        }
        return Msg.fail().mess("添加失败");
    }

    /**
     * 根据 id 删除城市
     *
     * @param id 城市 id
     * @return 统一响应体
     */
    public Msg deleteCityById(Integer id) {
        int i = cityMapper.deleteCityById(id);
        // 城市删除成功后，同步删除该城市下的所有医保政策，避免产生孤儿数据
        medicalPolicyMapper.deleteByCity(id);
        if (i > 0) {
            return Msg.success().mess("删除成功");
        } else {
            return Msg.fail().mess("删除失败");
        }
    }

    /**
     * 检查城市是否存在
     *
     * @param number 城市编号
     * @return 该编号的城市记录数，大于 0 表示已存在
     */
    public int checkCityByName(Integer number) {
        return cityMapper.checkCityByName(number);
    }
}
