package com.qst.medical.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.qst.medical.domain.DrugCompany;
import com.qst.medical.domain.Sale;
import com.qst.medical.mapper.CompanyMapper;
import com.qst.medical.mapper.DoctorMapper;
import com.qst.medical.mapper.DrugMapper;
import com.qst.medical.mapper.SaleMapper;
import com.qst.medical.model.CompanyPolicyModel;
import com.qst.medical.model.DoctorModel;
import com.qst.medical.model.DrugModel;
import com.qst.medical.model.MaterialModel;
import com.qst.medical.model.TreatTypeModel;
import com.qst.medical.param.DoctorParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 主页面数据面板业务层
 * <p>
 * 汇总医师、药品、医药公司、药店的总体数量，各职级医师人数、
 * 各诊治部位（医院科室）医师人数，以及最新的医保政策和医药公司政策。
 * <p>
 * 由于返回内容较多且格式并不固定，这里不专门定义返回模型，
 * 而是直接使用 Jackson 构造 Json 对象返回（对应任务知识点：Json 解析）。
 */
@Service
public class DashboardService {

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private DrugMapper drugMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private SaleMapper saleMapper;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private CompanyPolicyService companyPolicyService;

    /**
     * 组装数据面板所需的全部数据
     *
     * @return JsonNode 形式的对象，包含：
     * doctorNumb、drugNumb、companyNumb、saleNumb（各类总数量），
     * docLevel（各职级医师人数）、treatMap（各诊治部位医师人数），
     * materials（最新医保政策）、policys（最新医药公司政策）
     */
    public ObjectNode getDashboardData() {
        // 获取医师、药品、医药公司和药店的完整列表，用列表长度作为数量
        List<DoctorModel> allDoctor = doctorMapper.getAllDoctor(new DoctorParam());
        // 获取控制面板药品、医师、药店和公司数量
        List<DrugModel> allDrug = drugMapper.getAllDrug("");
        List<DrugCompany> allCompany = companyMapper.getAllCompany("");
        List<Sale> allSale = saleMapper.getAllSale("");
        int doctorNumb = allDoctor.size();
        int drugNumb = allDrug.size();
        int companyNumb = allCompany.size();
        int saleNumb = allSale.size();

        // 获取每个 doctor 等级
        int docLevel1 = 0, docLevel2 = 0, docLevel3 = 0;
        List<TreatTypeModel> allTreatType = doctorMapper.getAllTreatType();
        HashMap<String, Integer> treatMap = new HashMap<>();
        // 构建 Map 获取所有的诊治类型，初始计数为 0，避免某个部位没有医师时前端缺数据
        for (TreatTypeModel treat : allTreatType) {
            treatMap.put(treat.getName(), 0);
        }
        // 通过医生信息获取所有的医生级别和诊治类型医生数量
        for (DoctorModel doc : allDoctor) {
            if (doc.getLevelId() != null) {
                if (doc.getLevelId() == 1) {
                    docLevel1++;
                } else if (doc.getLevelId() == 2) {
                    docLevel2++;
                } else if (doc.getLevelId() == 3) {
                    docLevel3++;
                }
            }
            String treatType = doc.getTreatType();
            if (treatType != null) {
                treatMap.put(treatType, treatMap.getOrDefault(treatType, 0) + 1);
            }
        }

        // 获取最新的医保政策与医药公司政策（各取前 4 条）
        List<MaterialModel> allMaterial = materialService.getFirstMaterialWithPage();
        List<CompanyPolicyModel> allPolicy = companyPolicyService.getFirstPolicyWithPage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("doctorNumb", doctorNumb);
        objectNode.put("drugNumb", drugNumb);
        objectNode.put("companyNumb", companyNumb);
        objectNode.put("saleNumb", saleNumb);

        ObjectNode docLevelNode = objectMapper.createObjectNode();
        docLevelNode.put("l1", docLevel1);
        docLevelNode.put("l2", docLevel2);
        docLevelNode.put("l3", docLevel3);
        objectNode.put("docLevel", docLevelNode);

        JsonNode treatNode = objectMapper.convertValue(treatMap, JsonNode.class);
        objectNode.put("treatMap", treatNode);

        JsonNode materialNode = objectMapper.valueToTree(allMaterial);
        JsonNode policyNode = objectMapper.valueToTree(allPolicy);
        objectNode.put("materials", materialNode);
        objectNode.put("policys", policyNode);
        return objectNode;
    }
}
