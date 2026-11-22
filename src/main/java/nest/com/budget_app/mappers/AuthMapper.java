package nest.com.budget_app.mappers;

import nest.com.budget_app.controllers.dtos.requests.AuthLoginRequest;
import nest.com.budget_app.controllers.dtos.requests.AuthRefreshRequest;
import nest.com.budget_app.controllers.dtos.requests.AuthRegisterRequest;
import nest.com.budget_app.dtos.AuthDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {

  AuthDto toDto(AuthRegisterRequest request);

  AuthDto toDto(AuthLoginRequest request);

  AuthDto toDto(AuthRefreshRequest request);
}
