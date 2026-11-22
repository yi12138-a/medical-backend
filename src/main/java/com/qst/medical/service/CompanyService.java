package com.qst.medical.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qst.medical.domain.DrugCompany;
import com.qst.medical.entity.DrugCompanyEntity;
import com.qst.medical.mapper.CompanyMapper;
import com.qst.medical.mapper.CompanyPolicyMapper;
import com.qst.medical.util.Msg;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 医药公司服务层
 * <p>
 * 提供医药公司分页查询、根据id查询、添加、修改、删除功能。
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CompanyService {

    @Autowired
    private CompanyMapper companyMapper;

    /**
     * 医药公司政策数据库操作接口（删除医药公司时级联删除其政策，见任务8）
     */
    @Autowired
    private CompanyPolicyMapper companyPolicyMapper;

    /**
     * 获取所有医药公司信息并分页，name 不为空则模糊查询
     *
     * @param pn   页码，为空时默认为 1
     * @param size 页面大小，为空时默认为 Integer.MAX_VALUE，为 0 时按 1 处理
     * @param name 公司名关键字，可为 null
     * @return 分页信息
     */
    public PageInfo<DrugCompany> getCompanyWithPage(Integer pn, Integer size, String name) {
        if (pn == null && size == null) {
            pn = 1;
            size = Integer.MAX_VALUE;
        }
        if (pn == null) {
            pn = 1;
        }
        if (size == null) {
            size = Integer.MAX_VALUE;
        }
        if (size == 0) {
            size = 1;
        }
        PageHelper.startPage(pn, size);
        List<DrugCompany> list = companyMapper.getAllCompany(name);
        PageInfo<DrugCompany> info = new PageInfo<>(list, 5);
        return info;
    }

    /**
     * 根据id查找一个医药公司
     *
     * @param id 公司id
     * @return 成功则返回 data 中 key 为 company 的公司信息，找不到返回“没有找到”
     */
    public Msg getCompanyById(Integer id) {
        DrugCompany company = companyMapper.getCompanyById(id);

        if (company == null) {
            return Msg.fail().mess("没有找到");
        }
        return Msg.success().data("company", company);
    }

    /**
     * 添加一个医药公司
     * <p>
     * 创建时间与更新时间都置为当前时间，添加成功后根据总记录数计算前端应停留的页码
     * （每页 5 条，向上取整）。
     *
     * @param company 医药公司对象
     * @return 成功返回 data 中 key 为 pages 的页码信息
     */
    public Msg saveCompany(DrugCompany company) {
        Date d = new Date();
        company.setCreatetime(d);
        company.setUpdatetime(d);
        DrugCompanyEntity dce = new DrugCompanyEntity();
        BeanUtils.copyProperties(company, dce);//对象拷贝
        int i = companyMapper.saveCompany(dce);
        if (i > 0) {
            Long num = dce.getTotal() % 5 == 0 ? (dce.getTotal() / 5) : (dce.getTotal() / 5) + 1;
            return Msg.success().data("pages", num).mess("添加成功");
        }
        return Msg.fail().mess("添加失败");
    }

    /**
     * 根据id更新医药公司信息
     *
     * @param id      公司id
     * @param company 医药公司对象
     * @return 成功返回“修改成功”，否则返回“修改失败”
     */
    public Msg updateCompanyById(Long id, DrugCompany company) {
        company.setUpdatetime(new Date());
        company.setCompanyId(id);
        int i = companyMapper.updateCompanyById(company);
        if (i > 0) {
            return Msg.success().mess("修改成功");
        }
        return Msg.fail().mess("修改失败");
    }

    /**
     * 根据id删除医药公司信息
     *
     * @param id 公司id
     * @return 成功返回“删除成功”，否则返回“删除失败”
     */
    public Msg deleteCompanyById(Integer id) {
        int i = companyMapper.deleteCompanyById(id);
        // 删除医药公司时，同时删除该公司对应的所有医药公司政策
        companyPolicyMapper.deletePolicyByCompany(id);
        if (i > 0) {
            return Msg.success().mess("删除成功");
        } else {
            return Msg.fail().mess("删除失败");
        }
    }
}
