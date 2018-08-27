package cn.meilituibian.api.service;

import cn.meilituibian.api.domain.Advertisment;
import cn.meilituibian.api.mapper.AdvertismentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdvertismentService {

    @Autowired
    private AdvertismentMapper advertismentMapper;

    public List<Advertisment> getAdvertismentList(String code){
        List<Advertisment> result = new ArrayList<>();
        try{
            result = this.advertismentMapper.getAdvList(code);

        }catch (Exception ex){
            throw ex;
        }
        return result;
    }
}
