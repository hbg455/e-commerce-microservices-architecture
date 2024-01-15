package com.myshop.users.services.servicesImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myshop.users.dtos.AuthenticationRequest;
import com.myshop.users.dtos.AuthenticationResponse;
import com.myshop.users.dtos.UserDto;
import com.myshop.users.dtos.GetUserDto;
import com.myshop.users.entities.User;
import com.myshop.users.repositories.UserRepository;
import com.myshop.users.security.JwtService;
import com.myshop.users.services.IUsersService;
import com.myshop.users.token.Token;
import com.myshop.users.token.TokenRepository;
import com.myshop.users.token.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class IUserServiceImpl implements IUsersService {

    private final UserRepository userRepository ;

    private  final PasswordEncoder passwordEncoder;

    private  final AuthenticationManager authenticationManager ;

    private  final JwtService jwtService;

    private  final TokenRepository tokenRepository;



    @Override
    public GetUserDto addUser(UserDto userDto) {

        var user = User.builder()
                .firstname(userDto.firstname())
                .lastname(userDto.lastname())
                .username(userDto.username())
                .password(passwordEncoder.encode(userDto.password()))
                .email(userDto.email())
                .role(userDto.role())
                .build();
         userRepository.save(user);
        var GetuserDto = GetUserDto.builder()
                .username(user.getUsername())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
            return GetuserDto;
    }

    @Override
    public Optional<User> loadUserByUsername(String username) {
          return userRepository.findByUsername(username);
    }

    @Override
    public List<GetUserDto> listUsers() {

        var users = userRepository.findAll();
        List<GetUserDto> userDtos = users.stream()
                .map(user -> new GetUserDto(
                        user.getUsername(),
                        user.getFirstname(),
                        user.getLastname(),
                        user.getEmail(),
                        user.getRole()
                )).collect(Collectors.toList());

        return userDtos ;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request)  {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        var user = userRepository.findByUsername(request.username()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(jwtToken, user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();


    }
    private void revokeAllUserTokens(User user){
        var validUserToken = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserToken.isEmpty()) return;
        validUserToken.forEach(t ->{
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserToken);
    }

    private void saveUserToken(String jwtToken, User user) {
        var token = Token.builder()
                .token(jwtToken)
                .user(user)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }
    public void refreshToken(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            var user = this.userRepository.findByUsername(username)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(accessToken ,user );
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

}
