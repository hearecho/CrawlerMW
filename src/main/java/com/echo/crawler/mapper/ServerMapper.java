package com.echo.crawler.mapper;

import com.echo.crawler.entity.ServerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ServerMapper {
    /**
     * 查询状态为status的服务器
     * @param status 状态码
     * @return 服务器数组
     */
    @Select("select * from servers where status = #{status}")
    List<ServerEntity> queryByStatus(int status);

    /**
     * 查询全部服务器
     * @return 服务器数组
     */
    @Select("select * from servers")
    List<ServerEntity> queryAll();
}
