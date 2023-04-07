package com.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.entity.Blog;
import com.blog.queryDto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BlogDao extends BaseMapper<Blog> {
    List<BlogQuery> getallBlog();
    ShowBlog getShowBlog(@Param("id") Long id);
    List<BlogQuery> getBlogSearch(SearchBlog searchBlog);
    List<FirstPageBlog> getAllFirstPageBlog();
    List<RecommendBlog> getAllRecommendBlog();
    List<FirstPageBlog> getSearchBlog(String query);

    @Select("select coalesce(sum(views),0) from blog")
    int getViewTotal();

    DetailedBlog getDetailedBlog(Long id);

    List<FirstPageBlog> getBlogByTypeId(Long id);
}
