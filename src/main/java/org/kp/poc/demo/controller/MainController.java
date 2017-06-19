/**
 * 
 */
package org.kp.poc.demo.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.kp.poc.demo.CurrentUser;
import org.kp.poc.demo.model.FeedMessage;
import org.kp.poc.demo.model.Role;
import org.kp.poc.demo.model.User;
import org.kp.poc.demo.repository.RoleRepository;
import org.kp.poc.demo.service.UserServiceImpl;
import org.kp.poc.demo.util.Constants;
import org.kp.poc.demo.util.RSSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Robert Tu 06/18/2017
 *
 */
@Controller
public class MainController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
		
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@RequestMapping("/")
    public String index(Model model, HttpSession session) {
		if(getAuthenticatedUser() != null) {
			model.addAttribute("user", getAuthenticatedUser());
		}
		return "main";
    }
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
    public String login(@RequestParam Optional<String> error, Model model, HttpSession session) {
		User loginUser = getAuthenticatedUser();
		if(loginUser != null && session.getAttribute("user") == null) {
			session.setAttribute("user", loginUser);
		}
		if(getAuthenticatedUser() != null) {
			model.addAttribute("authenticated_user", getAuthenticatedUser());
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.getName().equalsIgnoreCase("anonymousUser")) {
			return "login";
		}
		session.setAttribute(Constants.USER_KEY, auth);
		
		List<User> userList = (List<User>) userService.getAllUsers();
		logger.info("Found " + userList.size() + " User(s)");
		if(error.isPresent()) {
			model.addAttribute("error", error);
		}
		return "login";
    }
	
	@RequestMapping("/signup")
    public String signup(@RequestParam Map<String,String> params, Model model) {
		//Set<String> keys = params.keySet();
		//keys.stream().forEach(key -> System.out.println("*******************Found Key " + key + " with value: " + params.get(key)));
		model.addAttribute("edituser", new User());
		return "signup";
    }
	
	@RequestMapping(value="/signon", method=RequestMethod.POST)
    public String signon(@RequestParam Map<String,String> params, Model model) {
		Role role = roleRepository.findRoleByRoleName("user");
        
        User user = new User();
        user.setFirst_name(params.get("firstName"));
        user.setLast_name(params.get("lastName"));
        user.setEmail(params.get("email"));
        user.setUsername(params.get("userName"));
        String password = params.get("password");
        user.setPassword(password);
        user.setDisplayEmailFlag(true);
        user.setReviewer_flag(true);
        user.setEnabled(true);
        Collection<Role> collectionRole = new ArrayList<Role>();
        collectionRole.add(role) ;
        user.setRole(collectionRole);
        
        User savedUser = userService.create(user);
        
        model.addAttribute("user", savedUser);
		return "main";
    }
	
	@RequestMapping(value="/rss/{id}", method=RequestMethod.GET)
    public String getAllNews(@PathVariable String id, Model model, HttpSession session) {
		String url = "http://feeds.washingtonpost.com/rss/rss_post-nation";
		if(id.equals("wp")) {
			url = "http://feeds.washingtonpost.com/rss/rss_post-nation";
		} else if(id.equals("nyt")) {
			url = "http://rss.nytimes.com/services/xml/rss/nyt/Politics.xml";
		}

        List<FeedMessage> feedList = RSSUtil.getRSSFeed(url);
		model.addAttribute("news", feedList);
		model.addAttribute("selectedPage", 0);
        return "blognews";
    }
	
	protected String getAuthenticatedUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username
	    String userName = "";
	    if(!name.equalsIgnoreCase("anonymousUser")) {
	    	CurrentUser user = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    	User loginUser = user.getUser();
	    	userName = loginUser.getFirst_name() + " " + loginUser.getLast_name();
	    	logger.info("Found Login User Name: " + userName);
	    }
		return userName;
	}
	
	protected User getAuthenticatedUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username
	    if(!name.equalsIgnoreCase("anonymousUser")) {
	    	CurrentUser user = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    	return user.getUser();
	    }
		return null;
	}

}
