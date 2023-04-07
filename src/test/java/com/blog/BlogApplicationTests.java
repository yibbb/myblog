package com.blog;

import com.blog.dao.BlogDao;
import com.blog.queryDto.BlogQuery;
import com.blog.service.BlogService;
import com.blog.util.MD5Utils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class BlogApplicationTests {

@Resource
    private BlogDao blogDao;
@Resource
    private BlogService service;
@Test
public void te(){
    service.update().setSql("views=views+1");
}

}
