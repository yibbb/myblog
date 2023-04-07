package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.dao.BlogDao;
import com.blog.dao.TypeDao;
import com.blog.entity.Blog;
import com.blog.entity.Comment;
import com.blog.entity.Type;
import com.blog.queryDto.*;
import com.blog.service.BlogService;
import com.blog.service.CommentService;
import com.blog.util.MarkdownUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl extends ServiceImpl<BlogDao, Blog> implements BlogService {
    @Resource
    private BlogDao blogDao;
    @Resource
    private TypeDao typeDao;
    @Resource
    private CommentService commentService;


    //获取所有博客
    @Override
    public List<BlogQuery> getallblog() {
        List<BlogQuery> list=blogDao.getallBlog();
        list.stream().map((item)->{
            Type type=typeDao.selectById(item.getTypeId());
            item.setType(type);
            return item;
        }).collect(Collectors.toList());
        return list;
    }

    //获取需要编辑的博客
    @Override
    public ShowBlog getShowBlog(long id) {
        return blogDao.getShowBlog(id);
    }

    //模糊查询博客
    @Override
    public List<BlogQuery> getBlogSearch(SearchBlog searchBlog) {
        List<BlogQuery> list=blogDao.getBlogSearch(searchBlog);
        list.stream().map((item)->{
            Type type=typeDao.selectById(item.getTypeId());
            item.setType(type);
            return item;
        }).collect(Collectors.toList());
        return list;
    }

    //分页查询博客列表,在首页输出
    @Override
    public List<FirstPageBlog> getAllFirstPageBlog() {
        List<FirstPageBlog> list=blogDao.getAllFirstPageBlog();
        return list;
    }

    //查询推荐文章
    @Override
    public List<RecommendBlog> getAllRecommendBlog() {
        List<RecommendBlog> list=blogDao.getAllRecommendBlog();
        return list;
    }

    //查询搜索博客
    @Override
    public List<FirstPageBlog> getSearchBlog(String query) {
        List<FirstPageBlog> list=blogDao.getSearchBlog(query);
        return  list;
    }

    //获取view总数
    @Override
    public int getViewtotal() {
        int blogViewTotal=blogDao.getViewTotal();
        return blogViewTotal;
    }

    //查询博客详情
    @Override
    public DetailedBlog getDetailedBlog(Long id) {
        DetailedBlog detailedBlog=blogDao.getDetailedBlog(id);
        if(detailedBlog==null){
            log.error("博客不存在");
        }
        String content=detailedBlog.getContent();
        detailedBlog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        //文章访问自增
        UpdateWrapper<Blog> wrapper=new UpdateWrapper<>();
        wrapper.eq("id",id);
        wrapper.setSql("views=views+1");
        this.update(wrapper);
        //文章评论更新
        QueryWrapper<Comment> wrapper1=new QueryWrapper<>();
        wrapper1.eq("blog_id",id);
        int count = commentService.count(wrapper1);
        wrapper=new UpdateWrapper<>();
        wrapper.eq("id",id);
        wrapper.setSql("comment_count="+count);
        this.update(wrapper);

        return detailedBlog;
    }

    //根据分类id获取博客
    @Override
    public List<FirstPageBlog> getBlogByTypeId(Long id) {
        List<FirstPageBlog> list=blogDao.getBlogByTypeId(id);
        return list;
    }


}
