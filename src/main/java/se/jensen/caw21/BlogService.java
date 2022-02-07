package se.jensen.caw21;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

@Service
public class BlogService {

    //ArrayList<Author> authors;
    ArrayList<Blog> blogs;
    int blogId;
    private Logger logger;
    //int latestAuthorID;

    public BlogService() {
        blogs = new ArrayList<>();
        blogId = 0;
        logger=LoggerFactory.getLogger(BlogService.class);
        //authors=new ArrayList<>();
        //latestAuthorID=0;
    }

    public ArrayList<Blog> getBlog() {
        logger.info("A info message");
        logger.debug("A debug message");
        return blogs;

    }

    public Blog createBlog(Blog newBlog) {
        blogId++;
        newBlog.setId(blogId);
        blogs.add(newBlog);
        logger.info("Added a new blog : " + newBlog.getTitle());
        return newBlog;
        /*latestAuthorID++;
        newAuthor.setId(latestAuthorID);
        authors.add(newAuthor);
        return newAuthor;*/
    }

    public Blog getBlogById(int id) {
        for (int i = 0; i < blogs.size(); i++) {
            Blog currentBlog = blogs.get(i);
            if (currentBlog.getId() == id) {
                logger.info("Fetched blog : " + currentBlog.getTitle());
                return blogs.get(i);
            } else {
                logger.warn("Could not find blog with id : " + id);
            }
        }
        return null;
    }

    public Blog updateBlogById(int id, Blog updatedBlog) {
        for (int i = 0; i < blogs.size(); i++) {
            Blog currentBlog = blogs.get(i);
            if (currentBlog.getId() == id) {
                blogs.set(i, updatedBlog);
                logger.info("Fetched blog : " + currentBlog.getTitle());
                return blogs.get(i);
            } else {
                logger.warn("Could not find blog with id : " + id);
                System.out.println("Id does not exist : " + id);
            }
        }
        return new Blog();

    }

    public Blog deleteBlogById(int id) {
        for (int i = 0; i < blogs.size(); i++) {
            Blog currentBlog = blogs.get(i);
            if (currentBlog.getId() == id) {
                blogs.remove(i);
            } else {
                logger.error("This id does not exist : " + id);
                System.out.println("This id does not exist :" + id);

            }
        }
        return null;
    }
}