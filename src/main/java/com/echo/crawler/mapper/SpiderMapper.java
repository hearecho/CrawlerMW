package com.echo.crawler.mapper;

import com.echo.crawler.entity.SpiderEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SpiderMapper {

    @Select("select * from spiders")
    List<SpiderEntity> findAll();

    @Select("select * from spiders where name = #{name}")
    SpiderEntity findByName(String name);

    @Delete("delete from spiders where name = #{name}")
    boolean delete(String name);

    @Update("update spiders set docker = #{docker}, dockerName = #{dockerName}, dockerID=#{dockerID} where name = #{name}")
    boolean updateDocker(boolean docker, String dockerID, String dockerName, String name);

    @Update("update spiders set file=#{file}, filePath=#{filePath} where name=#{name}")
    boolean updateFile(boolean file, String filePath, String name);

    @Update("update spiders set name=#{spider.name}, file=#{spider.file}, filePath=#{spider.filePath}," +
            "#{spider.docker}, #{spider.dockerName},#{spider.dockerID}, #{spider.status} where name = #{name}")
    boolean updateSpider(SpiderEntity spider, String name);

    @Update("insert into spiders(name, file, filePath, docker, dockerName, dockerID, status, createAt) " +
            "values(#{spider.name}, #{spider.file}, #{spider.filePath}, #{spider.docker}, #{spider.dockerName}," +
            "#{spider.dockerID}, #{spider.status}, #{spider.createAt})")
    boolean add(SpiderEntity spider);

    @Update("update spiders set status = #{status} where name = #{name}")
    boolean updateStatus(boolean status, String name);
}
