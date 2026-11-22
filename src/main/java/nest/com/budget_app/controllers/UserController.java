package nest.com.budget_app.controllers;

import lombok.RequiredArgsConstructor;
import nest.com.budget_app.controllers.dtos.responses.UserInfoResponse;
import nest.com.budget_app.models.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-info")
@RequiredArgsConstructor
@Validated
public class UserController {

  @GetMapping
  public UserInfoResponse getUserInfo(@AuthenticationPrincipal User user) {
    return new UserInfoResponse(
        user.getId(), user.getEmail(), user.getUsername(), user.getFirstName(), user.getLastName());
  }
}
