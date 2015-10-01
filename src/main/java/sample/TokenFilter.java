package sample;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

public class TokenFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		Cookie tokenCookie = WebUtils.getCookie(request, "token");
		if(tokenCookie != null) {
			String token = tokenCookie.getValue();

			Authentication authentication = createAuthentication(token);
			setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}

	private void setAuthentication(Authentication authentication) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authentication);
		SecurityContextHolder.setContext(context);
	}

	private Authentication createAuthentication(String token) {
		// Validate the token and figure out the user information
		// You could use some sort of external API to determine this

		String username = token;
		List<GrantedAuthority> authorities =
				AuthorityUtils.createAuthorityList("ROLE_USER");
		return new PreAuthenticatedAuthenticationToken(username, "na", authorities);
	}
}

















