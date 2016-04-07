# CMS
This was our final project for the Software Guild. It's a CMS (blog) system. 

###The Upshot
- My main focus on the project was buidling a custom image uploader:
    1. The image uploader takes a multipart file from html
    2. Creates a JQuery AJAX call with the file
    3. Throws to the FileUploadController REST endpoint
    4. Interacts with the FileUploadDao
    5. Interacts with MySQL, saving the byte[] as a long blob
    6. And reads from the "showImage/{id}" endpoint (which can be used as a regular &lt;img&gt; tag)
      
- In addition to the image uploader, I also 
  1. Stubbed out most of the Java classes from UML
  2. Built the UserDao.java to interact with the MySQL database
  3. Created the contact/mail system
  4. Integrated my file uploader into TinyMCE
  5. JUnit Testing
  
- Ongoing improvements:
  1. Finish the contact/mail system
  2. Clean up code/front-end
  3. Create different access levels (ROLE_ADMIN, ROLE_USER, etc.)
  4. Create single blog page (ie blog/showPost/{id})
  
###The Big Picture.
- This CMS system is written in JAVA, Javascriipt, JSON, HTML, CSS 
- It uses many libraries and frameworks, including: Spring, BootStrap, JQuery, TinyMCE
- The structure is a basic Spring MVC: Controllers / DAOs / Models / and includes Spring validation
- The controllers use Spring RequestMapping, ResponseBody, and many RequestParams, Models, and PathVariables
- The DAOs interact with the MySQL database, using JDBC 
- The security uses Spring security and BCrypt
- Tested using JUnit 
- Build dependencies managed via Maven

##Contributors
Christopher Glisch

David Egbert

Greg Svoboda

and Me --Ollie
