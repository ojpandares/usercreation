package com.opandares.user.domain.usecase.login;

import com.opandares.user.domain.exception.BusinessException;
import com.opandares.user.domain.exception.InvalidEmailException;
import com.opandares.user.domain.model.user.LoginRequest;
import com.opandares.user.domain.usescase.login.LoginUseCase;
import com.opandares.user.infrastructure.adapter.UserServiceAdapter;
import com.opandares.user.infrastructure.data.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class LoginUseCaseTest {

    private LoginUseCase loginUseCase;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserServiceAdapter userGateway;

    @Before
    public void setup() {
        this.loginUseCase = new LoginUseCase(userGateway);
    }

    @Test(expected = InvalidEmailException.class)
    public void userEmailIsNotValid_exceptionExpected() {
        LoginRequest request = new LoginRequest();
        request.setEmail("jose.perez");
        when(userGateway.validEmail(anyString())).thenCallRealMethod();
        loginUseCase.login(request);
    }

    @Test(expected = BusinessException.class)
    public void userPasswordIsEmpty_exceptionExpected() {
        LoginRequest request = new LoginRequest();
        request.setEmail("jose.perez@example.org");
        request.setPassword("");
        when(userGateway.validEmail(anyString())).thenCallRealMethod();
        loginUseCase.login(request);
    }

    @Test(expected = BusinessException.class)
    public void userPasswordIsNull_exceptionExpected() {
        LoginRequest request = new LoginRequest();
        request.setEmail("jose.perez@example.org");
        request.setPassword(null);
        when(userGateway.validEmail(anyString())).thenCallRealMethod();
        loginUseCase.login(request);
    }

    @Test()
    public void userDoesNotExist_exceptionExpected() {
        LoginRequest request = new LoginRequest();
        request.setEmail("jose.perez@example.com");
        request.setPassword("Kkkkk90");
        when(userGateway.validEmail("jose.perez@example.com")).thenCallRealMethod();
        loginUseCase.login(request);
        verify(userGateway, atMostOnce()).authenticate(anyString(), anyString());
    }
}