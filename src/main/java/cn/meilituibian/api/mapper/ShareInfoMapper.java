package cn.meilituibian.api.mapper;

import cn.meilituibian.api.domain.ShareInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShareInfoMapper {
    int saveShareInfo(ShareInfo shareInfo);

    ShareInfo getShareInfoByUserAndCategory(@Param("userId") Long userId, @Param("categoryId") Long categoryId);

    List<ShareInfo> list(@Param("userId") long userId);

    ShareInfo findShareInfoById(@Param("id") Long id);
}
