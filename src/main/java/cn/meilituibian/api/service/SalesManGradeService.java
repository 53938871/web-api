package cn.meilituibian.api.service;

import cn.meilituibian.api.domain.SalesmanGrade;
import cn.meilituibian.api.domain.WxUser;
import cn.meilituibian.api.mapper.SalesManGradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SalesManGradeService {
    private static final String UPGRADE = "upgrade";
    @Autowired
    private SalesManGradeMapper salesManGradeMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private WxUserService wxUserService;

    @Cacheable(value = "commonCache" ,unless = "#result.size() < 1")
    public List<SalesmanGrade> getGradeList() {
        List<SalesmanGrade> list = salesManGradeMapper.listGrade();
        return list;
    }

    private SalesmanGrade getPromotionGrade(int titleCode) {
        SalesmanGrade promotionGrade = null;
        List<SalesmanGrade> list = getGradeList();
        for (SalesmanGrade salesmanGrade : list) {
            if (salesmanGrade.getTitleCode() == titleCode) {
                promotionGrade = salesmanGrade;
                return promotionGrade;
            }
        }
        return null;
    }

    //在这里判断业务员是否在升级
    public Map<String,Object> upgrade(String openId) {
        WxUser wxUser = wxUserService.getUserByOpenId(openId);
        Map<String,Object> result = new HashMap<>();
        result.put(UPGRADE, false);
        result.put("openId", openId);
        if (wxUser.getJobTitle() == -1) {
            return result;
        }
        int promotionGradeCode = wxUser.getJobTitle() + 1;
        SalesmanGrade promotionGrade = getPromotionGrade(promotionGradeCode);
        if (promotionGrade == null) {
            return result;
        }
        BigDecimal price = orderService.computePrice(-1 * promotionGrade.getMonths(), openId);
        result.put("revenue", price);
        if (price.compareTo(promotionGrade.getAmountMoney()) >= 0) {
            result.put(UPGRADE, true);
            result.put("jobTitle", promotionGrade.getTitleCode());
            result.put("jobTitleName", promotionGrade.getTitleName());
        } else {
            result.put("gap", promotionGrade.getAmountMoney().subtract(price));
        }
        return result;
    }
}
