package telran.forum.service.filter;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import telran.forum.configuration.AccountConfiguration;
import telran.forum.configuration.AccountUserCredential;
import telran.forum.dao.UserAccountRepository;
import telran.forum.domain.UserAccount;

@Service
@Order(2)
public class ExpDateFilter implements Filter{
	
	@Autowired
	AccountConfiguration userConfiguration;

	@Autowired
	UserAccountRepository accountRepository;

	@Override
	public void doFilter(ServletRequest reqs, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) reqs;
		HttpServletResponse response = (HttpServletResponse) resp;
		int status = response.getStatus();
		if(status == 401 || status == 403) {
			chain.doFilter(request, response);
			return;
		}
		
		String path = request.getServletPath();
		if (!path.equalsIgnoreCase("/account/password") &&
				!path.equalsIgnoreCase("/account/register")) {
			String auth = request.getHeader("Authorization");
			AccountUserCredential userCredential = userConfiguration.tokenDecode(auth);
			UserAccount userAccount = accountRepository.findById(userCredential.getLogin()).orElse(null);
			if (LocalDateTime.now().isAfter(userAccount.getExpDate())) {
				response.sendError(403, "password expired");
			}
		}
		chain.doFilter(request, response);
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}
