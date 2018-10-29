package cn.meilituibian.api.service;

import cn.meilituibian.api.domain.ShareInfo;
import cn.meilituibian.api.mapper.ShareInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShareInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShareInfoService.class);

    @Autowired
    private ShareInfoMapper shareInfoMapper;

    @Transactional
    public ShareInfo saveShareInfo(ShareInfo shareInfo) {
        ShareInfo currentInfo = getShareInfoByUserAndCategory(shareInfo);
        if (currentInfo != null) {
            return currentInfo;
        }
        int success = shareInfoMapper.saveShareInfo(shareInfo);
        if (success < 1) {
            LOGGER.error("userId={}, categoryId={}, url={}", shareInfo.getUserId(), shareInfo.getCategoryId(), shareInfo.getUrl());
            throw new RuntimeException("保存分享信息失败");
        }
        return shareInfo;
    }

    public ShareInfo getShareInfoByUserAndCategory(ShareInfo shareInfo ){
        ShareInfo myShareInfo = shareInfoMapper.getShareInfoByUserAndCategory(shareInfo.getUserId(), shareInfo.getCategoryId());
        return myShareInfo;
    }

    public List<ShareInfo> list(Long userId) {
        return shareInfoMapper.list(userId);
    }

    public ShareInfo findShareInfoById(Long id) {
        return shareInfoMapper.findShareInfoById(id);
    }
}
