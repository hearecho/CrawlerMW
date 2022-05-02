package com.echo.crawler.mapper;

import com.echo.crawler.entity.ServerEntity;
import org.apache.ibatis.annotations.*;

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

    @Select("select * from servers where ip = #{ip}")
    ServerEntity findByIp(String ip);
    /**
     * 查询全部服务器
     * @return 服务器数组
     */
    @Select("select * from servers")
    List<ServerEntity> queryAll();

    @Insert("insert into servers(ip, user, pwd, status, port) values(#{ip}, #{user}, #{pwd}, #{status}, #{port})")
    boolean insert(String ip, String user, String pwd, int status, String port);

    @Delete("delete from servers where ip = #{ip}")
    boolean delete(String ip);

    @Update("update servers set status = #{status} where ip = #{ip}")
    boolean updateStatus(String ip, int status);
}
