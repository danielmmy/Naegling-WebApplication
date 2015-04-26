package br.com.naegling.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



/**
 * Controller to handle login. 
 * 
 * @author Marten Deinum
 * @author Koen Serneels
 *
 */
@Controller
public class LoginController extends AbstractController{

	public static final String ACCOUNT_ATTRIBUTE = "account";
	public static final String CLUSTERS_ATTRIBUTE = "clusters";
	public static final String REQUESTED_URL = "REQUESTED_URL";
	@RequestMapping("account/login")
	public ModelAndView authentication() {
		ModelAndView mov = new ModelAndView();
		mov.setViewName("account/login");
//		Account account=(Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		mov.addObject(ACCOUNT_ATTRIBUTE, account);
//		NaeglingUserDetails userDetails=SecurityContextSupport.getUserDetails();
//		mov.addObject(ACCOUNT_ATTRIBUTE, userDetails.getAccount());
		return mov;
	}
	
//	@RequestMapping("vnc")
//	public ModelAndView vncOpen() {
//		ModelAndView mov = new ModelAndView();
//		mov.setViewName("vnc");
////		Account account=(Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////		mov.addObject(ACCOUNT_ATTRIBUTE, account);
////		NaeglingUserDetails userDetails=SecurityContextSupport.getUserDetails();
////		mov.addObject(ACCOUNT_ATTRIBUTE, userDetails.getAccount());
//		return mov;
//	}

//	@Autowired
//	private AccountService accountService;
//
//	@RequestMapping(value = "/account/login",method = RequestMethod.GET)
//	public void login() {
//		//return "account/login";
//	}
//
//	@RequestMapping(value = "/account/login",method = RequestMethod.POST)
//	public String handleLogin(@RequestParam String username, @RequestParam String password,
//			RedirectAttributes redirect, HttpSession session) throws AuthenticationException {
//		//try {
//		Account account = this.accountService.login(username, password);
//		session.setAttribute(ACCOUNT_ATTRIBUTE, account);
//		String url = (String) session.getAttribute(REQUESTED_URL);
//		session.removeAttribute(REQUESTED_URL); // Remove the attribute
//		if (StringUtils.hasText(url) && !url.contains("login") && !url.contains("logout")) {
//			return "redirect:" + url;
//		} else {
//			return "redirect:/";
//		}
////
////		        } catch (AuthenticationException ae) {
////		            redirect.addFlashAttribute("exception", ae);
////		            return "redirect:/account/login";
////		        }
//	}
//
	@RequestMapping(value = "/index",method = RequestMethod.GET)
	public ModelAndView welcome(){
		ModelAndView mov = new ModelAndView();
		mov.setViewName("index");
		return mov;
	}

	/**
	 * Processes requests to home.
	 * @param model
	 * @return  The name of the account list view.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView showHome(Model model) {
		
		ModelAndView mov = new ModelAndView();
		mov.setViewName("index");
		return mov;
	}
	
	@RequestMapping(value = "websocket/vnc/{id}", method = RequestMethod.GET)
	public ModelAndView showWebsocket(@PathVariable("id") Long id,Model model) {
		
		ModelAndView mov = new ModelAndView();
		mov.setViewName("websocket/vnc");
		mov.addObject("nodeId", id);
		return mov;
	}

}
