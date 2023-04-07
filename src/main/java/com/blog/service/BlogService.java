package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.entity.Blog;
import com.blog.queryDto.*;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BlogService extends IService<Blog> {

    List<BlogQuery> getallblog();

    ShowBlog getShowBlog(long id);

    List<BlogQuery> getBlogSearch(SearchBlog searchBlog);

    List<FirstPageBlog> getAllFirstPageBlog();

    List<RecommendBlog> getAllRecommendBlog();

    List<FirstPageBlog> getSearchBlog(String query);

    int getViewtotal();

    DetailedBlog getDetailedBlog(Long id);


    List<FirstPageBlog> getBlogByTypeId(Long id);
}
