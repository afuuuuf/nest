package nest.com.budget_app.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import nest.com.budget_app.dtos.TokenClaims;
import nest.com.budget_app.enums.TokenType;
import nest.com.budget_app.exceptions.ApiError;
import nest.com.budget_app.exceptions.UnauthorizedException;
import nest.com.budget_app.models.User;
import nest.com.budget_app.repositories.UserRepository;
import nest.com.budget_app.services.JwtService;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.json.JsonMapper;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String BEARER_HEADER = "Bearer ";

  private final JwtService jwtService;
  private final UserRepository userRepository;
  private final JsonMapper jsonMapper;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String authorization = request.getHeader(AUTHORIZATION_HEADER);

    if (authorization == null || !authorization.startsWith(BEARER_HEADER)) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = authorization.substring(7);

    try {
      TokenClaims payload = jwtService.decodeToken(token);

      if (!TokenType.ACCESS.getValue().toLowerCase().equals(payload.type())) {
        throw new UnauthorizedException("Access token required.");
      }

      User user =
          userRepository
              .findById(UUID.fromString(payload.sub()))
              .orElseThrow(() -> new UnauthorizedException("User no longer exists."));

      var authentication =
          new UsernamePasswordAuthenticationToken(
              user, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));

      SecurityContextHolder.getContext().setAuthentication(authentication);

    } catch (UnauthorizedException ex) {
      SecurityContextHolder.clearContext();
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write(jsonMapper.writeValueAsString(new ApiError(ex.getMessage())));
      return;
    }

    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    return request.getServletPath().startsWith("/auth/");
  }
}
