package se.jensen.caw21;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(value="/api/v1/blog")
public class BlogController {

    private BlogService blogService;
    ArrayList<Blog> blogList;
    int blogId;
    private Logger logger;

    @Autowired
    public BlogController(BlogService blogService){   //We use this for Dependency injection
        //authorService=new AuthorService();
        blogService=new BlogService();
        this.blogService=blogService;
        //System.out.println(blogService);
    }

    public BlogController(){
        blogList=new ArrayList<Blog>();
        blogId=0;
        logger=LoggerFactory.getLogger(BlogController.class);
    }
@RequestMapping(value= "create", method = RequestMethod.POST)
   // public MyBlog createNewBlog(@RequestBody MyBlog blogg){

        public ResponseEntity<Blog> createNewBlog(@RequestBody Blog blogg){
            Blog newBlog=blogService.createBlog(blogg);
    System.out.println(newBlog.getTitle());
        if(blogg.getTitle()==""){
            return new ResponseEntity<Blog>( HttpStatus.BAD_REQUEST);
        }
        /*else {
            blogId++;
            blogg.setId(blogId);
            blogList.add(blogg);
            logger.info("Added a new blog : " + blogg.getTitle());
            //return blogg;*/
            return new ResponseEntity<Blog>(blogg,HttpStatus.CREATED);
        //}
}

@RequestMapping(value="list",method=RequestMethod.GET)
        //public ArrayList<Blog> listBlog (){
        public ResponseEntity <ArrayList<Blog>> listBlog(){
        ArrayList<Blog> newBlog=blogService.getBlog();
        //logger.info("A info message");
        //logger.debug("A debug message");
       // return blogList;
    //return blogService.getBlog();
    return new ResponseEntity<>(newBlog , HttpStatus.OK);

}

@RequestMapping(value="view/{id}",method = RequestMethod.GET)
    public ResponseEntity<Blog> viewBlog (@PathVariable ("id") int id){
        //Blog fetchBlogById=getBlogById(id);
    Blog fetchBlogById=blogService.getBlogById(id);
        if(fetchBlogById==null){
            //logger.warn("Could not find blog with id : " + id);
            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
        }
            //logger.info("Fetched blog : " + fetchBlogById.getTitle());
            return new ResponseEntity<Blog>(fetchBlogById,HttpStatus.OK);
    }

/*public Blog getBlogById(int id){
        for(int i=0;i<blogList.size();i++){
            Blog currentBlog=blogList.get(i);
            if(currentBlog.getId()==id){
                return blogList.get(i);
            }
        }
    return null;
}*/

@RequestMapping(value="update/{id}",method = RequestMethod.POST)
    //public Blog updateBlog(@PathVariable ("id") int id, @RequestBody Blog changeBlog){
        public ResponseEntity<Blog> updateBlog(@PathVariable ("id") int id, @RequestBody Blog changeBlog){
    System.out.println("Updating blog with id:" + id );
    System.out.println(changeBlog);
        //Blog blogToUpdate=getBlogById(id);
    Blog blogToUpdate=blogService.getBlogById(id);
    System.out.println(blogToUpdate);

    if(blogToUpdate==null){
        //logger.warn("Could not find blog with id : " + id);
        return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
    }
    //logger.info("Fetched blog : " + fetchBlogById.getTitle());
    //return new ResponseEntity<Blog>(blog,HttpStatus.OK);

        if(blogToUpdate.getTitle() != null){
            //System.out.println(blogToUpdate.getTitle());
            blogToUpdate.setTitle(changeBlog.getTitle());
            return new ResponseEntity<Blog>(blogToUpdate, HttpStatus.OK);
        }

        if(changeBlog.getContent() != null){
            //System.out.println("Old Content :" + changeBlog.getContent());
            blogToUpdate.setContent(changeBlog.getContent());
            return new ResponseEntity<Blog>(blogToUpdate, HttpStatus.OK);
            //System.out.println("New Content:" + changeBlog.getContent());

        }
        /*else{
            System.out.println("Old Content :" + changeBlog.getContent());
            System.out.println("New Content:" + changeBlog.getContent());
            System.out.println("Error occured");

        }*/

        if(changeBlog.getDate()!=null){
            blogToUpdate.setDate(changeBlog.getDate());
            return new ResponseEntity<Blog>(blogToUpdate, HttpStatus.OK);
        }
        blogService.updateBlogById(id,blogToUpdate);
        //return blogToUpdate;
        return new ResponseEntity<>(blogToUpdate,HttpStatus.OK);
}

/*public Blog updateBlogById(int id, Blog updatedBlog){
        for(int i=0;i<blogList.size();i++){
        Blog currentBlog=blogList.get(i);
        if(currentBlog.getId()==id){
            blogList.set(i,updatedBlog);
            return blogList.get(i);
        }
        else{
            System.out.println("Id does not exist : " + id);
        }
    }
        return new Blog();

}*/

/*@RequestMapping(value="delete/{id}",method = RequestMethod.DELETE)
    //public Blog deleteBlogByiId(@PathVariable("id") int id){
    public ResponseEntity<Blog> deleteBlogByiId(@PathVariable("id") int id){
    for(int i=0;i<blogList.size();i++){
        Blog currentBlog=blogList.get(i);
        if(currentBlog.getId()==id){
            blogList.remove(i);
            return new ResponseEntity<Blog>(currentBlog,HttpStatus.OK);
        }
        else{

            System.out.println("This id does not exist :" + id);
            logger.error("This id does not exist : " + id);
            return new ResponseEntity<Blog>(currentBlog,HttpStatus.NOT_FOUND);
        }
    }
    return null;

}*/
@RequestMapping(value="delete/{id}",method = RequestMethod.DELETE)
//public Blog deleteBlogByiId(@PathVariable("id") int id){
public ResponseEntity<Blog> deleteBlogByiId(@PathVariable("id") int id) {
    Blog fetchBlogById=blogService.getBlogById(id);
    Blog blog=blogService.deleteBlogById(id);
    if(fetchBlogById==null){
        //logger.warn("Could not find blog with id : " + id);
        return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
    }
    //logger.info("Fetched blog : " + fetchBlogById.getTitle());
    return new ResponseEntity<Blog>(blog,HttpStatus.OK);
}



@RequestMapping(value="clearBlog",method = RequestMethod.DELETE)
    public void clearBlogs(){
    blogList.clear();
    System.out.println("Cleared the list of Blog");
}
}
